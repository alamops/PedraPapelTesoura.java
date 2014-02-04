import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Jogo
{
	static final int NUM_MAX_PLAYERS = 2;
	
	public static void main(String[] args)
	{
		ServerSocket serverSocket;
		ArrayList<Player> listaJogadores = new ArrayList<Player>();
		int contPlayers = 0;
		
		try
		{
			serverSocket = new ServerSocket(12345);
		}
		catch (IOException ex)
		{
			System.out.println("Não foi possível abrir o ServerSocket!");
			return;
		}
		
		while(contPlayers < NUM_MAX_PLAYERS)
		{
			try
			{
				//abre socket e streams de entrada e saida
				Socket novaConexao = serverSocket.accept();
				DataInputStream stream_entrada = new DataInputStream(novaConexao.getInputStream());
				DataOutputStream stream_saida = new DataOutputStream(novaConexao.getOutputStream());
				
				//Pedir Nome
				String nome = stream_entrada.readUTF();
				
				//Cria objeto Jogador
				Player novoJogador = new Player(nome, novaConexao, stream_entrada, stream_saida);
				
				//add jogador a lista de jogadores online
				listaJogadores.add(novoJogador);
				
				//envia mensagem para aguardar restante dos jogador
				stream_saida.writeUTF("Aguardando adversário...");
				
				contPlayers++;
			}
			catch(IOException ex)
			{
				System.out.println("Não foi possível abrir um novo socket!");
				return;
			}
		}
		
		//Jogando...
		//inicia partida
		//CLASSE PARTIDA
	}

}
