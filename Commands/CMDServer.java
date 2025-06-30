import java.io.*;
import java.net.*;

class CMDServer {
    public static void main(String[] args){
		try {
		ServerSocket server=new ServerSocket(5000);
		Socket client=server.accept();
		BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out=new PrintWriter(client.getOutputStream(),true);
		while(true){
		StringBuffer command=new StringBuffer("cmd /c ");
		String input=in.readLine();
		if(input!=null && !input.equals("")){
			command.append(input);
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", input);
			Process process = pb.start();
			BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line=reader.readLine() )!= null) {
					out.println(line);
		}
		out.println("0");
		int exitCode=process.waitFor();
		System.out.println("Exit Code: "+exitCode);
				reader.close();
				process.destroy();
		}
		}
		
		

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}