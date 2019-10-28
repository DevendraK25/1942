import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FlightObject extends GameObject {

	public static int i = 0;
	public static Rectangle2D.Double plane = null;
	
	public FlightObject(int x, int y) {
		super(x, y);
	}
	
	//upgrades the players weapon
	public void upgrade() {
		i++;
	}
	
	//draws the object to the jpanel
	@Override
	public void paintComponent(Graphics2D g2) {
		 BufferedImage image;
			try {
				image = ImageIO.read(new File("plane.png"));
				g2.drawImage(image,x,y,null);
			} catch (IOException e) {
				e.printStackTrace();
			} try {
	            image = ImageIO.read(new File("plane.png"));
	            plane = new Rectangle2D.Double(x, y, image.getWidth(), image.getHeight());
	            TexturePaint texture;
	            texture = new TexturePaint(image, plane);
	            g2.setPaint(texture);
	            g2.fill(plane);  
	        } catch (Exception ex) {
	
	        }
	}

}
