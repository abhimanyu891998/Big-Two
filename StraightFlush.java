/**
 * 
 * This is a subclass of Hand class and models a hand of StraightFlush type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class StraightFlush extends Hand {
    
	/**
	    *  Creates and returns an instance of StraightFlush hand.
		* @param player player for whom this StraightFlush hand is made
		* @param cards  list of cards used to model a StraightFlush hand
	    */
	StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	
	private static final long serialVersionUID = 1L;

	/**
	 * A method to check if this is a valid StraightFlush hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		
		boolean flush_is_valid=false; int ctr1=0;
		if(this.size()==5)
		{int suit_val = this.getCard(0).getSuit();
		for(int i=1 ; i<5 ; i++)
		{
			if(this.getCard(i).getSuit()!=suit_val)
			{
				flush_is_valid=false;
				ctr1++;
			}
		}
		if(ctr1==0)
		{flush_is_valid=true;}		
	    }
		
		if(flush_is_valid)
		{
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
			
		}
		
		return false;
	}

	/**
	* A method to return a string containing the name StraightFlush
	* 
	* @return A string containing the name of this StraightFlush hand
	*/
	public String getType() {
		
		return "StraightFlush";
		
	}
	


}
