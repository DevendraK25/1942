import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Boss extends EnemyObject {

    public Boss(int x, int y, double xSpeed, double ySpeed, double multiplier, int direction, boolean boss, int health) {
        super(x, y, 0, 0, multiplier, direction, boss, health);
    }
    
    //Moves the boss
    public void update(int chooser) {
        if (getHealth() > 40) {
            shoot = true;
            multiplier += 0.025;
            if (y < 200) {
                y++;
            }
            x = (int) (150 * Math.sin(multiplier) + 250);

        } else if (getHealth() > 30) {
            if (!secondMotion) {
                if (y < 629) {
                    y += 5;
                }
                if (x < 246) {
                    x += 5;
                } else if (x > 254) {
                    x -= 5;
                }
                if ((y >= 621 && y <= 629) && (x >= 246 && x <= 254)) {
                    multiplier = 0;
                    secondMotion = true;
                }
            } else {
                multiplier += 0.01 + 0.01 * (20 - 0.5 * getHealth());
                y = (int) (350 + 275 * Math.sin(Math.PI / 2 + multiplier));
                x = (int) (250 + 225 * Math.cos(Math.PI / 2 + multiplier));
            }
        } else if (getHealth() > 20) {
            if (!secondMotion) {
                if (y < 296) {
                    y += 5;
                } else if (y > 304) {
                    y -= 5;
                }
                if (x < 246) {
                    x += 5;
                } else if (x > 254) {
                    x -= 5;
                }
                if ((y >= 296 && y <= 304) && (x >= 246 && x <= 254)) {
                    secondMotion = true;
                }
            } else {
                multiplier += 0.015 + 0.01 * (30 - getHealth());
                if (y <= 100) {
                    forwardY = 1;
                } else if (y >= 700) {
                    forwardY = -1;
                }
                x = (int) (225 * Math.sin(multiplier) + 250);
                y += forwardY * (int) (5 + Math.random() * 3 + 1);
            }
        } else if (getHealth() > 10) {
            if (!secondMotion) {
                if (y > 15) {
                    y -= 13;
                }
                if (x < 100) {
                    x += 3;
                } else if (x > 435) {
                    x -= 3;
                }
                if (y <= 15) {
                    if (x >= 525) {
                        forwardX = -1;
                    } else if (x <= 10) {
                        forwardX = 1;
                    }
                    x += forwardX * 10;
                    multiplier += 0.2;
                    if (Math.round(multiplier) >= 8) {
                        secondMotion = true;
                        shoot = false;
                        forwardY = 0;
                    }
                }
            } else {
                if (forwardY == 0) {
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
                    forwardY = 1;
                } else {
                    x += 15 * Math.cos(angle);
                    y += 15 * Math.abs(Math.sin(angle));
                }
                if (x <= 0 || x >= 500 || y >= 675) {
                    multiplier = 0;
                    shoot = true;
                    secondMotion = false;
                }
            }
        } else {
            if (!secondMotion) {
                if (y > 15) {
                    y -= 13;
                }
                if (x < 100) {
                    x += 3;
                } else if (x > 430) {
                    x -= 3;
                }
                if (y <= 15) {
                    if (x >= 425) {
                        forwardX = -1;
                    } else if (x <= 10) {
                        forwardX = 1;
                    }
                    x += forwardX * 10;
                    multiplier += 0.2;
                    if (Math.round(multiplier) >= 10) {
                        secondMotion = true;
                        shoot = false;
                        forwardY = 0;
                    }
                }
            } else {
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
                x += 15 * Math.cos(angle);
                y += 15 * Math.abs(Math.sin(angle));
                if (x <= 0 || x >= 500 || y >= 675) {
                    multiplier = 0;
                    shoot = true;
                    secondMotion = false;
                }
            }

        }
    }
    
    //draws the boss
    public void paintComponent(Graphics2D g2) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("boss.png"));
            g2.drawImage(image, x, y, null);
        } catch (IOException e) {

        }
    }
}
