
public class Tesoura extends Escolha
{
	public String nome = "Tesoura";
	Pedra winner = new Pedra();
	Papel loser = new Papel();

	public Player dono;
	
	@Override
	public void setDono(Player dono)
	{
		this.dono = dono;
	}
	
	@Override
	public Class<? extends Escolha> getWinner()
	{
		return winner.getClass();
	}
	
	@Override
	public Class<? extends Escolha> getLoser()
	{
		return loser.getClass();
	}
	
	@Override
	public Class<? extends Escolha> getType()
	{
		return this.getClass();
	}
}
