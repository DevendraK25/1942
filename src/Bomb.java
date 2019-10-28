import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomb extends PowerUp {

	public Bomb(int x, int y) {
		super(x, y);
	}
	
	//Moves the bomb
	public void move(Graphics2D g2) {
		y += 4;
		BufferedImage image;
		  try {
			image = ImageIO.read(new File("bomb.png"));
			g2.drawImage(image,x,y,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
