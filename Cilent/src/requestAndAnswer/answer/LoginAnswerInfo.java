package requestAndAnswer.answer;

import requestAndAnswer.answer.command.Answer;
import requestAndAnswer.answer.command.LoginAnswer;

public class LoginAnswerInfo extends AnswerInfo {
    private LoginAnswer loginAnswer;
    private int integral;

    public LoginAnswerInfo() {
        super(Answer.loginAnswer);
    }

    public LoginAnswer getLoginAnswer() {
        return loginAnswer;
    }

    public void setLoginAnswer(LoginAnswer loginAnswer) {
        this.loginAnswer = loginAnswer;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
