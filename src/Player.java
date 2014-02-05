import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class Player extends Thread
{
	public String nome;
	private Escolha escolha;
	public int pontuacao = 0;
	public Socket conexao;
	public DataInputStream stream_entrada;
	public DataOutputStream stream_saida;
	public ObjectInputStream object_entrada;
	public ObjectOutputStream object_saida;
	public BlockingQueue<Escolha> partida;
	
	private volatile boolean isRunning = true;
	
	public Player(String nome)
	{
		this.nome = nome;
	}
	
	public Player(String nome, Socket conexao, DataInputStream stream_entrada, DataOutputStream stream_saida, ObjectInputStream object_entrada, ObjectOutputStream object_saida, BlockingQueue<Escolha> partida)
	{
		this.nome = nome;
		this.conexao = conexao;
		this.stream_entrada = stream_entrada;
		this.stream_saida = stream_saida;
		this.object_entrada = object_entrada;
		this.object_saida = object_saida;
		this.partida = partida;
	}
	
	public String getNome()
	{
		return this.nome;
	}
	
	public void setEscolha(Escolha escolha)
	{
		this.escolha = escolha;
	}
	
	public Escolha getEscolha()
	{
		return this.escolha;
	}
	
	public void addPonto()
	{
		this.pontuacao++;
	}
	
	@Override
	public void run()
	{
		while(isRunning)
		{
			try
			{
				//Envia o sinal para o player fazer sua escolha
				this.stream_saida.writeInt(1);
				
				//Recebe a escolha do Player
				this.escolha = (Escolha) this.object_entrada.readObject();
				
				//Define o este player como dono da escolha
				this.escolha.setDono(this);
				
				//Adiciona na partida
				partida.put(this.escolha);
			}
			catch(IOException ex)
			{
				System.out.println("Um jogador se retirou");
				break;
			}
			catch(Exception ex)
			{
				System.out.println("Impossível de interpretar a escolha deste player.");
				break;
			}
		}
	}
	
	public void kill()
	{
		this.isRunning = false;
	}
}
