package com.b2a.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.b2a.cardModel.WildCard;
import com.b2a.interfaces.GameConstants;



public class TablePanel extends JPanel implements GameConstants {
	
	private UNOCard topCard;	
	private JPanel table;
	
	public TablePanel(UNOCard firstCard){
		setOpaque(false);
		setLayout(new GridBagLayout());
		
		this.topCard = firstCard;
		this.table = new JPanel();
		this.table.setBackground(new Color(64,64,64));
		
		setTable();
		setComponents();
	}
	
	private void setTable(){
		
		this.table.setPreferredSize(new Dimension(500,200));
		this.table.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.table.add(this.topCard, c);
	}
	
	private void setComponents() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 130, 0, 45);
		add(this.table,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 1, 0, 1);
		add(infoPanel, c);	
	}

	public void setPlayedCard(UNOCard playedCard){
		this.table.removeAll();
		this.topCard = playedCard;
		setTable();
		
		setBackgroundColor(playedCard);
	}
	
	public void setBackgroundColor(UNOCard playedCard){
		Color background;
		if(playedCard.getType()==WILD)
			background = ((WildCard) playedCard).getWildColor();
		else
			background = playedCard.getColor();
		
		this.table.setBackground(background);
	}
}
