package com.b2a.uno.cardModel;

import java.awt.Color;

import com.b2a.uno.view.UNOCard;

public class ActionCard extends UNOCard{
	
	private int Function = 0;
	
	//Constructor
	public ActionCard(){
	}
	
	public ActionCard(Color cardColor, String cardValue){
		super(cardColor,ACTION, cardValue);		
	}	
}