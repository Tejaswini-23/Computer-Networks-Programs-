import java.io.*;
import java.net.*;

public class Sender {
    Socket socket;
    DataOutputStream out;


    DataInputStream in;
    int seq = 0;

    public void start() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connecting...");
            socket = new Socket("localhost", 3000);

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            while (true) {
                System.out.println("Enter message (0 or 1, stop to end):");
                String msg = br.readLine();
                if (msg.equals("stop")) {
                    out.writeUTF("stop");
                    break;
                }
                out.writeUTF(seq + msg);
                String ack = in.readUTF();
                
                System.out.println("ACK Received: " + ack);
                if (ack.equals("ACK" + seq)) {
                    seq ^= 1;
                } else {
                    System.out.println("Resending...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { in.close(); out.close(); socket.close(); } catch (Exception e) {}
            System.out.println("Connection closed.");
        }
    }

    public static void main(String args[]) throws Exception {
        new Sender().start();
    }
}