package com.b2a.server.uno.view;

import java.awt.Color;
import java.io.Serializable;

import com.b2a.server.uno.interfaces.CardInterface;
import com.b2a.server.uno.interfaces.UNOConstants;
import com.b2a.server.uno.cardModel.ActionCard;
import com.b2a.server.uno.cardModel.NumberCard;
import com.b2a.server.uno.cardModel.WildCard;




public abstract class UNOCard implements CardInterface, UNOConstants, Serializable {
	
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
	
	@Override
	public String toString() {
		String cardToString = "";
		String separator = ",";
		cardToString += cardColor.toString() + separator;
		cardToString += (String) value + separator;
		
		cardToString += type;
		cardToString += "\\n";
		
		return cardToString;
	}
	
	public static UNOCard fromString(String cardInString) {
		String[] attributes = cardInString.split(",");
		Color cardColor = BLACK;
		if(attributes[1] == "BLACK") {
			cardColor = BLACK;
		} else if(attributes[1] == "RED") {
			cardColor = RED;
		} else if(attributes[1] == "BLUE") {
			cardColor = BLUE;
		} else if(attributes[1] == "GREEN") {
			cardColor = GREEN;
		} else if(attributes[1] == "YELLOW") {
			cardColor = YELLOW;
		}
		
		String value = "0";
		if(attributes[2] == "2+") {
			value = DRAW2PLUS;
		} else if (attributes[2] == "↺") {
			value = REVERSE;
		} else if (attributes[2] == "✘") {
			value = SKIP;
		} else if (attributes[2] == "W") {
			value = W_COLORPICKER;
		} else if (attributes[2] == "4+") {
			value = W_DRAW4PLUS;
		} else {
			value = attributes[2];
		}
		
		
		int type = Integer.parseInt(attributes[3]);
		if (type == ACTION) {
			return new ActionCard(cardColor, value);
		} else if (type == NUMBERS) {
			return new NumberCard(cardColor, value);
		} else if (type == WILD) {
			return new WildCard(value);
		}
		
		
		return null;
	}
}
