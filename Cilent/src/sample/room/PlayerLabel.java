package sample.room;

import javafx.scene.control.Label;

public class PlayerLabel{
    Label username;
    Label integral;
    Label cardsRemaining;
    Label timeRemaining;

    public PlayerLabel(Label username, Label integral, Label cardsRemaining, Label timeRemaining){
        this.username = username;
        this.integral = integral;
        this.cardsRemaining = cardsRemaining;
        this.timeRemaining = timeRemaining;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public Label getIntegral() {
        return integral;
    }

    public void setIntegral(Label integral) {
        this.integral = integral;
    }

    public Label getCardsRemaining() {
        return cardsRemaining;
    }

    public void setCardsRemaining(Label cardsRemaining) {
        this.cardsRemaining = cardsRemaining;
    }

    public Label getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Label timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
