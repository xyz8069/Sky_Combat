package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Resource{
	ArrayList<String> aircraftList;
	ArrayList<String> bulletList;
	
	public Resource(){
		File aircraft = new File("image" + File.separator + "Aircraft");
		if(aircraft.exists()) {
			File[] fs = aircraft.listFiles();
			for(int i = 0; i < fs.length; i++) {
				try {
					aircraftList.add(fs[i].getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		File bullet = new File("image" + File.separator + "Bullet");
		if(bullet.exists()) {
			File[] fs = bullet.listFiles();
			for(int i = 0; i < fs.length; i++) {
				try {
					bulletList.add(fs[i].getCanonicalPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}