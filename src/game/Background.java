package game;

import java.awt.*;

public class Background extends Base{
	private static final long serialVersionUID = -4632020405990875408L;
	int time = 0;
			
	public Background(int x, int y, String imgName){
		super(x, y, imgName);
	}
	
	@Override
	public void display(Graphics brush) {
		final int y = -h + 720;
		brush.drawImage(this.image, x, y + time, null);
		time += 2;
		if(y + time >= 0) {
			time = 0;
		}
	}
}