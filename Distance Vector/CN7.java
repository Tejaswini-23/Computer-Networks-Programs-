import java.util.Scanner;

public class CN7 {

    public static void performDistanceVectorRouting(int numNodes, int[][] costMatrix, int[][] distance, int[][] nextHop) {
        // Step 1: Initialize distance and nextHop tables
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                distance[i][j] = costMatrix[i][j];
                if (costMatrix[i][j] != 9999 && i != j) {
                    nextHop[i][j] = j;
                } else {
                    nextHop[i][j] = -1; // No direct path
                }
            }
        }

        // Step 2: Distance Vector Routing using Bellman-Ford update
        boolean updated;
        do {
            updated = false;
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    for (int k = 0; k < numNodes; k++) {
                        if (distance[i][k] != 9999 && distance[k][j] != 9999) { // Ensure valid path
                            if (distance[i][j] > distance[i][k] + distance[k][j]) {
                                distance[i][j] = distance[i][k] + distance[k][j];
                                nextHop[i][j] = nextHop[i][k]; // Update next hop
                                updated = true;
                            }
                        }
                    }
                }
            }
        } while (updated); // Repeat until no updates occur
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter the number of nodes: ");
        int numNodes = scanner.nextInt();

        int[][] costMatrix = new int[numNodes][numNodes];
        int[][] distance = new int[numNodes][numNodes];
        int[][] nextHop = new int[numNodes][numNodes];

        System.out.println("\nEnter the cost matrix (use 0 for unreachable nodes): ");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                costMatrix[i][j] = scanner.nextInt();
                if (costMatrix[i][j] == 0 && i != j) {
                    costMatrix[i][j] = 9999; // Set unreachable
                }
            }
        }

        performDistanceVectorRouting(numNodes, costMatrix, distance, nextHop);

        // Print routing tables
        for (int i = 0; i < numNodes; i++) {
            System.out.println("\nRouter " + (i + 1) + " routing table:");
            System.out.println("Destination | Next  | Distance");
            for (int j = 0; j < numNodes; j++) {
                if (i != j) {
                    if (distance[i][j] == 9999) {
                        System.out.println((j + 1) + "          | -        | ∞");
                    } else {
                        System.out.println((j + 1) + "          | " + (nextHop[i][j] + 1) + "        | " + distance[i][j]);
                    }
                } else {
                    System.out.println((j + 1) + "          | " + (i + 1) + "        | 0");
                }
            }
        }

        scanner.close();
    }
}
