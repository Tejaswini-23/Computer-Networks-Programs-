import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("[SERVER] Listening on port " + port + "...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("[SERVER] Client connected: " + clientSocket.getInetAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String command;
        while ((command = in.readLine()) != null) {
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("[SERVER] Exit command received. Closing connection.");
                break;
            }

            System.out.println("[SERVER] Executing: " + command);

            try {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder output = new StringBuilder();

                while ((line = processReader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                processReader.close();

                // Also include error stream (if any)
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                errorReader.close();

                out.println(output.toString());
            } catch (IOException e) {
                out.println("Error executing command: " + e.getMessage());
            }
        }

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
