import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UpgradedBullet extends BulletObject {

	public UpgradedBullet(int x, int y) {
		super(x, y);
	}
	
	//moves new bullet
	public void move(Graphics2D g) {
    	setY(y - 20);
    	BufferedImage image;
		  try {
			image = ImageIO.read(new File("bullet4.png"));
			g.drawImage(image,x,y,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	//draws new bullet
	public void paintComponent(Graphics2D g) {
		if (remove()) {
			return;
		} else {
			BufferedImage image;
			  try {
				image = ImageIO.read(new File("bullet4.png"));
				g.drawImage(image,x,y,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
