import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private static final String FLAG = "01111110";
    private static final String ESCAPE = "01111101";

    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter bit string (multiple of 8):");
        String input = sc.nextLine();

        if (input.length() % 8 != 0) {
            System.out.println("Error: Input length must be a multiple of 8.");
            return;
        }

        String stuffed = performByteStuffing(input);
        stuffed = FLAG + stuffed + FLAG;

        byte[] sendData = stuffed.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
        clientSocket.send(sendPacket);
        System.out.println("Sent stuffed data: " + stuffed);

        byte[] receiveData = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());

        System.out.println("Server Response: " + response);
        clientSocket.close();
    }

    private static String performByteStuffing(String inputBits) {
        StringBuilder stuffedData = new StringBuilder();
        for (int i = 0; i < inputBits.length(); i += 8) {
            String byteStr = inputBits.substring(i, i + 8);
            if (byteStr.equals(FLAG)) {
                stuffedData.append(ESCAPE).append(FLAG);
            } else if (byteStr.equals(ESCAPE)) {
                stuffedData.append(ESCAPE).append(ESCAPE);
            } else {
                stuffedData.append(byteStr);
            }
        }
        return stuffedData.toString();
    }
}
