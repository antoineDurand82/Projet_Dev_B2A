package com.b2a.server.uno.cardModel;

import java.awt.Color;

import com.b2a.server.uno.view.UNOCard;

public class NumberCard extends UNOCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1891864707677821854L;

	public NumberCard(){
	}
	
	public NumberCard(Color cardColor, String cardValue){
		super(cardColor, NUMBERS, cardValue);		
	}

}