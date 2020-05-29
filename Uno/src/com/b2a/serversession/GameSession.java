package com.b2a.serversession;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

import com.b2a.uno.cardModel.WildCard;
import com.b2a.uno.game.Game;
import com.b2a.uno.game.Player;
import com.b2a.uno.interfaces.GameConstants;
import com.b2a.uno.main.Main;
import com.b2a.uno.view.Session;
import com.b2a.uno.view.UNOCard;


public class GameSession implements GameConstants {
	
	private Game game;
	private Session session;
	private Stack<UNOCard> playedCards;
	public boolean canPlay;
	
	public GameSession(Game newGame) throws ClassNotFoundException, IOException {
		
		this.game = newGame;
		this.playedCards = new Stack<UNOCard>();
		
		// First Card
		UNOCard firstCard = this.game.getCard();
		modifyFirstCard(firstCard);
		
		this.playedCards.add(firstCard);
		this.session = new Session(game, firstCard);
		
		this.game.whoseTurn();
		this.canPlay = true;
	}
	
	
	
	private int requestMode() {
		Object[] options = {
				"vs PC",
				"Manual",
				"Cancel"
		};
		
		int n = JOptionPane.showOptionDialog(null,
				"Choose a Game Mode to play", "Game Mode",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
		if (n == 2) {
			System.exit(1);
		}
		return GAMEMODES[n];
	}
	
	private void modifyFirstCard(UNOCard firstCard) {
		firstCard.removeMouseListener(CARDLISTENER);
		if (firstCard.getType() == WILD) {
			int random = new Random().nextInt() % 4;
			try {
				((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
			} catch (Exception exc) {
				System.err.println(exc.getMessage());
			}
		}
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public void playThisCard(UNOCard clickedCard) throws ClassNotFoundException, IOException {
		// Check player's turn
		if(!isHisTurn(clickedCard)) {
			infoPanel.setError("It's not your turn");
			infoPanel.repaint();
		} else {
			
			// Card Validation
			if (isValidMove(clickedCard)) {
				clickedCard.removeMouseListener(CARDLISTENER);
				this.playedCards.add(clickedCard);
				this.game.removePlayedCard(clickedCard);
				
				// function cards ??
				
				switch (clickedCard.getType()) {
				case WILD:
					clickedCard = performWild((WildCard) clickedCard);
					break;
				default:
					break;
				}
				
				this.session.updatePanel(clickedCard);
			} else {
				infoPanel.setError("invalid move");
				infoPanel.repaint();
			}
		}
	}
	
	// check player's turn
	public boolean isHisTurn(UNOCard clickedCard) {
//		for (Player p : game.getPlayers()) {
		// TODO
		// CHANGER LE GAME.GETPLAYERS POUR RECUP DEPUIS SERVEUR
			if (Main.getPlayer().hasCard(clickedCard) && Main.getPlayer().isMyTurn()) {
				return true;
			}
//		}
		return false;
	}
	
	public boolean isValidMove(UNOCard playedCard) {
		UNOCard topCard = peekTopCard();
		
		if (playedCard.getColor().equals(topCard.getColor()) || playedCard.getValue().equals(topCard.getValue())) {
			return true;
		}
		
		else if (playedCard.getType() == WILD) {
			return true;
		} else if (topCard.getType() == WILD) {
			Color color = ((WildCard) topCard).getWildColor();
			if(color.equals(playedCard.getColor()))
				return true;
		}
		return false;
	}
	
	// Action Cards
	
	
	private WildCard performWild(WildCard functionCard) {
		
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("RED");
		colors.add("BLUE");
		colors.add("GREEN");
		colors.add("YELLOW");
		
		String chosenColor = (String) JOptionPane.showInputDialog(null,
				"Choose a color", "Wild Card Color",
				JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);
		
		functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
		return functionCard;

	}
	
	public void requestCard() throws ClassNotFoundException, IOException {
		this.game.drawCard(peekTopCard());
		session.refreshPanel();
	}
	
	public UNOCard peekTopCard() {
		return playedCards.peek();
	}
	
	public void submitSaidUNO() {
		this.game.setSaidUNO();
	}
	// TODO

	
	
}
