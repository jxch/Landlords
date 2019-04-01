package thread.requestAndAnswer.request;

import requestAndAnswer.poke.Card;
import requestAndAnswer.request.roomInRequest.CardsRequestInfo;
import requestAndAnswer.request.roomInRequest.LandlordRequestInfo;
import thread.ClientThread;

public class ClientToRoomRequest {
    public enum ClientRequest{
        landlord,
        play,
    }

    private ClientThread clientThread;
    private ClientRequest clientRequest;
    private Card[] cards;
    private LandlordRequestInfo landlordRequestInfo;
    private CardsRequestInfo cardsRequestInfo;

    public ClientToRoomRequest(ClientRequest clientRequest){
        this.clientRequest = clientRequest;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public ClientRequest getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(ClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public LandlordRequestInfo getLandlordRequestInfo() {
        return landlordRequestInfo;
    }

    public void setLandlordRequestInfo(LandlordRequestInfo landlordRequestInfo) {
        this.landlordRequestInfo = landlordRequestInfo;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public CardsRequestInfo getCardsRequestInfo() {
        return cardsRequestInfo;
    }

    public void setCardsRequestInfo(CardsRequestInfo cardsRequestInfo) {
        this.cardsRequestInfo = cardsRequestInfo;
    }
}
