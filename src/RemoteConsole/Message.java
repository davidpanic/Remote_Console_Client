package RemoteConsole;

public class Message
{
	// Exit
	public static final String exitMessage			= "Exiting now";
	
	// Communication
	
	public static final String requestUsername		= "Please enter your username:";
	public static final String requestToken			= "Please enter your token:";
	public static final String requestPassword		= "Please enter your password:";
	public static final String requestNewPassword	= "Please enter your new password:";
	public static final String requestConfirm		= "Please enter your new password again:";
	public static final String passwordChanged		= "Successfully changed your password!";
	public static final String changeAborted		= "Password change aborted!";
	public static final String awaitingCommand		= "To change your password, type cgpass. To start streaming, type stream. To disconnect, type exit.";
	public static final String unknownMessage		= "Unkown command. Available commands are cgpass, stream and exit.";
	public static final String startingStream		= "Starting Stream.";
	
}
