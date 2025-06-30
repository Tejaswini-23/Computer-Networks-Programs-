import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        // File read cheyyadam
        BufferedReader fileReader = new BufferedReader(new FileReader("data.txt"));
        StringBuilder dataBuilder = new StringBuilder();
        String line;

        while ((line = fileReader.readLine()) != null) {
            dataBuilder.append(line).append("\n");
        }
        fileReader.close();

        // Data ni client ki pampadam
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write(dataBuilder.toString());
        writer.flush();

        System.out.println("Data sent to client.");

        writer.close();
        socket.close();
        serverSocket.close();
    }
}
