package com.giri.player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import com.giri.player.AllSongsObject;
import com.giri.player.Constants;
import com.giri.player.Message;

public class NewClient {

    private  String SERVER_IP = "192.168.0.89";
    private  int SERVER_PORT = 8081;
    private  String message = null;
    private static Socket socket = null;

    private LinkedList<String> playList = new LinkedList<String>();
 
    public NewClient() {
    	
    }
    
   public NewClient(String msg) {
    	message = msg;
    }

    public String getSERVER_IP() {
		return SERVER_IP;
	}
	public void setSERVER_IP(String sERVER_IP) {
		SERVER_IP = sERVER_IP;
	}
	public int getSERVER_PORT() {
		return SERVER_PORT;
	}
	public void setSERVER_PORT(int sERVER_PORT) {
		SERVER_PORT = sERVER_PORT;
	}
	public LinkedList<String> getPlayList() {
		return playList;
	}
	public void setPlayList(LinkedList<String> playList) {
		this.playList = playList;
	}
			
    public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void playIndex(int value) {
		
		Object obj = null;
        
        try
        {
        	//if(socket!=null) {
        	//	if(!socket.isClosed()) {
        	//		socket.close();
        	//	}
        	//}
            socket = new Socket(SERVER_IP, SERVER_PORT);
            socket.setReuseAddress(true);
            System.out.println("# Client # : Connected to server!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        	System.out.println("# Client # : Error connecting to server: " + ex.getMessage());
        }

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

 
        try
        {
    
            out = new ObjectOutputStream(socket.getOutputStream());

            //read a string
            
            System.out.println("# Client # : Message entered is: " + Constants.PLAYINDEX+Integer.toString(value));

            //send it to server
            out.writeObject(new Message(Constants.PLAYINDEX+Integer.toString(value)));
            out.flush();
            
            System.out.println("# Client # : Reading from input stream...");
      
            in = new ObjectInputStream(socket.getInputStream());
            
            obj = (Object) in.readObject();
      
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            System.out.println("# Client # : Error: " + ex);
        }
        finally
        {
        	try 
        	{
            	out.close();
            	//socket.close();
        	} catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
	}
	
	public LinkedList<String> fetchPlayListLinkedList() {
		
		LinkedList<String> ll = new LinkedList<String>();

        AllSongsObject aso = null;
        
        try
        {
        	//if(socket != null) {
		    //	if(!socket.isClosed()) {
		    //		socket.close();
		    //	}
        	//}
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("# Client # : Connected to server!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        	System.out.println("# Client # : Error connecting to server: " + ex.getMessage());
        }

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

 
        try
        {
    
            out = new ObjectOutputStream(socket.getOutputStream());

            //read a string
            
            System.out.println("# Client # : Message entered is: " + Constants.GETPLAYLIST);

            //send it to server
            out.writeObject(new Message(Constants.GETPLAYLIST));
            out.flush();
            
            System.out.println("# Client # : Reading from input stream...");
      
            in = new ObjectInputStream(socket.getInputStream());
            
            aso = (AllSongsObject) in.readObject();
            
            System.out.println("# Client # : Server returned object of size: " + aso.get_ll().size());
            
            System.out.println("# Client # : Contents of the list: ");
            
            for(int i=0; i<aso.get_ll().size(); i++) {
            	System.out.println(aso.get_ll().get(i));
            }
            
            ll = aso.get_ll();

        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            System.out.println("# Client # : Error: " + ex);
        }
        finally
        {
        	try 
        	{
            	out.close();
            	//socket.close();
        	} catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        return ll;
	}
	
	public void MainMethod()
    {
        AllSongsObject aso = null;
        
        try
        {
        	//if(socket != null) {
	        //	if(!socket.isClosed()) {
	        //		socket.close();
	        //	}
        	//}
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("# Client # : Connected to server!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        	System.out.println("# Client # : Error connecting to server: " + ex.getMessage());
        }

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

 
        try
        {
    
            out = new ObjectOutputStream(socket.getOutputStream());

            //read a string
            
            System.out.println("# Client # : Message entered is: " + message);

            //send it to server
            out.writeObject(new Message(message));
            out.flush();
            
            System.out.println("# Client # : Reading from input stream...");
      
            in = new ObjectInputStream(socket.getInputStream());
            
            aso = (AllSongsObject) in.readObject();
            
            System.out.println("# Client # : Server returned object of size: " + aso.get_ll().size());
            
            System.out.println("# Client # : Contents of the list: ");
            
            for(int i=0; i<aso.get_ll().size(); i++) {
            	System.out.println(aso.get_ll().get(i));
            }

        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            System.out.println("# Client # : Error: " + ex);
        }
        finally
        {
        	try 
        	{
            	out.close();
            	//socket.close();
        	} catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
    }
    
    public void execute() {
    	new NewClient().MainMethod();
    }
    
}