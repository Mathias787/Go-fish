/* Deck class
 * Class that control the cards, deals cards to players, shuffles, etc.
 * Version 1.0
 */

 import java.util.Stack;
 import java.util.ArrayList;
 import java.util.Random;

public class Deck
{
	private Stack deck = new Stack(); // List holding the cards for the deck.

	// Constructs a default deck of 52 cards.
	public Deck()
	{
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Aragorn"));
		
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Bilbo"));	
		
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Boromir"));
			
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Frodo"));

		for (int k = 0; k < 4; k++)
			deck.push(new Card("Gandalf"));
		
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Gimli"));
		
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Gollum"));

		for (int k = 0; k < 4; k++)
			deck.push(new Card("Legolas"));

		for (int k = 0; k < 4; k++)
			deck.push(new Card("Merry"));

		for (int k = 0; k < 4; k++)
			deck.push(new Card("Pippin"));

		for (int k = 0; k < 4; k++)
			deck.push(new Card("Samwise"));

		for (int k = 0; k < 4; k++)
			deck.push(new Card("Saruman"));

		
		for (int k = 0; k < 4; k++)
			deck.push(new Card("Sauron"));
	}

	public boolean isDeckEmpty()
	{
		if (deck.empty())
			return true;
		return false;
	}

	// Shuffles the deck with the given stack of cards.
	public void shuffle()
	{
		Random rand = new Random();
		ArrayList temp = new ArrayList(52);

		while(!deck.empty())
			temp.add(deck.pop());

		for (int k = 0; k < temp.size(); k++)
		{
			int tempPosition = rand.nextInt(52);
			Object tempCard = temp.get(k);
			temp.set(k,temp.get(tempPosition));
			temp.set(tempPosition,tempCard);
		}

		while(!temp.isEmpty())
			deck.push(temp.remove(temp.size()-1));
	}

	// Returns a card from the deck, thus removing it from the deck.
	public Card draw()
	{
		return (Card)deck.pop();
	}

	// Returns the String representation of the Deck.
	public String toString()
	{
		String stringDeck;
		Stack temp = new Stack();

		while (!deck.empty())
			temp.push(deck.pop());;

		Card value = (Card)temp.pop();
		stringDeck = "[" + value;
		deck.push(value);

		while (!temp.empty())
		{
			value = (Card)temp.pop();
			stringDeck += "; " + value;
			deck.push(value);
		}
		stringDeck += "]";

		return stringDeck;
	}
}