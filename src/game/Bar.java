package game;

import java.awt.*;
import java.io.Serializable;

public class Bar implements Serializable{
	private static final long serialVersionUID = -7883481304160727979L;
	int x;
	int y;
	int w;
	int h;

	public Bar(int x, int y, int w, int h){
		this.x = x;
	    this.y = y;
	    this.w = w;
	    this.h = h;
	}
	
	void display(Graphics brush, float percent) {
		brush.setColor(new Color(0, 0, 0));
		brush.drawRect(x, y, w, h);
		brush.setColor(new Color(128, 128, 128));
		brush.fillRect(x, y, w, h);
		brush.setColor(new Color(255, 0, 0));
		brush.fillRect(x, y, (int)(w * percent), h);
	}
}