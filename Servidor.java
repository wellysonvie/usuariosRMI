
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Servidor extends UnicastRemoteObject implements InterfaceServidor, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Usuario> usuarios;
	TratarArquivo base = new TratarArquivo();
	
	public Servidor() throws RemoteException {
		this.usuarios = base.carregarUsuarios();
	}
	
	public static void main(String[] args) {
		
		try{
			System.out.println("Servidor iniciado!");
			
			Registry registry = LocateRegistry.getRegistry();
			
			registry.rebind("Servidor_1", new Servidor());
		}
		catch(Exception e){
			System.out.println("Erro na inicializacao do servidor!");
			e.printStackTrace();
		}
	}

	@Override
	public void adduser(String nome, String senha) throws RemoteException {
		
		Usuario u = new Usuario(nome, senha);
		usuarios.add(u);
		base.atualizarArquivo(this.usuarios);
	}

	@Override
	public String help() throws RemoteException {
		
		String resultado = "\n HELP - Retorna os comandos suportados\n"
				+ " Uso: HELP\n\n"
				+ " ADDUSER - Adiciona um usuário à base\n"
				+ " Uso: ADDUSER [nome] [senha]\n\n"
				+ " LISTUSER - Lista todos os usuários já cadastrados\n"
				+ " Uso: LISTUSER\n\n"
				+ " DELUSER - Remove um usuário da base\n"
				+ " Uso: DELUSER [nome] [senha]\n\n"
				+ " PASS - Altera/adiciona uma senha para um usuário existente\n"
				+ " Uso: PASS [nome] [senha] [nova senha]\n\n"
				+ " VERIFY - Checa se um par usuario/senha é valido na base de dados.\n"
				+ " Uso: VERIFY [nome]\n\n"
				+ " EXIT - Terminar a execucao.\n"
				+ " Uso: EXIT\n";
		
		return resultado;
	}

	@Override
	public String listuser() throws RemoteException {
		
		String resultado = "\n";
		
		for(int i = 0; i < usuarios.size(); i++){
			resultado += " "+i+" - "+usuarios.get(i).nome+"\n";
		}
		
		return resultado;
	}

	@Override
	public Boolean deluser(String nome, String senha) throws RemoteException {
		
		for(Usuario u : usuarios){
			if(u.nome.equals(nome) && u.senha.equals(senha)){
				usuarios.remove(u);
				base.atualizarArquivo(this.usuarios);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Boolean pass(String nome, String senha, String novaSenha) throws RemoteException {
		
		for(Usuario u : usuarios){
			if(u.nome.equals(nome) && u.senha.equals(senha)){
				u.senha = novaSenha;
				base.atualizarArquivo(this.usuarios);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Boolean verify(String nome) throws RemoteException {
		
		for(Usuario u : usuarios){
			if(u.nome.equals(nome))
				return false;
		}
		
		return true;
	}
	
	public Boolean login(String nome, String senha) throws RemoteException {
		
		for(Usuario u : usuarios){
			if(u.nome.equals(nome) && u.senha.equals(senha))
				return true;
		}
		
		return false;
	}

}
