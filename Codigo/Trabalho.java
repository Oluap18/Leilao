import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Trabalho{

	private Map<Integer,Utilizador> utilizadores;
	private Map<Integer,Leilao> leiloes;
	private int index;
	private Lock lockTrabalho;

	public Trabalho(){
		this.utilizadores = new TreeMap<Integer,Utilizador>();
		this.leiloes = new TreeMap<Integer,Leilao>();
		this.index=0;
		this.lockTrabalho = new ReentrantLock();
	}

	public Utilizador getUtilizador(Integer i){
		return utilizadores.get(i);
	}

	public synchronized String autenticar(String s, String u) throws InvalidAccountException, InvalidPasswordException{
		int hash = s.hashCode();
		Utilizador uti = utilizadores.get(hash);
		String m;
		if(uti==null)
			throw new InvalidAccountException("Essa conta não está registada.");
		try{
			m=uti.autenticar(u);
		}
		catch(InvalidPasswordException e){
			m=e.toString();
		}
		return m;
	}

	public synchronized int iniciarLeilao(String u, String desc) {
		Leilao l = new Leilao(desc,index,u);
		leiloes.put(index,l);
		index++;
		return (index-1);
	}

	public synchronized String registar(String u, String p) throws UsernameExistenteException{
		int hash = u.hashCode();
		if(utilizadores.containsKey(hash)==false){
			Utilizador l = new Utilizador(u,p);
			utilizadores.put(hash,l);
		}
		else
			throw new UsernameExistenteException("Esse username já existe.");
		return "Registo Efetuado.";
	}

	public synchronized int listar(String u, ArrayList<String> array) throws InterruptedException {
		int n;
		int a=0;
		StringBuilder sb = new StringBuilder();
		Collection<Leilao> lei = leiloes.values();
		for(Leilao l: lei ){
			sb=new StringBuilder();
			n=l.getIndex();
			sb.append("Leilão ");
			sb.append(n);
			if(l.getIniciador().equals(u)){
				sb.append(" *");
			}
			if(l.getVencedor().equals(u)){
				sb.append(" +");
			}
			sb.append(", Preço: ");
			sb.append(l.getPreco()+ ".");
			sb.append(" "+ "Descrição do Item: " + l.getDescricao()+".");
			sb.append(" Maior Licitador: " + l.getVencedor());
			array.add(sb.toString());
			a++;
		}
		return a;
	}

	public List<String> historico(int i)  throws InvalidLeilaoException, InterruptedException{
		lockTrabalho.lock();
		List<String> array = null;
		Leilao l = null;
		try{
			l = leiloes.get(i);
			if(l==null)
				throw new InvalidLeilaoException("Leilao não existe.");
			l.lock();
		}
		finally{
			lockTrabalho.unlock();
		}
		try{
			array = l.getMensagens();
		}
		finally{
			l.unlock();
		}
		return array;
	}

	public String licitacao(ChatMensagens s, String st, int n, int valor) throws InvalidLeilaoException, InterruptedException{
		lockTrabalho.lock();
		Leilao l;
		int m=0;
		String res;
		try{
			l = leiloes.get(n);
			if(l==null)
				throw new InvalidLeilaoException("Leilao não existe.");
			l.lock();
		}
		finally{
			lockTrabalho.unlock();
		}
		try{
			m = l.licitar(s,st,valor);
		}
		finally{
			l.unlock();
		}
		if(m==0){
			res="Não é a licitação mais alta.";
			return res;
		}
		else{
			res=("Nova licitação mais alta. Licitador: "+ st + " com o valor de " + m);
			l.addMensagens(res);
			return "";
		}
	}

	public synchronized String fecharLeilao(String s, int n) throws InvalidLeilaoException, InvalidIniciadorException, InterruptedException{
		String venc, res;
		int valor;
		Leilao ll=null;
		ll=leiloes.get(n);
		if(ll==null)
			throw new InvalidLeilaoException("Leilão não existe.");
		if(!ll.getIniciador().equals(s))
			throw new InvalidIniciadorException("Não é o leiloador do leilão.");
		valor=ll.getPreco();
		venc=ll.getVencedor();
		res = ("O leilão " + n + " acabou. Vencedor do leilao: "+ venc + ". Preco final : "+valor + ".");
		ll.addMensagens(res);
		leiloes.remove(n);
		return "";
	}
}