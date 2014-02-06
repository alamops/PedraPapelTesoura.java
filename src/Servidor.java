import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Servidor
{
	static final int NUM_MAX_PLAYERS = 2;
	
	static Player winner, loser;
	static boolean empate = false;
	static Escolha escolhaGanhadora, escolhaPerdedora = null;
	
	static public void rodaPartida(ArrayList<Player> listaJogadores)
	{
		System.out.println("Rodando a partida...");
		for(Player p : listaJogadores)
		{
			p.start();
		}
	}
	
	static public void analisaPartida(ArrayList<Player> listaJogadores)
	{
		ArrayList<Escolha> escolhas = new ArrayList<Escolha>();
		System.out.println("Analisando a partida...");
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
					if(escolhaGanhadora.getWinner() == e.getType())
					{
						escolhaPerdedora = escolhaGanhadora;
						escolhaGanhadora = e;
						empate = false;
					}
					else
					{
						escolhaPerdedora = e;
						empate = false;
					}
				}
			}
		}
		
		//seleciona winner se empate = false
		if(!empate)
		{
			winner = escolhaGanhadora.getDono();
			loser = escolhaPerdedora.getDono();
		}
	}
	
	static public void anunciaResultado(ArrayList<Player> listaJogadores) throws IOException
	{
		for(Player p : listaJogadores)
		{
			if(winner.hashCode() == p.hashCode())
			{
				p.anunciaResultado(loser, escolhaPerdedora, "ganhou");
			}
			else
			{
				p.anunciaResultado(winner, escolhaGanhadora, "perdeu");
			}
		}
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
			
			while(contPlayers < NUM_MAX_PLAYERS)
			{
				try
				{
					//abre socket
					Player novoJogador = new Player(serverSocket);
					
					//Pedir Nome
					novoJogador.pedirNome();
					
					//Cria objeto Jogador
					novoJogador.selecionarPartida(partida);
					
					//add jogador a lista de jogadores online
					listaJogadores.add(novoJogador);
					contPlayers++;
					
					novoJogador.esperar();
				}
				catch(Exception ex)
				{
					System.out.println("Servidor1: " + ex.toString());
					return;
				}
			}
			
			//Jogando...
			//Roda solicitações dos Players
			rodaPartida(listaJogadores);
			
			//Aguarda as escolhas
			int i = 0;
			while(partida.size() < 2)
			{
				if(i == 0)
				{
					System.out.println("Aguardando escolhas");
					i++;
				}
			}
			
			//Analisa as escolhas dos jogadores
			analisaPartida(listaJogadores);
			
			//CONTINUAR
			if(empate)
			{
				System.out.println("Empatou!");
			}
			else
			{
				anunciaResultado(listaJogadores);
			}
			
			
			serverSocket.close();
		}
		catch (Exception ex)
		{
			System.out.println("Servidor2: " + ex.toString());
			return;
		}
	}

}
