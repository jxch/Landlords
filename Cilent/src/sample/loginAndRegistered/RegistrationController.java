package sample.loginAndRegistered;

import account.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import requestAndAnswer.answer.RegisteredAnswerInfo;
import requestAndAnswer.answer.command.RegisteredAnswer;
import requestAndAnswer.request.RegisteredRequestInfo;
import sample.Main;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;

import static sample.loginAndRegistered.FieldTest.setFieldTestMessage;

public class RegistrationController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField socket;
    @FXML
    private Label fieldTestMessage;

    private HashSet<FieldTest> initialization() {
        // FIXME: 2018/1/7 多余代码
        HashSet<FieldTest> fieldTests = new HashSet<>();

        String username = this.username.getText().trim();
        String password = this.password.getText().trim();
        String email = this.email.getText().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty())
            fieldTests.add(FieldTest.emptyValue);
        if (username.contains(":") || username.length() > 10)
            fieldTests.add(FieldTest.usernameWrong);
        if (password.contains(":") || password.length() > 10)
            fieldTests.add(FieldTest.passwordWrong);
        if (email.contains(":") || !email.contains("@") || email.length() > 10)
            fieldTests.add(FieldTest.emailWrong);

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

    public void registrationAndLogin() {
        HashSet<FieldTest> fieldTests = initialization();
        System.out.println("22");
        if (fieldTests.isEmpty()) {
            System.out.println("11");
            try {
                ObjectOutputStream outputStream = new ObjectOutputStream(Account.getSocket().getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(Account.getSocket().getInputStream());
                Account.setObjectInputStream(inputStream);
                Account.setObjectOutputStream(outputStream);

                RegisteredRequestInfo registeredRequestInfo = new RegisteredRequestInfo();
                registeredRequestInfo.setRegisteredInfo(username.getText().trim(), password.getText().trim(), email.getText().trim());

                outputStream.writeObject(registeredRequestInfo);
                RegisteredAnswerInfo registeredAnswerInfo = (RegisteredAnswerInfo) inputStream.readObject();

                registrationAnswerProcess(registeredAnswerInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setFieldTestMessage(fieldTestMessage, fieldTests, Color.RED);
        }
    }

    private void registrationAnswerProcess(RegisteredAnswerInfo registeredAnswerInfo) throws Exception {
        RegisteredAnswer registeredAnswer = registeredAnswerInfo.getRegisteredAnswer();

        switch (registeredAnswer) {
            case userAlreadyExists:
                setFieldTestMessage(fieldTestMessage, RegisteredAnswer.userAlreadyExists.toString(), Color.RED);
                break;
            case success:
                setFieldTestMessage(fieldTestMessage, RegisteredAnswer.success.toString(), Color.GREEN);
                Account.setIntegral(registeredAnswerInfo.getIntegral());
                Main.nextStage(fieldTestMessage, "rooms/rooms.fxml", "rooms");
                break;
            case fail:
                setFieldTestMessage(fieldTestMessage, RegisteredAnswer.fail.toString(), Color.RED);
                break;
            default:
                throw new Exception("未知错误");
        }
    }
}
