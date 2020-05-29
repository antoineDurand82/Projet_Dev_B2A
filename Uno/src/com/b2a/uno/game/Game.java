package com.b2a.uno.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Stack;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.b2a.uno.cardModel.*;
import com.b2a.uno.interfaces.GameConstants;
import com.b2a.uno.main.Main;
import com.b2a.uno.view.UNOCard;

public class Game implements GameConstants {

	private Player[] players;
	private boolean isOver;
	private int GAMEMODE;
	
	private Stack<UNOCard> cardStack;
	
	
	public Game(int mode){
		
		GAMEMODE = mode;
	}

	public Player[] getPlayers() {
		return players;
	}

	public UNOCard getCard() throws IOException, ClassNotFoundException {
		JSONObject dataClient = new JSONObject();
        dataClient.put("askCard", "yes");
        if(dataClient.toString() != null)
        	Main.getSocketOut().println(dataClient.toString());
        ObjectInputStream objectIn = new ObjectInputStream(Main.getSocket().getInputStream());
        UNOCard cardGiven = (UNOCard) objectIn.readObject();
        objectIn.close();
        return cardGiven;
	}
//	TODO
//	GO VOIR AVEC LE SERVER
	
	public void removePlayedCard(UNOCard playedCard) throws ClassNotFoundException, IOException {

		for (Player p : players) {
			if (p.hasCard(playedCard)){
				p.removeCard(playedCard);
				
				if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
					infoPanel.setError(p.getName() + " Forgot to say UNO");
					p.obtainCard(getCard());
					p.obtainCard(getCard());
				}else if(p.getTotalCards()>2){
					p.setSaidUNOFalse();
				}
			}			
		}
	}
	
	//give player a card
	public void drawCard(UNOCard topCard) throws ClassNotFoundException, IOException {

		boolean canPlay = false;

		for (Player p : players) {
			if (p.isMyTurn()) {
				UNOCard newCard = getCard();
				p.obtainCard(newCard);
				canPlay = canPlay(topCard, newCard);
				break;
			}
		}

		if (!canPlay)
			switchTurn();
	}

	public void switchTurn() {
//		for (Player p : players) {
//			p.toggleTurn();
//		}
//		whoseTurn();
		// TODO
//		ENVOI AU SERVEUR QUE MON TOUR EST FINIT
	}
	
	//Draw cards x times
	public void drawPlus(int times) throws ClassNotFoundException, IOException {
		for (Player p : players) {
			if (!p.isMyTurn()) {
				for (int i = 1; i <= times; i++)
					p.obtainCard(getCard());
			}
		}
	}
	
	//response whose turn it is
	public void whoseTurn() {

		for (Player p : players) {
			if (p.isMyTurn()){
				infoPanel.updateText(p.getName() + "'s Turn");
				System.out.println(p.getName() + "'s Turn");
			}
		}
		infoPanel.setDetail(playedCardsSize(), remainingCards());
		infoPanel.repaint();
	}
	

	public int remainingCards() {
		return cardStack.size();
	}

	public int[] playedCardsSize() {
		int[] nr = new int[2];
		int i = 0;
		for (Player p : players) {
			nr[i] = p.totalPlayedCards();
			i++;
		}
		return nr;
	}

	//Check if this card can be played
	private boolean canPlay(UNOCard topCard, UNOCard newCard) {

		// Color or value matches
		if (topCard.getColor().equals(newCard.getColor())
				|| topCard.getValue().equals(newCard.getValue()))
			return true;
		// if chosen wild card color matches
		else if (topCard.getType() == WILD)
			return ((WildCard) topCard).getWildColor().equals(newCard.getColor());

		// suppose the new card is a wild card
		else if (newCard.getType() == WILD)
			return true;

		// else
		return false;
	}

	//Check whether the player said or forgot to say UNO
	public void checkUNO() throws ClassNotFoundException, IOException {
		for (Player p : players) {
			if (p.isMyTurn()) {
				if (p.getTotalCards() == 1 && !p.getSaidUNO()) {
					infoPanel.setError(p.getName() + " Forgot to say UNO");
					p.obtainCard(getCard());
					p.obtainCard(getCard());
				}
			}
		}		
	}

	public void setSaidUNO() {
		for (Player p : players) {
			if (p.isMyTurn()) {
				if (p.getTotalCards() == 2) {
					p.saysUNO();
					infoPanel.setError(p.getName() + " said UNO");
				}
			}
		}
	}
}
