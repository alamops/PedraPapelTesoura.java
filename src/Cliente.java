import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		try
		{
			//Criar socket com servidor
			Socket novaConexao = new Socket("127.0.0.1", 12345);
			Escolha escolhaPlayer;
			boolean sair = false;
			
			//Cria variaves de strams
			DataInputStream data_input;
			DataOutputStream data_output;
			ObjectInputStream object_input;
			ObjectOutputStream object_output;
			
			//Cria streams de entrada e saida
			data_output = new DataOutputStream(novaConexao.getOutputStream());
			
			//Solicita nome
			Scanner scan = new Scanner(System.in);
			System.out.println("Escreva seu nome: ");
			String nome = scan.next();
			
			//Envia nome
			data_output.writeUTF(nome);
			data_output.close();
			
			//loop para aguardar adversario
			//
			
			//loop para o jogo
			data_input = new DataInputStream(novaConexao.getInputStream());
			int opcaoServidor = -1;
			int opcaoPlayer = -1;
			while(true)
			{
				//Le o sinal vindo do servidor
				opcaoServidor = data_input.readInt();
				data_input.close();
				
				switch(opcaoServidor)
				{
					case 1:
						//lança opções
							System.out.println("Faça sua escolha: ");
							System.out.println("1 -> Pedra");
							System.out.println("2 -> Papel");
							System.out.println("3 -> Tesoura");
							System.out.println("0 -> Sair");
							opcaoPlayer = scan.nextInt();
						
						//Interpreta escolha do jogador
							switch(opcaoPlayer)
							{
								case 1:
										escolhaPlayer = new Pedra();
									break;
									
								case 2:
										escolhaPlayer = new Papel();
									break;
								
								case 3:
										escolhaPlayer = new Tesoura();
									break;
									
								case 0:
										sair = true;
									break;
									
								default:
									escolhaPlayer = new Pedra();
							}
							
						//Define escolha do usuário
							if(sair)
							{
								System.out.println("Saindo do servidor...");
								novaConexao.close();
								scan.close();
							}
						break;
				}
			}
			//novaConexao.close();
			//scan.close();
		}
		catch (Exception e)
		{
			System.out.println("Cliente1: " + "Não foi possível estabelecer uma conexão com o servidor.");
			return;
		}
	}

}
