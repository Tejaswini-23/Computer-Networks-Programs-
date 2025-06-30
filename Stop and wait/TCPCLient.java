import java.io.*;
import java.net.*;

class TCPClient {
    public static void main(String[] args) throws Exception {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket("localhost", 50001);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Connected to the Server. Type a message (give 'exit' to disconnect).");

        Communicator communicator = new Communicator(userIn, out, serverIn);
        communicator.start();

        socket.close();
    }

    static class Communicator {
        private final BufferedReader userIn;
        private final DataOutputStream out;
        private final BufferedReader serverIn;

        Communicator(BufferedReader userIn, DataOutputStream out, BufferedReader serverIn) {
            this.userIn = userIn;
            this.out = out;
            this.serverIn = serverIn;
        }

        void start() throws IOException {
            while (true) {
                System.out.print("Client: ");
                String msg = userIn.readLine();
                out.writeBytes(msg + "\n");

                if (msg.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection...");
                    break;
                }

                String serverResponse = serverIn.readLine();
                System.out.println("Server: " + serverResponse);
            }
        }
    }
}
