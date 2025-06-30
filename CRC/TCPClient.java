import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            // Step 1: Receive generator polynomial from server
            String generatorStr = in.readLine();
            int[] g = new int[generatorStr.length()];
            for (int i = 0; i < generatorStr.length(); i++) {
                g[i] = Character.getNumericValue(generatorStr.charAt(i));
            }
            int r = g.length - 1;
            System.out.println("Generator polynomial received: " + generatorStr);

            // Step 2: Get message input from user
            System.out.println("Enter message bits (space-separated):");
            String[] msgInput = sc.nextLine().trim().split(" ");
            int md = msgInput.length - 1;
            int[] M = new int[md + r + 1];
            for (int i = 0; i <= md; i++) {
                M[i] = Integer.parseInt(msgInput[i]);
            }

            // Step 3: Perform CRC and prepare transmitted message
            int[] encoded = sender(M, g, md, r);
            StringBuilder encodedStr = new StringBuilder();
            for (int bit : encoded) {
                encodedStr.append(bit);
            }

            // Step 4: Send encoded message to server
            out.println(encodedStr.toString());
            System.out.println("Sent to server (CRC encoded): " + encodedStr.toString());

            // Step 5: Receive server validation
            String response = in.readLine();
            System.out.println("Server response: " + response);

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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