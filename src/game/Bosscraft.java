package game;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Bosscraft extends Enemycraft{
	private static final long serialVersionUID = -7539751835863715289L;
	Bar hpBar;
	
	public Bosscraft(JFrame game, int x, int y, String img_name, int hp_amount){
		super(game, x, y, img_name, hp_amount);
		this.hpBar = new Bar((int)(width * 0.1), (int)(height * 0.2), (int)(width * 0.8), (int)(height * 0.02));
	}
    
	@Override
	void move() {
		if(this.hp > 0) {
			this.fire();
			if(this.y < 100) {
				this.y += 1;
			}
		}
    }
	
	public void display(Graphics brush, int[] score, ArrayList<Bullet> bulletList, ArrayList<Playercraft> playerList, ArrayList<Enemycraft> enemyDelList) {
    	if(this.hp > 0) {
    		this.display(brush);
    		//brush.drawImage(this.image, x, y, null);
    		this.hit(bulletList, playerList);
    		this.hpBar.display(brush, (float)this.hp / this.hpAmount);
    	}
    	else {
    		if(this.time < 30) {
    			this.display(brush);
    			this.imgExplode.x = this.x;
    			this.imgExplode.y = this.y;
    			this.imgExplode.display(brush);
    			//brush.drawImage(this.image, x, y, null);
    			this.time++;
    		}
    		else {
    			if(this.authority == "enemy" && this.hp <= 0) {
    				enemyDelList.add((Enemycraft) this);
    				score[0]++;
    			}
    		}
    	}
    	for(Bullet bullet: this.bulletList) {
    		if(!bullet.judge()) {
    			this.bulletDelList.add(bullet);
    			
    		}
    	}
    	for(Bullet bullet: this.bulletDelList) {
    		this.bulletList.remove(bullet);
    	}
	}
	
	@Override
    void fire() {
    	if(this.bulletList.size() != 0) {
            if(this.y - 14 + 20 > bulletList.get(bulletList.size() - 1).y) {
            	bulletList.add(new Bullet(this.x + 40, this.y + 14, "image/Bullet/3.png", "enemy", -1));
            }
    	}
        else {
        	bulletList.add(new Bullet(this.x + 40, this.y + 14, "image/Bullet/3.png", "enemy", -1));
        }
    }
}