 import java.util.*;

public class ByteStuffingReceiver {
    private static final String FLAG = "01111110";
    private static final String ESCAPE = "01111101";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        System.out.println("Enter the received stuffed data: ");
        String stuffedData = sc.nextLine();

        // Ensure it contains start and end flags
        if (!stuffedData.startsWith(FLAG) || !stuffedData.endsWith(FLAG)) {
            System.out.println("Error: Data does not contain proper flags.");
            return;
        }

        // Remove the start and end flags
        String dataWithoutFlags = stuffedData.substring(FLAG.length(), stuffedData.length() - FLAG.length());

        // Perform byte unstuffing
        String unstuffedData = performByteUnstuffing(dataWithoutFlags);

        // Output the unstuffed data
        System.out.println("UnStuffed Data: ");
        for (int i = 0; i < unstuffedData.length(); i += 8) {
            String currentByte = unstuffedData.substring(i, i + 8);
            System.out.print(currentByte+" ");
        }
    }

    // Method to perform byte unstuffing
    private static String performByteUnstuffing(String stuffedData) {
        StringBuilder unstuffedData = new StringBuilder();

        for (int i = 0; i < stuffedData.length(); i += 8) {
            String currentByte = stuffedData.substring(i, i + 8);

            if (!currentByte.equals(ESCAPE)) {
                // Append the byte as is
                unstuffedData.append(currentByte);
            } else {
                // Skip the escape byte
                i +=8;
	String nextbyte = stuffedData.substring(i, i + 8);
	unstuffedData.append(nextbyte);
 // Skip the next transformed byte
            }
        }

        return unstuffedData.toString();
    }
}
