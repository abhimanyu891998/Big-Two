
/**
 * 
 * It is an abstract class and is a sublclass of CardList class and it models a specific hand of cards.
 * @author Abhimanyue Singh
 *
 */
public abstract class Hand extends CardList {
	
	private static final long serialVersionUID = 1L;
	private CardGamePlayer player; // Instance variable to store the player who plays this hand
	
	
   /**
    * 
    * A constructor to build a hand and return its instance.
 * @param player specified player for whom hand is made
 * @param cards  list of cards which should be modelled into a hand
 */
Hand(CardGamePlayer player, CardList cards)
   {
	this.player = player;   
	for(int i=0; i<cards.size(); i++)
	{
		this.addCard(cards.getCard(i));
	}
	
   }



   /**
    *  Returns a player who plays this hand 
    *  
 * @return An object of type CardGamePlayer of plays this hand.
 */
public CardGamePlayer getPlayer()
   {
	   return player;
   }
   
  /**
   * A method to retrieve the top card of this hand
   * 
 * @return An object of type Card which is the topCard in this hand.
 */
public Card getTopCard() 
   {
	   Card max_card =  this.getCard(0);
		for(int i=0 ; i<this.size(); i++)
		{
			if(this.getCard(i).compareTo(max_card)==1)
			{
				max_card = this.getCard(i);
			}
		}
		
		return max_card;
   }
   

   /**
    * A method to check if this hand beats another specific hand
    * 
 * @param hand The hand to be checked if it is beaten by this hand
 * @return true if this hand beats the parameter hand else false
 */
public boolean beats(Hand hand)
   {   if(this.size()!=hand.size())
       {return false;}
   
	   if(this.getType()=="Single" || this.getType()=="Pair" || this.getType()=="Triple")
	   {
		if(this.getTopCard().compareTo(hand.getTopCard())==1)
		{
			return true;
		}
		else
		{
			return false;
		}
		   
		   }
	   
	   else if(this.getType()=="Straight")
	   {
		   if(hand.getType()=="Flush" || hand.getType()=="FullHouse" || hand.getType()=="Quad" || hand.getType()=="StraightFlush")
		   {
			   return false;
		   }
		   
		   else
		   { 
			 if(this.getTopCard().compareTo(hand.getTopCard())==1)
			 {
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		   
		   }
			   
		   }
	    else if(this.getType()=="Flush")
	   {
		   if(hand.getType()=="Straight")
		   {
			   return true;
		   }
		   
		   else if(hand.getType()=="FullHouse" || hand.getType()=="Quad" || hand.getType()=="StraightFlush")
		   {
			   return false;
		   }
		   
		   else
		   {
			   if(this.getTopCard().compareTo(hand.getTopCard())==1)
			   {
				   return true;
			   }
			   else
			   {
				   return false;
			   }
				   
		   }
	   }
	   
	   else if(this.getType()=="FullHouse")
	   {
		   if(hand.getType()=="Straight"||hand.getType()=="Flush")
		   {
			   return true;
		   }
		   
		   else if(hand.getType()=="Quad" || hand.getType()=="StraightFlush")
		   {
			   return false;
		   }
		   else
		   {
			   if(this.getTopCard().compareTo(hand.getTopCard())==1)
			   {
				   return true;
			   }
			   else
			   {
				   return false;
			   }
				   
		   }
	   }
	   
	   else if(this.getType()=="Quad")
	   {
		   if(hand.getType()=="FullHouse" || hand.getType()=="Straight"||hand.getType()=="Flush")
		   {
			   return true;
		   }
		   
		   else if(hand.getType()=="StraightFlush")
		   {
			   return false;
		   }
		   
		   else
		   {
			   if(this.getTopCard().compareTo(hand.getTopCard())==1)
			   {
				   return true;
			   }
			   else
			   {
				   return false;
			   }
				   
		   }
			   
		   }
	   
	   else if(this.getType()=="StraightFlush")
	   {
		   if(hand.getType()=="FullHouse" || hand.getType()=="Straight"||hand.getType()=="Flush"||hand.getType()=="Quad")
		   {
			   return true;
		   }
		   
		   else if(this.getTopCard().getRank()==hand.getTopCard().getRank())
		   {
			   if(this.getTopCard().getSuit()>hand.getTopCard().getSuit())
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
			   if(this.getTopCard().compareTo(hand.getTopCard())==1)
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
  * A method to check if this is a valid hand
 * @return true if valid, false otherwise
 */
public abstract boolean isValid();

   /**
    * A method to return a string specifying the type of this hand.
    * 
    * @return A string containing the name of this hand
    */
public abstract String getType();
   
   
   
   
	
	
}
