/**
 * 
 * This is a subclass of Hand class and models a hand of flush type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class Flush extends Hand {
    
	/**
	 *  Creates and returns an instance of flush hand.
	 * @param player player for whom this flush hand is made
	 * @param cards  list of cards used to model a flush hand
	 */
	Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	
	private static final long serialVersionUID = 1L;

	 /**
		 * A method to check if this is a valid Flush hand. It overrides its form in Hand class
		 * @return true if valid, false otherwise
		 */
	public boolean isValid() {
		if(this.size()==5)
		{int suit_val = this.getCard(0).getSuit();
		for(int i=1 ; i<5 ; i++)
		{
			if(this.getCard(i).getSuit()!=suit_val)
			{
				return false;
			}
		}
		return true;
		
		}
		else
		return false;
	}

	/**
	* A method to return a string containing the name Flush
	* 
	* @return A string containing the name of this flush hand
	*/
	public String getType() {
		return "Flush";
	}
	
	

}
