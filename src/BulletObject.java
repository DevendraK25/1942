import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BulletObject extends GameObject {

    private int speed = 5;
    
	public BulletObject(int x, int y) {
		super(x,y);
    }

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
    
	public int getY() {
		return y;
	}
	
    public void setSpeed(int speed) {
    	this.speed = speed;
    }
    
    public int getSpeed() {
    	return speed;
    }
    
    public boolean remove() {
    	if (y < 0) {
    		return true;
    	}
    	return false;
    }
    
    public void move(Graphics2D g) {
    	setY(y - 20);
    	BufferedImage image;
		  try {
			image = ImageIO.read(new File("bullet3.png"));
			g.drawImage(image,x,y,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public void paintComponent(Graphics2D g) {
		if (remove()) {
			return;
		} else {
			BufferedImage image;
			  try {
				image = ImageIO.read(new File("bullet3.png"));
				g.drawImage(image,x,y,null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
