import java.io.*;
import java.net.*;

public class  TCPServer
{
    private static final String FLAG = "01111110";
    private static final String ESCAPE = "01111101";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server is running and waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected: " + socket.getInetAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String stuffedData = in.readLine();
        System.out.println("Received stuffed data: " + stuffedData);

        if (!stuffedData.startsWith(FLAG) || !stuffedData.endsWith(FLAG)) {
            out.println("Invalid Frame: Missing flags");
        } else {
            String withoutFlags = stuffedData.substring(FLAG.length(), stuffedData.length() - FLAG.length());
            String unstuffed = performByteUnstuffing(withoutFlags);
            out.println("Unstuffed Data: " + unstuffed);
        }

        socket.close();
        serverSocket.close();
    }

    private static String performByteUnstuffing(String data) {
        StringBuilder unstuffed = new StringBuilder();
        for (int i = 0; i < data.length(); i += 8) {
            String currentByte = data.substring(i, i + 8);
            if (!currentByte.equals(ESCAPE)) {
                unstuffed.append(currentByte);
            } else {
                i += 8;
                String nextByte = data.substring(i, i + 8);
                unstuffed.append(nextByte);
            }
        }
        return unstuffed.toString();
    }
}
