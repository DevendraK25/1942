import java.awt.*;

public abstract class GameObject {
	
	/*
	 * PARENT CLASS TO ALL OBJECTS
	 */
    protected int x;
    protected int y;
    protected double velX;
    protected double velY;
    protected Color color;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
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
    
    public void setVelX(double velX) {
        this.velX = velX;
    }
    
    public void setVelY(double velY) {
        this.velY = velY;
    }
    
    
    //restricts movements for player
    public void tick() {
        if (velX > 0 && x <= 515 || velX < 0 && x >= 15) {
            x+=velX;
        } if (velY > 0 && y <= 900 || velY < 0 && y >= 15) {
            y+=velY;
        }
    }

    public abstract void paintComponent(Graphics2D g2);

}
