package game;

import java.awt.*;

public class Button{
	int x;
	int y;
	int w;
	int h;
	String text;

	public Button(int x, int y, int w, int h, String text){
		this.x = x;
	    this.y = y;
	    this.w = w;
	    this.h = h;
	    this.text = text;
	}
	
	void display(Graphics brush, int[] mousePos) {
		if(mousePos[0] < x + w && mousePos[0] > x && mousePos[1] < y + h && mousePos[1] > y) {
			brush.setColor(new Color(178, 178, 178));
		}
		else {
			brush.setColor(new Color(128, 128, 128));
		}
		brush.drawRect(x, y, w, h);
        brush.setFont(new Font("ËÎÌו", Font.BOLD, 32));
        brush.drawString(text, x, y);
	}
	
	boolean click(int[] mousePos) {
		if(mousePos[0] < x + w && mousePos[0] > x && mousePos[1] < y + h && mousePos[1] > y) {
			return true;
		}
		return false;
	}
}