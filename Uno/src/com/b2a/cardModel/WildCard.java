package com.b2a.cardModel;

import java.awt.Color;

import com.b2a.view.UNOCard;

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
