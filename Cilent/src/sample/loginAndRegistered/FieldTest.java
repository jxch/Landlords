package sample.loginAndRegistered;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.HashSet;

public enum FieldTest {
    usernameWrong, //用户名格式错误
    passwordWrong, //密码格式错误
    emailWrong, //电子邮件格式错误
    socketWrong, //套接字格式错误
    emptyValue, //含有空值
    success; //成功

    public static void setFieldTestMessage(Label fieldTestMessage, String message, Color color) {
        fieldTestMessage.setText(message);
        fieldTestMessage.setTextFill(color);
    }

    public static void setFieldTestMessage(Label fieldTestMessage, HashSet<FieldTest> fieldTests, Color color){
        StringBuilder message = new StringBuilder();
        for (FieldTest ft : fieldTests) {
            message.append(ft).append(";");
        }

        setFieldTestMessage(fieldTestMessage, message.toString(), color);
    }
}
