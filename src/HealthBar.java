import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class HealthBar extends GameObject {
    
    protected int h0;
    protected int health;
    protected double healthRemaining = 50;
    
    public HealthBar(int x, int y, int health) {
        super(x, y);
        h0 = health;
        this.health = health;
    }
    
    //removes health from healthbar
    public void healthAttack(int i) {
        health -= i;
        healthRemaining = (int) (50 * ((double) health / (double) h0));
    }
    
    //draws healthbar to screen
    public void paintComponent(Graphics2D g2) {
        Rectangle2D healthTaken = new Rectangle2D.Double(x, y, 50, 5);
        g2.setColor(Color.RED);  
        g2.fill(healthTaken);
        Rectangle2D healthRemaining = new Rectangle2D.Double(x, y, this.healthRemaining, 5);
        g2.setColor(Color.GREEN);
        g2.fill(healthRemaining);
    }
    
}
