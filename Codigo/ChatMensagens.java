import java.io.*;
import java.util.*;

public class ChatMensagens {

	private List<String> mensagens;

	public ChatMensagens(){
		this.mensagens = new ArrayList<String>();
	}

	public synchronized void addMensagens(String s){
		mensagens.add(s);
		notifyAll();
	}

	public List<String> getMensagens(){
		return this.mensagens;
	}

	public synchronized void readMensagens(PrintWriter cliente) throws Exception{
		while(0==mensagens.size())
			wait();
		while(0<mensagens.size()){
			cliente.println(mensagens.get(0));
			cliente.flush();
			mensagens.remove(0);
		}
	}
}