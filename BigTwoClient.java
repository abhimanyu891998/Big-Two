	import java.util.*;
	import java.io.IOException;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.net.Socket;
	
	import javax.swing.JOptionPane;
	/**
	 * BigTwoClient is used to model a Big Two card game that supports 4 players over the Internet.
	 * 
	 * @author Abhimanyue Singh
	 */
	public class BigTwoClient implements CardGame, NetworkGame {
		
		private int numOfPlayers;//value of number of players in the game
		private Deck deck;
		private ArrayList<CardGamePlayer> playerList;
		private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
		private int playerID; // the index of the local player
		private String playerName; // the name of the local player
		private String serverIP; // a string specifying the IP address of the game server
		private int serverPort; // an integer specifying the TCP port of the game server
		private Socket sock; // a socket connection to the game server
		private ObjectOutputStream oos; // an ObjectOutputStream for sending messages to the server
		private int currentIdx;  // current player
		private BigTwoTable table;
		private  boolean first_played = false; //to check if first player has played or not
		private int pass_check=0; // counter to check if a pass is valid or not
		private ObjectInputStream ois; //ObjectInptStream to receive objects from server
		
		/**
		 * Constructor for creating a Big Two client.
		 */
		BigTwoClient()
		{   
			playerList = new ArrayList<CardGamePlayer>();
			CardGamePlayer p1 = new CardGamePlayer();
			CardGamePlayer p2 = new CardGamePlayer();
			CardGamePlayer p3 = new CardGamePlayer();
			CardGamePlayer p4 = new CardGamePlayer();
			this.playerList.add(p1);
			this.playerList.add(p2);
			this.playerList.add(p3);
			this.playerList.add(p4);
			this.table = new BigTwoTable(this);
			playerName = (String) JOptionPane.showInputDialog("Please enter your name: ");
			
			this.makeConnection();
		}
		
		
		
		/**
		 * Returns the number of players in this card game.
		 * 
		 * @return the number of players in this card game
		 */
		public int getNumOfPlayers()
		{
		 return numOfPlayers;	
		}
		
		/**
		 * Returns the deck of cards being used in this card game.
		 * 
		 * @return the deck of cards being used in this card game
		 */
		public Deck getDeck()
		{
			return this.deck;
		}
		
		/**
		 * Returns the list of players in this card game.
		 * 
		 * @return the list of players in this card game
		 */
		public ArrayList<CardGamePlayer> getPlayerList()
		{
			return this.playerList;
		}
	
		/**
		 * Returns the list of hands played on the table.
		 * 
		 * @return the list of hands played on the table
		 */
		public ArrayList<Hand> getHandsOnTable() {
			return this.handsOnTable;
		}
		
		/**
		 * Returns the index of the current player.
		 * 
		 * @return the index of the current player
		 */
		public int getCurrentIdx()
		{
			return this.currentIdx;
		}
		
		private Card card = new Card(0,2);//card to check for 3 of diamonds
		
		
		/**
		 * Starts the card game.
		 * 
		 * @param deck
		 *            the deck of (shuffled) cards to be used in this game
		 */
		public void start(Deck deck) {
			this.deck = deck;
			 for(int i=0 ; i<13 ; i++)
			    {
			    	this.playerList.get(0).addCard(deck.getCard(i));
			    }
			    
			    for(int i=13 ; i<26 ; i++)
			    {
			    	this.playerList.get(1).addCard(deck.getCard(i));
			    }
			    
			    for(int i=26 ; i<39 ; i++)
			    {
			    	this.playerList.get(2).addCard(deck.getCard(i));
			    }
			    
			    for(int i=39 ; i<52 ; i++)
			    {
			    	this.playerList.get(3).addCard(deck.getCard(i));
			    }
			    
			   
			    
			    
			    for(int i=0 ; i<playerList.size();i++)
			    {   this.playerList.get(i).sortCardsInHand();
			    	if(this.playerList.get(i).getCardsInHand().contains(card))
			    	{
			    		currentIdx = i;
			    	}	
			    }
			    
			    table.setActivePlayer(this.playerID);
			    if(!endOfGame())
			    	table.printMsg( playerList.get(currentIdx).getName() +  "'s turn");
			    
		        }
		
		
		
		/**
		 * Makes a move by the player.
		 * 
		 * @param playerID
		 *            the playerID of the player who makes the move
		 * @param cardIdx
		 *            the list of the indices of the cards selected by the player
		 */
		public void makeMove(int playerID, int[] cardIdx)
		{
		CardGameMessage message = new CardGameMessage(6,playerID,cardIdx);
		sendMessage(message);
			
			
		}
		
		
		/**
		 * Checks the move made by the player.
		 * 
		 * @param playerID
		 *            the playerID of the player who makes the move
		 * @param cardIdx
		 *            the list of the indices of the cards selected by the player
		 */
	    public void checkMove(int playerID, int[] cardIdx)
	    { 
	    
		if(first_played==false)
			
		{
			
			if(cardIdx==null)
			{   
				String printMessage = " ";
				
				/*if(cardIdx!=null)
				{
					for(int i=0 ; i<cardIdx.length ; i++)
					{
						printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
					}
					
					
				}*/
				
				table.printMsg(printMessage + "Not a legal move!!!");
	
			}
			
			
			
			else if(this.playerList.get(currentIdx).play(cardIdx).contains(card)==false)
			{
				
				String printMessage = " ";
				
				if(cardIdx!=null)
				{    
					for(int i=0 ; i<cardIdx.length ; i++)
					{
						printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
					}
					
					
				}
				
				table.printMsg(printMessage + "Not a legal move!!!");
				
				
			}
			
			else if((composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx)))==null)
					{
				      
				String printMessage = " ";
				
				if(cardIdx!=null)
				{
					for(int i=0 ; i<cardIdx.length ; i++)
					{
						printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
					}
					
					
				}
				
				table.printMsg(printMessage + "Not a legal move!!!");
				
				
				
				
					}
			else
			{ 
			 
			  first_played=true;	
			  table.printMsg("{"+ (composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx))).getType() + "}" + " " + this.playerList.get(this.currentIdx).play(cardIdx).toString());
			  table.printMsg(" ");
	       this.handsOnTable.add(composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx)));
	       this.playerList.get(currentIdx).removeCards(this.playerList.get(currentIdx).play(cardIdx));
	       this.currentIdx = (this.currentIdx+1)%4;
	       if(!endOfGame())
	    	   table.printMsg( playerList.get(currentIdx).getName() +  "'s turn");
	 	  table.setActivePlayer(playerID);
	 	  if((playerList.get(0).getNumOfCards()!=0)&&(playerList.get(1).getNumOfCards()!=0)&&(playerList.get(2).getNumOfCards()!=0)&&(playerList.get(3).getNumOfCards()!=0))
	 	  {table.repaint();}
	 	  
			}
			
		}
		
		else
		{       if(pass_check==3 && cardIdx!=null)
				{
			      this.handsOnTable.clear();
				}
			
			
		    	if(cardIdx==null)
		    	{   if(pass_check!=4)
		    		{pass_check+=1;}
		    		if(pass_check==4)
		    		{
		    			String printMessage = " ";
	     			
	     			/*if(cardIdx!=null)
	     			{
	     				for(int i=0 ; i<cardIdx.length ; i++)
	     				{
	     					printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
	     				}
	     				
	     				
	     			}*/
	     			
	     			table.printMsg(printMessage + "Not a legal move!!!");
		    			this.handsOnTable.clear();
		    		}
		    		else
		    		{
		    			this.currentIdx = (this.currentIdx+1)%4;
		    		    table.setActivePlayer(playerID);
		    		 table.printMsg("{Pass}");
		    		 table.printMsg("");
		    		 if(!endOfGame())
		    			 table.printMsg( playerList.get(currentIdx).getName() +  "'s turn");
		    		 if((playerList.get(0).getNumOfCards()!=0)&&(playerList.get(1).getNumOfCards()!=0)&&(playerList.get(2).getNumOfCards()!=0)&&(playerList.get(3).getNumOfCards()!=0))
			    	  {table.repaint();}
		    		 
		    		    
		    		}
		    		
		        }
		    	
		    	
		        else if(this.handsOnTable.size()!=0)
		    	{   if((composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx))==null))
		    	    {String printMessage = " ";
	 			
	 			if(cardIdx!=null)
	 			{
	 				for(int i=0 ; i<cardIdx.length ; i++)
	 				{
	 					printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
	 				}
	 				
	 				
	 			}
	 			
	 			table.printMsg(printMessage + "Not a legal move!!!");}
		    	else if((composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx)).beats(this.handsOnTable.get(this.handsOnTable.size()-1))==false))
		    		{String printMessage = " ";
	 			
	 			if(cardIdx!=null)
	 			{
	 				for(int i=0 ; i<cardIdx.length ; i++)
	 				{
	 					printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
	 				}
	 				
	 				
	 			}
	 			
	 			table.printMsg(printMessage + "Not a legal move!!!");
		    	}
		    	
		    	else
		    	{
		    		table.printMsg("{"+ (composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx))).getType() + "}" + " " + this.playerList.get(this.currentIdx).play(cardIdx).toString());
		    		table.printMsg(" ");
		              this.handsOnTable.add(composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx)));
		              this.playerList.get(currentIdx).removeCards(this.playerList.get(currentIdx).play(cardIdx));
		              this.currentIdx = (this.currentIdx+1)%4;
		              if(!endOfGame())
			    			 table.printMsg( playerList.get(currentIdx).getName() +  "'s turn");
		            
			    	  table.setActivePlayer(playerID);
			    	  
			    	  if((playerList.get(0).getNumOfCards()!=0)&&(playerList.get(1).getNumOfCards()!=0)&&(playerList.get(2).getNumOfCards()!=0)&&(playerList.get(3).getNumOfCards()!=0))
			    	  {table.repaint();}
		    		  pass_check=0;
		    		
		    	}
		    	
		    	
		    	}
		    	
		    	else if(this.handsOnTable.size()==0)
		    	{     if(composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx))==null)
		    		{
		    			String printMessage = " ";
	 			
	 			if(cardIdx!=null)
	 			{
	 				for(int i=0 ; i<cardIdx.length ; i++)
	 				{
	 					printMessage += "{" + this.playerList.get(currentIdx).getCardsInHand().getCard(cardIdx[i]).toString() + "}";
	 				}
	 				
	 				
	 			}
	 			
	 			table.printMsg(printMessage + "Not a legal move!!!");
		    		}
		    		else 
		    		{table.printMsg("{"+ (composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx))).getType() + "}" + " " + this.playerList.get(this.currentIdx).play(cardIdx).toString());
		              this.handsOnTable.add(composeHand(playerList.get(currentIdx), this.playerList.get(currentIdx).play(cardIdx)));
		              this.playerList.get(currentIdx).removeCards(this.playerList.get(currentIdx).play(cardIdx));
		              this.currentIdx = (this.currentIdx+1)%4;
		              if(!endOfGame())
			    			 table.printMsg( playerList.get(currentIdx).getName() +  "'s turn");		    	  
		              table.setActivePlayer(playerID);
		    		  	if((playerList.get(0).getNumOfCards()!=0)&&(playerList.get(1).getNumOfCards()!=0)&&(playerList.get(2).getNumOfCards()!=0)&&(playerList.get(3).getNumOfCards()!=0))
			    	  {table.repaint();}
		    		  pass_check=0;
		    		}
		    	}
		    	 }
		
		      if(endOfGame())
		      { 
		    	 table.setActivePlayer(-1);
		    	 
		    	table.printMsg(" ");
			    table.repaint(); //to display cards left of all the players
			   table.printMsg(" ");
			  table.printMsg("Game ends");
			 table.printMsg(" ");
			    
			    for(int i=0 ; i<playerList.size();i++)
			    {  
			    	if(playerList.get(i).getNumOfCards()!=0)
			    	{table.printMsg("Player"+ " " + (i)+ " " + "has "+ playerList.get(i).getNumOfCards() + " "+ "cards in hand." );}
			    else
			    {
			       table.printMsg("Player"+" "+ (i) + " wins the game.");
			    }
			    }
			    
			    table.disable();
		    	  
		    	  
		      }
	
		
	         }
	    
	    
	    /**
		 * A method to return a valid hand from the specified list of card of the player
		 * @param player  player for which the hand is to be returned
		 * @param cards list of cards whose hand is to be composed
		 * @return a valid hand according to the validty of cardlist, null otherwise
		 */
	    public static Hand composeHand(CardGamePlayer player, CardList cards)
		{
		
			StraightFlush h = new StraightFlush(player,cards);
		    if(h.isValid())
		    {
		    	return h;
		    }
		    
		    Quad g = new Quad(player,cards);
		    if(g.isValid())
		    {
		    	return g;
		    }
		    
		    FullHouse f = new FullHouse(player, cards);
		    if(f.isValid())
		    {
		    	return f;
		    }
		    
		    Flush e = new Flush(player, cards);
		    if(e.isValid())
		    {
		    	return e;
		    }
		    
		    Straight d = new Straight(player,cards);
		    if(d.isValid())
		    {
		    	return d;
		    }
		    
	
		    Triple c = new Triple(player,cards);
		    if(c.isValid())
		    {
		    	return c;
		    }
		    
		    Pair b = new Pair(player,cards);
		    if(b.isValid())
		    {
		    	return b;
		    }
		    
		    Single a = new Single(player,cards);
			if(a.isValid())
			{
				return a;
			}
			
		    
		    
		    return null;
		    
		    
		    	
		}
	    
	    
	    
		
	    /**
		 * Returns the playerID (index) of the local player.
		 * 
		 * @return the playerID (index) of the local player
		 */
		public int getPlayerID()
		{
			return this.playerID;
		}
		
		
		/**
		 * Sets the playerID (index) of the local player.
		 * 
		 * @param playerID
		 *            the playerID (index) of the local player.
		 */
		public void setPlayerID(int playerID)
		{
			this.playerID = playerID;
		}
		
		
		/**
		 * Returns the name of the local player.
		 * 
		 * @return the name of the local player
		 */
		public String getPlayerName() {
			return playerName;
		}
		
		/**
		 * Sets the name of the local player.
		 * 
		 * @param playerName
		 *            the name of the local player
		 */
		public void setPlayerName(String playerName) {
			playerList.get(playerID).setName(playerName);
		}
		
		/**
		 * Returns the IP address of the server.
		 * 
		 * @return the IP address of the server
		 */
		public String getServerIP() {
			return serverIP;
		}
		
	
		/**
		 * Sets the IP address of the server.
		 * 
		 * @param serverIP
		 *            the IP address of the server
		 */
		public void setServerIP(String serverIP) {
			this.serverIP = serverIP;
		}
		
		/**
		 * Returns the TCP port of the server.
		 * 
		 * @return the TCP port of the server
		 */
		public int getServerPort() {
			return serverPort;
		}
		
		/**
		 * Returns the TCP port of the server.
		 * 
		 * @return the TCP port of the server
		 */
		public void setServerPort(int serverPort) {
			this.serverPort = serverPort;
		}
		
		
	
		/**
		 * Makes a network connection to the server.
		 */
		public void makeConnection()
		{   this.setServerIP("127.0.0.1");
		 	this.setServerPort(2396);
		 	try
		    {sock = new Socket(this.getServerIP(),this.getServerPort());
		     oos = new ObjectOutputStream(sock.getOutputStream());
		     Thread receiveThread = new Thread(new ServerHandler());
		     receiveThread.start();
		     CardGameMessage message = new CardGameMessage(CardGameMessage.JOIN, -1,this.getPlayerName());
		     this.sendMessage(message);
		     
		     this.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,this.getPlayerName()));
		    }
		 	
		 	catch(Exception e)
		 	{
		 		e.printStackTrace();
		 	}
		 	
		}
		
		
		/**
		 * Parses the specified message received from the server.
		 * 
		 * @param message
		 *            the specified message received from the server
		 */
		public void parseMessage(GameMessage message)
		{
			if(message.getType()==0) //player_list
			{
				this.setPlayerID(message.getPlayerID());
				table.setActivePlayer(playerID);
				String[] playerNames = (String[])message.getData();
			    
				for(int i=0; i<4 ; i++)
				{  if(playerNames!= null)
					{playerList.get(i).setName(playerNames[i]);
				
					}
				}
				table.repaint();
			}
			
			else if(message.getType()==1) //join
			{
	            int newPlayerId = message.getPlayerID();
	            numOfPlayers++;
	            
	            playerList.get(newPlayerId).setName((String)message.getData());
				
			}
			
			else if(message.getType()==2)//full
			{
				table.printMsg("Server is full, Cannot join the game!!!");
				
			}
			
			else if(message.getType()==3) //quit
			{
				int id = message.getPlayerID();
				this.playerList.get(id).setName("");
				this.numOfPlayers--;
				for(int i=0 ; i<playerList.size();i++)
				{
					playerList.get(i).removeAllCards();
				}
				table.repaint();
				table.disable(); //to stop the game
				CardGameMessage readyMessage = new CardGameMessage(4,-1,null);
				
				sendMessage(readyMessage);
				
			
			}
			
			else if(message.getType()==4)  //ready
			{   table.enable();
				this.handsOnTable = new ArrayList<Hand>();
				table.clearMsgArea();
				for(int i=0 ; i<4; i++)
				{   if(playerList.get(i).getName()!= null)
						table.printMsg("Player "+ playerList.get(i).getName() + " is ready");}
				table.repaint();
				
			}
			
			else if(message.getType()==5) //start
			{   
				first_played = false;
				pass_check=0; // counter to check if a pass is valid or not
			    card = new Card(0,2);
			   
				this.start((BigTwoDeck)message.getData());
				table.printMsg("All Players are ready, game starts now");
			}
			
			else if(message.getType()==6) //move
			{   
	            this.checkMove(message.getPlayerID(), (int[])message.getData());
	            table.repaint();
			}
			
			else if(message.getType()==7) //msg
			{
				table.printChat((String)message.getData());
			}
			
		}
		
		
		
		/**
		 * Sends the specified message to the server.
		 * 
		 * @param message
		 *            the specified message to be sent the server
		 */
		public void sendMessage(GameMessage message)
		{
			try {
				oos.writeObject(message);
				oos.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
			
			
			
	
		/**
		 * A method for creating an instance of BigTwoClient
		 * @param args
		 */		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			BigTwoClient game = new BigTwoClient();
	
		}
		
		/**
		 * An inner class that implements the Runnable interface.
		 * @author Abhimanyue Singh
		 *
		 */
		public class ServerHandler implements Runnable
		{
			public void run()
			{
				try
				{ois = new ObjectInputStream(sock.getInputStream());
				
				while(true)
				{
					parseMessage((CardGameMessage)ois.readObject());
				}
				}
				catch(Exception ex)
				{  
					ex.printStackTrace();
				}
				table.repaint();
			}
		}
	
	
	
	
		/**
		 * Checks for end of game.
		 * 
		 * @return true if the game ends; false otherwise
		 */
		@Override
		public boolean endOfGame() {
			// TODO Auto-generated method stub
			 for(int i=0 ; i<4 ; i++)
	  	   {
	  		   if(this.playerList.get(i).getNumOfCards()==0)
	  		   {
	                
	  			   return true;
	  		   }
	  	   }
	  	   
	  	   return false;
		
		}
	
	}
