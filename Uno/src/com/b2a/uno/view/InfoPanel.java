package com.b2a.uno.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5801628724426150149L;
	private String error;
	private String text;
	private int panelCenter;
	
	private int you = 0;
	private int pc = 0;
	private int rest = 0;
	
	public InfoPanel(){
		setPreferredSize(new Dimension(275,200));
		setOpaque(false);
		this.error = "";
		this.text = "Game Started";
		
		updateText(this.text);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.panelCenter = getWidth()/2;
		
		printMessage(g);
		printError(g);
		printDetail(g);
	}

	private void printError(Graphics g) {
		if(!this.error.isEmpty()){
			Font adjustedFont = new Font("Calibri", Font.PLAIN,	25);
			
			//Determine the width of the word to position
			FontMetrics fm = this.getFontMetrics(adjustedFont);
			int xPos = this.panelCenter - fm.stringWidth(this.error) / 2;
			
			g.setFont(adjustedFont);
			g.setColor(Color.red);
			g.drawString(this.error, xPos, 35);
			
			this.error = "";
		}
	}

	private void printMessage(Graphics g) {
		Font adjustedFont = new Font("Calibri", Font.BOLD,	25);	
		
		//Determine the width of the word to position
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		int xPos = this.panelCenter - fm.stringWidth(this.text) / 2;
		
		g.setFont(adjustedFont);
		g.setColor(new Color(228,108,10));
		g.drawString(this.text, xPos, 75);		
	}
	
	private void printDetail(Graphics g){
		Font adjustedFont = new Font("Calibri", Font.BOLD,	25);	
		FontMetrics fm = this.getFontMetrics(adjustedFont);
		g.setColor(new Color(127,127,127));
		
		//Determine the width of the word to position
		String text = "Played Cards";
		int xPos = this.panelCenter - fm.stringWidth(text) / 2;
		
		g.setFont(adjustedFont);
		g.drawString(this.text, xPos, 120);
		
		text = "Remaining: " + rest;
		xPos = this.panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 180);
		
		//Details
		adjustedFont = new Font("Calibri", Font.PLAIN,	20);
		g.setFont(adjustedFont);
		fm = this.getFontMetrics(adjustedFont);
		
		text = "You : "+ this.you + "  PC : " + this.pc;
		xPos = this.panelCenter - fm.stringWidth(text) / 2;
		g.drawString(text, xPos, 140);
		
		text = String.valueOf(rest);
		xPos = this.panelCenter - fm.stringWidth(text) / 2;
		//g.drawString(text, xPos, 190);
	}

	public void updateText(String newText) {
		this.text = newText;
	}
	
	public void setError(String errorMgs){
		this.error = errorMgs;
	}
	
	public void setDetail(int[] playedCards, int remaining){
		this.you = playedCards[1];
		this.pc = playedCards[0];
		this.rest = remaining;
	}
}
