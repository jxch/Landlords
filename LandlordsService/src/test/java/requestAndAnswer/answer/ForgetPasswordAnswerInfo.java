package requestAndAnswer.answer;

import requestAndAnswer.answer.command.Answer;
import requestAndAnswer.answer.command.ForgetPasswordAnswer;

public class ForgetPasswordAnswerInfo extends AnswerInfo {
    private ForgetPasswordAnswer forgetPasswordAnswer;
    private String password;

    public ForgetPasswordAnswerInfo() {
        super(Answer.forgetPasswordAnswer);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForgetPasswordAnswer getForgetPasswordAnswer() {
        return forgetPasswordAnswer;
    }

    public void setForgetPasswordAnswer(ForgetPasswordAnswer forgetPasswordAnswer) {
        this.forgetPasswordAnswer = forgetPasswordAnswer;
    }
}
