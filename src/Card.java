/* A Wrapper class dealing with the cards in play, it is what will be in
 * people's hands and in the deck and can keep track of a value and it's own name.
 * Version 1.0
 */

public class Card implements Comparable
{
	String name;

	// Constucts the card with the numeric value.
	public Card(String _name)
	{
		name = _name;
	}

	// Return the name of the card.
	public String getName()
	{
		return name;
	}

	// Returns the String representation of the card.
	public String toString()
	{
		return "Name: " + name;
	}

	// Allows the cards to be sorted.
	public int compareTo(Object o)
	{
		return name.compareTo(((Card)o).getName());
	}
	
	// Allows the usage of the equals command to determine likeness in name.
	public boolean equals(Card c)
	{
		return name.equals(c.getName());
	}
}