import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        try{
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String filename=in.readLine();
        System.out.println("File name received: " + filename);
        System.out.println("Reading file: " + filename);

        File file = new File(filename);
        FileInputStream fin = new FileInputStream(file);
        String data=new String();
        while(file.exists()){
            String d1=.read();
            data=d1;
        }
        out.println(data.toString());

        System.out.println("Data sent to client.");
        socket.close();
        serverSocket.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
