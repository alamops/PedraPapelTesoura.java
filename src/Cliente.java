import java.io.*;
import java.net.*;

public class Cliente {

	public static void main(String[] args) {
		try
		{
			Socket novaConexao = new Socket("localhost", 5000);
		}
		catch (Exception e)
		{
			System.out.println("N�o foi poss�vel estabelecer uma conex�o com o servidor.");
			return;
		}
	}

}
