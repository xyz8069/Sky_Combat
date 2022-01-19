package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SaveFile implements Serializable{
	private static final long serialVersionUID = -1144684063143797093L;
	int[] score = new int[1];
    ArrayList<Playercraft> playerList = new ArrayList<Playercraft>();
    ArrayList<Enemycraft> enemyList = new ArrayList<Enemycraft>();
    ArrayList<Enemycraft> enemyDelList = new ArrayList<Enemycraft>();
    ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    ArrayList<Supply> supplyList = new ArrayList<Supply>();
    int playerMode;
    Map<String, Boolean> aircraftBonus;
	
	public SaveFile(Maingame game) {
		score = game.score;
		playerList = game.playerList;
		enemyList = game.enemyList;
		enemyDelList = game.enemyDelList;
		bulletList = game.bulletList;
		supplyList = game.supplyList;
		playerMode = game.playerMode;
		aircraftBonus = game.aircraftBonus;
	}
	
	public void save() {
		String timestamp = Long.toString(System.currentTimeMillis());
    	ObjectOutputStream file;
		try {
			file = new ObjectOutputStream(new FileOutputStream("savefile/" + timestamp));
			SaveFile data = this;
			file.writeObject(data);
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SaveFile load(String timestamp) {
		SaveFile data = null;
        try{
        	String path = "savefile/" + timestamp;
        	ObjectInputStream file = new ObjectInputStream(new FileInputStream(path));
        	data = (SaveFile)file.readObject();
        	file.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        for(Playercraft item: data.playerList) {
        	item.image = item.getImage(item.imgName);
        }
        for(Enemycraft item: data.enemyList) {
        	item.image = item.getImage(item.imgName);
        }
        for(Enemycraft item: data.enemyDelList) {
        	item.image = item.getImage(item.imgName);
        }
        for(Bullet item: data.bulletList) {
        	item.image = item.getImage(item.imgName);
        }
        for(Supply item: data.supplyList) {
        	item.image = item.getImage(item.imgName);
        }
        return data;
	}
}