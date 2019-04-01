package thread;

import account.Player;
import gameService.GameService;
import requestAndAnswer.answer.command.roomInAnswer.BeforeStartAnswer;
import requestAndAnswer.answer.roomInAnswer.*;
import requestAndAnswer.poke.Card;
import requestAndAnswer.poke.Combination;
import requestAndAnswer.poke.Deck;
import requestAndAnswer.poke.HandCards;
import requestAndAnswer.request.roomInRequest.CardsRequestInfo;
import requestAndAnswer.request.roomInRequest.LandlordRequestInfo;
import requestAndAnswer.roomIn.PlayerInfo;
import requestAndAnswer.roomIn.RoomInfo;
import requestAndAnswer.roomIn.SeatNum;
import thread.requestAndAnswer.request.ClientToRoomRequest;

import java.io.IOException;
import java.util.*;

public class RoomThread implements Runnable {
    private Stack<ClientToRoomRequest> clientToRoomRequests = new Stack<>();
    private static final int maxCount = 3;
    private String roomName;
    private int count = 0;
    private HashMap<ClientThread, Player> players = new HashMap<>();
    private HashMap<ClientThread, Boolean> ready = new HashMap<>();
    private HashMap<SeatNum, ClientThread> seatNumClientThreadHashMap = new HashMap<>();
    private int start = 0;
    private Deck deck = new Deck();
    private SeatNum currentPlayer = SeatNum.one;
    private SeatNum landlord;


    RoomThread(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void run() {

        while (count > 0 && count <= 3) {
            if (count == 3 && start == 3) {
                start();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(Player player) {
        if (count < maxCount) {
            players.put(player.getClientThread(), player);
            count++;
        }

        setSeatNum();
        roomStateSynchronize();
    }

    private void roomStateSynchronize() {
        RoomStateAnswerInfo roomStateAnswerInfo = new RoomStateAnswerInfo(BeforeStartAnswer.someoneEnters);
        RoomInfoAnswerInfo roomInfoAnswerInfo = new RoomInfoAnswerInfo();
        RoomInfo roomInfo = new RoomInfo();
        HashMap<String, PlayerInfo> playersInfo = new HashMap<>();

        for (HashMap.Entry<ClientThread, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            PlayerInfo playerInfo = new PlayerInfo(player.getUsername(), player.getIntegral(), player.getSeatNum());
            playersInfo.put(entry.getKey().getAccount().getUsername(), playerInfo);
        }

        roomInfo.setPlayersInfo(playersInfo);
        roomInfoAnswerInfo.setRoomInfo(roomInfo);
        roomStateAnswerInfo.setRoomInfoAnswerInfo(roomInfoAnswerInfo);

        for (HashMap.Entry<ClientThread, Player> entry : players.entrySet()) {
            try {
                if (ready.containsKey(entry.getKey()))
                    entry.getKey().getOutputStream().writeObject(roomStateAnswerInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void ready(ClientThread clientThread) {
        ready.put(clientThread, true);
        start++;

        roomStateSynchronize();
    }

    private void start() {
        deck.shuffle();

        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            Card[] cards = deck.deal(Deck.cardsNumber / maxCount - 1);
            entry.getValue().setHandCards(new HandCards(cards));
        }

        deal();

        System.err.println("deal - RemainingCards :: " + deck.getRemainingCards());

        landlord = landlord();
        currentPlayer = landlord;
        landlordSynchronize(true, landlord);

        System.err.println("landlord handCards :: " + players.get(seatNumClientThreadHashMap.get(landlord)).getHandCards().getHandCards().size());

        play();
    }

    private void play() {
        boolean win = false;
        ClientThread winPlayer = null;
        while (!win) {
            if (!clientToRoomRequests.empty()) {
                ClientToRoomRequest clientToRoomRequest = clientToRoomRequests.pop();
                if (clientToRoomRequest.getClientRequest() == ClientToRoomRequest.ClientRequest.play) {
                    ClientThread clientThread = clientToRoomRequest.getClientThread();
                    SeatNum seatNum = players.get(clientThread).getSeatNum();
                    CardsRequestInfo cardsRequestInfo = clientToRoomRequest.getCardsRequestInfo();
                    Combination combination = cardsRequestInfo.getCombination();
                    Card[] cards = cardsRequestInfo.getCards();

                    System.err.print(clientThread.getAccount().getUsername() + "={ card :: " + ((cards == null) ? "null" : cards.length));

                    if (cardsRequestInfo.isPlay()) {
                        playerSynchronize(cards, seatNum, combination);
                        players.get(clientThread).getHandCards().play(cards);

                        System.err.println(". han :: " + players.get(clientThread).getHandCards().getHandCards().size() + " }");

                        if (players.get(clientThread).getHandCards().getHandCards().size() == 0) {
                            win = true;
                            winPlayer = clientThread;
                        }
                    } else {
                        playerSynchronize(seatNum);
                    }

                    currentPlayer = seatNum;
                }
            }
        }

        win(winPlayer);
    }

    private void win(ClientThread clientThread) {
        SeatNum seatNum = players.get(clientThread).getSeatNum();

        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            WinAnswerInfo winAnswerInfo = new WinAnswerInfo();
            winAnswerInfo.setSeatNum(seatNum);
            winAnswerInfo.setUsername(entry.getValue().getUsername());

            if (seatNum == landlord) {
                winAnswerInfo.setWin(entry.getValue().getSeatNum() == landlord);
            } else {
                winAnswerInfo.setWin(entry.getValue().getSeatNum() != landlord);
            }

            try {
                entry.getKey().getOutputStream().writeObject(winAnswerInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        clear();
    }

    private void clear() {
        start = 0;
        ready.clear();
        deck.reset();

        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            entry.getValue().reset();
        }

        System.err.println("reset - deck num :: " + deck.getRemainingCards());
    }

    private void playerSynchronize(SeatNum seatNum) {
        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            PlayersStateAnswerInfo playersStateAnswerInfo = new PlayersStateAnswerInfo();
            playersStateAnswerInfo.setPlay(false);
            playersStateAnswerInfo.setSeatNum(seatNum);

            try {
                entry.getKey().getOutputStream().writeObject(playersStateAnswerInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void playerSynchronize(Card[] cards, SeatNum seatNum, Combination combination) {
        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            PlayersStateAnswerInfo playersStateAnswerInfo = new PlayersStateAnswerInfo();
            playersStateAnswerInfo.setSeatNum(seatNum);
            playersStateAnswerInfo.setCards(cards);
            playersStateAnswerInfo.setDeal(false);
            playersStateAnswerInfo.setPlay(true);
            playersStateAnswerInfo.setCombination(combination);

            try {
                entry.getKey().getOutputStream().writeObject(playersStateAnswerInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deal() {
        PlayersStateAnswerInfo playersStateAnswerInfo = new PlayersStateAnswerInfo();
        playersStateAnswerInfo.setDeal(true);

        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            HandCards handCards = entry.getValue().getHandCards();
            handCards.sort();
            playersStateAnswerInfo.setCards(handCards.getHandCards().toArray(new Card[handCards.getHandCards().size()]));
            playersStateAnswerInfo.setSeatNum(entry.getValue().getSeatNum());

            try {
                entry.getKey().getOutputStream().writeObject(playersStateAnswerInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private SeatNum landlord() {
        SeatNum seatNum = SeatNum.one;
        try {
            int i;
            for (i = 0; i < maxCount; i++) {
                seatNum = SeatNum.values()[i];
                landlordSynchronize(false, seatNum);

                while (clientToRoomRequests.empty()) {
                    Thread.sleep(100);
                }

                ClientToRoomRequest clientToRoomRequest = clientToRoomRequests.pop();
                if (clientToRoomRequest.getClientRequest() == ClientToRoomRequest.ClientRequest.landlord) {
                    LandlordRequestInfo landlordRequestInfo = clientToRoomRequest.getLandlordRequestInfo();
                    if (landlordRequestInfo.isLandlord()) {
                        break;
                    }
                }

            }

            if (i == maxCount) {
                seatNum = SeatNum.one;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return seatNum;
    }

    private void landlordSynchronize(boolean start, SeatNum seatNum) {
        Card[] cards = null;
        if (start) {
            cards = deck.deal(maxCount);

            System.err.println("landlord cards :: " + cards.length);

            players.get(seatNumClientThreadHashMap.get(landlord)).getHandCards().addCards(cards);

            System.err.println("landlord hanCards :: " + players.get(seatNumClientThreadHashMap.get(landlord)).getHandCards().getHandCards().size());
        }

        for (Map.Entry<ClientThread, Player> entry : players.entrySet()) {
            LandlordAnswerInfo landlordAnswerInfo = new LandlordAnswerInfo();
            landlordAnswerInfo.setSeatNum(seatNum);
            landlordAnswerInfo.setStart(start);
            landlordAnswerInfo.setCards(cards);

            try {
                entry.getKey().getOutputStream().writeObject(landlordAnswerInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelPlayer(ClientThread clientThread) {
        players.remove(clientThread);
        if (ready.containsKey(clientThread)) {
            ready.remove(clientThread);
            start--;
        }

        if (--count > 0) {
            setSeatNum();
            roomStateSynchronize();
        } else {
            RoomsThread.getRoomThreads().remove(roomName);
            GameService.getThreadHashMap().remove(this.toString());
        }
    }

    private void setSeatNum() {
        Iterator<Map.Entry<ClientThread, Player>> iterator = players.entrySet().iterator();
        int i = 0;

        while (iterator.hasNext()) {
            Map.Entry<ClientThread, Player> entry = iterator.next();
            seatNumClientThreadHashMap.put(SeatNum.values()[i], entry.getKey());
            entry.getValue().setSeatNum(SeatNum.values()[i++]);
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCount() {
        return count;
    }

    public HashMap<ClientThread, Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "RoomThread{" +
                "roomName='" + roomName + '\'' +
                ", count=" + count +
                '}';
    }

    public Stack<ClientToRoomRequest> getClientToRoomRequests() {
        return clientToRoomRequests;
    }
}
