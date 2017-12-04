/**
 * 
 * This is a subclass of Hand class and models a hand of pair type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class Pair extends Hand{
    
	 
	/**
    *  Creates and returns an instance of Pair hand.
	* @param player player for whom this pair hand is made
	* @param cards  list of cards used to model a pair hand
    */
	Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	
	private static final long serialVersionUID = 1L;
	
	 /**
	 * A method to check if this is a valid Pair hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public  boolean isValid()
	{
		if(this.size()==2)
		{  if(this.getCard(0).getRank()==this.getCard(1).getRank())
			return true;
		
		else
			return false;
		}
		
		else
		{
			return false;
		}
	}
	
	/**
	* A method to return a string containing the name Pair
	* 
	* @return A string containing the name of this pair hand
	*/
	public String getType()
	{
		return "Pair";
	}
	
	

}
