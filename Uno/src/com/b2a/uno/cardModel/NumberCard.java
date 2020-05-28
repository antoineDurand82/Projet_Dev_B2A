package com.b2a.uno.cardModel;

import java.awt.Color;

import com.b2a.uno.view.UNOCard;

public class NumberCard extends UNOCard {

	public NumberCard(){
	}
	
	public NumberCard(Color cardColor, String cardValue){
		super(cardColor, NUMBERS, cardValue);		
	}

}