
/**
 * 
 * This is a subclass of Hand class and models a hand of single type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class Single extends Hand{

	 
	/**
	 *  Creates and returns an instance of single hand.
	 * @param player player for whom this single hand is made
	 * @param cards  list of cards used to model a single hand
	 */
	Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	
	private static final long serialVersionUID = 1L;
	
	
	   
	  /**
	  * A method to check if this is a valid single hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public  boolean isValid()
	{
		if(this.size()==1)
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	
	
	
	/**
	    * A method to return a string containing the name Single
	    * 
	    * @return A string containing the name of this single hand
	    */
	public String getType()
	{
		return "Single";
	}
	
	

}
