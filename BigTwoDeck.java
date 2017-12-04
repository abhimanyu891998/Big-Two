
/**
 * This class is a subclass of Deck class and models a deck of cards used in BigTwo game.
 * 
 * @author Abhimanyue Singh
 *
 */
public class BigTwoDeck extends Deck {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Initialize the deck of cards. This method overrides its form in its superclass.
	 */
	public void initialize()
	{
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}


	}
}
