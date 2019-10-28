import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;


public class ControlPanel extends JPanel implements Runnable {
	
	int positioner = -300;
	static int weapon = 1;
	
	static int animationFrame = 0;
	static boolean dropBomb = false;
	static int score = 0;
	static JLabel playerScore;
	private boolean bomb = false;
	
	static JLabel anim = new JLabel();
	static Timer bombTime;
    static Timer timer;
    protected static int delay = 2000;
    private static int skill = 0;
    private static int level = 1;
    private int targetFPS = 30;
    private double averageFPS;
    private boolean readyToShoot = true;
    private boolean run = false;
    private boolean isFired = false;
    static boolean isPower = false;
	static JLabel weaponLevel;
    private boolean isDead = false;
	static int enemyX = 0;
	static int enemyY = 0;
    private int backGroundY = -300;
    static int bombCounter = 3;
    static int health = 10;
    static JLabel label;
    static JLabel winner;
    static JLabel healthBar;
    static int checker = 0;
	static ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
	static ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    static ArrayList<BulletObject> bullets = new ArrayList<BulletObject>();
    static ArrayList<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>();
    static ArrayList<EnemyObject> enemies = new ArrayList<EnemyObject>();
    static ArrayList<HealthBar> enemyHealth = new ArrayList<HealthBar>();
    static Random ran = new Random();
    static FlightObject object = new FlightObject(365, 500);
    File file = new File("back.jpg");
    JLabel background = new JLabel();
    static JFrame frame = new JFrame();
    public Graphics2D g2;

    public ControlPanel() {
    	clean();
    	try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf")));
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension((int) screenSize.getHeight() - 100, (int) screenSize.getHeight() - 100));
		addKeyListener(new Controlplane());
		addMouseListener(new MouseEvent());
		addMouseMotionListener(new MouseMovement());
		setFocusable(true);
		weaponLevel = new JLabel();
		weaponLevel.setText("Weapon Level" + weapon);
		weaponLevel.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		weaponLevel.setForeground(Color.BLUE);
		healthBar = new JLabel();
		healthBar.setText("Health " + health);
		healthBar.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		healthBar.setForeground(Color.RED);
		label = new JLabel();
		playerScore = new JLabel();
		playerScore.setText("SCORE " + score);
		playerScore.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		playerScore.setForeground(Color.DARK_GRAY);
		label.setText("Bombs " + bombCounter);
		label.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		this.setLayout(new FlowLayout(0, 40, 0));
		this.add(label);
		this.add(healthBar);
		this.add(playerScore);
		this.add(weaponLevel);
		frame.setPreferredSize(new Dimension(600, 1000));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		startTimer();
		bombDisplayTimer();
        start();
    }
    
    //resets values for new game
    public static void clean() {
    	FlightObject.i = 0;
    	level = 1;
    	skill = 0;
    	delay = 2000;
    	health = 10;
    	bombCounter = 3;
    	score = 0;
    	weapon = 1;
    	weaponLevel = new JLabel();
		weaponLevel.setText("Weapon Level" + weapon);
		weaponLevel.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		weaponLevel.setForeground(Color.BLUE);
		healthBar = new JLabel();
		healthBar.setText("Health " + health);
		healthBar.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		healthBar.setForeground(Color.RED);
		label = new JLabel();
		playerScore = new JLabel();
		playerScore.setText("SCORE " + score);
		playerScore.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		playerScore.setForeground(Color.DARK_GRAY);
		label.setText("Bombs " + bombCounter);
    	powerups.removeAll(powerups);
    	enemies.removeAll(enemies);
    	bombs.removeAll(bombs);
    	bullets.removeAll(bullets);
    	enemyBullets.removeAll(enemyBullets);
    	enemyHealth.removeAll(enemyHealth);
    }
    
    //deletes items to reduce lag
    public void delete() {
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getY() <= 0) {
				bullets.remove(i);
			}
		}
		for (int i = 0; i < enemyBullets.size(); i++) {
			if (enemyBullets.get(i).getY() >= 1000) {
				enemyBullets.remove(i);
			}
		}
	}
    
    //removes bomb after 2000 milliseconds
    public void bombDisplayTimer() {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (bombTime.getDelay() == 2000) {
					bomb = false;
				}
			}
		};
		bombTime = new Timer(2000, action);
		bombTime.setInitialDelay(0);
		bombTime.start();
	}

    //animates the explosion
	public void bombAnimation() {
		if (bomb) {
			BufferedImage image;
			try {
				image = ImageIO.read(new File("bomb11.png"));
				g2.drawImage(image, 50, 250, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//moves the bomb after it appears
	public void bombMovement() {
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).move(g2);
		}
	}
	
	//collects the weapon upgrade
	public void powerUpCollection() {
		for (int i = 0; i < powerups.size(); i++) {
			if (object.getX() >= powerups.get(i).getX() - 35 && object.getX() <= powerups.get(i).getX() + 35
					&& object.getY() >= powerups.get(i).getY() - 35 && object.getY() <= powerups.get(i).getY() + 35) {
				weapon++;
				weaponLevel.setText("Weapon Level " + weapon);
				playSound("powerUpSound.wav");
				if (weapon >= 5) {
					weaponLevel.setText("Weapon Level MAX");
				}
				object.upgrade();
				powerups.remove(i);
			}
		}
	}
	
	//drops the bomb power up
	public void bombDrop() {
		if (dropBomb) {
			Bomb bomb = new Bomb(enemyX, enemyY);
			bomb.paintComponent(g2);
			bombs.add(bomb);
			dropBomb = false;
		}
	}
	
	//collects the bomb
	public void bombCollection() {
		for (int i = 0; i < bombs.size(); i++) {
			if (object.getX() >= bombs.get(i).getX() - 35 && object.getX() <= bombs.get(i).getX() + 35
					&& object.getY() >= bombs.get(i).getY() - 35 && object.getY() <= bombs.get(i).getY() + 35) {
				bombCounter++;
				label.setText("Bomb " + bombCounter);
				bombs.remove(i);
			}
		}
	}
	
	//moves powerup after it appears
	public void powerUpMovement() {
		for (int i = 0; i < powerups.size(); i++) {
			powerups.get(i).move(g2);
		}
	}

	public void powerUpDrop() {
		if (isPower && FlightObject.i != 4) {
			PowerUp power = new PowerUp(enemyX, enemyY);
			power.paintComponent(g2);
			powerups.add(power);
			isPower = false;
		}
	}
	
	//starts enemy shooter timer
    public void startTimer() {
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer.getDelay() == delay) {
                    enemyShooter(skill);
                }
            }

        };
        timer = new Timer(delay, action);
        timer.setInitialDelay(0);
        timer.start();
    }
    
    //Shoots players bullet depending on level
    public void shootBullet() {
		if (isFired && FlightObject.i == 0) {
			BulletObject bullet = new BulletObject(object.getX() + 24, object.getY() - 45);
			bullets.add(bullet);
			bullet.paintComponent(g2);
			isFired = false;
			readyToShoot = false;
		} else if (isFired && FlightObject.i == 1) {
			BulletObject bullet = new BulletObject(object.getX() + 16, object.getY() - 45);
			BulletObject bullet2 = new BulletObject(object.getX() + 32, object.getY() - 45);
			bullets.add(bullet);
			bullets.add(bullet2);
			bullet.paintComponent(g2);
			bullet2.paintComponent(g2);
			isFired = false;
			readyToShoot = false;
		} else if (isFired && FlightObject.i == 2) {
			BulletObject bullet = new BulletObject(object.getX() + 8, object.getY() - 45);
			BulletObject bullet2 = new BulletObject(object.getX() + 24, object.getY() - 45);
			BulletObject bullet3 = new BulletObject(object.getX() + 40, object.getY() - 45);
			bullets.add(bullet);
			bullets.add(bullet2);
			bullets.add(bullet3);
			bullet.paintComponent(g2);
			bullet2.paintComponent(g2);
			bullet3.paintComponent(g2);
			isFired = false;
			readyToShoot = false;
		} else if (isFired && FlightObject.i == 3) {
			UpgradedBullet bullet = new UpgradedBullet(object.getX() + 24, object.getY() - 45);
			bullets.add(bullet);
			bullet.paintComponent(g2);
			isFired = false;
			readyToShoot = false;
		} else if (isFired && FlightObject.i >= 4) {
			UpgradedBullet bullet = new UpgradedBullet(object.getX() + 16, object.getY() - 45);
			UpgradedBullet bullet2 = new UpgradedBullet(object.getX() + 32, object.getY() - 45);
			bullets.add(bullet);
			bullets.add(bullet2);
			bullet.paintComponent(g2);
			bullet2.paintComponent(g2);
			isFired = false;
			readyToShoot = false;
		}
	}
    
    //occurs when player and enemy collide
    public void playerCollision() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) instanceof Boss) {
                if (object.getX() <= enemies.get(i).getX() + 100 && object.getX() + 65 >= enemies.get(i).getX()
                        && object.getY() + 50 >= enemies.get(i).getY() && object.getY() <= enemies.get(i).getY() + 100) {
                    isDead = true;
                }  
            } else {
                if (object.getX() <= enemies.get(i).getX() + 65 && object.getX() + 65 >= enemies.get(i).getX()
                        && object.getY() + 50 >= enemies.get(i).getY() && object.getY() <= enemies.get(i).getY() + 50) {
                    isDead = true;
                }
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        run = true;
        thread.start();
    }

    public void stop() {
        run = false;
    }

    public void run() {
        long startTimeNano;
        long updateTime;
        long waitTime;
        long totalTime = 0;
        long targetTime = 1000 / targetFPS;

        int frameCount = 0;
        int maxFrameCount = 30;

        while (run) {
            startTimeNano = System.nanoTime();
            repaint();
            updateTime = (System.nanoTime() - startTimeNano) / 1000000;
            waitTime = targetTime - updateTime;
            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {

            }
            totalTime += System.nanoTime() - startTimeNano;
            frameCount++;
            if (frameCount == maxFrameCount) {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    //occurs after player is hit by bullet
    public void playerHit() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (enemyBullets.get(i).getX() + 16 >= object.getX() && enemyBullets.get(i).getX() <= object.getX() + 66
                    && enemyBullets.get(i).getY() + 16 >= object.getY() && enemyBullets.get(i).getY() <= object.getY() + 55) {
                health--;
                enemyBullets.remove(i);
                healthBar.setText("Health " + health);
            }
        }
    }
    
    public void deleteEnemy() {
    	for (int i = 0; i < enemies.size(); i++) {
    		if (enemies.get(i).getY() >= 1000) {
    			enemies.remove(i);
    		}
    	}
    }
    
    //Gauges enemy skill and determines a shoot path
    public void enemyShooter(int skill) {
        if (skill == 0) {
            for (int i = 0; i < enemies.size(); i++) {
                EnemyBullet bullet = new EnemyBullet(enemies.get(i).getX() + 25, enemies.get(i).getY() + 50, enemies.get(i).getX() + 25);
                enemyBullets.add(bullet);
                enemyBullets.get(i).paintComponent(g2);
            }
        } else {
            for (int i = 0; i < enemies.size() * 3; i += 3) {
                if (enemies.get(i / 3).shoot == true) {
                    EnemyBullet bullet = new EnemyBullet(enemies.get(i / 3).getX() + 32, enemies.get(i / 3).getY() + 105, enemies.get(i / 3).getX() + 40);
                    enemyBullets.add(bullet);
                    bullet = new EnemyBullet(enemies.get(i / 3).getX() + 40, enemies.get(i / 3).getY() + 105, enemies.get(i / 3).getX() + 40);
                    enemyBullets.add(bullet);
                    bullet = new EnemyBullet(enemies.get(i / 3).getX() + 48, enemies.get(i / 3).getY() + 105, enemies.get(i / 3).getX() + 40);
                    enemyBullets.add(bullet);
                    enemyBullets.get(i).paintComponent(g2);
                    enemyBullets.get(i + 1).paintComponent(g2);
                    enemyBullets.get(i + 2).paintComponent(g2);
                }
            }
        }
    }
    
    //moves enemy bullets
    public void enemyBulletMover() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).move(g2, i, skill);
        }
    }
    
    //deletes bullets after leaving the screen
    public void deleteBullet() {
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (enemyBullets.get(i).getY() >= 1000 || enemyBullets.get(i).getY() <= -250) {
                enemyBullets.remove(i);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getY() <= -25) {
                bullets.remove(i);
            }
        }
    }

    public void isHit() {
        for (int i = 0; i < bullets.size(); i++) {
            for (int i2 = 0; i2 < enemies.size(); i2++) {
                if (enemies.get(i2) instanceof Boss) {
                	//If player hits boss
                	if (bullets.get(i).getX() + 16 >= enemies.get(i2).getX() && bullets.get(i).getX() <= enemies.get(i2).getX() + 100
                            && bullets.get(i).getY() + 16 >= enemies.get(i2).getY() && bullets.get(i).getY() <= enemies.get(i2).getY() + 100) {
                		if (FlightObject.i <= 2) {
                			enemies.get(i2).healthAttack(1);
                        	enemyHealth.get(i2).healthAttack(1);
                		} else {
                			enemies.get(i2).healthAttack(3);
                        	enemyHealth.get(i2).healthAttack(3);
                		}
                        if (enemies.get(i2).getHealth() <= 0) {
                        	playSound("enemyDeath.wav");
                        	score += 1000;
                        	playerScore.setText("SCORE " + score);
                        	enemyX = enemies.get(i2).getX();
    						enemyY = enemies.get(i2).getY();
                            enemyHealth.remove(i2);
                            enemies.remove(i2);
                            checker = 5;
                        }
                        bullets.remove(i);
                    }
                } else {
                	//If player hits enemies
                	if (bullets.get(i).getX() + 18 >= enemies.get(i2).getX() && bullets.get(i).getX() <= enemies.get(i2).getX() + 65
                            && bullets.get(i).getY() + 16 >= enemies.get(i2).getY() && bullets.get(i).getY() <= enemies.get(i2).getY() + 50) {
                		if (FlightObject.i <= 2) {
                			enemies.get(i2).healthAttack(1);
                        	enemyHealth.get(i2).healthAttack(1);
                		} else {
                			enemies.get(i2).healthAttack(3);
                        	enemyHealth.get(i2).healthAttack(3);
                		}
                        if (enemies.get(i2).getHealth() <= 0) {
                        	playSound("enemyDeath.wav");
                        	score += 100;
                        	playerScore.setText("SCORE " + score);
                        	enemyX = enemies.get(i2).getX();
    						enemyY = enemies.get(i2).getY();
                        	checker++;
                            enemyHealth.remove(i2);
                            enemies.remove(i2);
                        }
                        bullets.remove(i);
                    }
                }
                //Chooses power up randomly
                int powerChooser = ran.nextInt(2);
				if (checker == 10 && powerChooser == 1 && FlightObject.i != 4) {
					isPower = true;
					checker = 0;
				} else if (checker == 10 && powerChooser == 0 || checker == 10 && FlightObject.i == 4) {
					dropBomb = true;
					checker = 0;
				}
            }
        }
    }
    
    //Does bomb damage to enemies
    public void bomb() {
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.remove(i);
			i = -1;
		}
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) instanceof Boss) {
				enemies.get(i).healthAttack(20);
				enemyHealth.get(i).healthAttack(20);
			} else {
				enemies.get(i).healthAttack(5);
				enemyHealth.get(i).healthAttack(5);
			}
			if (enemies.get(i).getHealth() <= 0) {
				enemies.remove(i);
				score += 50;
				playerScore.setText("SCORE " + score);
				enemyHealth.remove(i);
				i = -1;
			}
			if (enemies.isEmpty()) {
				level++;
			}
		}
	}
    
    //Occurs when player wins
    public void winner() {
    	if (level == 26) {
    		timer.stop();
    		MainMenu.winner = new Winner();
    		stop();
    	}
    }
    
    //Main loop method runs 30 times a second
    public void paintComponent(Graphics g) {
        requestFocus();
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        BufferedImage bg = null;
        try {
        	bg = ImageIO.read(file);
        } catch (IOException e) {

        }
        g.drawImage(bg, 0, backGroundY, null);
        BufferedImage island = null;
		try {
			island = ImageIO.read(new File("island3.png"));	
		} catch (IOException e) {

		}
		g.drawImage(island, 2, positioner, null);
		positioner += 2;
		if (positioner == 1250) {
			positioner = -300;
		}
        try {
            try {
                if (health > 0 && !isDead) {
                    object.paintComponent(g2);
                } else {
                	timer.stop();
                	playSound("enemyDeath.wav");
                    MainMenu.gameover = new GameOver();
                    stop();
                }
                winner();
                deleteEnemy();
                delete();
                bombAnimation();
				playerHit();
				powerUpDrop();
				bombDrop();
				bombMovement();
				bombCollection();
				powerUpMovement();
				powerUpCollection();
				enemySpawner(level);
				enemyBulletMover();
				isHit();
				shootBullet();
				playerCollision();
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).move(g2);
                }
                if (enemies.size() == 0) {
                    level++;
                }
            } catch (Exception e) {

            }
        } catch (Exception ex) {

        }
        tick();
    }

    public void tick() {
        object.tick();
    }

    //THIS IS WHERE THE LEVELS WILL BE CREATED
    public void enemySpawner(int level) {
    	//Levels creation level 1, 6, 11, 16, 21
        if (level % 5 == 1 && level != 26) {
            skill = 0;
            delay = 2000 - (level / 5) * 200;
            timer.setDelay(delay);
            if (enemies.isEmpty()) {
                for (int i = 0; i < 7; i++) {
                    EnemyObject enemy = new EnemyObject(ran.nextInt(100) + i * 70, -150 + (Math.abs(i - 3) - 4) * 60, 0, 0, 0, 0, false, 3 + (level / 5));
                    HealthBar bar = new HealthBar(0, 0, 3 + (level / 5));
                    enemies.add(enemy);
                    enemyHealth.add(bar);
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).paintComponent(g2);
                enemyHealth.get(i).paintComponent(g2);
                enemies.get(i).update(1);
                enemyHealth.get(i).setX(enemies.get(i).getX() + 7);
                enemyHealth.get(i).setY(enemies.get(i).getY() - 10);
            }
            //Levels creation level 2, 7, 12, 17, 22
        } else if (level % 5 == 2) {
            if (enemies.isEmpty()) {
                for (int i = 0; i < 7; i++) {
                    EnemyObject enemy = new EnemyObject(ran.nextInt(100) + (i % 4) * (125 + 25 * ((i + 1) / 4)), -150 - 200 * ((i + 1) / 4), 0, 0, 0, 0, false, 3 + (level / 5));
                    HealthBar bar = new HealthBar(0, 0, 3 + (level / 5));
                    enemies.add(enemy);
                    enemyHealth.add(bar);
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).paintComponent(g2);
                enemyHealth.get(i).paintComponent(g2);
                enemies.get(i).update(2);
                enemyHealth.get(i).setX(enemies.get(i).getX() + 7);
                enemyHealth.get(i).setY(enemies.get(i).getY() - 10);
            }
            //Levels creation level 3, 8, 13, 18, 23
        } else if (level % 5 == 3) {
            if (enemies.isEmpty()) {
                for (int i = 0; i < 7; i++) {
                    EnemyObject enemy = new EnemyObject(ran.nextInt(70) + i * 70, -150 + (i - 7) * 75, 0, 0, 0, 0, false, 3 + (level / 5));
                    HealthBar bar = new HealthBar(0, 0, 3 + (level / 5));
                    enemies.add(enemy);
                    enemyHealth.add(bar);
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).paintComponent(g2);
                enemyHealth.get(i).paintComponent(g2);
                enemies.get(i).update(3);
                enemyHealth.get(i).setX(enemies.get(i).getX() + 7);
                enemyHealth.get(i).setY(enemies.get(i).getY() - 10);
            } 
            //Levels creation level 4, 9, 14, 19, 24
        } else if (level % 5 == 4) {
            if (enemies.isEmpty()) {
                for (int i = 0; i < 7; i++) {
                    EnemyObject enemy = new EnemyObject(ran.nextInt(70) + i * 70, -150 + (i - 7) * 75, 0, 0, 0, 0, false, 3 + (level / 5));
                    HealthBar bar = new HealthBar(0, 0, 3 + (level / 5));
                    enemies.add(enemy);
                    enemyHealth.add(bar);
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).paintComponent(g2);
                enemyHealth.get(i).paintComponent(g2);
                enemies.get(i).update(4);
                enemyHealth.get(i).setX(enemies.get(i).getX() + 7);
                enemyHealth.get(i).setY(enemies.get(i).getY() - 10);
            } 
            //Levels creation level 5, 10, 15, 20, 25
        } else if (level % 5 == 0) {
            if (enemies.isEmpty()) {
                Boss boss1 = new Boss(ran.nextInt(400) + 50, -150, 0, 0, 0, 0, true, 50 * (level / 5));
                HealthBar bar = new HealthBar(0, 0, 50 * (level / 5));
                enemies.add(boss1);
                enemyHealth.add(bar);
                skill = 1;
            }
            enemies.get(0).paintComponent(g2);
            enemyHealth.get(0).paintComponent(g2);
            enemies.get(0).update(4);
            enemyHealth.get(0).setX(enemies.get(0).getX() + 25);
            enemyHealth.get(0).setY(enemies.get(0).getY() - 10);
            delay = 1000;
            timer.setDelay(delay);
        }
    }
    
    //Mouse listener
	private class MouseEvent implements MouseListener {

		public void mouseClicked(java.awt.event.MouseEvent e) {

		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {

		}
		
		//Sets the left and right click
		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
				if (readyToShoot) {
					playSound("bulletFire.wav");
					isFired = true;
				}
			} else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				if (bombCounter > 0) {
					playSound("bombSound.wav");
					bombCounter--;
					label.setText("Bombs " + bombCounter);
					bomb();
					bomb = true;
				}
			}
		}
		
		//Resets shooter
		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
				readyToShoot = true;
			}
		}

	}

    public class Controlplane implements KeyListener {
    	//Keylistener when keys are pressed
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case (KeyEvent.VK_DOWN):
                    object.setVelY(10);
                    break;
                case KeyEvent.VK_RIGHT:
                    object.setVelX(10);
                    break;
                case KeyEvent.VK_LEFT:
                    object.setVelX(-10);
                    break;
                case KeyEvent.VK_UP:
                    object.setVelY(-10);
                    break;
                case KeyEvent.VK_B:
    				if (bombCounter > 0) {
    					playSound("bombSound.wav");
    					bombCounter--;
    					label.setText("Bombs " + bombCounter);
    					bomb();
    					bomb = true;
    				}	
                    break;
                case KeyEvent.VK_SPACE:
                    if (readyToShoot) {
                    	playSound("bulletFire.wav");
                        isFired = true;
                    }
                    break;
            }
        }
        
        //Occurs after the key is released
        public void keyReleased(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    object.setVelY(0);
                    break;
                case KeyEvent.VK_DOWN:
                    object.setVelY(0);
                    break;
                case KeyEvent.VK_LEFT:
                    object.setVelX(0);
                    break;
                case KeyEvent.VK_RIGHT:
                    object.setVelX(0);
                    break;
                case KeyEvent.VK_SPACE:
                    readyToShoot = true;
                    break;
            }
        }

        public void keyTyped(KeyEvent event) {

        }
    }
    
    //Plays wav files
    public static void playSound(String file) {
		if (MainMenu.soundOn) {
			try {
				//Reads the path file and reads as an audio file
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file).getAbsoluteFile());
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (UnsupportedAudioFileException e) {
				//Catches error if file is not found or not compatible 
				System.out.println("Unsupport Audio File");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Can't read media file");
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				System.out.println("Audio file cannot be read");
				e.printStackTrace();
			}
		}
	}
    

    public class MouseMovement extends JPanel implements MouseMotionListener {

        public MouseMovement() {
            //Register for mouse events on blankArea and panel.
            background.addMouseMotionListener(this);
            addMouseMotionListener(this);
        }

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			object.setX(e.getX() - 32);
            object.setY(e.getY() - 25);

		}
    }
}