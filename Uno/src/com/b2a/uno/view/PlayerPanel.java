package com.b2a.uno.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.b2a.uno.game.Player;
import com.b2a.uno.interfaces.CardInterface;
import com.b2a.uno.interfaces.GameConstants;



public class PlayerPanel extends JPanel implements GameConstants {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6208234504269273215L;
	private Player player;
	private String name;

	private Box myLayout;
	private JLayeredPane cardHolder;
	private Box controlPanel;

	private JButton draw;
	private JButton sayUNO;
	private JLabel nameLbl;
	private MyButtonHandler handler;

	// Constructor
	public PlayerPanel(Player newPlayer) {
		setPlayer(newPlayer);

		this.myLayout = Box.createHorizontalBox();
		this.cardHolder = new JLayeredPane();
		this.cardHolder.setPreferredSize(new Dimension(600, 175));

		// Set
		setCards();
		setControlPanel();

		this.myLayout.add(this.cardHolder);
		this.myLayout.add(Box.createHorizontalStrut(40));
		this.myLayout.add(this.controlPanel);
		add(this.myLayout);

		// Register Listeners
		this.handler = new MyButtonHandler();
		this.draw.addActionListener(BUTTONLISTENER);
		this.draw.addActionListener(this.handler);
		
		this.sayUNO.addActionListener(BUTTONLISTENER);
		this.sayUNO.addActionListener(this.handler);
	}

	public void setCards() {
		this.cardHolder.removeAll();

		// Origin point at the center
		Point origin = getPoint(this.cardHolder.getWidth(), this.player.getTotalCards());
		int offset = calculateOffset(this.cardHolder.getWidth(),
				this.player.getTotalCards());

		int i = 0;
		for (UNOCard card : this.player.getAllCards()) {
			card.setBounds(origin.x, origin.y, CardInterface.CARDSIZE.width,
					CardInterface.CARDSIZE.height);
			this.cardHolder.add(card, i++);
			this.cardHolder.moveToFront(card);
			origin.x += offset;
		}
		repaint();
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		setPlayerName(player.getName());
	}

	public void setPlayerName(String playername) {
		this.name = playername;
	}

	private void setControlPanel() {
		this.draw = new JButton("Draw");
		this.sayUNO = new JButton("Say UNO");
		this.nameLbl = new JLabel(name);

		// style
		this.draw.setBackground(new Color(79, 129, 189));
		this.draw.setFont(new Font("Arial", Font.BOLD, 20));
		this.draw.setFocusable(false);

		this.sayUNO.setBackground(new Color(149, 55, 53));
		this.sayUNO.setFont(new Font("Arial", Font.BOLD, 20));
		this.sayUNO.setFocusable(false);

		this.nameLbl.setForeground(Color.WHITE);
		this.nameLbl.setFont(new Font("Arial", Font.BOLD, 15));

		this.controlPanel = Box.createVerticalBox();
		this.controlPanel.add(this.nameLbl);
		this.controlPanel.add(this.draw);
		this.controlPanel.add(Box.createVerticalStrut(15));
		this.controlPanel.add(this.sayUNO);
	}

	private int calculateOffset(int width, int totalCards) {
		int offset = 71;
		if (totalCards <= 8) {
			return offset;
		} else {
			double o = (width - 100) / (totalCards - 1);
			return (int) o;
		}
	}

	private Point getPoint(int width, int totalCards) {
		Point p = new Point(0, 20);
		if (totalCards >= 8) {
			return p;
		} else {
			p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
			return p;
		}
	}
	
	class MyButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if(player.isMyTurn()){
				
				if(e.getSource() == draw)
					BUTTONLISTENER.drawCard();
				else if(e.getSource() == sayUNO)
					BUTTONLISTENER.sayUno();
			}
		}
	}
}
