import java.util.*;

public class ByteStuffingSender {
    private static final String FLAG = "01111110";
    private static final String ESCAPE = "01111101";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        System.out.println("Enter the data in bits (multiple of 8, e.g., 0110010101111110): ");
        String inputBits = sc.nextLine();


        if (inputBits.length() % 8 != 0) {
            System.out.println("Error: Input length must be a multiple of 8.");
            return;
        }

        // Perform byte stuffing
        String stuffedData = performByteStuffing(inputBits);

        // Add flags at the start and end of the stuffed data
        stuffedData = FLAG + stuffedData + FLAG;
        System.out.println("Stuffed Data (Transmitted by Sender): ");
        for (int i = 0; i < stuffedData.length(); i += 8) {
            String currentByte = stuffedData.substring(i, i + 8);
            System.out.print(currentByte+" ");
        }
    }
    // Method to perform byte stuffing
    private static String performByteStuffing(String inputBits) {
        StringBuilder stuffedData = new StringBuilder();

        for (int i = 0; i < inputBits.length(); i += 8) {
            String currentByte = inputBits.substring(i, i + 8);

            if (currentByte.equals(FLAG)) {
                // Stuff the flag byte
                stuffedData.append(ESCAPE).append(FLAG);
            } else if (currentByte.equals(ESCAPE)) {
                // Stuff the escape byte
                stuffedData.append(ESCAPE).append(ESCAPE);
            } else {
                // Append the byte as is
                stuffedData.append(currentByte);
            }
        }

        return stuffedData.toString();
    }
}