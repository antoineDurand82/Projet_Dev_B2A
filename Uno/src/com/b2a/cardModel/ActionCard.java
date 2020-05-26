package com.b2a.cardModel;

import java.awt.Color;

import com.b2a.view.UNOCard;

public class ActionCard extends UNOCard{
	
	private int Function = 0;
	
	//Constructor
	public ActionCard(){
	}
	
	public ActionCard(Color cardColor, String cardValue){
		super(cardColor,ACTION, cardValue);		
	}	
}