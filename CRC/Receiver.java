import java.util.*;

public class Receiver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the degree of the generator polynomial:");
        int r = sc.nextInt();
        int[] g = new int[r + 1];
        System.out.println("Enter the generator polynomial:");
        for (int i = 0; i <= r; i++) {
            g[i] = sc.nextInt();
        }

        System.out.println("Enter the degree of the message:");
        int md = sc.nextInt();

        System.out.println("Enter the received message:");
        int[] received = new int[md+ 1];
        for (int i = 0; i < received.length; i++) {
            received[i] = sc.nextInt();
        }

        if (receiver(received, g, md, r)) {
            System.out.println("Valid message received");
        } else {
            System.out.println("Invalid message received");
        }
    }

    public static boolean receiver(int[] received, int[] g, int md, int r) {
        for (int i = 0; i <= md; i++) {
            if (received[i] == 1) {
                for (int j = 0; j <= r; j++) {
                    received[i + j] ^= g[j];
                }
            }
        }

        for (int i = md + 1; i < received.length; i++) {
            if (received[i] != 0) {
                return false;
            }
        }

        return true;
    }
}

