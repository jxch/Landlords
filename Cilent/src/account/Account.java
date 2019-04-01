package account;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Account {
    private String username;
    private String password;
    private String email;
    private static Socket socket;
    private static Account account;
    private static int integral;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String email) {
        this(username, password);
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        Account.socket = socket;
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        Account.account = account;
    }

    public static int getIntegral() {
        return integral;
    }

    public static void setIntegral(int integral) {
        Account.integral = integral;
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        Account.objectOutputStream = objectOutputStream;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public static void setObjectInputStream(ObjectInputStream objectInputStream) {
        Account.objectInputStream = objectInputStream;
    }
}
