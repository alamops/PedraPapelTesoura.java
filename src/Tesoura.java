
public class Tesoura extends Escolha
{
	public String nome = "Tesoura";
	String winner = Pedra.class.getName();
	String loser = Papel.class.getName();

	public Player dono;
	
	@Override
	public void setDono(Player dono)
	{
		this.dono = dono;
	}
	
	@Override
	public Player getDono()
	{
		return this.dono;
	}
	
	@Override
	public String getWinner()
	{
		return winner;
	}
	
	@Override
	public String getLoser()
	{
		return loser;
	}
	
	@Override
	public String getType()
	{
		return this.getClass().getName();
	}
}
