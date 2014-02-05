
public class Pedra extends Escolha
{
	public String nome = "Pedra";
	Escolha winner = new Papel();
	Escolha	loser = new Tesoura();
	
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
