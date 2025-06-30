import java.io.*;
import java.net.*;

class UDPClient {
    public static void main(String args[]) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");
        byte[] sendData;
        byte[] receiveData = new byte[512];
        System.out.println("Connected to the Server.Type a message('exit'to disconnect)");
        while (true) {
            System.out.print("Client: ");
            String msg = in.readLine();
            sendData = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 50002);
            socket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("Closing Connection...");
                break;
            }
            System.out.println("Server: " + response);
        }

        socket.close();
    }
}
