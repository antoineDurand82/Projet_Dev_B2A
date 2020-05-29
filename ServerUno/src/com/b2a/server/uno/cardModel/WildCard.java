package com.b2a.server.uno.cardModel;

import java.awt.Color;

import com.b2a.server.uno.view.UNOCard;


public class WildCard extends UNOCard {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -534578921049494922L;
	private Color chosenColor;
	
	public WildCard() {
	}
	
	public WildCard(String cardValue){
		super(BLACK, WILD, cardValue);		
	}
	
	public void useWildColor(Color wildColor){
		chosenColor = wildColor;
	}
	
	public Color getWildColor(){
		return chosenColor;
	}

}
