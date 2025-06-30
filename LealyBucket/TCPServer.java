import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("[SERVER] Waiting for client...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("[SERVER] Client connected.");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String bucketSizeStr = in.readLine();
        int bucketSize = Integer.parseInt(bucketSizeStr);
        System.out.println("[SERVER] Received bucket size: " + bucketSize);

        int leakRate = bucketSize / 4; // or any logic
        System.out.println("[SERVER] Sending leak rate: " + leakRate);
        out.println(leakRate);

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
