package game;

import javax.swing.JFrame;

public class Obstacle extends Enemycraft{
	private static final long serialVersionUID = -5575320614774490290L;
	int damage = 10;
	
	public Obstacle(JFrame game, int x, int y, String img_name, int hp_amount){
		super(game, x, y, img_name, hp_amount);
	}
            
	void move() {
		if(this.hp > 0) {
			this.y += 2;
		}
    }
}