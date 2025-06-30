import java.util.*;

public class Sender {
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
        int[] M = new int[md + r + 1];
        System.out.println("Enter the message:");
        for (int i = 0; i <= md; i++) {
            M[i] = sc.nextInt();
        }

        int[] transmittedMessage = sender(M, g, md, r);
        System.out.println("Generated message to transmit:");
        for (int bit : transmittedMessage) {
            System.out.print(bit);
        }
        System.out.println();
    }

    public static int[] sender(int[] M, int[] g, int md, int r) {
        int[] R = Arrays.copyOf(M, M.length);

        for (int i = md + 1; i < M.length; i++) {
            M[i] = 0;
        }

        for (int i = 0; i <= md; i++) {
            if (R[i] == 1) {
                for (int j = 0; j <= r; j++) {
                    R[i + j] ^= g[j];
                }
            }
        }

        for (int i = md + 1; i < M.length; i++) {
            M[i] = R[i];
        }

        return M;
    }
}
