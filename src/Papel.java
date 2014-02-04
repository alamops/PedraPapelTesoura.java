
public class Papel extends Escolha
{
	public String nome = "Papel";
	
	@Override
	public void getWinner()
	{
		Tesoura.class.getClass();
	}
	
	@Override
	public void getLoser()
	{
		Pedra.class.getClass();
	}
}
