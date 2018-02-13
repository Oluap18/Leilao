import java.io.*;
import java.net.*;
import java.util.*;

public class ClienteReader extends Thread{

	private Socket cliente;

	public ClienteReader(Socket cliente){
		this.cliente=cliente;
	}

	public void run(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			String str;
			while(true){
				str = in.readLine();
				if(str==null){
					break;
				}
				switch(str){
					case "help":
						System.out.println("-------------------------------");
						System.out.println("Comandos para executar programa:");
						System.out.println("r 'Username' 'Password': para fazer o registo do utilizador");
						System.out.println("a 'Username' 'Password': para autenticar o utilizador.");
						System.out.println("i 'Descrição do item': para leiloar um item.");
						System.out.println("listar: para listar todos os leilões ativos. * se é o dono, + se tem a licitação mais alta.");
						System.out.println("l 'número do leilão' 'valor': para licitar num leilão.");
						System.out.println("f 'número do leilão': para terminar um leilão.");
						System.out.println("h 'número do leilão': para verificar o histório de notificações do leilão.");
						System.out.println("sair: para sair da aplicação.");
						System.out.println("Happy hunting, and may the odds be ever in your favor!");
						System.out.println("-------------------------------");
						break;
					case "listar":
						int i=Integer.parseInt(in.readLine());
						int a=0;
						while(a<i){
							System.out.println(in.readLine());
							a++;
						}
						break;
					case "erro":
						System.out.println("Comando inválido. Insira 'help' para saber os comandos corretos.");
						break;
					case "":
						break;
					case "h":
						System.out.println("Histórico:");
						int length=Integer.parseInt(in.readLine());
						int aux=0;
						while(aux<length){
							System.out.println(in.readLine());
							aux++;
						}
						System.out.println("-------------------------------");
						break;
					default:
						System.out.println(str);
						break;
				}
			}
		}
		catch(Exception e){}
	}	
}
