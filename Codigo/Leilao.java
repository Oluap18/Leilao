import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Leilao{

	private String descricao;
	private int index;
	private String iniciador;
	private Map<Integer,ChatMensagens> licitadores;
	private String vencedor;
	private int preco;
	private Lock lockLeilao;
	private ChatMensagens chat;

	public Leilao(String d, int n, String i){
		this.descricao=d;
		this.index=n;
		this.iniciador=i;
		this.licitadores= new HashMap<Integer,ChatMensagens>();
		this.preco=0;
		this.lockLeilao = new ReentrantLock();
		this.chat = new ChatMensagens();
		this.vencedor="";

	}

	public String getDescricao(){
		return this.descricao;
	}

	public String getIniciador(){
		return this.iniciador;
	}

	public String getVencedor(){
		return this.vencedor;
	}

	public int getIndex(){
		return this.index;
	}

	public void addMensagens(String n){
		chat.addMensagens(n);
		sendMensagens(n);
	}

	public int licitar(ChatMensagens n, String s, int b){
		if(!licitadores.containsKey(s.hashCode())){
			licitadores.put(s.hashCode(),n);
		}
		if(b>preco){
			preco=b;
			vencedor=s;
			return b;
		}
		return 0;
	}

	public int getPreco(){
		return preco;
	}

	public List<String> getMensagens(){
		return chat.getMensagens();

	}

	public void sendMensagens(String s){
		Collection<ChatMensagens> col = licitadores.values();
		for(ChatMensagens sw : col){
			sw.addMensagens(s);
		}
	}

	public void lock(){
		lockLeilao.lock();
	}

	public void unlock(){
		lockLeilao.unlock();
	}
}