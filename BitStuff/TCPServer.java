import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is waiting for client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String stuffedWithFlags = in.readLine();
            System.out.println("Received Stuffed Data (with flags): " + stuffedWithFlags);

            String unstuffedData = performBitUnstuffing(stuffedWithFlags);
            out.println("Unstuffed Data: " + unstuffedData);
            System.out.println("Sent Unstuffed Data back to client.");

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String performBitUnstuffing(String stuffedData) {
        StringBuilder msg = new StringBuilder();

        StringBuilder stuffed = new StringBuilder(stuffedData);
        stuffed.delete(0, 8); // Remove starting flag
        stuffed.delete(stuffed.length() - 8, stuffed.length()); // Remove ending flag

        int count = 0;

        for (int i = 0; i < stuffed.length(); i++) {
            msg.append(stuffed.charAt(i));
            if (stuffed.charAt(i) == '1') {
                count++;
            } else {
                count = 0;
            }

            if (count == 5 && i + 1 < stuffed.length() && stuffed.charAt(i + 1) == '0') {
                i++; // Skip stuffed '0'
                count = 0;
            }
        }

        return msg.toString();
    }
}
