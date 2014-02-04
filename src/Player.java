import java.io.*;
import java.net.*;

public class Player
{
	public String nome;
	private Escolha escolha;
	public int pontuacao = 0;
	public Socket conexao;
	public DataInputStream stream_entrada;
	public DataOutputStream stream_saida;
	
	public Player(String nome)
	{
		this.nome = nome;
	}
	
	public Player(String nome, Socket conexao, DataInputStream stream_entrada, DataOutputStream stream_saida)
	{
		this.nome = nome;
		this.conexao = conexao;
		this.stream_entrada = stream_entrada;
		this.stream_saida = stream_saida;
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
}
