package com.b2a.server.uno.cardModel;

import java.awt.Color;

import com.b2a.server.uno.view.UNOCard;


public class ActionCard extends UNOCard{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2231562716638631347L;

	//Constructor
	public ActionCard(){
	}
	
	public ActionCard(Color cardColor, String cardValue){
		super(cardColor,ACTION, cardValue);		
	}	
}