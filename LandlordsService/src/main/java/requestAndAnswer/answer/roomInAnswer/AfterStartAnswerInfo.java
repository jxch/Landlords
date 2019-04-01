package requestAndAnswer.answer.roomInAnswer;

import requestAndAnswer.answer.command.RoomInAnswer;
import requestAndAnswer.answer.command.roomInAnswer.AfterStartAnswer;

public abstract class AfterStartAnswerInfo extends ServiceRoomInAnswerInfo {
    private AfterStartAnswer afterStartAnswer;

    public AfterStartAnswerInfo(AfterStartAnswer afterStartAnswer) {
        super(RoomInAnswer.afterStartAnswer);
        this.afterStartAnswer = afterStartAnswer;
    }

    public AfterStartAnswer getAfterStartAnswer() {
        return afterStartAnswer;
    }

    public void setAfterStartAnswer(AfterStartAnswer afterStartAnswer) {
        this.afterStartAnswer = afterStartAnswer;
    }
}
