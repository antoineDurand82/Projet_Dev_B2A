package com.b2a.cardModel;

import java.awt.Color;

import com.b2a.view.UNOCard;

public class NumberCard extends UNOCard {

	public NumberCard(){
	}
	
	public NumberCard(Color cardColor, String cardValue){
		super(cardColor, NUMBERS, cardValue);		
	}

}