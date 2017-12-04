/**
 * 
 * This is a subclass of Hand class and models a hand of FullHouse type in a Big Two Card game.
 * @author Abhimanyue Singh
 *
 */
public class FullHouse extends Hand {
    
	 
		/**
	    *  Creates and returns an instance of FullHouse hand.
		* @param player player for whom this FullHouse hand is made
		* @param cards  list of cards used to model a FullHouse hand
	    */
	FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}


	private static final long serialVersionUID = 1L;

	/**
	 * A method to check if this is a valid FullHouse hand. It overrides its form in Hand class
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		this.sort();
		if(this.size()==5)
		{
			int ctr1=0; int ctr2=0; int ctr3=0; int ctr4=0;
			for(int i=0 ; i<2 ; i++)
			{
				if(this.getCard(i).getRank()!=this.getCard(i+1).getRank())
				{
					ctr1++;
				}
			}
			
		    if(this.getCard(3).getRank()!=this.getCard(4).getRank())
		    {
		    	ctr2++;
		    }
		    
		    if(this.getCard(0).getRank()==this.getCard(3).getRank())
		    {
		    	ctr1++;
		    }
		    
		    if(this.getCard(0).getRank()!=this.getCard(1).getRank())
		    {
		    	ctr3++;
		    }
		    
		    for(int i=2 ; i<4 ; i++)
		    {
		    	if(this.getCard(i).getRank()!=this.getCard(i+1).getRank())
		    	{
		    		ctr4++;
		    	}
		    }
		    
		    if(this.getCard(0).getRank()==this.getCard(2).getRank())
		    {
		    	ctr3++;
		    }
		    
		    if((ctr1==0 && ctr2==0) || (ctr3==0 && ctr4==0))
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
	* A method to return a string containing the name FullHouse
	* 
	* @return A string containing the name of this FullHouse hand
	*/
	public String getType() {
		return "FullHouse";
		
	}
	
	
	/**
	   * A method to retrieve the top card of the FullHouse hand. It overrides its form in Hand class
	   * 
	 * @return An object of type Card which is the topCard in this hand.
	 */
	public Card getTopCard()
	{
		if(this.getCard(0).getRank()==this.getCard(2).getRank())
		{
			int max_suite=this.getCard(0).getSuit();
			int num =0;
			for(int i=1 ; i<3 ; i++)
			{
				if(this.getCard(i).getSuit()>max_suite)
				{
					max_suite=this.getCard(i).getSuit();
					num=i;
				}
			}
			
			return this.getCard(num);
			
		}
		
		else
		{
			int max_suite = this.getCard(2).getSuit();
			int num = 2;
			for(int i=3 ; i<5 ; i++)
			{
				if(this.getCard(i).getSuit()>max_suite)
				{
					max_suite = this.getCard(i).getSuit();
					num=i;
				}
			}
			
			return this.getCard(num);
		}
	}

}
