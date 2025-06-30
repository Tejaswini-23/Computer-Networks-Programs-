import java.util.*;
class Dijkstras{
    public static void main(String args[]){
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter the number of vertices : ");
    int n=sc.nextInt();
    int[][] a=new int[n][n];
    System.out.println("Enter the matrix : ");
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            a[i][j]=sc.nextInt();
        }
    }
    System.out.println("Enter the  source vertex :(indexing starts with zero) : ");
    int s=sc.nextInt();
    int[] visited=new int[n];
    int[] distance=new int[n];
    for(int i=0;i<n;i++){
        visited[i]=0;
        distance[i]=999;
    }
    distance[s]=0;
    for(int i=0;i<n;i++){
        int u=-1;
        for(int j=0;j<n;j++){
            if ((visited[j]==0) && (u==-1 || distance[j]<distance[u])) {
                u=j;
            }
        }
        for(int v=0;v<n;v++){
            if((visited[v]==0) && (a[u][v]!=999)){
                int newdistance=distance[u]+a[u][v];
                if(newdistance<distance[v]){
                    distance[v]=newdistance;
                }
            }
        }
        visited[u]=1;
    }
    System.out.println("The Distances from the source vertex : ");
    for(int i=0;i<n;i++){
        System.out.print(distance[i]+" ");
    }
}
}