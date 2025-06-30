import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(50002);
        System.out.println("UDP Server is running on port 50002...");

        byte[] receiveData = new byte[1024];
        byte[] sendData;

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String stuffedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received Stuffed Data: " + stuffedData);

            if (stuffedData.equalsIgnoreCase("exit")) {
                System.out.println("Client disconnected.");
                break;
            }

            String unstuffedData = performBitUnstuffing(stuffedData);

            sendData = ("Unstuffed Data: " + unstuffedData).getBytes();
            InetAddress clientIP = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
            serverSocket.send(sendPacket);
        }

        serverSocket.close();
    }

    public static String performBitUnstuffing(String stuffedData) {
        StringBuilder msg = new StringBuilder();
        StringBuilder stuffed = new StringBuilder(stuffedData);
        if (stuffed.length() < 16) return "Invalid stuffed data.";

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
