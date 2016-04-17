package RemoteConsole;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.crypto.SealedObject;

public class NetworkHandler extends Thread
{
	private final ObjectInputStream objectIn;
	private final ObjectOutputStream objectOut;
	
	private final Console console = System.console();
	
	public NetworkHandler(Socket s) throws IOException
	{
		objectIn = new ObjectInputStream(s.getInputStream());
		objectOut = new ObjectOutputStream(s.getOutputStream());
	}
	
	public void run()
	{
		try
		{
			objectOut.writeObject(new SealedObject(Ciphers.AES_SECRET_KEY, Ciphers.RSA_ENCODE));
			objectOut.flush();
		}
		catch (Exception e)
		{
			System.exit(0);
		}
		while (true)
		try
		{
			String message = (String)((SealedObject)objectIn.readObject()).getObject(Ciphers.AES_DECODE);
			System.out.println(message);
			if (message.equals(Message.requestPassword) || message.equals(Message.requestNewPassword) || message.equals(Message.requestConfirm))
			{
				Object obj = new SealedObject(toString(console.readPassword()), Ciphers.AES_ENCODE);
				objectOut.writeObject(obj);
				objectOut.flush();
			}
			else if (message.equals(Message.changeAborted) || message.equals(Message.passwordChanged)) continue;
			else if (message.equals(Message.startingStream))
			{
				while (true)
				{
					message = (String)((SealedObject)objectIn.readObject()).getObject(Ciphers.AES_DECODE);
					System.out.print(message);
				}
			}
			else
			{
				objectOut.writeObject(new SealedObject(console.readLine(), Ciphers.AES_ENCODE));
				objectOut.flush();
			}
		}
		catch (Exception e)
		{
			System.exit(0);
		}
	}
	
	private String toString(char[] phrase)
	{
		String result = "";
		for (char c : phrase)
			result += c;
		return result;
	}
}
