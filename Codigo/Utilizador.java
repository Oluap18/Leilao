public class Utilizador{

	private String username;
	private String password;
	private ChatMensagens chat;

	public Utilizador (String u, String p){
		this.username = u;
		this.password = p;
		this.chat = new ChatMensagens();
	}

	public String autenticar(String u) throws InvalidPasswordException{
		if(password.equals(u)==false)
			throw new InvalidPasswordException("Password inválida");
		return "Autenticado.";
	}

	public ChatMensagens getChat(){
		return this.chat;
	}

	public void setUsername(String u){
		this.username=u;
	}

	public void setPassword(String p){
		this.password=p;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}
}