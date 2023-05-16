package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class GameView extends JFrame {
	private JProgressBar loadingScreen;
	private JLabel startScreenBackground;
	private JLabel startBackgroundImage;
	
	private JPanel championSelection; //LeftMost panel 
	private JLabel firstplayer ; 
	private JLabel secondplayer ;
	private JPanel championButton;
	private JTextArea championDetails; //rightMost panel 
	private JPanel ButtonsForSelectionChampion ; // buttons panel to Navigate 
	private JButton confirm ;
	private JButton start ; 
	private JButton selectFirstLeader;
	private JButton selectSecondLeader;
	private int buttonpositioningindex;
	private String player1nameDialogue;
	private String player2nameDialogue;


	private JPanel boardPanel; //center panel in actual game view
	private JPanel leftmost; //left panel in actual game view
    private JTextArea currentChampionDetails , abilityDetails;
    
	private JPanel rightmost; //right panel in actual game view
	private JTextArea remainingChampionDetails;
	private JLabel currentChampionTurn;
	private JPanel turnOrderPanel;
	private JPanel playerUseLeaderPanel;
	private JPanel lowerButtonsPanel;
	private JButton FIRSTuseleaderAbility;
	
	private JButton SECONDuseleaderAbility;
	private JLabel firstPlayerInGame;
	
	private JLabel secondPlayerInGame;
    private JButton castAbility , firstAbility , secondAbility ,thirdAbility ,endTurn ;
    private JButton punchAbility;
	
	
	public GameView() {
				
		buttonpositioningindex =20;
		this.setSize(1920,1080);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Marvel: Ultimate War");
		
		//fonts
		Font font1 = new Font(Font.DIALOG, Font.PLAIN, 14);
		Font font2 = new Font(Font.SERIF, Font.BOLD, 20);
		
		ImageIcon image = new ImageIcon("Marvel_Logo.png");
		this.setIconImage(image.getImage());
		
		ImageIcon backgroundImage = new ImageIcon("background.png");
		startScreenBackground = new JLabel("",backgroundImage,JLabel.CENTER);
		this.add(startScreenBackground);
		this.revalidate();
		this.repaint();
		
		
		
	
		
		do {
			player1nameDialogue = JOptionPane.showInputDialog("First Player Name");
		}while (player1nameDialogue.equals(""));
		player1nameDialogue = player1nameDialogue.toUpperCase();
		
		do {
			player2nameDialogue= JOptionPane.showInputDialog("Second Player Name");
		}while (player2nameDialogue.equals(""));
		player2nameDialogue = player2nameDialogue.toUpperCase();
		
		startScreenBackground.setVisible(false);
		championButton = new JPanel ();
		championButton.setPreferredSize(new Dimension(500,this.getHeight()));
		championButton.setLayout(new GridLayout (0,4));
		championButton.setVisible(true);
		this.add(championButton,BorderLayout.CENTER);
		

		championDetails = new JTextArea();
		championDetails.setPreferredSize(new Dimension(300,600));
		championDetails.setEditable(false);
		championDetails.setOpaque(false);
		//insert method here
		this.add(championDetails,BorderLayout.EAST);
		
		championDetails.setFont(font1);
		championDetails.setForeground(Color.BLUE);
		
		
		selectFirstLeader = new JButton ();
		selectFirstLeader.setText ("SET FIRST LEADER");
		selectFirstLeader.setPreferredSize(new Dimension (300,150));
		selectFirstLeader.setFont(font2);
		selectFirstLeader.setEnabled(false);
		selectFirstLeader.setFocusable(false);//-------------
		
		
		selectSecondLeader = new JButton ();
		selectSecondLeader.setText ("SET SECOND LEADER");
		selectSecondLeader.setPreferredSize(new Dimension (300,150));
		selectSecondLeader.setFont(font2);
		selectSecondLeader.setEnabled(false);
		selectSecondLeader.setFocusable(false);//--------------
		
		
		confirm = new JButton ();
		confirm.setText ("CONFIRM");
		confirm.setPreferredSize(new Dimension (300,150));
		confirm.setFont(font2);
		confirm.setFocusable(false);//-----------
		
		start = new JButton ();
		start.setText("START");
		start.setPreferredSize(new Dimension (300,150));
		start.setFont(font2);
		start.setEnabled(false);
		start.setFocusable(false); //-----------

		
		ButtonsForSelectionChampion = new JPanel();
		ButtonsForSelectionChampion.add(selectFirstLeader);
		ButtonsForSelectionChampion.add(selectSecondLeader);
		ButtonsForSelectionChampion.add(confirm);
		ButtonsForSelectionChampion.add(start);
		this.add(ButtonsForSelectionChampion , BorderLayout.SOUTH);
		
		
		firstplayer = new JLabel();
		firstplayer.setText(player1nameDialogue +"'S TEAM" );
		
		
		secondplayer = new JLabel();
		secondplayer.setText(player2nameDialogue +"'S TEAM" );
		
		
		championSelection = new JPanel( );
		championSelection.setPreferredSize(new Dimension(450,this.getHeight()));
		championSelection.setVisible(true);
		championSelection.setLayout(null);
		championSelection.add(firstplayer);
		championSelection.add(secondplayer);

		//firstplayer.setLocation(20,20);
		firstplayer.setBounds(20,20,200,20);
		firstplayer.repaint();
		firstplayer.revalidate();
		//firstplayer.setVerticalAlignment(20);;
		//firstplayer.setHorizontalAlignment(20);
		secondplayer.setBounds(20,230,200,20);
		secondplayer.repaint();
		secondplayer.revalidate();
		championSelection.revalidate();
		championSelection.repaint();
		//firstplayer.setVerticalAlignment(20);
		//firstplayer.setHorizontalAlignment(300);
		this.add(championSelection , BorderLayout.WEST);
	//	this.revalidate();
	//	this.repaint();
	
		//actual gameplay
		boardPanel = new JPanel();
		boardPanel.setPreferredSize(new Dimension(500,this.getHeight()));
		boardPanel.setLayout(new GridLayout (5,5));
	
	    //this.add(championButton,BorderLayout.CENTER); //additions during co
		firstPlayerInGame = new JLabel();
		firstPlayerInGame.setText("PLAYER 1: " + player1nameDialogue);
		firstPlayerInGame.setFont(font1);
		firstPlayerInGame.setForeground(Color.BLUE);
		secondPlayerInGame = new JLabel();
		secondPlayerInGame.setText("PLAYER 2: " + player2nameDialogue);
		secondPlayerInGame.setFont(font1);
		secondPlayerInGame.setForeground(Color.RED);
		
		playerUseLeaderPanel = new JPanel ();
		playerUseLeaderPanel.setBounds(0,0,this.getWidth(),80);
		
		playerUseLeaderPanel.setLayout(new GridLayout(0,4));
	    FIRSTuseleaderAbility =new JButton();   // (player1nameDialogue+" use leader Ability");/// name of hero champion
	    FIRSTuseleaderAbility.setFocusable(false); // so it doesnt stay highlighted
	    FIRSTuseleaderAbility.setFont(font1);
	    FIRSTuseleaderAbility.setForeground(Color.BLUE);

	    SECONDuseleaderAbility =new JButton();   // (player2nameDialogue+ " use leader Ability");
	    SECONDuseleaderAbility.setFocusable(false); // so it doesnt stay highlighted
	    SECONDuseleaderAbility.setFont(font1);
	    SECONDuseleaderAbility.setForeground(Color.RED);
	    firstPlayerInGame.setPreferredSize(new Dimension(300,60));
	    secondPlayerInGame.setPreferredSize(new Dimension(300,60));
	    FIRSTuseleaderAbility.setPreferredSize(new Dimension(300,60));
	    SECONDuseleaderAbility.setPreferredSize(new Dimension(300,60));
	    firstPlayerInGame.setHorizontalAlignment(SwingConstants.CENTER);
	    secondPlayerInGame.setHorizontalAlignment(SwingConstants.CENTER);
	    firstPlayerInGame.setAlignmentX(CENTER_ALIGNMENT);
	    secondPlayerInGame.setAlignmentX(CENTER_ALIGNMENT);

	    firstPlayerInGame.setAlignmentY(CENTER_ALIGNMENT);
	    secondPlayerInGame.setAlignmentY(CENTER_ALIGNMENT);
	    secondPlayerInGame.setVisible(true);
	    firstPlayerInGame.setVisible(true);
	
	    playerUseLeaderPanel.add(firstPlayerInGame);
	    playerUseLeaderPanel.add(FIRSTuseleaderAbility);
	    
	    
	    playerUseLeaderPanel.add(SECONDuseleaderAbility);
	    playerUseLeaderPanel.add(secondPlayerInGame);
	    playerUseLeaderPanel.setVisible(false);
	    this.add(playerUseLeaderPanel);
		
	    this.revalidate();
	    this.repaint();
	    
		lowerButtonsPanel = new JPanel ();
		lowerButtonsPanel.setBounds(0,this.getHeight()-100,this.getWidth(),80);
		lowerButtonsPanel.setLayout(new GridLayout(0,5));
		
		castAbility = new JButton ("CAST ABILITY");
		castAbility.setPreferredSize(new Dimension(300,60));
		
		lowerButtonsPanel.add(castAbility);
		
		
		firstAbility = new JButton ("FIRST ABILITY");
		firstAbility.setPreferredSize(new Dimension(300,60));
		lowerButtonsPanel.add(firstAbility);
		
		secondAbility = new JButton ("SECOND ABILITY");
		secondAbility.setPreferredSize(new Dimension(300,60));
		lowerButtonsPanel.add(secondAbility);
		
		thirdAbility = new JButton ("THIRD ABILITY");
		thirdAbility.setPreferredSize(new Dimension(300,60));
		lowerButtonsPanel.add(thirdAbility);
		
		endTurn = new JButton ("END TURN");
		endTurn.setPreferredSize(new Dimension(300,60));
		lowerButtonsPanel.add(endTurn);
		
		lowerButtonsPanel.setVisible(false);
		this.add(lowerButtonsPanel);
		
		
		
		

		leftmost = new JPanel ();
		leftmost.setBounds(5,80,245,this.getHeight()-180);
		leftmost.setLayout(new BorderLayout());
		
		currentChampionDetails = new JTextArea();
		currentChampionDetails.setPreferredSize(new Dimension (250,(this.getHeight()-160)/2));
		currentChampionDetails.setOpaque(false);
		currentChampionDetails.setEditable(false);
		leftmost.add(currentChampionDetails,BorderLayout.NORTH);
		
		abilityDetails = new JTextArea();
		abilityDetails.setPreferredSize(new Dimension (250,(this.getHeight()-160)/2));
		abilityDetails.setOpaque(false);
		abilityDetails.setEditable(false);
		leftmost.add(abilityDetails,BorderLayout.CENTER);
		
		punchAbility = new JButton ("PUNCH"); //
		punchAbility.setPreferredSize(new Dimension(300,60)); //
		punchAbility.setVisible(false); //
		leftmost.add(punchAbility, BorderLayout.SOUTH); //
		
		leftmost.setVisible(false);
		this.add(leftmost); 
		
		rightmost = new JPanel();
		rightmost.setBounds(1020,80,255,this.getHeight()-160); //1275
	    rightmost.setLayout(new BorderLayout());
	    
	    remainingChampionDetails = new JTextArea();
	    remainingChampionDetails.setOpaque(false);
	    remainingChampionDetails.setEditable(false);
	    
	   
	    remainingChampionDetails.setPreferredSize(new Dimension (250,(this.getHeight()-160)/2));
	    //remainingChampionDetails.setEditable(false);
	    rightmost.add(remainingChampionDetails,BorderLayout.NORTH);
	 
	    turnOrderPanel = new JPanel();
	    turnOrderPanel.setPreferredSize(new Dimension (250,(this.getHeight()-160)/2));
	    turnOrderPanel.setLayout(new BoxLayout(turnOrderPanel,BoxLayout.Y_AXIS));
	    rightmost.add(turnOrderPanel,BorderLayout.SOUTH);
	    
		
		
		
	}
	
	
	public static void main(String[] args) {
		GameView g = new GameView();
	}
	
	
	public JTextArea getCurrentChampionDetails() {
		return currentChampionDetails;
	}
	
	public JLabel getCurrentChampionTurn() {
		return currentChampionTurn;
	}

	public JButton getCastAbility() {
		return castAbility;
	}

	public JButton getFirstAbility() {
		return firstAbility;
	}

	public JButton getSecondAbility() {
		return secondAbility;
	}

	public JButton getThirdAbility() {
		return thirdAbility;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public JProgressBar getLoadingScreen() {
		return loadingScreen;
	}

	public JPanel getPlayerUseLeaderPanel() {
		return playerUseLeaderPanel;
	}

	public JPanel getLowerButtonsPanel() {
		return lowerButtonsPanel;
	}

	public JButton getFIRSTuseleaderAbility() {
		return FIRSTuseleaderAbility;
	}

	public JButton getSECONDuseleaderAbility() {
		return SECONDuseleaderAbility;
	}

	public JLabel getFirstPlayerInGame() {
		return firstPlayerInGame;
	}

	public JLabel getSecondPlayerInGame() {
		return secondPlayerInGame;
	}

	public JLabel getStartScreenBackground() {
		return startScreenBackground;
	}

	public JPanel getChampionButton() {
		return championButton;
	}

	public JTextArea getChampionDetails() {
		return championDetails;
	}

	public JPanel getChampionSelection() {
		return championSelection;
	}

	public JLabel getFirstplayer() {
		return firstplayer;
	}

	public JLabel getSecondplayer() {
		return secondplayer;
	}

	public JPanel getButtonsForSelectionChampion() {
		return ButtonsForSelectionChampion;
	}

	public JButton getConfirm() {
		return confirm;
	}

	public JButton getStart() {
		return start;
	}

	public JButton getSelectFirstLeader() {
		return selectFirstLeader;
	}

	public JButton getSelectSecondLeader() {
		return selectSecondLeader;
	}

	public void setButtonpositioningindex(int buttonpositioningindex) {
		this.buttonpositioningindex = buttonpositioningindex;
	}

	public int getButtonpositioningindex() {
		return buttonpositioningindex;
	}
	
	public String getPlayer1nameDialogue() {
		return player1nameDialogue;
	}
	public String getPlayer2nameDialogue() {
		return player2nameDialogue;
	}
	public int getButtonPositioningIndex() {
		return buttonpositioningindex;
	}

	public JPanel getBoardPanel() {
		return boardPanel;
	}



	public JPanel getRightmost() {
		return rightmost;
	}

	public JTextArea getRemainingChampionDetails() {
		return remainingChampionDetails;
	}

	public JPanel getTurnOrderPanel() {
		return turnOrderPanel;
	}
	public JPanel getLeftmost() {
		return leftmost;
	}

	public JTextArea getAbilityDetails() {
		return abilityDetails;
	}

	public JButton getPunchAbility() {
		return punchAbility;
	}

	public JLabel getStartBackgroundImage() {
		return startBackgroundImage;
	}
}
