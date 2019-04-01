package thread;

import account.Account;
import gameService.GameService;
import mysqlTools.MysqlConnection;
import requestAndAnswer.answer.command.LoginAnswer;
import requestAndAnswer.answer.command.RegisteredAnswer;
import requestAndAnswer.request.command.Request;
import requestAndAnswer.request.RequestInfo;
import thread.requestAndAnswer.answer.RoomsToClientAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.Stack;

import static thread.RequestProcess.processingTheRequest;

public class ClientThread implements Runnable {
    private Stack<RoomsToClientAnswer> roomsToClientAnswers = new Stack<>();
    private boolean listening = true;
    private Account account = new Account();
    private Socket socket;
    private ObjectOutputStream outputStream;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            this.outputStream = outputStream;

            while (listening) {
                RequestInfo requestInfo = (RequestInfo) inputStream.readObject();
                Request request = requestInfo.getRequest();

                System.out.println(request.toString());
                processingTheRequest(request, requestInfo, outputStream, this);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasLoggedIn(String name) {
        return GameService.getClientThreadHashMap().containsKey(name);
    }

    public LoginAnswer login(String name, String password) {
        try {
            Statement st = MysqlConnection.mysqlStatement("userInfo", "root", "");
            String sql = "select * from user where name = '" + name + "';";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                String pw = rs.getString("password");
                if (!pw.equals(password)) {
                    return LoginAnswer.wrongPassword;
                }
            } else {
                return LoginAnswer.noSuchUser;
            }

            sql = "SELECT * FROM integral WHERE name = '" + name + "'";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                account.setAccount(name, rs.getInt("integral"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return LoginAnswer.success;
    }

    public RegisteredAnswer registration(String name, String password, String email) {
        try {
            Statement st = MysqlConnection.mysqlStatement("userinfo", "root", "");

            String sql = "select * from user where name = '" + name + "';";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return RegisteredAnswer.userAlreadyExists;
            }

            sql = "INSERT INTO user (name, password, email) VALUES ('" + name + "','" + password + "','" + email + "');";
            int result = st.executeUpdate(sql);
            if (result <= 0) {
                return RegisteredAnswer.fail;
            }

            sql = "INSERT INTO integral (name, integral) VALUES ('" + name + "'," + Account.normalIntegral + ")";
            st.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        account.setAccount(name, Account.normalIntegral);

        return RegisteredAnswer.success;
    }

    public String getPassword(String name){
        try {
            Statement st = MysqlConnection.mysqlStatement("userinfo", "root", "");
            String sql = "select password from user where name = '" + name + "';";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next())
                return rs.getString("password");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public Account getAccount() {
        return account;
    }

    public Stack<RoomsToClientAnswer> getRoomsToClientAnswers() {
        return roomsToClientAnswers;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public String toString() {
        return "ClientThread{" +
                "username=" + account.getUsername() +
                '}';
    }
}
