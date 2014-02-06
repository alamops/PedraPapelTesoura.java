import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class Player extends Thread
{
	public String nome;
	private Escolha escolha;
	public int pontuacao = 0;
	public ServerSocket serverSocket;
	public Socket conexao;
	public DataInputStream data_input;
	public DataOutputStream data_output;
	public ObjectInputStream object_input;
	public ObjectOutputStream object_output;
	public BlockingQueue<Escolha> partida;
	
	public Player(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
		try
		{
			this.conexao = this.serverSocket.accept();
		}
		catch(IOException e)
		{
			System.out.println("Tentativa de adicionar jogador: " + e.toString());
		}
	}
	
	public Player(String nome)
	{
		this.nome = nome;
	}
	
	public Player(String nome, Socket conexao, BlockingQueue<Escolha> partida)
	{
		this.nome = nome;
		this.conexao = conexao;
		this.partida = partida;
	}
	
	public void pedirNome() throws IOException
	{
		this.data_input = new DataInputStream(this.conexao.getInputStream());
		String nome = (String) this.data_input.readUTF();
		System.out.println(nome + " entrou na partida!");
		this.data_input.close();
		
		this.nome = nome;
	}
	
	public String getNome()
	{
		return this.nome;
	}
	
	public void selecionarPartida(BlockingQueue<Escolha> partida)
	{
		this.partida = partida;
	}
	
	public void setEscolha(Escolha escolha)
	{
		this.escolha = escolha;
	}
	
	public Escolha getEscolha()
	{
		return this.escolha;
	}
	
	public int getHash()
	{
		return this.hashCode();
	}
	
	public void addPonto()
	{
		this.pontuacao++;
	}
	
	public void esperar()
	{
		try
		{
			this.data_output = new DataOutputStream(this.conexao.getOutputStream());
			this.data_output.writeInt(99);
			this.data_output.close();
		}
		catch(IOException e)
		{
			System.out.println("Tentativa de fazer player esperar: " + e.toString());
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			//Envia o sinal para o player fazer sua escolha
			this.data_output = new DataOutputStream(this.conexao.getOutputStream());
			this.data_output.writeInt(1);
			this.data_output.close();
			
			//Recebe a escolha do Player
			this.object_input = new ObjectInputStream(this.conexao.getInputStream());
			this.escolha = (Escolha) this.object_input.readObject();
			this.object_input.close();
			
			//Define o este player como dono da escolha
			this.escolha.setDono(this);
			
			//Adiciona na partida
			partida.put(this.escolha);
		}
		catch(IOException ex)
		{
			System.out.println("Um jogador se retirou");
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Impossível de interpretar a escolha deste player.");
			return;
		}
	}
	
}
