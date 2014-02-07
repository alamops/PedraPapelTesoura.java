import java.awt.BorderLayout;
import java.awt.Container;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Cliente {
	
	static Escolha escolhaPlayer = null;
	static boolean sair = false;

	public static void main(String[] args) {
		try
		{
			//Criar socket com servidor
			Socket novaConexao = new Socket("127.0.0.1", 12345);
			//Escolha escolhaPlayer = null;
			//boolean sair = false;
			
			//Cria variaves de strams
			DataInputStream data_input = new DataInputStream(novaConexao.getInputStream());;
			DataOutputStream data_output = new DataOutputStream(novaConexao.getOutputStream());;
			
			//Solicita nome
			Scanner scan = new Scanner(System.in);
			//System.out.println("Escreva seu nome: ");
			//String nome = scan.nextLine();
			String nome = JOptionPane.showInputDialog("Qual o seu nome?");
			
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
						new Interface(nome, data_input, data_output, escolhaPlayer);
						break;
					
					case 99:
							//JOptionPane.showMessageDialog(null, "Aguardando o adversário...");
							System.out.println("Aguardando o adversário...");
						break;
				}
			}
			
			//Pegando os resultados
			JOptionPane.showMessageDialog(null, data_input.readUTF());
			
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
