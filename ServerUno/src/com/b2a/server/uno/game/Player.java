package com.b2a.server.uno.game;

import java.io.Serializable;
import java.util.LinkedList;

import com.b2a.server.uno.view.UNOCard;
import com.b2a.server.uno.game.Player;


public class Player implements Serializable{
	
	private String name = null;
	private boolean isMyTurn = false;
	private boolean saidUNO = false;
	private LinkedList<UNOCard> myCards;
	
	private int playedCards = 0;
	
	public Player(){
		myCards = new LinkedList<UNOCard>();
	}
	
	public Player(String player){
		setName(player);
		myCards = new LinkedList<UNOCard>();
	}
	
	public Player(String name, LinkedList<UNOCard> myNewCards) {
		this.name = name;
		this.myCards = myNewCards;
	}

	public void setName(String newName){
		name = newName;
	}
	
	public String getName(){
		return this.name;
	}
	public void obtainCard(UNOCard card){
		myCards.add(card);
	}
	
	public LinkedList<UNOCard> getAllCards(){
		return myCards;
	}
	
	public int getTotalCards(){
		return myCards.size();
	}
	
	public boolean hasCard(UNOCard thisCard){
		return myCards.contains(thisCard);		
	}
	
	public void removeCard(UNOCard thisCard){
		myCards.remove(thisCard);
		playedCards++;
	}
	
	public int totalPlayedCards(){
		return playedCards;
	}
	
	public void toggleTurn(){
		isMyTurn = (isMyTurn) ? false : true;
	}
	
	public boolean isMyTurn(){
		return isMyTurn;
	}
	
	public boolean hasCards(){
		return (myCards.isEmpty()) ? false : true;
	}
	
	public boolean getSaidUNO(){
		return saidUNO;
	}
	
	public void saysUNO(){
		saidUNO = true;
	}
	
	public void setSaidUNOFalse(){
		saidUNO = false;
	}
	
	public void setCards(){
		myCards = new LinkedList<UNOCard>();
	}
	
	@Override
	public String toString() {
		String stringToReturn = "";
		String separator = ";";
		stringToReturn += name + separator;
		while(!myCards.isEmpty()) {
			UNOCard card = myCards.pop();
			stringToReturn += card.toString() + separator;
		}
		stringToReturn += "\\n";
		return stringToReturn;
	}
	
	public static Player fromString(String playerInString) {
		String[] attributes = playerInString.split(";");
		String name = attributes[0];
		LinkedList<UNOCard> myCards = new LinkedList<UNOCard>();
		if(attributes.length > 2) {
			for (int i = 1; i < (attributes.length - 1); i ++) {
				String cardStringed = attributes[i];
				UNOCard card = UNOCard.fromString(cardStringed);
				myCards.add(card);
//				System.out.println(myCards);
			
			}
		}
		return new Player(name, myCards);
	}
}
