import java.io.*;
import java.net.*;

public class Jogo
{
	
	public static void main(String[] args)
	{
		ServerSocket serverSocket;
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
		
		while(contPlayers < 2)
		{
			try
			{
				Socket novaConexao = serverSocket.accept();
				
				//criar novo player
				
				contPlayers++;
			}
			catch(IOException ex)
			{
				System.out.println("Não foi possível abrir um novo socket!");
				return;
			}
		}
	}

}
