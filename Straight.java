/**
 * 
 * This is a subclass of Hand class and models a hand of straight type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class Straight extends Hand {

	/**
	 *  Creates and returns an instance of straight hand.
	 * @param player player for whom this straight hand is made
	 * @param cards  list of cards used to model a straight hand
	 */
	Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	
	private static final long serialVersionUID = 1L;

	/**
	  * A method to check if this is a valid straight hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		if(this.size()==5)
		{ 
			this.sort();
			if((this.getCard(0).getRank()==10)&&(this.getCard(1).getRank()==11)&&(this.getCard(2).getRank()==12)&&(this.getCard(3).getRank()==0)&&(this.getCard(4).getRank())==1)
					{
				      return true;
					}
			else if(((this.getCard(1).getRank()-this.getCard(0).getRank())==1)&&((this.getCard(2).getRank()-this.getCard(1).getRank())==1)&&((this.getCard(3).getRank()-this.getCard(2).getRank())==1)&&((this.getCard(4).getRank()-this.getCard(3).getRank())==1))
				{
					return true;
				}
				
				else
				{
					return false;
				}
			
			}
		
		else
		{
			return false;
		}
	}

	/**
	    * A method to return a string containing the name Straight
	    * 
	    * @return A string containing the name of this straight hand
	    */
	public String getType() {
		return "Straight";
	}
	


}
