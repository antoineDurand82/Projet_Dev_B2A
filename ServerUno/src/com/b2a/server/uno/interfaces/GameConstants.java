package com.b2a.server.uno.interfaces;

import java.awt.*;


public interface GameConstants extends UNOConstants {
	 
	int TOTAL_CARDS = 108;
	int FIRSTHAND = 8;
	
	Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
	Color WILD_CARDCOLOR = BLACK;
	
	int[] UNO_NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	String[] ActionTypes = {REVERSE, SKIP, DRAW2PLUS};
	String[] WildTypes = {W_COLORPICKER, W_DRAW4PLUS};
	
	int vsPC = 1;
	int MANUAL = 2;
	
	int[] GAMEMODES = {vsPC, MANUAL};
	
}
