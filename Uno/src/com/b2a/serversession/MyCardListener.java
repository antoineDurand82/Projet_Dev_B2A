package com.b2a.serversession;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.b2a.uno.view.UNOCard;

public class MyCardListener extends MouseAdapter {
	
	UNOCard sourceCard;
	GameSession myGameSession;
	
	public void setGameSession(GameSession gameSession) {
		this.myGameSession = gameSession;
	}
	
	public void mousePressed(MouseEvent e) {
		this.sourceCard = (UNOCard) e.getSource();
		
		try {
			if(this.myGameSession.canPlay)
				this.myGameSession.playThisCard(sourceCard);
		} catch (NullPointerException exc) {
			exc.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		
		this.sourceCard = (UNOCard) e.getSource();
		Point p = this.sourceCard.getLocation();
		p.y -=20;
		this.sourceCard.setLocation(p);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		this.sourceCard = (UNOCard) e.getSource();
		Point p = this.sourceCard.getLocation();
		p.y +=20;
		this.sourceCard.setLocation(p);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
