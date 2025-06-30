import java.io.*;
import java.net.*;

public class UDPServerCRC {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(50002);
        System.out.println("UDP Server is active on port 50002...");

        byte[] receiveData = new byte[1024];
        byte[] sendData;

        // Example generator polynomial: x^3 + x + 1 => 1011
        int[] generator = {1, 0, 1, 1};
        int r = generator.length - 1;

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + message);

            if (message.equalsIgnoreCase("request_poly")) {
                StringBuilder poly = new StringBuilder();
                for (int bit : generator) poly.append(bit);
                sendData = poly.toString().getBytes();
            } else if (message.equalsIgnoreCase("exit")) {
                System.out.println("Client requested termination.");
                break;
            } else {
                int[] receivedBits = new int[message.length()];
                for (int i = 0; i < message.length(); i++) {
                    receivedBits[i] = Character.getNumericValue(message.charAt(i));
                }

                boolean isValid = checkCRC(receivedBits, generator, receivedBits.length - r, r);
                String result = isValid ? "Valid Message Received" : "Invalid Message Received";
                sendData = result.getBytes();
            }

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);
        }

        serverSocket.close();
    }

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
