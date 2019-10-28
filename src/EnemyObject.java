import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

public class EnemyObject extends GameObject {

    static Random ran = new Random();
    protected boolean boss;
    protected boolean shoot;
    protected boolean secondMotion = false;
    protected int y0;
    protected int x0;
    protected int h0;
    protected double angle = Math.PI;
    protected double unchangedAngle;
    protected int forwardX = 1;
    protected int forwardY = 1;
    protected static int width;
    protected static int height;
    protected double multiplier;
    protected int skill;
    protected int health;
    public static Rectangle2D.Double plane = null;

    public EnemyObject(int x, int y, double xSpeed, double ySpeed, double multiplier, int skill, boolean boss, int health) {
        super(x, y);
        y0 = y;
        x0 = x;
        h0 = health;
        this.multiplier = multiplier;
        this.skill = skill;
        this.boss = boss;
        this.health = health;
    }
    
    //returns enemy health
    public int getHealth() {
        return health;
    }
    
    //removes health from enemy
    public void healthAttack(int i) {
        if ((health - 1) % 10 <= 0) {
            secondMotion = false;
        }
        health = health - i;
    }
    
    //moves the enemy
    public void update(int chooser) {
        shoot = true;
        if (chooser == 1) {
            if (!secondMotion) {
                y += 5;
                if (y - y0 == 500) {
                    secondMotion = true;
                }
            } else {
                if (health != h0) {
                    //if (angle == Math.PI) {
                        if (ControlPanel.object.getX() != x && ControlPanel.object.getY() != y) {
                            angle = Math.atan((double) (y - ControlPanel.object.getY()) / (double) (x - ControlPanel.object.getX()));
                            if (angle < 0) {
                                angle += Math.PI;
                            }
                        } else if (ControlPanel.object.getX() < x && ControlPanel.object.getY() == y) {
                            angle = 0;
                        } else if (ControlPanel.object.getX() > x && ControlPanel.object.getY() == y) {
                            angle = Math.PI;
                        } else if (ControlPanel.object.getY() == y) {
                            angle = 0.5 * Math.PI;
                        }
                    //} else {
                        x += 5 * Math.cos(angle);
                        y += 5 * Math.abs(Math.sin(angle));
                    //}
                }
            }
        } else if (chooser == 2) {
            if (!secondMotion) {
                y += 5;
                if (y - y0 > 700) {
                    secondMotion = true;
                }
            } else {
                multiplier += 0.05;
                y = (int) (575 + y0 + 125 * Math.sin(Math.PI / 2 + multiplier));
                if ((int) Math.abs(x0 + 125 * Math.cos(Math.PI / 2 + multiplier)) < 500) {
                    x = (int) Math.abs(x0 + 125 * Math.cos(Math.PI / 2 + multiplier));
                } else {
                    x = 1000 - (int) Math.abs(x0 + 125 * Math.cos(Math.PI / 2 + multiplier));
                }
            }
       } else if (chooser == 3) {
            if (!secondMotion) {
                y += 5;
                if (y - y0 > 700) {
                    secondMotion = true;
                }
            } else {
                if (x > 525) {
                    forwardX = -1;
                } else if (x < 10) {
                    forwardX = 1;
                }
                x += forwardX * 10;
            }
        } else {
           if (!secondMotion) {
               y += 5;
               if (y - y0 > 700) {
                   secondMotion = true;
               }
           } else {
               if (y < 10) {
                   forwardY = 1;
               } else if (y > 725) {
                   forwardY = -1;
               }
               y += forwardY * 8;
           }
       }
    }
    
    //draws enemy to jpanel 
    public void paintComponent(Graphics2D g2) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("enemy.png"));
            g2.drawImage(image, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
