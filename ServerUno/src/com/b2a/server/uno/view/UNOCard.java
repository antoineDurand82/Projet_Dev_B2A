package com.b2a.server.uno.view;

import java.awt.Color;
import com.b2a.server.uno.interfaces.CardInterface;
import com.b2a.server.uno.interfaces.UNOConstants;




public abstract class UNOCard implements CardInterface, UNOConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8812247644048412215L;
	private Color cardColor = null;
	private String value = null;
	private int type = 0;
	
	
	public UNOCard(){
	}
	
	public UNOCard(Color cardColor, int cardType, String cardValue){
		this.cardColor = cardColor;
		this.type = cardType;
		this.value = cardValue;
	}
	
	
	public void setColor(Color newColor) {
		this.cardColor = newColor;
	}

	public Color getColor() {
		return cardColor;
	}

	@Override
	public void setValue(String newValue) {
		this.value = newValue;		
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setType(int newType) {
		this.type = newType;
	}

	@Override
	public int getType() {
		return type;
	}
}
