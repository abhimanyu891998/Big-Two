/**
 * 
 * This is a subclass of Hand class and models a hand of Quad type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class Quad extends Hand {
	/**
	    *  Creates and returns an instance of Quad hand.
		* @param player player for whom this Quad hand is made
		* @param cards  list of cards used to model a Quad hand
	    */
	Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}


	private static final long serialVersionUID = 1L;

	/**
	 * A method to check if this is a valid Quad hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		if(this.size()==5)
		{
			this.sort();
			int ctr1=0; int ctr2=0;
			for(int i=0 ; i<3 ; i++)
			{
              if(this.getCard(i).getRank()!=this.getCard(i+1).getRank())
              {
            	  ctr1++;
              }
     		}
			for(int i=1 ; i<4 ; i++)
			{
              if(this.getCard(i).getRank()!=this.getCard(i+1).getRank())
              {
            	  ctr2++;
              }
     		}
			
			if(ctr1==0)
			{
				if(this.getCard(0).getRank()==this.getCard(4).getRank())
				{
					return false;
				}
				
				else
				{
					return true;
				}	
			}
			
			else if(ctr2==0)
			{
				if(this.getCard(0).getRank()==this.getCard(4).getRank())
				{
					return false;
				}
				
				else
				{
					return true;
				}
			}
				
		}
		
		
		else
		{
			return false;
		}
		
		return false;
		
	}

	/**
	* A method to return a string containing the name Quad
	* 
	* @return A string containing the name of this Quad hand
	*/
	public String getType() {
		
		return "Quad";
		
	}
	
	
	/**
	   * A method to retrieve the top card of the Quad hand. It overrides its form in Hand class
	   * 
	 * @return An object of type Card which is the topCard in this hand.
	 */
	public Card getTopCard()
	{
		if(this.getCard(0).getRank()==this.getCard(3).getRank())
		{
			int max_suit = this.getCard(0).getSuit();
			int num = 0;
			for(int i=1 ; i<4 ; i++)
			{
				if(this.getCard(i).getSuit()>max_suit)
				{
					max_suit = this.getCard(i).getSuit();
					num = i;
				}
			}
			
			return this.getCard(num);
		}
		
		else
		{
			int max_suit = this.getCard(1).getSuit();
			int num=1;
			for(int i=2 ; i<5 ; i++)
			{
				if(this.getCard(i).getSuit()>max_suit)
				{
					max_suit = this.getCard(i).getSuit();
					num = i;
				}
			}
			
			return this.getCard(num);
		}
	}

}
