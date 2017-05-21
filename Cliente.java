
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

	private static InterfaceServidor is;

	public Cliente() {

		System.out.println("Cliente iniciado!");

		try {
			Registry registry = LocateRegistry.getRegistry();
			is = (InterfaceServidor) registry.lookup("Servidor_1");
		} catch (Exception e) {
			System.out.println("Erro na inicializacao do cliente!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		new Cliente();

		Scanner ler = new Scanner(System.in);
		String cmd = "";

		try{
			while (!cmd.equals("EXIT")) {

				System.out.print("cliente:~$ ");
				cmd = ler.nextLine();
				String[] cmdTtd = tratarCmd(cmd);

				switch (cmdTtd[0]) {

				case "HELP":
					help();
					break;
				case "ADDUSER":
					addUser(cmdTtd);
					break;
				case "LISTUSER":
					listUser();
					break;
				case "DELUSER":
					delUser(cmdTtd);
					break;
				case "PASS":
					pass(cmdTtd);
					break;
				case "VERIFY":
					verify(cmdTtd);
					break;
				case "EXIT":
					System.out.println("\n Cliente finalizado!\n");
					break;
				default:
					System.out.println("Comando \'" + cmdTtd[0] + "\' nao encotrado!");
				}
			}
			
		}catch(Exception e){
			System.out.println("Ocorreu um erro!");
			e.printStackTrace();
		}

		ler.close();
	}

	public static void help() throws RemoteException {
		System.out.println(is.help());
	}

	public static void addUser(String[] cmd) throws RemoteException {

		if(! is.verify(cmd[1])){
			System.out.println("\n Nome de usuario ja cadastrado!");
		}else{
			is.adduser(cmd[1], cmd[2]);
			System.out.println("\n Usuario adicionado com sucesso!");
		}
	}

	public static void listUser() throws RemoteException {
	
		String resultado = is.listuser();
		
		if(resultado.equals("\n")){
			System.out.println("\n Nenhum usuário encontrado!\n");
		}else{
			System.out.println(resultado);
		}
	}

	public static void delUser(String[] cmd) throws RemoteException {
		
		if(is.login(cmd[1], cmd[2])){
			if(is.deluser(cmd[1], cmd[2]))
				System.out.println("\n Usuario deletado com sucesso!");
		}else{
			System.out.println("\n Usuario nao encontrado!");
		}
	}

	public static void pass(String[] cmd) throws RemoteException {
		
		if(is.login(cmd[1], cmd[2])){
			if(is.pass(cmd[1], cmd[2], cmd[3]))
				System.out.println("\n Usuario alterado com sucesso!");
		}else{
			System.out.println("\n Usuario nao encotrado!");
		}

	}

	public static void verify(String[] cmd) throws RemoteException {
		
		if(is.verify(cmd[1])){
			System.out.println("\n Nome de usuario valido para ser adicionado!");
		}else{
			System.out.println("\n Nome de usuario ja cadastrado!");
		}
	}

	public static String[] tratarCmd(String cmd) throws RemoteException {

		String[] v = cmd.split(" ");

		for (String s : v) s.trim();

		int cont = 0;
		for (String s : v) 
			if (!s.equals("")) cont++;

		String[] aux = new String[cont];

		int j = 0;
		for (String s : v) {
			if (!s.equals("")) {
				aux[j] = s;
				j++;
			}
		}

		return aux;
	}

}
