package game;

public class ImageButton extends Base{
	private static final long serialVersionUID = 8263215992915459958L;

	public ImageButton(int x, int y, String imgName){
		super(x, y, imgName);
	}
	
	boolean click(int[] mousePos) {
		if(mousePos[0] < x + w && mousePos[0] > x && mousePos[1] < y + h && mousePos[1] > y) {
			return true;
		}
		return false;
	}
}