import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCP

Client {
    private static final String FLAG = "01111110";
    private static final String ESCAPE = "01111101";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("Connected to server.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter bit string (multiple of 8):");
        String input = sc.nextLine();

        if (input.length() % 8 != 0) {
            System.out.println("Error: Input length must be a multiple of 8.");
            socket.close();
            return;
        }

        String stuffed = performByteStuffing(input);
        stuffed = FLAG + stuffed + FLAG;
        System.out.println("Stuffed and sent: " + stuffed);

        out.println(stuffed);
        String response = in.readLine();
        System.out.println("Server Response: " + response);

        socket.close();
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
