import java.io.*;
import java.net.*;

class UDPServerModified {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(50002);
        System.out.println("UDP Server is active on port 50002...");
        

        
        byte[] receiveData = new byte[1024];
        byte[] sendData;
        
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            
            System.out.println("Received from client (" + clientAddress + ":" + clientPort + "): " + message);
            
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Client requested termination. Server shutting down...");
                break;
            }
            
            String response = new StringBuilder(message).reverse().toString();
            sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            serverSocket.send(sendPacket);
        }
        serverSocket.close();
    }
}
