		import java.awt.*;
		import java.awt.event.*;
		import javax.swing.*;
		
		
		/**
		 * This is designed for GUI of Big Two Game.
		 * It implement CardGameTable interface.
		 * @author Abhimanyue Singh
		 */
		public class BigTwoTable implements CardGameTable{
			private BigTwoClient game;//Card game for this table
			private boolean[] selected;//a boolean array indicating the selected cards
			private int activePlayer;// an integer to specify the current player
			private JFrame frame;//main frame of the GUI application
			private JPanel bigTwoPanel;//Panel to show cards of each player and hands played on the table
			private JButton playButton;//Button for active player to play the selected cards
			private JButton passButton;//Button for active player to pass the turn to next player
			private JTextArea msgArea;//text area to show current game status and end of game message
			private Image[][] cardImages;//an array storing the images of cards
			private Image cardBackImage;//an image to store the back side of cards
			private Image[] avatars;//array to store the images of avatars
			private int cStartX = 200;//indicates the horizontal starting coordinate of cards
			private int cStartY = 30;//indicates the vertical starting coordinate of cards
			boolean endOfGame = false;//to disable endOfCards
			private JTextField chatMessageArea;
			private JTextArea chatArea;
			
			
			
			
			
			/**
			 * This constructor takes CardGame game as parameter, which is a reference to the card game associated with this table.
			 * @param game
			 */
			public BigTwoTable(BigTwoClient game)
			{   
				this.game = game;
				selected = new boolean[13];
				this.resetSelected();
				setActivePlayer(game.getCurrentIdx());
				
				
				avatars = new Image[4];
				avatars[0] = new ImageIcon("images/batman_72.png").getImage();
				avatars[1] = new ImageIcon("images/flash_72.png").getImage();
				avatars[2] = new ImageIcon("images/green_lantern_72.png").getImage();
				avatars[3] = new ImageIcon("images/superman_72.png").getImage();
				cardBackImage = new ImageIcon("images/b.gif").getImage();
				cardImages = new Image[4][13];
				char suit[] = {'d', 'c', 'h', 's'};
				for (int i = 0; i < 4; i++) {
					cardImages[i] = new Image[13];
					for (int j = 0; j < 13; j++) {
						cardImages[i][j] = new ImageIcon("images/" + (j+1) + suit[i] + ".gif").getImage();
					}
				}
				
			   //frame properties
				bigTwoPanel = new BigTwoPanel(); //panel to display cards
				bigTwoPanel.setMinimumSize(new Dimension(800,840));
				bigTwoPanel.setMaximumSize(new Dimension(1000,840));
				
				
				
				frame = new JFrame("Big Two Game");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				JSplitPane splitFrame = new JSplitPane(); //to split the frame horizontally
				splitFrame.setDividerLocation(0.60);
				splitFrame.setResizeWeight(0.60);
				splitFrame.setDividerSize(2);
				
				//MenuBar 
				JMenuBar menuBar = new JMenuBar();
				JMenu menu = new JMenu("Options");
				JMenu messageMenu = new JMenu("Message");
				JMenuItem chatClear = new JMenuItem("Clear Chat");
				chatClear.addActionListener(new chatClearListener());
				JMenuItem messageClear = new JMenuItem("Clear Table Text area");
				
				messageClear.addActionListener(new messageClearListener());
				messageMenu.add(chatClear);
				messageMenu.add(messageClear);
				menuBar.add(messageMenu);
			
				JMenuItem quit = new JMenuItem("Quit");
				quit.addActionListener(new QuitMenuItemListener());
				JMenuItem connect = new JMenuItem("Connect");
				connect.addActionListener(new ConnectMenuItemListener());
				menu.add(quit);
				menu.add(connect);
				menuBar.add(menu);
				frame.setJMenuBar(menuBar);
				JPanel bottomArea = new JPanel();
				bottomArea.setLayout(new BorderLayout());
				
				chatArea = new JTextArea();
				JScrollPane chatScroll = new JScrollPane(chatArea);
				chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				//chatScroll.setPreferredSize(new Dim);
				//chatArea.setLayout(new BoxLayout());
				bottomArea.add(chatScroll,BorderLayout.CENTER);
				JPanel typeMessage = new JPanel();
				typeMessage.setLayout(new FlowLayout());
				JLabel messageLabel = new JLabel("Message: ");
				chatMessageArea = new JTextField(40);
				chatMessageArea.addKeyListener(new EnterKeyListener());
				chatMessageArea.setMinimumSize(new Dimension(40,20));
				typeMessage.add(messageLabel);
				typeMessage.add(chatMessageArea);
				bottomArea.add(typeMessage,BorderLayout.PAGE_END);
				
				
				
				
				msgArea = new JTextArea(); // area to display messages
				msgArea.setEditable(false);
				msgArea.setBackground(new Color(48,63,159));
				msgArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
				msgArea.setForeground(Color.WHITE);
				JScrollPane scrollBar = new JScrollPane(msgArea); //scrollPanel displaying messages
				msgArea.setLineWrap(true);
				scrollBar.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scrollBar.setHorizontalScrollBarPolicy(  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				JSplitPane VSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,scrollBar,bottomArea);
				VSplit.setDividerLocation(0.5);
				VSplit.setResizeWeight(0.60);
				VSplit.setDividerSize(2);
				splitFrame.setRightComponent(VSplit);
				
				
				
				
				
				bigTwoPanel.setLayout(new BorderLayout());
				JPanel buttonPanel = new JPanel(); //buttonPanel at the bottom of left side
				playButton = new JButton("PLAY");
				passButton = new JButton("PASS");
				playButton.addActionListener(new PlayButtonListener());
			    buttonPanel.add(playButton);
			    passButton.addActionListener(new PassButtonListener());
			    buttonPanel.add(passButton);
			    bigTwoPanel.add(buttonPanel, BorderLayout.PAGE_END);
			    splitFrame.setLeftComponent(bigTwoPanel); //setting left side of split to the bigTwoPanel
			    frame.add(splitFrame,BorderLayout.CENTER);
			    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			    frame.setSize(screenSize.width,screenSize.height);
			    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			    frame.setVisible(true);
			  
				
				}
			
			@Override
			/**
			 * Sets the index of the active player (i.e., the current player).
			 * 
			 * @param activePlayer
			 *            an int value representing the index of the active player
			 */
			public void setActivePlayer(int activePlayer)
			{
			 this.activePlayer = activePlayer;	
			}
			
			@Override
			/**
			 * Returns an array of indices of the cards selected.
			 * 
			 * @return an array of indices of the cards selected
			 */
			public int[] getSelected()
			{  int[] selectedCardsList = null;
				int numOfSelectedCards = 0;
				for (int i = 0; i < 13; i++) {
					if (selected[i])
						numOfSelectedCards++;
				}
				if(numOfSelectedCards!=0)
				{ selectedCardsList = new int[numOfSelectedCards];}
				int counter = 0;
				for (int i = 0; i < 13; i++) {
					
					if (selected[i]) {
						selectedCardsList[counter] = i;
						counter++;
					}
				}
				return selectedCardsList;  
				
			}
			
			@Override
			/**
			 * Resets the list of selected cards to an empty list.
			 */
			public void resetSelected()
			{
				for(int i=0 ; i<13 ; i++)
				{
					selected[i]=false;
				}
			}
			
			@Override
			/**
			 * Repaints the GUI.
			 */
			public void repaint()
			{
				frame.repaint();
			}
			
			@Override
			/**
			 * Prints the specified string to the message area of the card game table.
			 * 
			 * @param msg
			 *            the string to be printed to the message area of the card game
			 *            table
			 */
			public void printMsg(String msg)
			{
			msgArea.append(msg + "\n\r");
			}
			
			public void printChat(String msg)
			{
			chatArea.append(msg + "\n\r");
			}
			
			
			@Override
			/**
			 * Clears the message area of the card game table.
			 */
			public void clearMsgArea()
			{
				msgArea.setText(null);
			}
			
		    @Override
		    /**
			 * Resets the GUI.
			 */
			public void reset()
		    {
		    	this.resetSelected();
		    	this.clearMsgArea();
		    	this.enable();
		    }
		    
		    @Override
		    /**
			 * Enables user interactions.
			 */
		    public void enable()
		    {
		    	playButton.setEnabled(true);
		    	passButton.setEnabled(true);
		    	bigTwoPanel.setEnabled(true);
		    }
		    
		   
		    @Override
		    /**
			 * Disables user interactions.
			 */
		    public void disable()
		    {
		    	playButton.setEnabled(false);
		    	passButton.setEnabled(false);
		    	bigTwoPanel.setEnabled(false);
		    	endOfGame = true;
		    	
		    }
		    
		   
		    /**
			 * An inner class - BigTwoPanel
			 * Draw the cards of each player and the hands on table.
			 * @author Abhimanyue Singh
			 *
			 */
		   public class BigTwoPanel extends JPanel implements MouseListener
		    {   
			   
			   /**
				 * Constructor for BigTwoPanel
				 */
		    	public BigTwoPanel()
		    	{
		    	   this.addMouseListener(this);	
		    	   
		    	
		    	}
		    	
		    	/**
				 * Inherited from JPanel class to draw the card game table
				 */
		    	public void paintComponent(Graphics g)
		    	{  int panelWidth = this.getWidth();
		    	   int panelHeight = this.getHeight();
		    		super.paintComponent(g);
		    	   this.setBackground(new Color(0,150,136));
		            g.setFont(new Font("SansSarif",Font.BOLD,18));
		            setActivePlayer(game.getCurrentIdx());
		            if(game.getCurrentIdx() ==0)
		            {g.setColor(Color.RED);}
		            if(game.getPlayerID()==0)
		            {g.drawString("You", 10,30);}
		            else
		            {   if(game.getPlayerList().get(0).getName()!=null)
		            		g.drawString(game.getPlayerList().get(0).getName(), 10,30);	
		            }	
		            g.setColor(Color.BLACK);
		            g.drawImage(avatars[0], 10, 50,this);
		            g.drawLine(0, panelHeight/5 , panelWidth, panelHeight/5);
		            if(game.getCurrentIdx() ==1)
		            {g.setColor(Color.RED);}
		            if(game.getPlayerID()==1)
		            {g.drawString("You", 10,(panelHeight/5)+30);}
		            else
		            {   if(game.getPlayerList().get(1).getName()!=null)
		            		g.drawString(game.getPlayerList().get(1).getName(), 10,(panelHeight/5)+30);	
		            }
		            g.setColor(Color.BLACK);
		            g.drawImage(avatars[1], 10, (panelHeight/5)+50,this);
		            g.drawLine(0, 2*(panelHeight/5), panelWidth, 2*(panelHeight/5));
		            if(game.getCurrentIdx() == 2)
		            {g.setColor(Color.RED);}
		            if(game.getPlayerID()==2)
		            { g.drawString("You", 10, 2*(panelHeight/5)+30);}
		            else
		            {   if(game.getPlayerList().get(2).getName()!=null)
		            		g.drawString(game.getPlayerList().get(2).getName(), 10, 2*(panelHeight/5)+30);
		            }
		            g.setColor(Color.BLACK);
		            g.drawImage(avatars[2], 10,  2*(panelHeight/5)+50,this);
		            g.drawLine(0, 3*(panelHeight/5), panelWidth, 3*(panelHeight/5));
		            if(game.getCurrentIdx() ==3)
		            {g.setColor(Color.RED);}
		            if(game.getPlayerID()==3)
		            { g.drawString("You", 10,  3*(panelHeight/5)+30);}
		            else
		            {   if(game.getPlayerList().get(3).getName()!=null)
		            		g.drawString(game.getPlayerList().get(3).getName(), 10,  3*(panelHeight/5)+30);}
		            g.setColor(Color.BLACK);
		            g.drawImage(avatars[3], 10,  3*(panelHeight/5)+50,this);
		            g.drawLine(0, 4*(panelHeight/5), panelWidth, 4*(panelHeight/5));
		            
		            if (game.getPlayerID() == 0) {				//Checking ActivePlayer and Displaying the Cards accordingly. 
				    	for (int i = 0; i < game.getPlayerList().get(0).getNumOfCards(); i++) {
				    		if (selected[i]==false)
				    			g.drawImage(cardImages[game.getPlayerList().get(0).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(0).getCardsInHand().getCard(i).getRank()], 200+30*i, 30, this);//front image
				    		else
				    			g.drawImage(cardImages[game.getPlayerList().get(0).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(0).getCardsInHand().getCard(i).getRank()], 200+30*i, 30-20, this);
				    	}
			    } else {
				    	for (int i = 0; i < game.getPlayerList().get(0).getCardsInHand().size(); i++) {
				    		g.drawImage(cardBackImage, 200 + 30*i, 30, this);//Back Image
				    	}
			    }
		        if (game.getPlayerID() == 1) {
				    	for (int i = 0; i < game.getPlayerList().get(1).getNumOfCards(); i++) {
				    		if (selected[i]==false)
				    			g.drawImage(cardImages[game.getPlayerList().get(1).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(1).getCardsInHand().getCard(i).getRank()], 200+30*i, (panelHeight/5)+30, this);
				    		else
				    			g.drawImage(cardImages[game.getPlayerList().get(1).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(1).getCardsInHand().getCard(i).getRank()], 200+30*i, (panelHeight/5)+30-20, this);
				    	}
			    } else {
				    	for (int i = 0; i < game.getPlayerList().get(1).getCardsInHand().size(); i++) {
				    		g.drawImage(cardBackImage, 200 + 30*i,  (panelHeight/5)+30, this);
				    	}
			    }
		        if (game.getPlayerID()== 2) {
				    	for (int i = 0; i < game.getPlayerList().get(2).getNumOfCards(); i++) {
				    		if (selected[i]==false)
				    			g.drawImage(cardImages[game.getPlayerList().get(2).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(2).getCardsInHand().getCard(i).getRank()], 200+30*i, 2*(panelHeight/5)+30, this);
				    		else
				    			g.drawImage(cardImages[game.getPlayerList().get(2).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(2).getCardsInHand().getCard(i).getRank()], 200+30*i, 2*(panelHeight/5)+30-20, this);
				    	}
			    } else {
				    	for (int i = 0; i < game.getPlayerList().get(2).getCardsInHand().size(); i++) {
				    		g.drawImage(cardBackImage, 200 + 30*i,  2*(panelHeight/5)+30, this);
				    	}
			    }
			    if (game.getPlayerID() == 3) {
				    	for (int i = 0; i < game.getPlayerList().get(3).getNumOfCards(); i++) {
				    		if (selected[i]==false)
				    			g.drawImage(cardImages[game.getPlayerList().get(3).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(3).getCardsInHand().getCard(i).getRank()], 200+30*i, 3*(panelHeight/5)+30, this);
				    		else
				    			g.drawImage(cardImages[game.getPlayerList().get(3).getCardsInHand().getCard(i).getSuit()][game.getPlayerList().get(3).getCardsInHand().getCard(i).getRank()], 200+30*i,3*(panelHeight/5)+30-20, this);
				    	}
			    } else {
				    	for (int i = 0; i < game.getPlayerList().get(3).getCardsInHand().size(); i++) {
				    		g.drawImage(cardBackImage, 200 + 30*i,  3*(panelHeight/5)+30, this);
				    	}
				    	
				    	
				    	
			    }
			   
			    
			    
			    
			
			  
		        
		        
			    
			    //g.drawString(tbp, 10, 4*(panelHeight/5)+30);
			    int lastPlayer;
			   
			    if(game.getHandsOnTable().size()!=0) {
			    	 
			         if(game.getCurrentIdx()==3)
			         {
			        	 lastPlayer = 0;
			         }
			         else if(game.getCurrentIdx()==0)
			         {
			        	lastPlayer = 3; 
			         
			         }
			         
			         else
			         {
			        	 lastPlayer = game.getCurrentIdx()-1;
			        	 
			        	 
			         }
			         
			         String lund = game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName();
			         
			        g.drawString("Last Hand Played by " + lund, 10, 4*(panelHeight/5)+30 );
			    
			         
			      Hand toPrint = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
			    	for(int i=0 ; i<toPrint.size() ; i++)
			    	{
			    		g.drawImage(cardImages[toPrint.getCard(i).getSuit()][toPrint.getCard(i).getRank()], 300+(30*i), 4*(panelHeight/5)+10, this);
			    	}
			    }
			    
		            
		          
		        }
		    	
		    	/**
				 * Handles the mouse click event.
				 */
		    	public void mouseClicked(MouseEvent event)
		    	{   int panelWidth = this.getWidth();
		 	        int panelHeight = this.getHeight();
		    		int xCord = event.getX();
		    		int yCord = event.getY();
		    		boolean skipRepaint = false;
		    		
		    	
		 
		    		int currentNoOfCards = game.getPlayerList().get(game.getPlayerID()).getNumOfCards()-1;
		    		//for double click
		    		if(yCord>=(game.getPlayerID()*(panelHeight/5)+30-20) && yCord<=(game.getPlayerID()*(panelHeight/5)+30+97-20))
		    		{
		    			
		    			if(xCord>=(200+currentNoOfCards*30) && xCord<=(200+(currentNoOfCards*30)+73))
		    			{
		    				if(selected[currentNoOfCards])
		    				{  
		    					selected[currentNoOfCards]=false;
		    					skipRepaint = true;
		    				}
		    			}
		    			
		    			else
		    			{for(int i=0; i<currentNoOfCards ; i++)
		    			{
		    				if(xCord>=(cStartX+(30*i)) && xCord<=(cStartX+(30*i)+30))
		    				{
		    					if(selected[i])
		    					{   
		    						selected[i]=false;
		    						skipRepaint = true;
		    					}
		    				}
		    				
		    					
		    			}
		    			
		    			}
		    			
		    		}
		    		
		    		
		    		
		    		//for single click
		    		if(!skipRepaint)
		    		{if(yCord>=(game.getPlayerID()*(panelHeight/5)+30) && yCord<= (game.getPlayerID()*(panelHeight/5)+30+97) )
		    		{
		    			if(xCord>=(200+currentNoOfCards*30) && xCord<=(200+(currentNoOfCards*30)+73))
		    			{
		    				if(selected[currentNoOfCards]==false)
		    				{  
		    					selected[currentNoOfCards]=true;
		    				}
		    			}
		    			
		    			else
		    			{for(int i=0; i<currentNoOfCards ; i++)
		    			{
		    				if(xCord>=(cStartX+(30*i)) && xCord<=(cStartX+(30*i)+30))
		    				{
		    					if(selected[i]==false)
		    					{   
		    						selected[i]=true;
		    					}
		    				}
		    				
		    					
		    			}
		    			
		    			}
		    			
		    		}
		    		}
		    		
		    		
		    		
		    		
		    		
		    		if(endOfGame)
		    		{
		    			resetSelected();
		    		}
		    		
		    		
		    		
		    		
		    		frame.repaint();
		    		
		    	}
		    	
		    	@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
		
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
		
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
		
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
		    	
		   
		    	
		    }
		   
		   /**
			 * An inner class to handle the playButton listener.
			 * @author Abhimanyue Singh
			 *
			 */
		     class PlayButtonListener implements ActionListener
		   {   /**
		 		 * Handles the button click events for "Play" button
		 		 */
		 		@Override
			   public void actionPerformed(ActionEvent e)
			   {  if(game.getPlayerID()==game.getCurrentIdx())
			   { if(getSelected()!=null)
				   {
					   game.makeMove(game.getCurrentIdx(), getSelected());
					   
				   }
				   
				   resetSelected();
				   frame.repaint();
		
			   }
			   }
			   
		   }
		     
		     /**
		 	 * An inner class to handle the passButton listener.
		 	 * @author Abhimanyue Singh
		 	 *
		 	 */
		   class PassButtonListener implements ActionListener
		   {  /**
				 * Handles the button click events for "Pass" button
				 */
				@Override
			   public void actionPerformed(ActionEvent e)
			   {  if(game.getPlayerID()==game.getCurrentIdx())
					{if(getSelected()==null)
			   {
				 game.makeMove(game.getCurrentIdx(), getSelected());  
			   }
			   
			   resetSelected();
			   frame.repaint();
			   }
			   }
			   
			}
		   
		   /**
			 * An inner class to handle the action when Restart is clicked
			 * @author Abhimanyue Singh
			 *
			 */
		   
		 
		   
		   
		   class ConnectMenuItemListener implements ActionListener
		   {
			   /**
				 * Handles the options-click events for "Restart" option item
				 */
				@Override
			   public void actionPerformed(ActionEvent e)
			   {   game.makeConnection();
			    
					
			   }
		   }
		   
		   
		   /**
		 	 * An inner class to handle the action when Quit is clicked
		 	 * @author Abhimanyue Singh
		 	 *
		 	 */
		   class QuitMenuItemListener implements ActionListener
		   {
			   /**
				 * Handles the options-click events for "Quit" options
				 */
			   public void actionPerformed(ActionEvent e)
			   {
				   System.exit(0);
			   }
		   }
		   
		   /**
		 	 * An inner class to handle the action when clear Chat Area is clicked
		 	 * @author Abhimanyue Singh
		 	 *
		 	 */
		  class chatClearListener implements ActionListener
		   {   
			  /**
				 * Handles the message-click events for "Clear chat area" options
				 */
			   public void actionPerformed(ActionEvent e)
			   {
				   chatArea.setText("");
			   }
		   }
		  
		  /**
		 	 * An inner class to handle the action when clear table text Area is clicked
		 	 * @author Abhimanyue Singh
		 	 *
		 	 */
		  class messageClearListener implements ActionListener
		  {
			  /**
				 * Handles the message-click events for "Clear table text area" options
				 */
			  public void actionPerformed(ActionEvent e)
			  {
				  msgArea.setText("");
			  }
		  }
		   
		  /**
		 	 * An inner class to handle the action when enter key  is pressed
		 	 * @author Abhimanyue Singh
		 	 *
		 	 */
		   class EnterKeyListener implements KeyListener
		   {
			   @Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void keyReleased(KeyEvent arg0) {
					if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					{
						CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1, chatMessageArea.getText());
						((BigTwoClient)game).sendMessage(message);
						chatMessageArea.setText("");
						
					}
					
				}
	
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			   
			   
		   }
		   
		   
		   
		   
		   
		   
		   
		  
		   
		
			
			
			
		
		}
