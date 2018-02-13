import java.net.*;
import java.io.*;
import java.util.*;

public class ClienteWriter extends Thread{

	private Socket cliente;
			
	public ClienteWriter(Socket cliente){
		this.cliente=cliente;
	}

	public void run(){
		try{
			int i=1;
			StringBuilder sb = new StringBuilder();
			PrintWriter out = new PrintWriter(cliente.getOutputStream());
			while (true){
				String s = System.console().readLine();
				if(s == null)
					break;
				String[] s1 = s.split(" ");
				s1=valida(s1);
				switch(s1[0]) {
					case "a":
						i=1;
						sb = new StringBuilder();
						while(i<s1.length-1){
							sb.append(s1[i]);
							sb.append(" ");
							i++;
						}
						sb.setLength(sb.length() - 1);
						out.println("a " + sb.toString() + " " + s1[i]);
						out.flush();
						break;
					case "r":
						i=1;
						sb = new StringBuilder();
						while(i<s1.length-1){
							sb.append(s1[i]);
							sb.append(" ");
							i++;
						}
						sb.setLength(sb.length() - 1);
						out.println("r " + sb.toString() + " " + s1[i]);
						out.flush();
						break;
					case "i":
						i=1;
						sb = new StringBuilder();
						while(i<s1.length){
							sb.append(s1[i]);
							sb.append(" ");
							i++;
						}
						sb.setLength(sb.length() - 1);
						out.println("i " + sb.toString());
						out.flush();
						break;
					case "listar":
						out.println("listar");
						out.flush();
						break;
					case "l":
						out.println("l " + s1[1] + " " + s1[2]);
						out.flush();
						break;
					case "f":
						out.println("f " + s1[1]);
						out.flush();
						break;
					case "help":
						out.println("help");
						out.flush();
						break;
					case "sair":
						out.println("sair");
						out.flush();
						break;
					case "erro":
						out.println("erro");
						out.flush();
						break;
					case "h":
						out.println("h "+s1[1]);
						out.flush();
						break;
					default:
						out.println("erro");
						out.flush();
						break;
				}
				if(s1[0].equals("sair"))
					break;
			}
			cliente.shutdownOutput();
			cliente.close();
		} catch(Exception e){}
	}

	public String[] valida(String[] s){
		switch(s[0]){
			case "r":
				if(s.length<3)
					s[0]="erro";
				return s;
			case "a":
				if(s.length<3)
					s[0]="erro";
				return s;
			case "i":
				if(s.length<2)
					s[0]="erro";
				return s;
			case "listar":
				if(s.length<1)
					s[0]="erro";
				return s;
			case "l":
				if(s.length<3){
					s[0]="erro";
					return s;
				}
				try{
					Integer.parseInt(s[2]);
				}
				catch(NumberFormatException e){
					s[0]="erro";
				}
				return s;
			case "f":
				if(s.length<2)
					s[0]="erro";
				try{
					Integer.parseInt(s[1]);
				}
				catch(NumberFormatException e){
					s[0]="erro";
				}
				return s;
			case "sair":
				if(s.length<1)
					s[0]="erro";
				return s;
			case "h":
				if(s.length<2)
					s[0]="erro";
				try{
					Integer.parseInt(s[1]);
				}
				catch(NumberFormatException e){
					s[0]="erro";
				}
				return s;
			default:
				s[0]="erro";
				return s;
			}
	}
}