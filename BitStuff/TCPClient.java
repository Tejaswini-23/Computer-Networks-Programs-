import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter data to be bit-stuffed: ");
            String inputBits = userInput.readLine();

            String stuffed = bitStuffing(inputBits);
            String flag = "01111110";
            String stuffedWithFlags = flag + stuffed + flag;

            out.println(stuffedWithFlags);
            System.out.println("Sent Stuffed Data: " + stuffedWithFlags);

            String serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String bitStuffing(String bits) {
        StringBuilder stuffed = new StringBuilder();
        int count = 0;

        for (int i = 0; i < bits.length(); i++) {
            stuffed.append(bits.charAt(i));
            if (bits.charAt(i) == '1') {
                count++;
            } else {
                count = 0;
            }

            if (count == 5) {
                stuffed.append('0');
                count = 0;
            }
        }

        return stuffed.toString();
    }
}
