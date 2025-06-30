 import java.io.*;
import java.net.*;

class UDPClientModified {
    public static void main(String[] args) throws Exception {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        
        byte[] sendData;
        byte[] receiveData = new byte[1024];
        
        System.out.println("Connected to UDP server. Type a message ('exit' to disconnect).\n");
        
        while (true) {
            System.out.print("Client: ");
            String message = userInput.readLine();
            sendData = message.getBytes();
            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 50002);
            clientSocket.send(sendPacket);
            
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Closing connection...");
                break;
            }
            
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server: " + response);
        }
        clientSocket.close();
    }
}
