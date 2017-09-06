/* Double Buffer Code from:
 * http://java.about.com/gi/dynamic/offsite.htm?zi=1/XJ&sdn=java&zu=http%3A%2F%2Fjavacooperation.gmxhome.de%2FTutorialStartEng.html
 *
 *
 * Go Fish Main, from here, everything starts and finishes...
 * Version 1.0
 */
//Component
import java.applet.Applet;
//import java.applet.AudioClip;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.util.ArrayList;


public class GoFish extends Applet implements Runnable
{
	// Audio
//	AudioClip intro;
	
	// For the double buffer
	Image dbImage;
	Graphics dbg;
	
	Deck deck;
	Image aragorn, bilbo, boromir, frodo, gandalf, gimli, gollum, legolas, merry, pippin, samwise, saruman, sauron, backOfCard;
	Font f, big, small, superBig;
	
	Random rand;
	Player human;
	ComputerPlayer computer;

	Object[] possibleValues;

	int numOfTurns, humansMod, computersMod;
	
	// paint variable (so they don't keep getting reinitialized or so it can be printed easier (for me))
	int y, x, drawingX, tempSize;
	ArrayList playersHand;
	boolean goFish, showSelected, showSecondMessage;
	boolean win, lose, tie;
	String selectedCard, messageToPrint, secondMessageToPrint;
	
	
	
	public void init()
	{
		JOptionPane.showMessageDialog(null, "My thanks go out to:\nSeth F. - Doing card graphics that saved me from hours of frustration\nMatt I. - Helping me thread and saving me hours of frustration.\nMatt M. - Helped me with this and that which also, saved me from hours of frustration.");
		setBackground(new Color(0, 128, 0));
		repaint();
		setSize(1024,768);
		setMinimumSize(new Dimension(640, 480));
		setMaximumSize(new Dimension(1024, 768));
		
//		intro = getAudioClip(getCodeBase(), "title.wav");
		
		aragorn = getImage(getCodeBase(), "Aragorn.gif");
		bilbo = getImage(getCodeBase(), "Bilbo.gif");
		boromir = getImage(getCodeBase(), "Boromir.gif");
		frodo = getImage(getCodeBase(), "Frodo.gif");
		gandalf = getImage(getCodeBase(), "Gandalf.gif");
		gimli = getImage(getCodeBase(), "Gimli.gif");
		gollum = getImage(getCodeBase(), "Gollum.gif");
		legolas = getImage(getCodeBase(), "Legolas.gif");
		merry = getImage(getCodeBase(), "Merry.gif");
		pippin = getImage(getCodeBase(), "Pippin.gif");
		samwise = getImage(getCodeBase(), "Samwise.gif");
		saruman = getImage(getCodeBase(), "Saruman.gif");
		sauron = getImage(getCodeBase(), "sauron.gif");
		backOfCard = getImage(getCodeBase(), "BackCard.gif");
		
//		intro.play();
		
		f = new Font("Times New Roman", Font.PLAIN, 16);
		big = new Font("Times New Roman", Font.BOLD, 36);
		small = new Font("Times New Roman", Font.PLAIN, 12);
		superBig = new Font("Times New Roman", Font.BOLD, 48);
		rand = new Random();
		deck = new Deck();
		human = new Player(JOptionPane.showInputDialog(null, "Please enter your name.", "Player"));
		computer = new ComputerPlayer(JOptionPane.showInputDialog(null, "Please enter your opponent's name.", "Computer Player"));
		numOfTurns = 0;
		humansMod = rand.nextInt(2);
		goFish = showSelected = showSecondMessage = win = lose = tie = false;
		
		
		if (humansMod == 0)
			computersMod = 1;
		else
			computersMod = 0;

		deck.shuffle();

		for (int k = 0; k < 7; k++)
		{
			human.receiveCard(deck.draw());
			computer.receiveCard(deck.draw());
		}

		human.startTurn();
		computer.startTurn();
	}

	public void start()
	{
		win = lose = tie = false;
		Thread t = new Thread(this);
		t.start();
	}

	public void run()
	{
		repaint();
		pause(5000);
		
		while ((!human.isHandEmpty()) && (!computer.isHandEmpty()) && (!deck.isDeckEmpty()))
		{
			// The Human player's turn
			if((numOfTurns % 2) == humansMod)
				humanTurn();

			// The computer player's turn
			else if ((!human.isHandEmpty()) && (!computer.isHandEmpty()) && (!deck.isDeckEmpty()))
				computerTurn();

			numOfTurns++;
		}
		
		if (human.getNumBooks() > computer.getNumBooks())
			win = true;
		if (human.getNumBooks() == computer.getNumBooks())
			tie = true;
		if (human.getNumBooks() < computer.getNumBooks())
			lose = true;
			
		int choice = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Again?", JOptionPane.YES_NO_OPTION);
	
		if(choice == 0)
			init();
		
	}

	public void update (Graphics g)
	{
		// initialize buffer
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		// Clears the Screen in the background
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

		// Draws elements in background
		dbg.setColor (getForeground());
		paint (dbg);

		// draw image on the screen
		g.drawImage (dbImage, 0, 0, this);
	}

	public void paint(Graphics g)
	{
		if(this.getSize().width < this.getMinimumSize().width)
			this.setSize(this.getMinimumSize().width, this.getSize().height);
		
		if(this.getSize().height < this.getMinimumSize().height)
			this.setSize(this.getSize().width, this.getMinimumSize().height);
			
		if(this.getSize().width > this.getMaximumSize().width)
			this.setSize(this.getMaximumSize().width, this.getSize().height);
		
		if(this.getSize().height > this.getMaximumSize().height)
			this.setSize(this.getSize().width, this.getMaximumSize().height);
		
		g.setColor(Color.white);
		g.setFont(f);
		g.drawString(human.getName() + " has " + human.getNumBooks() + " books.",10,20);
		g.drawString(computer.getName() + " has " + computer.getNumBooks() + " books.", this.getSize().width - 195, 20);
		
		int maxCards = (this.getSize().width - 10) / 85;
		tempSize = computer.getHandSize();
		x = drawingX = 0;
		y = 50;
		
		while (x < tempSize)
		{
			g.drawImage(backOfCard, drawingX * 85 + 10, y, this);
			x++;
			drawingX++;
			if (drawingX == maxCards)
			{
				drawingX = 0;
				y += 125;
			}
		}
		
		playersHand = human.getHand();
		tempSize = playersHand.size();
		x = drawingX = 0;
		
		if (tempSize >= maxCards * 2)
			y = this.getSize().height - 375;
		
		else if (tempSize >= maxCards)
			y = this.getSize().height - 250;
		
		else
			y = this.getSize().height - 125;

		while (x < tempSize)
		{
			g.drawImage(getCard(((Card)playersHand.remove(0)).getName()), drawingX * 85 + 10, y, this);
			x++;
			drawingX++;
			if (drawingX == maxCards)
			{
				drawingX = 0;
				y += 125;
			}
		}
		
		// Draws selection and books gotten (when necessary)
		
		if (showSelected)
		{
			g.setColor(Color.white);
			g.setFont(f);
			g.drawString(messageToPrint, this.getSize().width - 195, this.getSize().height / 2 - 66);
			g.drawImage(getCard(selectedCard), this.getSize().width - 195, this.getSize().height / 2 - 56, this);
			
			if (showSecondMessage)
			{
				g.setFont(small);
				g.drawString(secondMessageToPrint, this.getSize().width - 195, this.getSize().height / 2 + 76);
			}
		}
	
		if (goFish)
		{
			g.setColor(Color.white);
			g.setFont(big);
			g.drawString("Go Fish!", this.getSize().width / 2 - 50, this.getSize().height / 2 - 18);
		}
		
		if (win)
		{
			g.setFont(superBig);
			g.drawString("You Win!", this.getSize().width / 2 - 20, this.getSize().height / 2 - 24);
		}
		if (tie)
		{
			g.setFont(superBig);
			g.drawString("You Tied.", this.getSize().width / 2 - 20, this.getSize().height / 2 - 24);
		}
		
		if (lose)
		{
			g.setFont(superBig);
			g.drawString("Sucker...", this.getSize().width / 2 - 20, this.getSize().height / 2 - 24);
		}	
	}

	public void humanTurn()
	{
		System.out.println(human + "\n" + computer);
		boolean done = false;

		human.startTurn();
		repaint();
		
		while (!done)
		{
			possibleValues = human.getChoices();
			done = true;

			String selectedValue = null;
		
			String temp = "Select a card to ask for.";

			while(selectedValue == null)
				selectedValue = (String)JOptionPane.showInputDialog(null, temp, human.getName() + "'s turn", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
			pause(2000);

			selectedCard = selectedValue;
			messageToPrint = "You asked for:";

			showSelected = true;
			repaint();
			pause(2000);
			
			
			Card fishingTemp;

			if ((fishingTemp = computer.giveCard(selectedValue)) != null)
			{
				Card anotherTemp;
				while ((anotherTemp = computer.giveCard(selectedValue)) != null)
					human.receiveCard(anotherTemp);
				
				secondMessageToPrint = computer.getName() + " had " + selectedCard;
	
				done = false;
			}
			else
			{
				goFish = true;
				repaint();
				pause(750);
				
				fishingTemp = deck.draw();
				
				selectedCard = fishingTemp.getName();
				messageToPrint = "You drew:";
				repaint();
				pause(1000);
				
				if (selectedValue.equals(fishingTemp.getName()))
				{
					secondMessageToPrint = "You get another turn!";
					done = false;
				}

				else
					secondMessageToPrint = "Your turn is over.";
				
				
			}
			showSecondMessage = true;
			repaint();
			pause(2500);			
			showSelected = showSecondMessage = goFish = false;
			repaint();
			pause(500);
			
			human.receiveCard(fishingTemp);
			repaint();
			if(human.checkBooks())
			{
				messageToPrint = "You have this book:";
				selectedCard = human.getRecentBook();
				showSelected = true;
				repaint();
				pause(2000);
				showSelected = false;
				repaint();
			}	
			repaint();
		
			if (human.isHandEmpty())
				done = true;
		}
	}

	public void computerTurn()
	{
		System.out.println(human + "\n" + computer);
		boolean done = false;

		computer.startTurn();
		repaint();
	
		while (!done)
		{
			done = true;
			
			String selectedValue = computer.askCard();
			
			pause(1250);
		
			messageToPrint = computer.getName() + " asks for:";
			selectedCard = selectedValue;
	
			showSelected = true;
			repaint();
			pause(2000);
			

			Card fishingTemp;

			if ((fishingTemp = human.giveCard(selectedValue)) != null)
			{
				Card anotherTemp;
				while ((anotherTemp = human.giveCard(selectedValue)) != null)
					computer.receiveCard(anotherTemp);
				
				secondMessageToPrint = "You have " + selectedCard;
				
				done = false;
			}

			else
			{
				goFish = true;
				repaint();

				

				fishingTemp = deck.draw();

				if (selectedValue.equals(fishingTemp.getName()))
				{
					secondMessageToPrint = computer.getName() + " drew " + selectedValue;
					done = false;
				}
				else
					secondMessageToPrint = computer.getName() + " draws a card.";
			}
			pause(500);
			showSecondMessage = true;
			repaint();
			pause(2500);
			showSecondMessage = showSelected = goFish = false;			
			repaint();
		
			computer.receiveCard(fishingTemp);
			repaint();
			if(computer.checkBooks())
			{
				messageToPrint = computer.getName() + "has this book:";
				selectedCard = computer.getRecentBook();
				showSelected = true;
				repaint();
				pause(2000);
				showSelected = false;
				repaint();
			}
			repaint();
		
			if (computer.isHandEmpty())
				done = true;
		}
	}

	public Image getCard(String name)
	{
		if (name.equals("Aragorn"))
			return aragorn;

		else if (name.equals("Bilbo"))
			return bilbo;

		else if (name.equals("Boromir"))
			return boromir;

		else if (name.equals("Frodo"))
			return frodo;

		else if (name.equals("Gandalf"))
			return gandalf;

		else if (name.equals("Gimli"))
			return gimli;

		else if (name.equals("Gollum"))
			return gollum;

		else if (name.equals("Legolas"))
			return legolas;

		else if (name.equals("Merry"))
			return merry;

		else if (name.equals("Pippin"))
			return pippin;

		else if (name.equals("Samwise"))
			return samwise;

		else if (name.equals("Saruman"))
			return saruman;

		else if (name.equals("Sauron"))
			return sauron;

		else
			return null;
	}
	
	public void pause(int d)
	{
		long delayStart = System.currentTimeMillis();
		long delayEnd = 0;
		
		while (delayEnd - delayStart < d)
			delayEnd = System.currentTimeMillis();
	}
}

