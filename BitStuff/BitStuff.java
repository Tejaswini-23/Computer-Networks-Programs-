import java.util.*;

public class BitStuff {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the data to be stuffed: ");
        String bits = sc.nextLine();

        String stuffedData = bitStuffing(bits);
        System.out.println("Stuffed Data: " + stuffedData);

        String flag = "01111110";
        String framedData = flag + stuffedData + flag;
        System.out.println("Framed Stuffed Data: " + framedData);

        sc.close();
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
                stuffed.append('0'); // Stuff '0' after 5 consecutive '1's
                count = 0;
            }
        }
        return stuffed.toString();
    }
}
