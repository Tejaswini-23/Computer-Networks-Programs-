import java.io.*;
import java.net.*;

class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(50001);
        System.out.println("Main Server is active on port 50001...");

        Socket client = null;

        while (true) {
            try {
                client = server.accept();
                System.out.println("New connection from: " + client.getInetAddress() + ":" + client.getPort());

                Thread t = new Thread(new Handler(client));
                t.start();
            } catch (SocketException e) {
                if (client != null) {
                    System.out.println("Session closed for client (" + client.getInetAddress() + ":" + client.getPort() + ")");
                } else {
                    System.out.println("SocketException occurred before client connection was established.");
                }
                break;
            }
        }

        server.close();
    }

    static class Handler implements Runnable {
        private final Socket client;

        public Handler(Socket socket) {
            this.client = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                String msg;
                while ((msg = in.readLine()) != null) {
                    String ip = client.getInetAddress().toString();
                    int port = client.getPort();

                    System.out.println("Received from client (" + ip + ":" + port + "): " + msg);

                    if (msg.equalsIgnoreCase("over")) {
                        System.out.println("Client (" + ip + ":" + port + ") request over");
                        break;
                    }

                    // Echo reversed message back to client
                    out.writeBytes(new StringBuilder(msg).reverse().toString() + "\n");
                }

                client.close();
                System.out.println("Client (" + client.getInetAddress() + ":" + client.getPort() + ") disconnected.");
            } catch (IOException e) {
                System.out.println("Client disconnected abruptly.");
            }
        }
    }
}
