import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PowerUp extends GameObject {

	BufferedImage image;
	
	public PowerUp(int x, int y) {
		super(x, y);
	}
	
	//Moves the powerup after falling
	public void move(Graphics2D g2) {
		y += 4;
		BufferedImage image;
		  try {
			image = ImageIO.read(new File("circle.png"));
			g2.drawImage(image,x,y,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Draws the moving power up 
	public void paintComponent(Graphics2D g2) {
		try {
			image = ImageIO.read(new File("circle.png"));
			g2.drawImage(image, x, y, null);
		} catch (IOException e) {
			
		}
	}
}
