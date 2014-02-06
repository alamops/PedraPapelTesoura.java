import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		try
		{
			//Criar socket com servidor
			Socket novaConexao = new Socket("127.0.0.1", 12345);
			Escolha escolhaPlayer = null;
			boolean sair = false;
			
			//Cria variaves de strams
			DataInputStream data_input = new DataInputStream(novaConexao.getInputStream());;
			DataOutputStream data_output = new DataOutputStream(novaConexao.getOutputStream());;
			
			//Solicita nome
			Scanner scan = new Scanner(System.in);
			System.out.println("Escreva seu nome: ");
			String nome = scan.next();
			
			//Envia nome
			data_output.writeUTF(nome);
			
			//loop para o jogo
			int opcaoServidor = -1;
			int opcaoPlayer = -1;
			while(opcaoServidor != 1 && opcaoPlayer != 0)
			{
				//Le o sinal vindo do servidor
				opcaoServidor = data_input.readInt();
				
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
							else
							{
								data_output.writeUTF(escolhaPlayer.getType());
							}
						break;
					
					case 99:
							System.out.println("Aguardando o adversário...");
						break;
				}
			}
			
			//Pegando os resultados
			System.out.println(data_input.readUTF());
			
			scan.close();
			novaConexao.close();
		}
		catch (Exception e)
		{
			System.out.println("Fim da partida!");
			return;
		}
	}

}
