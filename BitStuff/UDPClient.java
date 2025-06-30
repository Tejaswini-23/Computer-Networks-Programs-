import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        byte[] sendData;
        byte[] receiveData = new byte[1024];

        System.out.println("UDP Client connected. Type message (or 'exit'):\n");

        while (true) {
            System.out.print("Client: ");
            String message = userInput.readLine();

            if (message.equalsIgnoreCase("exit")) {
                sendData = "exit".getBytes();
                DatagramPacket exitPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
                clientSocket.send(exitPacket);
                System.out.println("Exiting...");
                break;
            }

            String stuffed = bitStuffing(message);
            String flag = "01111110";
            String stuffedWithFlags = flag + stuffed + flag;

            sendData = stuffedWithFlags.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + serverResponse);
        }

        clientSocket.close();
    }

    public static String bitStuffing(String bits) {
        StringBuilder stuffed = new StringBuilder();
        int count = 0;

        for (int i = 0; i < bits.length(); i++) {
            stuffed.append(bits.charAt(i));
            if (bits.charAt(i) == '1') {
                count++;
            } else {
                count = 0;
            }

            if (count == 5) {
                stuffed.append('0');
                count = 0;
            }
        }

        return stuffed.toString();
    }
}
