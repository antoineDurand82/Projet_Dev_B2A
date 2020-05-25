package com.b2a.server;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

import com.b2a.interfaces.GameConstants;


public class GameSession implements GameConstants {
	
	private Game game;
	private Session session;
	private Stack<UNOCard> playedCards;
	public boolean canPlay;
	private int mode;
	
	public GameSession() {
		this.mode = requestMode();
		this.game = new Game(mode);
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
	
	public void playThisCard(UNOCard clickedCard) {
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
				case ACTION:
					performAction(clickedCard);
					break;
				case WILD:
					performWild((WildCard) clickedCard);
					break;
				default:
					break;
				}
				
				this.game.switchTurn();
				this.session.updatePanel(clickedCard);
				checkResults();
			} else {
				infoPanel.setError("invalid move");
				infoPanel.repaint();
			}
		}
		
		if(this.mode == vsPC && this.canPlay) {
			if(this.game.isPCsTurn()) {
				this.game.playPC(peekTopCard());
			}
		}
	}
	
	private void checkResults() {
		if (this.game.isOver()) {
			this.canPlay = false;
			infoPanel.updateText("GAME OVER");
		}
	}
	
	// check player's turn
	public boolean isHisTurn(UNOCard clickedCard) {
		for (Player p : game.getPlayers()) {
			if (p.hasCard(clickedCard) && p.isMyTurn()) {
				return true;
			}
		}
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
			Color color = ((WildCard) topCard).getWildCOlor();
			if(color.equals(playedCard.getColor()))
				return true;
		}
		return false;
	}
	
	// Action Cards
	
	private void performAction(UNOCard actionCard) {
		
		// Draw2PLUS
		if(actionCard.getValue().equals(DRAW2PLUS))
			this.game.drawPlus(2);
		else if (actionCard.getValue().equals(REVERSE))
			this.game.switchTurn();
		else if (actionCard.getValue().equals(SKIP))
			this.game.swithTurn();
	}
	
	private void performWild(WildCard functionCard) {
		
		if(this.mode == 1 && this.game.isPCsTurn()) {
			int random = new Random().nextInt() % 4;
			functionCard.useWildColor(UNO_COLORS[Math.abs(random)]);
		} else {
			
			ArrayList<String> colors = new ArrayList<String>();
			colors.add("RED");
			colors.add("BLUE");
			colors.add("GREEN");
			colors.add("YELLOW");
			
			String chosenColor = (String) JOptionPane.showInputDialog(null,
					"Choose a color", "Wild Card Color",
					JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);
			
			functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
		}
		
		if (functionCard.getValue().equals(W_DRAW4PLUS))
			this.game.drawPlus(4);
	}
	
	public void requestCard() {
		this.game.drawCard(peekTopCard());
		
		if(this.mode == vsPC && this.canPlay) {
			if (this.game.isPCsTurn())
				this.game.playPC(peekTopCard());
		}
		
		session.refreshPanel();
	}
	
	public UNOCard peekTopCard() {
		return playedCards.peek();
	}
	
	public void submitSaidUNO() {
		this.game.setSaidUNO();
	}

	
	
}