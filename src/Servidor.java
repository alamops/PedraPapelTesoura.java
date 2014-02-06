import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
	
	static public void analisaPartida(ArrayList<Player> listaJogadores)
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
					if(escolhaGanhadora.getWinner() == e.getType())
					{
						escolhaGanhadora = e;
						empate = false;
					}
				}
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
					//Socket novaConexao = serverSocket.accept();
					Player novoJogador = new Player(serverSocket);
					
					//Cria streams de entrada e saida
					//DataInputStream data_input;
					
					//Pedir Nome
					novoJogador.pedirNome();
					
					//Cria objeto Jogador
					//Player novoJogador = new Player(nome, novaConexao, partida);
					novoJogador.selecionarPartida(partida);
					//System.out.println("added novo jogador");
					
					//add jogador a lista de jogadores online
					listaJogadores.add(novoJogador);
					//System.out.println("colocado na lista");
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
			
			//Analisa as escolhas dos jogadores
			//analisaPartida(listaJogadores);
			
			//CONTINUAR
			
			
			
			//serverSocket.close();
		}
		catch (Exception ex)
		{
			System.out.println("Servidor2: " + ex.toString());
			return;
		}
	}

}
