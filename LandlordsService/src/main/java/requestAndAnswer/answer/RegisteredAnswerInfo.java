package requestAndAnswer.answer;

import requestAndAnswer.answer.command.Answer;
import requestAndAnswer.answer.command.RegisteredAnswer;

public class RegisteredAnswerInfo extends AnswerInfo {
    private RegisteredAnswer registeredAnswer;
    private int integral;

    public RegisteredAnswerInfo() {
        super(Answer.registeredAnswer);
    }

    public RegisteredAnswer getRegisteredAnswer() {
        return registeredAnswer;
    }

    public void setRegisteredAnswer(RegisteredAnswer registeredAnswer) {
        this.registeredAnswer = registeredAnswer;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
