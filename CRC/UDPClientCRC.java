import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClientCRC {
    public static void main(String[] args) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        byte[] sendData;
        byte[] receiveData = new byte[1024];

        System.out.println("Type a message ('exit' to disconnect).");

        // Step 1: Request generator polynomial
        sendData = "request_poly".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String polyStr = new String(receivePacket.getData(), 0, receivePacket.getLength());
        int[] generator = new int[polyStr.length()];
        for (int i = 0; i < polyStr.length(); i++) {
            generator[i] = Character.getNumericValue(polyStr.charAt(i));
        }
        int r = generator.length - 1;
        System.out.println("Generator polynomial received: " + polyStr);

        while (true) {
            System.out.print("Client Message Bits (space-separated or 'exit'): ");
            String input = userInput.readLine();

            if (input.equalsIgnoreCase("exit")) {
                sendData = "exit".getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
                clientSocket.send(sendPacket);
                break;
            }

            String[] bits = input.trim().split(" ");
            int md = bits.length - 1;
            int[] M = new int[md + r + 1];
            for (int i = 0; i <= md; i++) {
                M[i] = Integer.parseInt(bits[i]);
            }

            int[] encoded = sender(M, generator, md, r);
            StringBuilder encodedStr = new StringBuilder();
            for (int bit : encoded) encodedStr.append(bit);

            sendData = encodedStr.toString().getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
            clientSocket.send(sendPacket);

            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + response);
        }

        clientSocket.close();
    }

    public static int[] sender(int[] M, int[] g, int md, int r) {
        int[] R = Arrays.copyOf(M, M.length);

        for (int i = md + 1; i < M.length; i++) {
            M[i] = 0;
        }

        for (int i = 0; i <= md; i++) {
            if (R[i] == 1) {
                for (int j = 0; j <= r; j++) {
                    R[i + j] =(R[i+j]==g[j]?0:1);
                }
            }
        }

        for (int i = md + 1; i < M.length; i++) {
            M[i] = R[i];
        }

        return M;
    }
}
