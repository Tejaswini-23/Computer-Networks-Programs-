import java.net.*;

public class UDPServer {
    private static final String FLAG = "01111110";
    private static final String ESCAPE = "01111101";

    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(50002);
        byte[] receiveData = new byte[2048];
        byte[] sendData;

        System.out.println("UDP Server is running on port 50002...");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received stuffed data: " + received);

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            String response;
            if (!received.startsWith(FLAG) || !received.endsWith(FLAG)) {
                response = "Invalid Frame: Missing flags";
            } else {
                String withoutFlags = received.substring(FLAG.length(), received.length() - FLAG.length());
                String unstuffed = performByteUnstuffing(withoutFlags);
                response = "Unstuffed Data: " + unstuffed;
            }

            sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);
        }
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
