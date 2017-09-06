/* The base class for player, keeps track of it's own hand and how many books it
 * has
 * Version 1.0
 */

import java.util.Collections;
import java.util.Set;
import java.util.ArrayList;
import java.util.TreeSet;

public class Player
{
	protected ArrayList hand = new ArrayList(7);
	protected ArrayList booksHad = new ArrayList();
	protected String name;
	protected Card thisTempCard;
	protected int numBooks;
	// This variable keeps track of a variable while going from one method to
	// another, hasCard returns a variable, but also keeps track of the card position.
	protected int cardPos;

	// Creates a player with an empty hand and a given name.
	public Player(String _name)
	{
		name = _name;
		numBooks = 0;
		thisTempCard = null;
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////// Accessor Methods ////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	
	// Returns most recent book
	public String getRecentBook()
	{
		return thisTempCard.getName();
	}
	
	// Returns the name of the player.
	public String getName()
	{
		return name;
	}

	// Returns the number of books the player has.
	public int getNumBooks()
	{
		return numBooks;
	}

	// Returns the size of the hand
	public int getHandSize()
	{
		return hand.size();
	}

	// returns the player's hand (for drawing purposes)
	public ArrayList getHand()
	{
		return (ArrayList)hand.clone();
	}

	// Returns an ArrayList of books.
	public ArrayList getBooksHad()
	{

		return booksHad;
	}

	// Returns an Object list of the card names in the player's hand without duplicates.
	public Object[] getChoices()
	{
		TreeSet handSet = new TreeSet();

		for(int k = 0; k < hand.size(); k++)
			handSet.add(((Card)hand.get(k)).getName());

		return handSet.toArray();
	}

	// Checks if the person's hand is empty, and if it is, it returns true, else, false.
	public boolean isHandEmpty()
	{
		if (hand.size() > 0)
			return false;
		return true;
	}

	////////////////////////////////////////////////////////////////////////////
	///////////////////// Mutator Methods //////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	// Sort's the player's hand and updates other things as I see fit.
	public void startTurn()
	{
		sortHand();
	}

	// Takes card and puts it in their hand, regardless to whether it
	// the beginning of the game or when they "Go Fish"
	public void receiveCard(Card card)
	{
		hand.add(card);
	}

	// Takes card out of the player's hand and returns the the card.
	public Card giveCard(String cardName)
	{
		if (hasCard(cardName))
		{
			Card temp = (Card)hand.get(cardPos);
			removeCard(cardName);
			return temp;
		}

		else
			return null;
	}

	// Checks the player's hand for books, and then discards the cards in a book
	// Nums books is then incremented.
	public boolean checkBooks()
	{
		ArrayList temp = (ArrayList)hand.clone();
		String cardName;
		int bookCounter = 0;

		sortHand();

		while (temp.size() != 0)
		{
			Card tempCard = (Card)temp.get(0);
			cardName = tempCard.getName();

			for (int k = 0; k < hand.size(); k++)
				if (cardName.equals(((Card)hand.get(k)).getName()))
					bookCounter++;

			if (bookCounter == 4)
			{
				numBooks++;
				for(int x = 0; x < 4; x++)
					removeCard(cardName);
				booksHad.add(cardName);
				bookCounter = 0;
				thisTempCard = (Card)temp.remove(0);
				return true;
			}
			else
			{
				bookCounter = 0;
				temp.remove(0);
			}
		}
		return false;
	}

	////////////////////////////////////////////////////////////////////////////
	///////////////////////////  Helper Methods ////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	// Precondition: The hand has already been searched for the card, and they are
	// just going through the deck and removing and returning the card.
	// Removes a card with the same name as given from the deck.
	protected Card removeCard(String theCard)
	{
		for (int pos = 0; pos < hand.size(); pos++)
			if (theCard.equals(((Card)hand.get(pos)).getName()))
				return (Card)hand.remove(pos);
		return null;
	}

	// Checks for in the hand and maintains the position of the card.
	protected boolean hasCard(String name)
	{
		for (cardPos = 0; cardPos < hand.size(); cardPos++)
			if (name.equals(((Card)hand.get(cardPos)).getName()))
				return true;
		return false;
	}

	// sorts the Hand into alphabetical order in order to make it easier to read.
	protected void sortHand()
	{
		Collections.sort(hand);
	}

	// Sort's the player's books into alphabetical order.
	protected void sortBooks()
	{
		Collections.sort(booksHad);
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////// Overloaded Methods //////////////////////////////
	////////////////////////////////////////////////////////////////////////////

	// Returns the String representation of a player including: name, hand, and number of books.
	public String toString()
	{
		String handString = "";
		if (!hand.isEmpty())
		{
			handString += name + "'s hand is:\n[" + ((Card)hand.get(0)).getName();

			for (int k = 1; k < hand.size(); k++)
				handString += ", " + ((Card)hand.get(k)).getName();

			handString += "]";
		}
		handString += "\n" + name + " has " + numBooks + " book(s).";
		return handString;
	}
}