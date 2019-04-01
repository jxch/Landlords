package gameService;

import thread.ClientThread;
import thread.RoomThread;
import thread.RoomsThread;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class GameService {
    private static HashMap<String, Thread> threadHashMap = new HashMap<>();
    private static HashMap<String, ClientThread> clientThreadHashMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServiceCommunicator serviceCommunicator = new ServiceCommunicator(8189);
        Thread clientThreads = new Thread(serviceCommunicator);
        clientThreads.start();

        RoomsThread roomsThread = new RoomsThread();
        Thread rooms = new Thread(roomsThread);
        rooms.start();

        threadHashMap.put(serviceCommunicator.toString(), clientThreads);
        threadHashMap.put(roomsThread.toString(), rooms);

        listening();
    }

    private static void listening() {
        Scanner input = new Scanner(System.in);
        boolean listening = true;

        while (listening) {
            String[] instructions = input.nextLine().trim().split(" ");

            if (!instructions[0].isEmpty()) {
                try {
                    Command instruction = Command.valueOf(instructions[0]);
                    // FIXME: 2018/1/8
                    switch (instruction) {
                        case exit:
//                        listening = false;
//                        System.out.println("ds");
                            break;
                        case roomsList:
                            for (HashMap.Entry<String, RoomThread> entry : RoomsThread.getRoomThreads().entrySet()) {
                                System.out.println(entry.getKey() + "::" + entry.getValue().toString());
                            }
                            break;
                        case clientThreadList:
                            for (HashMap.Entry<String, ClientThread> entry : clientThreadHashMap.entrySet()) {
                                System.out.println(entry.getKey());
                            }
                            break;
                        case threadList:
                            for (HashMap.Entry<String, Thread> entry : threadHashMap.entrySet()) {
                                System.out.println(entry.getKey());
                            }
                            break;
                        default:
                    }
                }catch (Exception e){
                    System.out.println("无此命令");
                }
            }
        }
    }

    public static HashMap<String, Thread> getThreadHashMap() {
        return threadHashMap;
    }

    public static HashMap<String, ClientThread> getClientThreadHashMap() {
        return clientThreadHashMap;
    }
}
