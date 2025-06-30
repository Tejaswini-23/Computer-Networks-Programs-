import java.util.*;

public class BitUnstuff {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the stuffed data to be unstuffed along with flags: ");
        String stuffed = sc.nextLine();

        String originalData = performBitUnstuffing(stuffed);
        System.out.println("Unstuffed Data: " + originalData);
        sc.close();
    }

    public static String performBitUnstuffing(String stuffed_data) {
        StringBuilder msg = new StringBuilder();
        
        //remove flags
        StringBuilder stuffed = new StringBuilder(stuffed_data);
        stuffed.delete(0,8);
        stuffed.delete(stuffed.length()-8,stuffed.length());


        int count = 0;

        for (int i = 0; i < stuffed.length(); i++) {
            msg.append(stuffed.charAt(i));
            if (stuffed.charAt(i) == '1') {
                count++;
            } else {
                count = 0;
            }

            if (count == 5 && i + 1 < stuffed.length() && stuffed.charAt(i + 1) == '0') {
                i++; // Skip the '0' after five consecutive '1's
                count = 0;
            }
        }
        return msg.toString();
    }
}