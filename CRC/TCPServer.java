import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) {
        int[] generator = {1, 0, 1, 1}; // Example: x^3 + x + 1 => 1011
        int r = generator.length - 1;
        ServerSocket serverSocket = new ServerSocket(12345); // Port number
        System.out.println("Server started on port 12345");

        try{

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // Send generator polynomial
                    StringBuilder genStr = new StringBuilder();
                    for (int bit : generator) {
                        genStr.append(bit);
                    }
                    out.println(genStr.toString()); // send generator

                    // Receive transmitted message
                    String receivedMessage = in.readLine();
                    System.out.println("Received Message from client: " + receivedMessage);

                    int[] received = new int[receivedMessage.length()];
                    for (int i = 0; i < received.length; i++) {
                        received[i] = Character.getNumericValue(receivedMessage.charAt(i));
                    }

                    // CRC check
                    boolean isValid = checkCRC(received, generator, received.length - r, r);

                    // Send result
                    out.println(isValid ? "Valid Message Received" : "Invalid Message Received");
                    System.out.println("Checked CRC: " + (isValid ? "Valid" : "Invalid"));
                }
            }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    // CRC check logic (same as your receiver method)
    public static boolean checkCRC(int[] received, int[] g, int md, int r) {
        int[] temp = received.clone();

        for (int i = 0; i <= md; i++) {
            if (temp[i] == 1) {
                for (int j = 0; j <= r; j++) {
                    temp[i + j] =( temp[i+j]==g[j])? 0 : 1;
                }
            }
        }

        for (int i = md + 1; i < received.length; i++) {
            if (temp[i] != 0) return false;
        }

        return true;
    }
}