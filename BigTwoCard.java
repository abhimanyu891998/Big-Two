
/**
 * This is a subclass of the Card class and specifically models a card in BigTwo Card game
 * 
 * @author Abhimanyue Singh
 *
 */
public class BigTwoCard extends Card{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * Creates and return an instance of the BigTwoCard class by taking specific suit and rank.
	 * @param suit An integer representing the suit of the card
	 * @param rank An integer value representing the ran of the card
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit,rank);
	}
	
	
	/**
	 * Compares this card with the specified card for order, it overrides the compareTo method of Card class.
	 * 
	 * @param card
	 *            the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card
	 */
	public int compareTo(Card card) {
		
		if(this.getRank()==1)
		{
			if(card.getRank()==1)
			{
				if(this.getSuit()>card.getSuit())
					{return 1;}
				else
					{return -1;}
			}
			else
				return 1;
		}
		
		else if(card.getRank()==1)
			
		{
			return -1;
		}
		
		else if(this.getRank()==0)
		{
			if(card.getRank()==0)
			{ 
				if(this.getSuit()>card.getSuit())
				{return 1;}
			else
				{return -1;}
				
			}
			else
				return 1;
		}
		
		else if(card.getRank()==0)
		{
			return -1;
		}
		
		
		else if(this.getRank()<card.getRank())
		{
			return -1;
		}
		else if(this.getRank()>card.getRank())
		{
			return 1;
		}
		else if(this.getSuit()>card.getSuit())
		{
			return 1;
		}
		else if(this.getSuit()<card.getSuit())
		{
			return -1;
		}
		else
		{return 0;}

	}

}
