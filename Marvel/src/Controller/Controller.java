package Controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import view.GameView;

public class Controller implements ActionListener, MouseListener , KeyListener {
	private Game game;
	private GameView view;
	private ArrayList<JButton> loadingScreenChampionButtons;
	private Player firstPlayer;
	private Player secondPlayer;
	private int [] firstPlayerIndices = new int[3];
	private int [] secondPlayerIndices = new int [3];
	private int x = -1;
	private int i =1;
	private JButton [][] boardButtons = new JButton [5][5] ;
	private ArrayList<Integer>  abIndex  = new ArrayList<>();
	private boolean abilityKeyPressed = false;
	private boolean castAbilityKeyPressed = false;
	private Ability ability;
	private boolean championSelectedToSetAsLeader = false;

	

	
	
	public Controller() throws IOException {
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		loadingScreenChampionButtons = new ArrayList<JButton>();
		view = new GameView();
		championSelectionButtons(view);
		firstPlayer = new Player(view.getPlayer1nameDialogue());
		secondPlayer = new Player(view.getPlayer2nameDialogue());
		
		
		view.getConfirm().addActionListener(this);
		view.getSelectFirstLeader().addActionListener(this);
		view.getSelectSecondLeader().addActionListener(this);
		view.getStart().addActionListener(this);
		
		view.getFirstAbility().addActionListener(this);
		view.getSecondAbility().addActionListener(this);
		view.getThirdAbility().addActionListener(this);
		
		view.getCastAbility().addActionListener(this);
		
		view.getFIRSTuseleaderAbility().addActionListener(this);
		view.getSECONDuseleaderAbility().addActionListener(this);
		
		view.getPunchAbility().addActionListener(this);//----------

		view.getEndTurn().addActionListener(this);
		
		view.revalidate();
		view.repaint();
	}
	//why was this method static
	private void championSelectionButtons(GameView view) {
		for (int i = 0; i< Game.getAvailableChampions().size(); i++) {
			JButton b = new JButton(Game.getAvailableChampions().get(i).getName());
			b.addActionListener(this);// the controller now listens to every buttonclick
			loadingIcons(b);
			loadingScreenChampionButtons.add(b); //model adding button to an arraylist containing buttons
			view.getChampionButton().add(b); //view adding button to panel
		}
	}
	
	private void gameOverAfterAction() {
		Player winner = game.checkGameOver();
		if (winner==null) {
			return;
		}
		int exit = JOptionPane.showConfirmDialog(view,"" + winner.getName() + " IS THE WINNER!", "GAME OVER", JOptionPane.DEFAULT_OPTION);
		if (exit==0)
			System.exit(0);
		else 
			System.exit(0);
	}
	
	//checks if current champion after endturn has punch ability
	private boolean hasPunch() {
		for (Ability ab: game.getCurrentChampion().getAbilities())
			if (ab.getName().equals("Punch"))
				return true;
		return false;
	}
	
	//gets punch ability index for current champion
	private int getPunchIndex() {
		for (int i = 0; i< game.getCurrentChampion().getAbilities().size();i++)
			if (game.getCurrentChampion().getAbilities().get(i).getName().equals("Punch"))
				return i;
		return 0;
	}
	
	private void refreshBoard() {
        view.getBoardPanel().removeAll();
		for (int i = Game.getBoardheight()-1; i>=0;i--) {
            for (int j = 0; j<=4;j++) {
                JButton b = new JButton();
                b.setOpaque(false);
                b.addMouseListener(this);
                if (game.getBoard()[i][j] !=null) {
                    if (game.getBoard()[i][j] instanceof Cover) {
                        b.setText( "Cover HP: " +((Cover)game.getBoard()[i][j]).getCurrentHP() + "");
                        b.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
                        b.setBackground(Color.decode("#818c8b"));
                        
                      
                    }
                    else {
                    	if(game.getBoard()[i][j]==game.getCurrentChampion()) {
                  	    	b.addKeyListener(this);
                  	    	b.setSelected(true);
                    	}
                    	else {
                    		b.removeKeyListener(this);
                    	}
                    	b.setText(((Champion)game.getBoard()[i][j]).getName());
                    	loadingIcons(b);
                    	b.setText("<html><center>" +((Champion) game.getBoard()[i][j]).getName() + "<br>" +((Champion)game.getBoard()[i][j]).getCurrentHP()+"</center></html>");
                        if (firstPlayer.getTeam().contains(game.getBoard()[i][j])) {
                        	b.setForeground(Color.BLUE);
                        }
                        else {
                        	b.setForeground(Color.RED);
                        }
                    }
                }
            
                boardButtons[i][j] =  b;
                
                view.getBoardPanel().add(b);
                view.revalidate();
                view.repaint();
            }
        }
		gameOverAfterAction();
    }
	
	
	
	private void loadingIcons(JButton b) {
		String champ = b.getText();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("captainamercia.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpool.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrange.png"); 
		break;
		case "Electro" : icon= new ImageIcon ("electro.png"); 
		break;
		case "Ghost Rider" : icon= new ImageIcon ("ghostrider.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("hela.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulk1.png");
		break;
		case "Iceman" : icon= new ImageIcon ("iceman.png");
		break;
		case "Ironman" : icon= new ImageIcon ("iron man.png");
		break;
		case "Loki" : icon= new ImageIcon ("loki.png");
		break;
		case "Quicksilver" : icon= new ImageIcon ("R.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spiderman.png");
		break;
		case "Thor" : icon= new ImageIcon ("thor png.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venom.png");
		break;
		case "Yellow Jacket" : icon= new ImageIcon ("yellowjacket.png");
		break;
		}
		b.setIcon(icon);
		b.setText("");
	}
	
	public static void main(String[] args) throws IOException {
		new Controller();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			if (b == view.getConfirm()) {
				if (x<0 || !championSelectedToSetAsLeader) {
					JOptionPane.showMessageDialog(view, "Need to Select Champion First");
					//set argument to view instead of b to get at center of screen
				}
				else {
					if (i<=6) {
						confirmLogic(x);
						i++;
						view.getChampionDetails().setText(null); //--------------
						championSelectedToSetAsLeader = false;
					}
				}
			}
			else if (b == view.getSelectFirstLeader()) {
				if (!championSelectedToSetAsLeader) //-------------
					JOptionPane.showMessageDialog(view, "Need to Select Champion First"); //-----------------
				else {
					firstPlayer.setLeader(Game.getAvailableChampions().get(x));
					for (int i = 0; i<firstPlayerIndices.length; i++)
						loadingScreenChampionButtons.get(firstPlayerIndices[i]).setEnabled(false);
					for (int i = 0; i<secondPlayerIndices.length; i++)
						loadingScreenChampionButtons.get(secondPlayerIndices[i]).setEnabled(true);
					view.getSelectSecondLeader().setEnabled(true);
					view.getSelectFirstLeader().setEnabled(false);
					view.getChampionDetails().setText(null); //--------------
					championSelectedToSetAsLeader = false;
				}
			}
			else if (b == view.getSelectSecondLeader()) {
				if (!championSelectedToSetAsLeader) //---------------
					JOptionPane.showMessageDialog(view, "Need to Select Champion First"); //-----------------
				else {
					secondPlayer.setLeader(Game.getAvailableChampions().get(x));
					for (int i = 0; i<secondPlayerIndices.length; i++)
						loadingScreenChampionButtons.get(secondPlayerIndices[i]).setEnabled(false);
					view.getSelectSecondLeader().setEnabled(false);
					view.getStart().setEnabled(true);
					view.getChampionDetails().setText(null);
					game = new Game(firstPlayer,secondPlayer);
					championSelectedToSetAsLeader = false;//----------
				}
				
			}
			else if (b == view.getStart()) {
				view.getChampionSelection().setVisible(false);
				view.getButtonsForSelectionChampion().setVisible(false);
				view.getChampionDetails().setVisible(false);
				view.getChampionButton().setVisible(false);
			
				view.setLayout(null);
				
				view.add(view.getBoardPanel());
		
				view.getLeftmost().setVisible(true);
				fillAndRefreshTurnorder();
				view.add(view.getRightmost());

				view.getBoardPanel().setBounds(250,80,770,view.getHeight()-180); //1025
				view.getPlayerUseLeaderPanel().setVisible(true);
				view.getFIRSTuseleaderAbility().setText(firstPlayer.getLeader().getName() + " USE LEADER ABILITY");//-------
				view.getSECONDuseleaderAbility().setText(secondPlayer.getLeader().getName() + " USE LEADER ABILITY");//----------
				view.getLowerButtonsPanel().setVisible(true);
				view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
				refreshBoard();
				view.revalidate();			
				view.repaint();
				JOptionPane.showMessageDialog(view, "Move With: arrow keys | Attack With: w,a,s,d");

			}
			else if (b == view.getFirstAbility()){
				view.getAbilityDetails().setText(game.getCurrentChampion().getAbilities().get(0).toString2());
				abIndex.add(0);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(true);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(false);
				view.getPunchAbility().setSelected(false);
			}
			else if (b == view.getSecondAbility()){
				view.getAbilityDetails().setText(game.getCurrentChampion().getAbilities().get(1).toString2());
				abIndex.add(1);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(true);
				view.getThirdAbility().setSelected(false);
				view.getPunchAbility().setSelected(false);
			}
			else if (b == view.getThirdAbility()){
				view.getAbilityDetails().setText(game.getCurrentChampion().getAbilities().get(2).toString2());
				abIndex.add(2);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(true);
				view.getPunchAbility().setSelected(false);
			}
			else if (b == view.getPunchAbility()) {
				int i = getPunchIndex();
				view.getAbilityDetails().setText(game.getCurrentChampion().getAbilities().get(i).toString2());
				abIndex.add(i);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(false);
				view.getPunchAbility().setSelected(true);
			}
			else if (b== view.getCastAbility()) {
				if(!abilityKeyPressed) {
					JOptionPane.showMessageDialog(view, " You Need To Select An Ability ");
				}
				else {
					   ability = game.getCurrentChampion().getAbilities().get(abIndex.get(abIndex.size()-1));
					   castAbilityKeyPressed = true;
					   abilityGUIlogic(ability);
					   abilityKeyPressed = false;
					   view.getFirstAbility().setSelected(false);
					   view.getSecondAbility().setSelected(false);
					   view.getThirdAbility().setSelected(false);
					   view.getPunchAbility().setSelected(false);
				}
			}
			else if (b == view.getFIRSTuseleaderAbility()) {
					if (firstPlayer.getTeam().contains(game.getCurrentChampion())) {
						try {
							game.useLeaderAbility();
							view.getFIRSTuseleaderAbility().setText(firstPlayer.getLeader().getName() + " LEADER ABILITY USED");//-------
						//	view.getFIRSTuseleaderAbility().setEnabled(false);
							refreshBoard();
							fillAndRefreshTurnorder();
							view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
						} catch (LeaderNotCurrentException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						} catch (LeaderAbilityAlreadyUsedException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}
					}
			}
			else if (b == view.getSECONDuseleaderAbility()) {
					if (secondPlayer.getTeam().contains(game.getCurrentChampion())) {
						try {
							game.useLeaderAbility();
							view.getSECONDuseleaderAbility().setText(secondPlayer.getLeader().getName() + " LEADER ABILITY USED");//-------
						//	view.getSECONDuseleaderAbility().setEnabled(false);
							refreshBoard();
							fillAndRefreshTurnorder();
							view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
						} catch (LeaderNotCurrentException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						} catch (LeaderAbilityAlreadyUsedException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}	
					}
			}
			else if (b == view.getEndTurn()) {
				game.endTurn();
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(false);
				view.getAbilityDetails().setText(null);
				if (hasPunch()) {
					view.getPunchAbility().setVisible(true); //-----------------
				}
				else
					view.getPunchAbility().setVisible(false);
				refreshBoard();
				fillAndRefreshTurnorder();
				view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n"+game.getCurrentChampion().effectsList());
			}
			
			else {
				int r = loadingScreenChampionButtons.indexOf(b);
				String champinfo = Game.getAvailableChampions().get(r).toString();
				for (int i = 0 ; i < Game.getAvailableChampions().get(r).getAbilities().size();i++)
				{
					String ab = "";
					if (i==0)
						ab = "FIRST ";
					if (i==1)
						ab = "SECOND ";
					if (i==2)
						ab = "THIRD ";
					champinfo += ab + Game.getAvailableChampions().get(r).getAbilities().get(i).toString();
					
				}
				view.getChampionDetails().setText(champinfo);
				view.getChampionDetails().setOpaque(false);
				x = r; // x stores index of button/champion in arraylist
				championSelectedToSetAsLeader = true; // flag to ensure a champion is selected before clicking on a button
				}
		}
			
	}
	private void abilityGUIlogic(Ability ability2) {
		
		try {
		switch(ability2.getCastArea()) {
		case SURROUND :
		case TEAMTARGET :
		case SELFTARGET :game.castAbility(ability2);
						castAbilityKeyPressed = false;
						break;
		}
		
		}
		catch (NotEnoughResourcesException ex)
		{
			castAbilityKeyPressed = false;
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		catch (AbilityUseException ex)
		{
			castAbilityKeyPressed = false;
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		catch ( CloneNotSupportedException ex)
		{
			castAbilityKeyPressed = false;
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		
		if( ability2.getCastArea()== AreaOfEffect.SINGLETARGET)
			{
				JOptionPane.showMessageDialog(view, "Please Choose a Target ");
			}
		else if(ability2.getCastArea()==AreaOfEffect.DIRECTIONAL)
		{
			JOptionPane.showMessageDialog(view, "Please Choose Direction Using Uppercase Attack Buttons: W,A,S,D");
		}
		
		refreshBoard();
		fillAndRefreshTurnorder();
		view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
		view.getAbilityDetails().setText(null);
	
	}
		
	private  void addaButtonTurnorder(String text, Container container, int i) {
		JButton b = new JButton(text);
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font = new Font(Font.DIALOG, Font.PLAIN, 14);
		b.setFont(font);
		b.setSize(new Dimension(250,100));
		if (i==0) {
		b.setSelected(true);
		
		}
		container.add(b);
	}
	private static void addaLabelTurnorder(String text, Container container) {
		JLabel b = new JLabel(text);
		
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setSize(new Dimension(250,40));
		Border border = BorderFactory.createLineBorder(Color.black);
		b.setBorder(border);
		container.add(b);
	}
	public void fillAndRefreshTurnorder() {
		//view.getTurnOrderPanel().setLayout(new BoxLayout(view.getTurnOrderPanel(),BoxLayout.Y_AXIS));
		view.getTurnOrderPanel().removeAll();
		ArrayList<Champion> temp = new ArrayList<Champion>();
		while(!game.getTurnOrder().isEmpty()) {
			temp.add((Champion) game.getTurnOrder().remove());
		}
		addaLabelTurnorder("CURRENT CHAMPION TURN",view.getTurnOrderPanel());
		for(int i = 0 ; i < temp.size();i++) {
			addaButtonTurnorder(temp.get(i).getName(), view.getTurnOrderPanel(), i);
			
		}
		
		for(int i = 0 ; i < temp.size();i++) {
			game.getTurnOrder().insert(temp.get(i));
			
			
		}
		
		
	}
	
	public void confirmLogic(int r) {
		JButton b = loadingScreenChampionButtons.get(r);
        if (i == 1 ) {
            view.getChampionSelection().add(b);
            b.setBounds(10,60,130,130);
           firstPlayerIndices[0] = x;
           b.setEnabled(false);
        }
        else if (i == 2)
        {
            view.getChampionSelection().add(b);
            b.setBounds(150,60,130,130);
            firstPlayerIndices[1] = x;
            b.setEnabled(false);
        }
        else if (i == 3)
        {
            view.getChampionSelection().add(b);
            b.setBounds(290,60,130,130);
            firstPlayerIndices[2] = x;
            b.setEnabled(false);
        }
        else if (i == 4)
        {
            view.getChampionSelection().add(b);
            b.setBounds(10,270,130,130);
            b.setEnabled(false);
            secondPlayerIndices[0] = x;
        }
        else if (i == 5)
        {
            view.getChampionSelection().add(b);
            b.setBounds(150,270,130,130);
            b.setEnabled(false);
            secondPlayerIndices[1] = x;
        }
        else if (i == 6)
        {
            view.getChampionSelection().add(b);
            b.setBounds(290,270,130,130);
            b.setEnabled(false);
            secondPlayerIndices[2] = x;
            view.getConfirm().setEnabled(false);
			view.getSelectFirstLeader().setEnabled(true);
			for (int i=0; i<loadingScreenChampionButtons.size();i++) {
				if (i!=firstPlayerIndices[0] && i!=firstPlayerIndices[1] &&i!=firstPlayerIndices[2] &&
						i!=secondPlayerIndices[0] && i!=secondPlayerIndices[1] && i!=secondPlayerIndices[2] ) {
					loadingScreenChampionButtons.get(i).setEnabled(false);
				}
				else {
					if (i==firstPlayerIndices[0] || i==firstPlayerIndices[1] || i==firstPlayerIndices[2] ) {
						loadingScreenChampionButtons.get(i).setEnabled(true);
					}
				}
			}
			
        }
        view.revalidate();
        view.repaint();
        view.getChampionButton().remove(b);
        if (i<=3) {
        	
        	firstPlayer.getTeam().add(Game.getAvailableChampions().get(r));
        
        	
        }
        else {
        	secondPlayer.getTeam().add(Game.getAvailableChampions().get(r));
        
        }
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		boolean flag = true;
		for (int i = 0; i<=4; i++)
			for (int j = 0; j<=4; j++) {
				if (boardButtons[i][j] ==b && (game.getBoard()[i][j] instanceof Champion) &&( (Champion)game.getBoard()[i][j]) ==game.getCurrentChampion())
					flag = false;
			}
		if (castAbilityKeyPressed && flag) {
			if (game.getCurrentChampion().getAbilities().get(abIndex.get(abIndex.size()-1)).getCastArea().toString().equals("DIRECTIONAL"))
				flag = false;
			if (flag){
				for (int i =0; i<=4; i++) {
					for(int j = 0; j<=4; j++) {
						if (b == boardButtons[i][j]) {
							try {
									game.castAbility(game.getCurrentChampion().getAbilities().get(abIndex.get(abIndex.size()-1)), i, j);
							}
							catch (NotEnoughResourcesException ex)
							{
								JOptionPane.showMessageDialog(view, ex.getMessage());
								
							}
							catch (AbilityUseException ex)
							{
								//System.out.println("hshdsj");
								JOptionPane.showMessageDialog(view, ex.getMessage());
							}
							catch ( CloneNotSupportedException ex)
							{
								JOptionPane.showMessageDialog(view, ex.getMessage());
							}
							catch (InvalidTargetException ex) {
								JOptionPane.showMessageDialog(view, ex.getMessage());
							}
							finally{
								refreshBoard();
								fillAndRefreshTurnorder();
								view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
								//view.getAbilityDetails().setText(game.getCurrentChampion().getAbilities().get(abIndex).toString2());
								view.getAbilityDetails().setText(null);
								castAbilityKeyPressed=false;
	
							}
						}
					}
				}
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		 JButton b = (JButton)e.getSource();
		 String s = "";
		 for (int i=0 ; i<=4 ;i++ )
		 {
			 for (int j = 0; j<= 4 ; j++)
			 {
				 if (boardButtons[i][j]==b)
				 
				 {
					if(game.getBoard()[i][j] instanceof Champion)
					{
						s += ((Champion)(game.getBoard())[i][j]).toString2();
						if (game.getBoard()[i][j].equals(firstPlayer.getLeader()) || game.getBoard()[i][j].equals(secondPlayer.getLeader())) {
							s += "Leader: YES } \n \n";
						}
						else {
							s += "Leader: NO } \n \n";
						}
						s += ((Champion)(game.getBoard())[i][j]).effectsList();
					}
					else
						s= "";
				 }
				 view.getRemainingChampionDetails().setText(s);
			 }
		 }
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		char c = e.getKeyChar();
		try {
			if (key == KeyEvent.VK_LEFT)
				game.move(Direction.LEFT);
			if ( key == KeyEvent.VK_RIGHT)
				game.move(Direction.RIGHT);
			if ( key == KeyEvent.VK_UP)
				game.move(Direction.UP);
			if ( key == KeyEvent.VK_DOWN)
				game.move(Direction.DOWN);
		}
		catch (NotEnoughResourcesException ex){
			JOptionPane.showMessageDialog(view, ex.getMessage());
			 
		}
		catch (UnallowedMovementException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		
		try {
			if(c == 'w') 
				game.attack(Direction.UP);
			else if(c =='a')
				game.attack(Direction.LEFT);
			else if(c== 's')
				game.attack(Direction.DOWN);
			else if(c=='d')
				game.attack(Direction.RIGHT);
		}
		catch(NotEnoughResourcesException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		catch(InvalidTargetException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		catch(ChampionDisarmedException ex ) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		
		try {
			if (castAbilityKeyPressed) { // check that cast ability pressed first so they dont work otherwise, cannot use (WASD) unless directed to do so
				if (c=='W') {
					game.castAbility(ability, Direction.UP);
					view.getAbilityDetails().setText(null);
				}
				else if(c =='A') {
					game.castAbility(ability, Direction.LEFT);
					view.getAbilityDetails().setText(null);
				}
				else if(c== 'S') {
					game.castAbility(ability, Direction.DOWN);
					view.getAbilityDetails().setText(null);
				}
				else if(c=='D') {
					game.castAbility(ability, Direction.RIGHT);
					view.getAbilityDetails().setText(null);
				}
			}
		}
		catch (NotEnoughResourcesException ex)
		{
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		catch (AbilityUseException ex)
		{
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		catch (CloneNotSupportedException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		finally {
			castAbilityKeyPressed = false;
		}
		
		
		refreshBoard();
		fillAndRefreshTurnorder();
		view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
