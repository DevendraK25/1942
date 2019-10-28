import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class EnemyBullet extends GameObject {
	
	Graphics2D g2;
        private int x0;
	
	public EnemyBullet(int x, int y, int x0) {
            super(x, y);
            this.x0 = x0;
	}
    
	//removes bullet to reduce lag
    public boolean remove() {
    	if (y > 1000) {
    		return true;
    	}
    	return false;
    }
    
    //moves bullets
    public void move(Graphics2D g, int index, int skill) {
        setY(y + 10);
        if (x > x0) {
            setX(x + 1);
        } else if (x < x0) {
            setX(x - 1);
        }
    	BufferedImage image;
        try {
            image = ImageIO.read(new File("bullet3.png"));
            g.drawImage(image,x,y,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //draws bullet to JPanel
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
