import java.net.*;
import java.io.*;

public class Server {

	public static void main(String[] args) throws Exception {
		int portNumber = Integer.parseInt(args[0]);
		ServerSocket srv = new ServerSocket(portNumber);
		Trabalho trabalho = new Trabalho();
		ChatMensagens chat = null;
		while(true) {
			chat = new ChatMensagens();
			Socket cliente = srv.accept();
			ServerWriter serverWriter = new ServerWriter(cliente, chat);
			ServerReader serverReader = new ServerReader(cliente, trabalho, serverWriter, chat);
			serverWriter.start();
			serverReader.start();
		}
	}
}