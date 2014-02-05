
public class Pedra extends Escolha
{
	public String nome = "Pedra";
	Papel winner = new Papel();
	Tesoura	loser = new Tesoura();
	
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
