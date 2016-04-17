package RemoteConsole;

import java.io.IOException;
import java.net.Socket;

public class Main
{
	static int[][] board = new int[7][6];
	static Socket s;
	
	public static void main(String[] args)
	{
		String ip = "localhost";
		int port = 9000;
		
		System.out.println("Connecting to: " + ip + ":" + port);
		System.out.println("After you started streaming, to close this just KILL ME! CTRL+C all the way. Sorry but I didn't implement async in and output yet, the server has to request an input before you can actually type something :P");
		try
		{	
			Ciphers.init();
			s = new Socket(ip, port);
			NetworkHandler netHandler = new NetworkHandler(s);
			netHandler.start();
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
			exit();
		}
		
		while (true)
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e) { }
	}

	public static void exit()
	{
		try
		{
			s.close();
		}
		catch (IOException e) { }
		
		System.exit(0);
	}
}
