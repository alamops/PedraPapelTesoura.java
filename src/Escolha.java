
public abstract class Escolha
{
	public abstract Class<? extends Escolha> getWinner();
	public abstract Class<? extends Escolha> getLoser();
	public abstract Class<? extends Escolha> getType();
	public abstract void setDono(Player dono);
}
