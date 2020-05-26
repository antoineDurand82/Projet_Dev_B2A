package com.b2a.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.b2a.game.Game;



public class Session extends JPanel {
	private PlayerPanel player1;
	private PlayerPanel player2;
	private TablePanel table;	
	
	private Game game;
	
	public Session(Game newGame, UNOCard firstCard){
		setPreferredSize(new Dimension(960,720));
		setBackground(new Color(30,36,40));
		setLayout(new BorderLayout());
		
		this.game = newGame;
		
		setPlayers();
		this.table = new TablePanel(firstCard);
		this.player1.setOpaque(false);
		this.player2.setOpaque(false);
		
		add(this.player1,BorderLayout.NORTH);
		add(this.table, BorderLayout.CENTER);
		add(this.player2, BorderLayout.SOUTH);		
	}
	
	private void setPlayers() {
		this.player1 = new PlayerPanel(this.game.getPlayers()[0]);
		this.player2 = new PlayerPanel(this.game.getPlayers()[1]);		
	}
	
	public void refreshPanel(){
		this.player1.setCards();
		this.player2.setCards();
		
		this.table.revalidate();		
		revalidate();
	}
	
	public void updatePanel(UNOCard playedCard){
		this.table.setPlayedCard(playedCard);
		refreshPanel();
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
