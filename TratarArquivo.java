
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TratarArquivo {

	public TratarArquivo(){}
	
	public ArrayList<Usuario> carregarUsuarios(){
		
		ArrayList<Usuario> usuarios = new ArrayList<>();
		
		try{
			
			BufferedReader buffer = new BufferedReader(new FileReader("usuarios.txt"));
			String linha = "";
			
			while((linha = buffer.readLine()) != null){
				String[] u = linha.split(" ");
				usuarios.add(new Usuario(u[0], u[1]));
			}
			
			buffer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return usuarios;
	}
	
	public void atualizarArquivo(ArrayList<Usuario> usuarios){
		
		try{
			PrintWriter writer = new PrintWriter(new FileWriter("usuarios.txt",false));
			
			for(Usuario u : usuarios)
				writer.println(u.nome + " " + u.senha);
			
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
