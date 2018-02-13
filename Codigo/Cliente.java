import java.net.*;
import java.io.*;


public class Cliente {

	public static void main(String[] args) throws Exception {
		int portNumber = Integer.parseInt(args[1]);
		String host = args[0];
		Socket cliente = new Socket(host,portNumber);
		ClienteWriter clienteWriter = new ClienteWriter(cliente);
		ClienteReader clienteReader = new ClienteReader(cliente);
		clienteWriter.start();
		clienteReader.start();
	}
}