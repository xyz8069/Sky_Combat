package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Base implements Serializable{
	private static final long serialVersionUID = -4236737666774540511L;
	int x;
	int y;
	int w;
	int h;
	boolean visible = true;
	String imgName;
	transient Image image;
	
	public Base(int x, int y, String imgName){
		this.x = x;
	    this.y = y;
	    //this.image = new ImageIcon(ClassLoader.getSystemResource("Aircraft/" + img_name)).getImage();
	    this.image = getImage(imgName);
	    this.imgName = imgName;
	}
	
	public Base(int x, int y, String imgName, boolean visible){
		this.x = x;
	    this.y = y;
	    this.visible = visible;
	    //this.image = new ImageIcon(ClassLoader.getSystemResource("Aircraft/" + img_name)).getImage();
	    this.image = getImage(imgName);
	    this.imgName = imgName;
	}
	
	public Image getImage(String imgName){
		BufferedImage img = null;
		File file = new File(imgName);
		try {
			img = ImageIO.read(file);
			this.w = img.getWidth();
			this.h = img.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public void setImage(String imgName){
		BufferedImage img = null;
		File file = new File(imgName);
		try {
			img = ImageIO.read(file);
			this.w = img.getWidth();
			this.h = img.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imgName = imgName;
		this.image = this.getImage(imgName);
	}
	
	public void display(Graphics brush) {
		if(this.visible) {
			brush.drawImage(this.image, x, y, null);
		}
	}
	
	public void display(Graphics brush, int x, int y, String imgName) {
		if(!this.imgName.equals(imgName)) {
			this.setImage(imgName);
		}
		brush.drawImage(this.image, x, y, null);
	}
}