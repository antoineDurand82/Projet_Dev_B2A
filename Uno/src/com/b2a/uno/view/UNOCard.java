package com.b2a.uno.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.b2a.uno.cardModel.ActionCard;
import com.b2a.uno.cardModel.NumberCard;
import com.b2a.uno.cardModel.WildCard;
import com.b2a.uno.view.UNOCard;
import com.b2a.uno.interfaces.CardInterface;
import com.b2a.uno.interfaces.UNOConstants;



public abstract class UNOCard extends JPanel implements CardInterface, UNOConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8812247644048412215L;
	private Color cardColor = null;
	private String value = null;
	private int type = 0;
	
	private Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
	private Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);
	
	public UNOCard(){
	}
	
	public UNOCard(Color cardColor, int cardType, String cardValue){
		this.cardColor = cardColor;
		this.type = cardType;
		this.value = cardValue;
		
		this.setPreferredSize(CARDSIZE);
		this.setBorder(defaultBorder);
		
		this.addMouseListener(new MouseHandler());
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		int cardWidth = CARDSIZE.width;
		int cardHeight = CARDSIZE.height;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, cardWidth, cardHeight);
		
		int margin = 5;
		g2.setColor(this.cardColor);
		g2.fillRect(margin, margin, cardWidth-2*margin, cardHeight-2*margin);
		
		g2.setColor(Color.white);
		AffineTransform org = g2.getTransform();
		g2.rotate(45,cardWidth*3/4,cardHeight*3/4);		

		g2.fillOval(0,cardHeight*1/4,cardWidth*3/5, cardHeight);
		g2.setTransform(org);		
		
		//Value in the center		
		Font defaultFont = new Font("Helvetica", Font.BOLD, cardWidth/2+5);		
		FontMetrics fm = this.getFontMetrics(defaultFont);
		int StringWidth = fm.stringWidth(this.value)/2;
		int FontHeight = defaultFont.getSize()*1/3;
		
		g2.setColor(this.cardColor);
		g2.setFont(defaultFont);
		g2.drawString(this.value, cardWidth/2-StringWidth, cardHeight/2+FontHeight);
		
		//Value in the corner
		defaultFont = new Font("Helvetica", Font.ITALIC, cardWidth/5);		
		fm = this.getFontMetrics(defaultFont);
		StringWidth = fm.stringWidth(this.value)/2;
		FontHeight = defaultFont.getSize()*1/3;
		
		g2.setColor(Color.white);
		g2.setFont(defaultFont);
		g2.drawString(this.value, 2*margin,5*margin);		
	}	
	
	/**
	 * My Mouse Listener 
	 */
	class MouseHandler extends MouseAdapter {
		
		public void mouseEntered(MouseEvent e){
			setBorder(focusedBorder);
		}
		
		public void mouseExited(MouseEvent e){
			setBorder(defaultBorder);
		}
	}
	
	public void setCardSize(Dimension newSize){
		this.setPreferredSize(newSize);
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
