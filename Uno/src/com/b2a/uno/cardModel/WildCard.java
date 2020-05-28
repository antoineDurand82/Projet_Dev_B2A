package com.b2a.uno.cardModel;

import java.awt.Color;

import com.b2a.uno.view.UNOCard;

public class WildCard extends UNOCard {
	
	private int Function = 0;
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
