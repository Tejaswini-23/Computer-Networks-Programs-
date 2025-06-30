import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Client{
    public static void main(String[] args){
        try{
        Socket socket =new Socket("localhost",5000);
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        Scanner sc=new Scanner(System.in);
        String input="";
        while(!input.equals("exit")){
            System.out.println("Enter command: ");
            input=sc.nextLine();
            out.println(input);
            while(true){
                String response=in.readLine();
                if(response==null || response.equals("0"))
                    break;
                System.out.println(response);
            }
        }
        socket.close();
        sc.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }
}