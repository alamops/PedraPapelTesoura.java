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
	public BlockingQueue<Escolha> partida;
	//public int partida;
	
	public Player(ServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
		try
		{
			this.conexao = this.serverSocket.accept();
			
			this.data_input = new DataInputStream(this.conexao.getInputStream());
			this.data_output = new DataOutputStream(this.conexao.getOutputStream());
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
	
	//public Player(String nome, Socket conexao, BlockingQueue<Escolha> partida)
	//{
	//this.nome = nome;
	//this.conexao = conexao;
	//this.partida = partida;
	//}
	
	public void pedirNome() throws IOException, ClassNotFoundException
	{
		String nome = this.data_input.readUTF();
		System.out.println(nome + " entrou na partida!");
		
		this.nome = nome;
	}
	
	public String getNome()
	{
		return this.nome;
	}
	
	public void anunciaResultado(Player p, Escolha e, String situacao) throws IOException
	{
		String resultado = "Você " + situacao + " de " + p.getNome() + " que escolheu " + e.getType();
		this.data_output.writeUTF(resultado);
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
			this.data_output.writeInt(99);
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
			this.data_output.writeInt(1);
			
			//Recebe a escolha do Player
			String escolhaPlayer = this.data_input.readUTF();
			System.out.println(this.nome + " escolheu " + escolhaPlayer);
			
			switch(escolhaPlayer)
			{
				case "Pedra":
					this.escolha = new Pedra();
					break;
					
				case "Papel":
					this.escolha = new Papel();
					break;
					
				case "Tesoura":
					this.escolha = new Tesoura();
					break;
			}
			System.out.println(this.nome + " escolha com Type " + this.escolha.getType());
			
			//Define o este player como dono da escolha
			this.escolha.setDono(this);
			System.out.println(this.nome + " o dono da escolha é " + this.escolha.getDono().getNome());
			
			//Adiciona na partida
			partida.put(this.escolha);
			System.out.println(this.nome + " escolha adicionada na partida " + this.escolha.getType());
			//this.partida++;
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
