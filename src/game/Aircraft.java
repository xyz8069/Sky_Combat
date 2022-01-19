package game;

import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;

public class Aircraft extends Base{
	private static final long serialVersionUID = -7266992330748764358L;
	int width;
	int height;
	String authority;
	int hpAmount;
	int hp;
	//int score;
	ArrayList<Bullet> bulletList;
	ArrayList<Bullet> bulletDelList;
	Base imgExplode;
	int time;
	boolean bonus;
	transient MusicPlayer hit, audioExplode;
	
	public Aircraft(JFrame game, int x, int y, String imgName, String authority, int hpAmount){
		super(x, y, imgName);
		this.width = game.getWidth();
		this.height = game.getHeight();
		this.authority = authority;
        this.hpAmount = hpAmount;
        this.hp = hpAmount;
        this.bulletList = new ArrayList<Bullet>();
        this.bulletDelList = new ArrayList<Bullet>();
        this.time = 0;
        this.imgExplode = new Base(0, 0, "image/Extra/Explode/explode.gif");
        this.hit = new MusicPlayer("music/hit.wav");
        this.audioExplode = new MusicPlayer("music/explode.wav");
	}
	
	public void display(Graphics brush, int[] score, ArrayList<Bullet> bulletList, ArrayList<Playercraft> playerList, ArrayList<Enemycraft> enemyDelList, Map<String, Boolean> aircraftBonus) {
    	if(this.hp > 0) {
    		this.display(brush);
    		//brush.drawImage(this.image, x, y, null);
    		this.hit(bulletList, playerList);
    	}
    	else {
    		if(this.time == 0) {
    			new Thread(audioExplode).start();
    		}
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
    				Random random = new Random();
    				if(random.nextInt(500) < 2 && this.bonus == true) {
    					aircraftBonus.put(this.imgName, true);
    				}
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
	
	public void hit(ArrayList<Bullet> bulletList, ArrayList<Playercraft> playerList){
    	if(!bulletList.isEmpty() && this.hp > 0) {
	    	for(Bullet bullet: bulletList) {
	    		if(bullet.authority != this.authority) {
		    		if(bullet.x < this.x + this.w && bullet.x > this.x && bullet.y < this.y + this.h && bullet.y > this.y) {
		    			this.hp -= bullet.damage;
		    			new Thread(hit).start();
		    			bullet.damage = 0;
		    			if(bullet.visible) {
		    				playerList.get(bullet.no).exp++;
		    			}
		    			bullet.visible = false;
		    		}
	    		}
	    	}
    	}
    }
	
    public void posCheck(int width, int height, ArrayList<Enemycraft> enemyList){
    	if(this.x < 0) {
    		this.x = width - this.w;
    	}
    	else if(this.x > width) {
    		this.x = 0;
    	}
    	if(this.authority == "player") {
	    	if(this.y < 0) {
	    		this.y = 0;
	    	}
	    	else if(this.y > height - this.h) {
	    		this.y = height - this.h;
	    	}
    	}
    	if(this.authority == "enemy" && this.y > height) {
	    	enemyList.remove(this);
    	}
    }
}