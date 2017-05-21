
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Usuario extends UnicastRemoteObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String nome;
	String senha;
	
	public Usuario(String nome, String senha) throws RemoteException{
		this.nome = nome;
		this.senha = senha;
	}

}
