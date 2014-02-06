import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Interface extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3878459561150775471L;
	
	DataInputStream data_input;
	DataOutputStream data_output;
	
	JLabel logo;
	JButton pedra, papel, tesoura;
	Escolha escolha;
	
	public Interface(DataInputStream data_input, DataOutputStream data_output, Escolha escolha) {
		super("Pedra - Papel - Tesoura");
		
		this.data_input = data_input;
		this.data_output = data_output;
		this.escolha = escolha;
		
		//inicialização dos botões
		this.pedra = new JButton("Pedra");
		this.papel = new JButton("Papel");
		this.tesoura = new JButton("Tesoura");
		
		//add eventos
		this.pedra.addActionListener(new BotaoPedraListener());
		this.papel.addActionListener(new BotaoPapelListener());
		this.tesoura.addActionListener(new BotaoTesouraListener());
		
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(2, 1));
		
		ImageIcon icon = new ImageIcon(getClass().getResource("img/logo.jpg"));
		this.logo = new JLabel(icon);
		
		Container c2 = new JPanel();
		c2.setLayout(new GridLayout(1, 3));
		
		c2.add(this.pedra);
		c2.add(this.papel);
		c2.add(this.tesoura);
		
		c.add(this.logo);
		c.add(c2);
		
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(600, 500);
		this.setVisible(true);
	}
	
	public void fechar() {
		this.setVisible(false);
		pegarResultado();
	}
	
	public void pegarResultado() {
		//JOptionPane.showMessageDialog(null, "Aguardando escolha do oponente...");
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	class BotaoPedraListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			escolha = new Pedra();
			try {
				data_output.writeUTF(escolha.getType());
				//JOptionPane.showMessageDialog(null, "Você escolheu Pedra!");
				//JOptionPane.showMessageDialog(null, "Aguardando escolha do oponente...");
				fechar();
			} catch (IOException e1) {
				System.out.println("Não foi possível enviar a escoha ao servidor");
			}			
		}
		
	}
	
	class BotaoPapelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			escolha = new Papel();
			try {
				data_output.writeUTF(escolha.getType());
				//JOptionPane.showMessageDialog(null, "Você escolheu Papel!");
				//JOptionPane.showMessageDialog(null, "Aguardando escolha do oponente...");
				fechar();
			} catch (IOException e1) {
				System.out.println("Não foi possível enviar a escoha ao servidor");
			}			
		}
		
	}
	
	class BotaoTesouraListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			escolha = new Tesoura();
			try {
				data_output.writeUTF(escolha.getType());
				//JOptionPane.showMessageDialog(null, "Você escolheu Tesoura!");
				//JOptionPane.showMessageDialog(null, "Aguardando escolha do oponente...");
				fechar();
			} catch (IOException e1) {
				System.out.println("Não foi possível enviar a escoha ao servidor");
			}			
		}
		
	}

}
