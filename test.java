import java.awt.*;
import matrix.db.*;
import matrix.util.*;
import java.io.*;

public class MqlExecuteCmd extends Frame
{
	Context _context;
	String _host;
	String _server;
	String _user;
	String _command;
	String _errfile;

public MqlExecuteCmd (String host, String server, String user, String cmd, String err_file)
	{
		_host = host;
		_server = server;
		_user = user;
		_command = cmd;
		_errfile = err_file;
	}

	public static void main( String args[])
	{
		new MqlExecuteCmd( args[0], args[1], args[2], args[3], args[4], args[5]);
	}
	// Set session context using the server and ENOVIA Live Collaboration user names
	public boolean setContext()
	{
		try
		{
			_context = new Context(_server,_host);
			_context.setUser(_user);
			_context.setPassword("");
			_context.setVault("");
			_context.connect();
			_context.disconnect();
		}
		catch (MatrixException e)
		{
			_context = null;
			System.out.println("Can't set context");
			ListItr itr = new ListItr(e.getMessages());
			while(itr.next())
			{
				System.out.println((String) itr.value());
			}
				return false;
		}
		return true;
	}
	private void executecmd()
	{
		int cursor = getCursorType();
		setCursor(WAIT_CURSOR);
		message("Executing Mql Command");
		try
		{
			_context.getContext();
			_context.connect();

			// Construct a new MQLCommand object
			MQLCommand mqlcommand = new MQLCommand();

			// mqlcommand executed
			mqlcommand.executeCommand(_context,_command);
			_context.disconnect();

			// mqlcommand results fetched
			String result = mqlcommand.getResult();
			System.out.println(result);

			// mqlcommand errors fetched
			String error1 = mqlcommand.getError();
			System.out.println(error1);
		}
		catch(MatrixException e)
		{
			_context = null;
			System.out.println("Can't set context");
			ListItr itr = new ListItr(e.getMessages());
			while(itr.next())
			{
				System.out.println((String) itr.value());
				error(e, "Mql command execute error");
			}
		}s
		finally
		{
			message("MQL command Executed");
			setCursor(cursor);
		}
	}
}