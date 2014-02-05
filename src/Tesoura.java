
public class Tesoura extends Escolha
{
	public String nome = "Tesoura";
	Escolha winner = new Pedra();
	Escolha	loser = new Papel();

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
}
