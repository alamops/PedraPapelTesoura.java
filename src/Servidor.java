import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Servidor
{
	static final int NUM_MAX_PLAYERS = 2;
	
	String winner;
	static boolean empate = false;
	static Escolha escolhaGanhadora = null;
	
	static public void rodaPartida(ArrayList<Player> listaJogadores)
	{
		for(Player p : listaJogadores)
		{
			p.start();
		}
	}
	
	static public Escolha analisaPartida(ArrayList<Player> listaJogadores)
	{
		ArrayList<Escolha> escolhas = new ArrayList<Escolha>();
		
		//Pega as escolhas dos jogadores e atribui a uma lista
		for(Player p : listaJogadores)
		{
			escolhas.add(p.getEscolha());
		}
		
		//Compara cada escolha, sobrando apenas a vitoriosa ou um empate
		for(Escolha e : escolhas)
		{
			if(escolhaGanhadora == null)
				escolhaGanhadora = e;
			else
			{
				if(escolhaGanhadora.getType() == e.getType())
				{
					empate = true;
				}
				else
				{
					if(escolhaGanhadora.getWinner() == e.getLoser())
					{
						escolhaGanhadora = e;
						empate = false;
					}
				}
			}
		}
		
		return escolhaGanhadora;
	}
	
	public static void main(String[] args)
	{
		ServerSocket serverSocket;
		ArrayList<Player> listaJogadores = new ArrayList<Player>();
		BlockingQueue<Escolha> partida = new ArrayBlockingQueue<Escolha>(NUM_MAX_PLAYERS);
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
				ObjectInputStream object_entrada = new ObjectInputStream(novaConexao.getInputStream());
				ObjectOutputStream object_saida = new ObjectOutputStream(novaConexao.getOutputStream());
				
				//Pedir Nome
				String nome = stream_entrada.readUTF();
				
				//Cria objeto Jogador
				Player novoJogador = new Player(nome, novaConexao, stream_entrada, stream_saida, object_entrada, object_saida, partida);
				
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
		//Roda solicitações dos Players
		rodaPartida(listaJogadores);
		
		//Analisa as escolhas dos jogadores
		escolhaGanhadora = analisaPartida(listaJogadores);
		
		
	}

}
