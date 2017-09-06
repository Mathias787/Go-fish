/* An extension of player, it has artificial inteligence that should hopefully
 * keep up with the player.
 * Version 1.0
 */

 import java.util.Random;
 import java.util.LinkedList;
 import java.util.ArrayList;

public class ComputerPlayer extends Player
{
	Random rand;
	LinkedList cardsAskedFor, lastRequest;
	
	// The Constructor.  Sends name to the player class.
	public ComputerPlayer(String _name)
	{
		super(_name);
		rand = new Random();
		lastRequest = new LinkedList();
		cardsAskedFor = new LinkedList();
	}

	// Overloaded askCard, this is the (only) part that will (need to) utilize
	// AI, It'll be a doozey.
	public String askCard()
	{
		Card chosenOne = null;
		ArrayList tempHand = (ArrayList)hand.clone();
			
		System.out.println("Compy hand: " + tempHand + "\nLast requests: " + lastRequest + "\nCards asked for: " + cardsAskedFor);
		// Prevents asking for the same card.
		for (int k = 0; k < lastRequest.size(); k++)
			for (int q = 0; q < tempHand.size(); q++)
				if (((Card)lastRequest.get(k)).equals((Card)tempHand.get(q)))
				{
					tempHand.remove(q);
					q--;
				}
		
		System.out.println("After elimination: " + tempHand);
		
		// Checks for any recent requests
					
		for (int k = 0; k < cardsAskedFor.size(); k++)
			for (int q = 0; q < tempHand.size(); q++)
				if (((Card)tempHand.get(q)).getName().equals((String)cardsAskedFor.get(k)))
					chosenOne = ((Card)tempHand.get(q));
															
		//Default base case in case all cards are eliminated.
		if (chosenOne == null && tempHand.isEmpty())
			chosenOne = (Card)hand.get(rand.nextInt(hand.size()));
		else
			chosenOne = (Card)tempHand.get(rand.nextInt(tempHand.size()));
				
		lastRequest.addLast(chosenOne);
		if (lastRequest.size() == 3)
			lastRequest.removeFirst();
		
		return chosenOne.getName();
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
		// Adds the asked for cards to the list to be asked for.
		cardsAskedFor.addFirst(cardName);
		if (cardsAskedFor.size() == 5)
			cardsAskedFor.removeLast();
	
		if (hasCard(cardName))
		{
			Card temp = (Card)hand.get(cardPos);
			removeCard(cardName);
			return temp;
		}
		
		else
			return null;
	}
}