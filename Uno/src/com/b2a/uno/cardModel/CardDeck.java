package com.b2a.uno.cardModel;

import java.util.LinkedList;

import com.b2a.serversession.MyCardListener;
import com.b2a.uno.interfaces.GameConstants;
import com.b2a.uno.view.UNOCard;

/**
 * This Class contains standard 108-Card stack
 */
public class CardDeck implements GameConstants {
	
	private LinkedList<UNOCard> UNOcards;
	
	public CardDeck(LinkedList<UNOCard> cardDeck){
		
		
		UNOcards = cardDeck;
		addCardListener(CARDLISTENER);
	}

	
	//Cards have MouseListener
	public void addCardListener(MyCardListener listener){
		for(UNOCard card: UNOcards)
		card.addMouseListener(listener);
	}
	
	public LinkedList<UNOCard> getCards(){
		return UNOcards;
	}
}