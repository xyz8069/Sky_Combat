package game;

import java.awt.Graphics;

public class Supply extends Base{
	private static final long serialVersionUID = -2294162558896213619L;
	Boolean visible;
	String type;
	int lives = 10;
	int bullet = 10;
	int oil = 100;
	
    public Supply(int x, int y, String image_name, String type) {
    	super(x, y, image_name);
    	this.visible = true;
    	this.type = type;
    }
        
    boolean judge(int height) {
    	if(this.y < height) {
    		return true;
    	}
        else {
        	return false;
        }  
    }
    
    public void display(Graphics brush) {
    	if(this.visible) {
    		brush.drawImage(this.image, x, y, null);
    	}
	}
    
    void move() {
    	this.y += 8;
    }
}