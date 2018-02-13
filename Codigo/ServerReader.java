import java.net.*;
import java.io.*;
import java.util.*;


public class ServerReader extends Thread {

	private Socket cliente;
	private Trabalho trabalho;
	private ChatMensagens chat;
	private Utilizador util;
	private ServerWriter serverWriter;

	public ServerReader(Socket client, Trabalho trabalho, ServerWriter serverWriter, ChatMensagens chat) {
		this.cliente = client;
		this.trabalho = trabalho;
		this.serverWriter = serverWriter;
		this.chat = chat;
	}

	public void run(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			int i = 1;
			StringBuilder sb = new StringBuilder();
			String s;
			while(true){
				String str = in.readLine();
				if(str==null){
					serverWriter.setLoop(false);
					break;
				}
				String[] s1 = str.split(" ");
				if(util!=null || s1[0].equals("r") || s1[0].equals("a") || s1[0].equals("help") || s1[0].equals("erro") || s1[0].equals("sair")){
					switch(s1[0]){
						case "a":
							sb = new StringBuilder();
							i=1;
							while(i<s1.length-1){
								sb.append(s1[i]);
								sb.append(" ");
								i++;
							}
							sb.setLength(sb.length() - 1);
							try{
								s=trabalho.autenticar(sb.toString(),s1[i]);	
								util = trabalho.getUtilizador(sb.toString().hashCode());
								this.chat = util.getChat();
								serverWriter.setChat(util.getChat());
								serverWriter.setLoop(true);
								chat.addMensagens(s);
							}
							catch(InvalidAccountException | InvalidPasswordException e){
								chat.addMensagens(e.toString());
							}
							break;
						case "r":
							sb = new StringBuilder();
							i=1;
							while(i<s1.length-1){
								sb.append(s1[i]);
								sb.append(" ");
								i++;
							}
							sb.setLength(sb.length() - 1);
							try{
								s=trabalho.registar(sb.toString(),s1[i]);
								chat.addMensagens(s);
							}
							catch(Exception e){
								chat.addMensagens(e.toString());
							}
							break;
						case "i":
							try{
								sb = new StringBuilder();
								i=1;
								while(i<s1.length){
									sb.append(s1[i]);
									sb.append(" ");
									i++;
								}
								sb.setLength(sb.length() - 1);
								s=(""+trabalho.iniciarLeilao(util.getUsername(),sb.toString()));
								chat.addMensagens(s);
							}
							catch(Exception e){
								chat.addMensagens(e.toString());
							}
							break;
						case "listar":
							try{
								ArrayList<String> array = new ArrayList<String>();
								int length = trabalho.listar(util.getUsername(),array);
								int aux=0;
								chat.addMensagens("listar");
								chat.addMensagens(""+length);
								while(aux<length){
									chat.addMensagens(array.get(aux));
									aux++;
								}
							}
							catch(InterruptedException e)
							{
								chat.addMensagens(e.toString());
							}
							break;
						case "l":
							try{
								s = trabalho.licitacao(chat,util.getUsername(),Integer.parseInt(s1[1]),Integer.parseInt(s1[2]));
								chat.addMensagens(s);
							}
							catch(InvalidLeilaoException | InterruptedException e){
								chat.addMensagens("Leilão inválido");
							}
							break;
						case "f":
							try{
								s=trabalho.fecharLeilao(util.getUsername(),Integer.parseInt(s1[1]));
								chat.addMensagens(s);
							}
							catch(InvalidIniciadorException | InvalidLeilaoException | InterruptedException e){
								chat.addMensagens(e.toString());								
							}
								break;
						case "help":
							chat.addMensagens("help");
							break;
						case "erro":
							chat.addMensagens("Comando inválido.");
							chat.addMensagens("help");
							break;
						case "h":
							try{
								List<String> array = null;
								try{
									array = trabalho.historico(Integer.parseInt(s1[1]));
									int aux=0;
									int length = array.size();
									chat.addMensagens("h");
									chat.addMensagens(""+length);
									while(aux<length){
										chat.addMensagens(array.get(aux));
										aux++;
									}
								}
								catch(InvalidLeilaoException e){
									chat.addMensagens("Leilão inválido");
								}
							}
							catch(InterruptedException e)
							{
								chat.addMensagens(e.toString());
							}
							break;
						case "sair":
							chat.addMensagens("sair");
							serverWriter.setLoop(false);
							break;
						default:
							return;
					}
				}
				else
					chat.addMensagens("Não tem sessão iniciada");
				if(s1[0].equals("sair")){
					break;
				}
			}
			cliente.shutdownOutput();
			cliente.close();
		}
		catch(Exception e){}
	}
}