import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
    try{
        Scanner sc=new Scanner(System.in);
        Socket socket = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the filename :");
        String filename=sc.nextLine();
        out.println(filename);
        String line;
        System.out.println("Received from server:");
        
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        in.close();
        socket.close();
    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}
}
