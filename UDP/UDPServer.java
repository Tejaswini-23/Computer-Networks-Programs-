import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(50002);
        byte[] receiveData = new byte[1024];
        byte[] sendData;

        System.out.println("UDP Server is active on the port 50002...");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String msg = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

            String reversedMsg = new StringBuilder(msg).reverse().toString();
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            sendData = reversedMsg.getBytes();

            System.out.println("Received from client ("+clientAddress+"."+clientPort+"):" + msg );
            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket.send(sendPacket);

            if (msg.equalsIgnoreCase("exit")) {
                System.out.println("Client requested termination Server Shutting down. ");
                break;
            }
        }

        socket.close();
    }
}
