import java.net.*;
import java.io.*;
import java.util.*;

public class ServerWriter extends Thread {

	private Socket cliente;
	private ChatMensagens chat;
	private Boolean loop;

	public ServerWriter(Socket c, ChatMensagens chat){
		cliente=c;
		loop=true;
		this.chat = chat;
	}

	public void setChat(ChatMensagens c){
		chat.addMensagens("");
		this.chat = c;

	}

	public ChatMensagens getChat(){
		return chat;
	}

	public void run(){
		try{
			PrintWriter out = new PrintWriter(cliente.getOutputStream());
			while(loop){
				chat.readMensagens(out);
			}
		}
		catch(Exception e){}
	}

	public void setLoop(boolean loop){
		this.loop = loop;
	}
}
