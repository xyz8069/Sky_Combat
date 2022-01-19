package game;

import java.awt.*;
import java.io.Serializable;

public class Label implements Serializable{
	private static final long serialVersionUID = 3825773867330304099L;
	int x;
	int y;
	int w;
	int h;

	public Label(int x, int y, int w, int h){
		this.x = x;
	    this.y = y;
	    this.w = w;
	    this.h = h;
	}
	
	void display(Graphics brush, String text, int size) {
		brush.setColor(new Color(0, 0, 0));
        brush.setFont(new Font("ËÎÌו", Font.BOLD, size));
        brush.drawString(text, x, y);
	}
}