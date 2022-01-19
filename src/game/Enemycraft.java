package game;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Enemycraft extends Aircraft{
	private static final long serialVersionUID = -606883514977317852L;
	//ArrayList<Bullet> bullet_list = new ArrayList<Bullet>();
	String direction;
	Base sight;
	int damage = 10;
	
	public Enemycraft(JFrame game, int x, int y, String img_name, int hp_amount){
		super(game, x, y, img_name, "enemy", hp_amount);
		this.direction = "right";
	}
	
	public Enemycraft(JFrame game, int x, int y, String img_name, int hp_amount, boolean bonus){
		super(game, x, y, img_name, "enemy", hp_amount);
		this.direction = "right";
		this.bonus = bonus;
		if(this.bonus) {
			//TODO:sight
			//sight = new Base(hp_amount, hp_amount, "image/");
		}
	}
            
	void move() {
		if(this.hp > 0) {
			this.fire();
			if(this.direction == "right") {
				this.x += 3;
			}
			else if(this.direction == "left") {
				this.x -= 3;
			}
			if(this.x > 860) {
				this.direction = "left";
			}
			else if(this.x < 10) {
				this.direction = "right";
			}
			this.y += 1;
		}
    }
	
	public void posCheck(int width, int height, ArrayList<Enemycraft> enemyDelList){
    	if(this.x < 0) {
    		this.x = width - this.w;
    	}
    	else if(this.x > width) {
    		this.x = 0;
    	}
    	if(this.y > height) {
	    	enemyDelList.add(this);
    	}
    }
	
    void fire() {
    	if(this.bulletList.size() != 0) {
            if(this.y - 14 + 20 > bulletList.get(bulletList.size() - 1).y) {
            	bulletList.add(new Bullet(this.x + 40, this.y + 14, "image/Bullet/2.png", "enemy", -1));
            }
    	}
        else {
        	bulletList.add(new Bullet(this.x + 40, this.y + 14, "image/Bullet/2.png", "enemy", -1));
        }
    }
    
    void aim() {
    	
    }
}