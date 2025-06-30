import java.io.*;
import java.net.*;

public class Receiver {
    ServerSocket server;
    Socket socket = null;
    DataOutputStream out;
    DataInputStream in;
    int expectedSeq = 0;

    public void start() {
        try {
            server = new ServerSocket(3000);
            System.out.println("Receiver waiting...");
            socket = server.accept();
            System.out.println("Connected.");

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            while (true) {
                String received = in.readUTF();
                if (received.equals("stop")) break;

                int seq = received.charAt(0) - '0';
                String data = received.substring(1);
                
                System.out.println("Received: Seq " + seq + " Data " + data);
                


                if (seq == expectedSeq) {
                    expectedSeq ^= 1;
                    out.writeUTF("ACK" + seq);
                } else {
                    out.writeUTF("ACK" + (1 - expectedSeq));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { in.close(); out.close(); socket.close(); server.close(); } catch (Exception e) {}
            System.out.println("Connection closed.");
        }
    }

    public static void main(String args[]) {
        new Receiver().start();
    }
}
