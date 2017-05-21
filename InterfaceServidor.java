
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServidor extends Remote{
	
	public void adduser(String nome, String senha) throws RemoteException;
	public String help() throws RemoteException;
	public String listuser() throws RemoteException;
	public Boolean deluser(String nome, String senha) throws RemoteException;
	public Boolean pass(String nome, String senha, String novaSenha) throws RemoteException;
	public Boolean verify(String nome) throws RemoteException;
	public Boolean login(String nome, String senha) throws RemoteException;
}
