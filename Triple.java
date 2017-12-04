/**
 * 
 * This is a subclass of Hand class and models a hand of triple type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class Triple extends Hand{
   
	/**
	 *  Creates and returns an instance of triple hand.
	 * @param player player for whom this triple hand is made
	 * @param cards  list of cards used to model a triple hand
	 */
	Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	
	private static final long serialVersionUID = 1L;

	/**
	  * A method to check if this is a valid triple hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		// TODO Auto-generated method stub
		if(this.size()==3)
		{   int a1 = this.getCard(0).getRank();
			if(this.getCard(1).getRank()==a1 && this.getCard(2).getRank()==a1)
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		else
			return false;
	}

	/**
	    * A method to return a string containing the name Triple
	    * 
	    * @return A string containing the name of this triple hand
	    */
	public String getType() {
		// TODO Auto-generated method stub
		return "Triple";
	}
	


	
}
