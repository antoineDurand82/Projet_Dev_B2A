package com.b2a.server.uno.cardModel;

import java.awt.Color;
import java.util.LinkedList;

import com.b2a.server.uno.interfaces.GameConstants;
import com.b2a.server.uno.view.UNOCard;


/**
 * This Class contains standard 108-Card stack
 */
public class CardDeck implements GameConstants {
	
//	private final LinkedList<NumberCard> numberCards;
//	private final LinkedList<ActionCard> actionCards;
//	private final LinkedList<WildCard> wildCards;
	
	private LinkedList<UNOCard> UNOcards;
	
	public CardDeck(){
		
//		//Initialize Cards
//		numberCards = new LinkedList<NumberCard>();
//		actionCards = new LinkedList<ActionCard>();
//		wildCards = new LinkedList<WildCard>();
		
		UNOcards = new LinkedList<UNOCard>();
		
		addCards();
	}
	
	
	//Create 108 cards for this CardDeck
	private void addCards() {
		for(Color color:UNO_COLORS){
			
			//Create 76 NumberCards --> doubles except 0s.
			for(int num : UNO_NUMBERS){
				int i=0;
				do{
					UNOcards.add(new NumberCard(color, Integer.toString(num)));
					i++;
				}while(num!=0 && i<2);
			}
			
			//Create 24 ActionCards --> everything twice
			for(String type : ActionTypes){
				for(int i=0;i<2;i++)
					UNOcards.add(new ActionCard(color, type));
			}					
		}		
		
		for(String type : WildTypes){
			for(int i=0;i<4;i++){
				UNOcards.add(new WildCard(type));
			}
		}
		
	}
	
	
	public LinkedList<UNOCard> getCards(){
		return UNOcards;
	}
}