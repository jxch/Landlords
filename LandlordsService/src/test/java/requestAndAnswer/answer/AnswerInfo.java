package requestAndAnswer.answer;

import requestAndAnswer.answer.command.Answer;

import java.io.Serializable;

public class AnswerInfo implements Serializable{
    private Answer answer;

    public AnswerInfo(Answer answer){
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
