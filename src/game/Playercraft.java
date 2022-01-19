package game;

import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class Playercraft extends Aircraft{
	private static final long serialVersionUID = 6263010136424310679L;
	int playerNo;
	int bulletNum, oilNum;
	Map<String, Integer> moveDict;
	Bar hpBar;
	Label bulletLabel, oilLabel;
	int exp;
	int level;
	String player;
	String bullet;
	boolean levelUpFlag;
	Base imgLevelUp;
	transient MusicPlayer eat, gunshot, jetloop;
	//ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	
	public Playercraft(JFrame game, int x, int y, String player, int hpAmount, int playerNo, String bullet){
		super(game, x, y, "image/Player/" + player + "/1.gif", "player", hpAmount);
		this.playerNo = playerNo;
		this.exp = 0;
		this.level = 1;
		this.player = player;
		this.bullet = bullet;
		this.bulletNum = 300;
		this.oilNum = 1000;
		this.levelUpFlag = false;
		this.imgLevelUp = new Base(0, 0, "image/Extra/Levelup/1.png");
		this.moveDict = new HashMap<String, Integer>();
		this.moveDict.put("horizontal", 0);
		this.moveDict.put("vertical", 0);
		this.moveDict.put("space", 0);
		if(this.playerNo == 0) {
			this.hpBar = new Bar((int)(width * 0.1), (int)(height * 0.88), (int)(width * 0.35), (int)(height * 0.02));
			this.bulletLabel = new Label((int)(width * 0.1), (int)(height * 0.8), (int)(width * 0.35), (int)(height * 0.02));
			this.oilLabel = new Label((int)(width * 0.1), (int)(height * 0.85), (int)(width * 0.35), (int)(height * 0.02));
		}
		else if(this.playerNo == 1) {
			this.hpBar = new Bar((int)(width * 0.55), (int)(height * 0.88), (int)(width * 0.35), (int)(height * 0.02));
			this.bulletLabel = new Label((int)(width * 0.55), (int)(height * 0.8), (int)(width * 0.35), (int)(height * 0.02));
			this.oilLabel = new Label((int)(width * 0.55), (int)(height * 0.85), (int)(width * 0.35), (int)(height * 0.02));
		}
		this.eat = new MusicPlayer("music/eat.wav");
        this.gunshot = new MusicPlayer("music/gunshot.wav");
        this.jetloop = new MusicPlayer("music/jet_loop.wav");
	}
	
	public void move() {
		new Thread(jetloop).start();
		this.posCheck(this.width, this.height);
		if(this.oilNum >= 0) {
			if(this.moveDict.get("horizontal") != 0 && this.moveDict.get("vertical") != 0) {
				this.x += 3 * this.moveDict.get("horizontal");
				this.y += 3 * this.moveDict.get("vertical");
				this.oilNum--;
			}
	        else if(this.moveDict.get("horizontal") != 0) {
	        	this.x += 5 * this.moveDict.get("horizontal");
	        	this.oilNum--;
	        }
	        else if(this.moveDict.get("vertical") != 0) {
	        	this.y += 5 * this.moveDict.get("vertical");
	        	this.oilNum--;
	        }
		}
		
		if(this.bulletNum >= 0) {
			if(this.moveDict.get("space") == 1) {
	        	if(this.bulletList.size() != 0) {
	                if(this.y - 14 - 120 > bulletList.get(bulletList.size() - 1).y) {
	                	bulletList.add(new Bullet(this.x+30, this.y-14, bullet, "player", this.playerNo));
	                	this.bulletNum--;
	                	new Thread(gunshot).start();
	                }
	        	}
	            else {
	            	bulletList.add(new Bullet(this.x+30, this.y-14, bullet, "player", this.playerNo));
	            	this.bulletNum--;
	            	new Thread(gunshot).start();
	            }
        	}
        }
	}
	
	public void hit(ArrayList<Bullet> bulletList, ArrayList<Supply> supplyList, int num){
    	if(!bulletList.isEmpty() && this.hp > 0) {
	    	for(Bullet bullet: bulletList) {
	    		if(bullet.authority != this.authority) {
		    		if(bullet.x < this.x + this.w && bullet.x > this.x && bullet.y < this.y + this.h && bullet.y > this.y) {
		    			this.hp -= bullet.damage;
		    			bullet.damage = 0;
		    			bullet.visible = false;
		    			new Thread(hit).start();
		    		}
	    		}
	    	}
    	}
    	if(!supplyList.isEmpty() && this.hp > 0) {
	    	for(Supply supply: supplyList) {
	    		if(supply.x < this.x + this.w && supply.x > this.x && supply.y < this.y + this.h && supply.y > this.y) {
	    			if(supply.type == "hp") {
		    			this.hp += supply.lives;
		    			if(this.hp >= this.hpAmount) {
		    				this.hp = this.hpAmount;
		    			}
	    			}
	    			if(supply.type == "bullet") {
		    			this.bulletNum += supply.bullet;
		    			if(this.bulletNum >= 300) {
		    				this.bulletNum = 300;
		    			}
	    			}
	    			if(supply.type == "oil") {
		    			this.oilNum += supply.oil;
		    			if(this.oilNum >= 1000) {
		    				this.oilNum = 1000;
		    			}
	    			}
	    			supply.lives = 0;
	    			supply.visible = false;
	    			new Thread(eat).start();
	    		}
	    	}
    	}
    }
	
	public void crash(ArrayList<Enemycraft> enemyList){
    	if(!enemyList.isEmpty() && this.hp > 0) {
	    	for(Enemycraft enemy: enemyList) {
	    		if(enemy.x < this.x + this.w && enemy.x + enemy.w > this.x && enemy.y < this.y + this.h && enemy.y > this.y) {
	    			this.hp -= enemy.damage;
	    			enemy.damage = 0;
	    			enemy.hp = 0;
	    		}
	    	}
    	}
    }
	
	public void display(Graphics brush, ArrayList<Bullet> bulletList, ArrayList<Supply> supplyList, ArrayList<Enemycraft> enemyList) {
    	if(this.hp > 0) {
    		this.display(brush);
    		//brush.drawImage(this.image, x, y, null);
    		this.hit(bulletList, supplyList, 0);
    		this.crash(enemyList);
    		this.levelUp(brush);
    		this.hpBar.display(brush, (float)this.hp / this.hpAmount);
    		this.bulletLabel.display(brush, "Bullet:" + this.bulletNum, 18);
    		this.oilLabel.display(brush, "Oil:" + this.oilNum, 18);
    	}
    	else {
    		if(this.time < 100) {
    			this.display(brush);
    			//brush.drawImage(this.image, x, y, null);
    			this.time++;
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
	
	public void levelUp(Graphics brush) {
		if(this.hp > 0 && this.exp > this.level * 50) {
			this.exp -= this.level * 50;
			if(this.level < 3) {
				this.level++;
			}
			this.levelUpFlag = true;
			this.setImage("image/Player/" + player + File.separator + this.level + ".gif");
		}
		if(this.levelUpFlag) {
			if(this.time < 13) {
				this.imgLevelUp.display(brush, this.x - 115, this.y - 100, "image/Extra/Levelup/1.png");
				this.time++;
			}
			else if(this.time < 26) {
				this.imgLevelUp.display(brush, this.x - 115, this.y - 100, "image/Extra/Levelup/2.png");
				this.time++;
			}
			else if(this.time < 40) {
				this.imgLevelUp.display(brush, this.x - 115, this.y - 100, "image/Extra/Levelup/3.png");
				this.time++;
			}
			else {
				this.time = 0;
				this.levelUpFlag = false;
			}
		}
	}
	
	public void posCheck(int width, int height){
    	if(this.x < 0) {
    		this.x = width - this.w;
    	}
    	else if(this.x > width) {
    		this.x = 0;
    	}
    	if(this.y < 0) {
    		this.y = 0;
    	}
    	else if(this.y > height - this.h) {
    		this.y = height - this.h;
    	}
    }
}