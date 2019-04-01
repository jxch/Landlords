package sample.loginAndRegistered;

import account.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import requestAndAnswer.answer.ForgetPasswordAnswerInfo;
import requestAndAnswer.answer.LoginAnswerInfo;
import requestAndAnswer.answer.command.ForgetPasswordAnswer;
import requestAndAnswer.answer.command.LoginAnswer;
import requestAndAnswer.request.ForgetPasswordRequestInfo;
import requestAndAnswer.request.LoginRequestInfo;
import sample.Main;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;

import static sample.loginAndRegistered.FieldTest.setFieldTestMessage;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField socket;
    @FXML
    private Label fieldTestMessage;

    private HashSet<FieldTest> initialization() {
        HashSet<FieldTest> fieldTests = new HashSet<>();

        String username = this.username.getText().trim();
        String password = this.password.getText().trim();
        if (username.isEmpty() || password.isEmpty())
            fieldTests.add(FieldTest.emptyValue);
        if (username.contains(":") || username.length() > 10)
            fieldTests.add(FieldTest.usernameWrong);
        if (password.contains(":") || password.length() > 10)
            fieldTests.add(FieldTest.passwordWrong);

        try {
            String[] hostPort = this.socket.getText().trim().split(":");
            Account.setSocket(new Socket(hostPort[0], Integer.parseInt(hostPort[1])));
        } catch (Exception e) {
            fieldTests.add(FieldTest.socketWrong);
        }

        if (fieldTests.isEmpty()) {
            Account.setAccount(new Account(username, password));
        }

        return fieldTests;
    }

    public void login() {
        HashSet<FieldTest> fieldTests = initialization();

        if (fieldTests.isEmpty()) {
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(Account.getSocket().getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(Account.getSocket().getInputStream());
                Account.setObjectInputStream(inputStream);
                Account.setObjectOutputStream(outputStream);

                LoginRequestInfo loginRequestInfo = new LoginRequestInfo();
                loginRequestInfo.setLoginInfo(username.getText().trim(), password.getText().trim());

                outputStream.writeObject(loginRequestInfo);
                LoginAnswerInfo loginAnswerInfo = (LoginAnswerInfo) inputStream.readObject();

                loginAnswerProcess(loginAnswerInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setFieldTestMessage(fieldTestMessage, fieldTests, Color.RED);
        }
    }

    private void loginAnswerProcess(LoginAnswerInfo loginAnswerInfo) throws Exception {
        LoginAnswer loginAnswer = loginAnswerInfo.getLoginAnswer();

        switch (loginAnswer) {
            case noSuchUser:
                setFieldTestMessage(fieldTestMessage, LoginAnswer.noSuchUser.toString(), Color.RED);
                break;
            case wrongPassword:
                setFieldTestMessage(fieldTestMessage, LoginAnswer.wrongPassword.toString(), Color.RED);
                break;
            case fail:
                setFieldTestMessage(fieldTestMessage, LoginAnswer.fail.toString(), Color.RED);
                break;
            case success:
                setFieldTestMessage(fieldTestMessage, LoginAnswer.success.toString(), Color.GREEN);
                Account.setIntegral(loginAnswerInfo.getIntegral());
                Main.nextStage(fieldTestMessage, "rooms/rooms.fxml", "rooms");
                break;
            case userHasLoggedIn:
                setFieldTestMessage(fieldTestMessage, LoginAnswer.userHasLoggedIn.toString(), Color.RED);
                break;
            default:
                throw new Exception("未知错误");
        }
    }

    public void forgetPassword() {
        if (username.getText().trim().isEmpty()) {
            setFieldTestMessage(fieldTestMessage, "请输入用户名", Color.RED);
        } else {
            password.setText("***");
            HashSet<FieldTest> fieldTests = initialization();

            if (fieldTests.isEmpty()) {
                try (
                        ObjectInputStream inputStream = new ObjectInputStream(Account.getSocket().getInputStream());
                        ObjectOutputStream outputStream = new ObjectOutputStream(Account.getSocket().getOutputStream())
                ) {
                    ForgetPasswordRequestInfo forgetPasswordRequestInfo = new ForgetPasswordRequestInfo();
                    forgetPasswordRequestInfo.setUsername(username.getText().trim());

                    outputStream.writeObject(forgetPasswordRequestInfo);
                    ForgetPasswordAnswerInfo forgetPasswordAnswerInfo = (ForgetPasswordAnswerInfo) inputStream.readObject();

                    forgetPasswordProcess(forgetPasswordAnswerInfo);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                setFieldTestMessage(fieldTestMessage, fieldTests, Color.RED);
            }
        }
    }

    private void forgetPasswordProcess(ForgetPasswordAnswerInfo forgetPasswordAnswerInfo) {
        ForgetPasswordAnswer forgetPasswordAnswer = forgetPasswordAnswerInfo.getForgetPasswordAnswer();

        switch (forgetPasswordAnswer) {
            case noSuchUser:
                setFieldTestMessage(fieldTestMessage, ForgetPasswordAnswer.noSuchUser.toString(), Color.RED);
                break;
            case success:
                setFieldTestMessage(fieldTestMessage, forgetPasswordAnswerInfo.getPassword(), Color.GREEN);
                break;
            default:
        }
    }

    public void registration() throws IOException {
        Main.nextStage(fieldTestMessage, "loginAndRegistered/registration.fxml", "registration");
    }
}
