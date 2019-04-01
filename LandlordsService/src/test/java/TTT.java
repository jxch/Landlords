import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TTT {
    public static void main(String[] args) throws IOException {
        //服务端：
        ServerSocket serverSocket = new ServerSocket(8189);
        Socket socket = serverSocket.accept();

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        String string = dataInputStream.readUTF();
        if("aa".equals(string)){
            //发牌：：dataOutputStream.writeUTF("...........");
        }

        //客户端：
        Socket socket1 = new Socket("127.0.0.1",8189);
        DataOutputStream outputStream = new DataOutputStream(socket1.getOutputStream());
        outputStream.writeUTF("aa");
    }
}
