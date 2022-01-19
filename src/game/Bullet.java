package game;

import java.awt.Graphics;

public class Bullet extends Base {
	private static final long serialVersionUID = 4492398334770986981L;
	String authority;
	int damage;
	int no;
	Boolean visible;
	
    public Bullet(int x, int y, String image_name, String authority, int no) {
    	super(x, y, image_name);
        this.authority = authority;
        this.no = no;
        this.visible = true;
        this.damage = 1;
    }
        
    boolean judge() {
    	if(this.y > 20 && this.y < 720) {
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
    	if(this.authority == "player") {
    		this.y -= 8;
    	}
    	else if(this.authority == "enemy") {
    		this.y += 8;
    	}
    } 
}