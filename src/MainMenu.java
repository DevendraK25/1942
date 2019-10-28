import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JPanel {
	
	public static boolean soundOn = true;
	private JButton startButton = new JButton();
	private JButton instructButton = new JButton();
	private JButton volume = new JButton();
	static MainMenu menu = null;
	static Winner winner;
	static GameOver gameover; 
	static ControlPanel panel;
	static HowToPlay howToPlay;
	private JLabel gameTitle = new JLabel();
	public Graphics2D g2;
	static BufferedImage image;
	
	//paints the background menu
	public void paintComponent(Graphics g) {
		requestFocus();
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		try {
			image = ImageIO.read(new File("menuBackground.jpg"));
		} catch (IOException e) {
			
		}
		g.drawImage(image, 0, 0, 600, 1000, null);
	}
	//creates the main menu
	public MainMenu() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf")));
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}
		if (howToPlay != null) {
			ControlPanel.frame.remove(howToPlay);
		}
		if (gameover != null) {
			ControlPanel.frame.remove(gameover);
		}
		volume.setText("Sound On");
		volume.setActionCommand("Sound On");
		volume.setPreferredSize(new Dimension(200, 60));
		volume.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		volume.addActionListener(new buttonPresser());
		gameTitle.setText("Jet Fighters");
		gameTitle.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		this.setLayout(new FlowLayout(0, 190, 50));
		instructButton.setText("How to play");
		instructButton.setPreferredSize(new Dimension(200, 60));
		instructButton.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		instructButton.setActionCommand("How to play");
		instructButton.addActionListener(new buttonPresser());
		startButton.setText("Start");
		startButton.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		startButton.setPreferredSize(new Dimension(200, 60));
		this.add(gameTitle);	
		this.add(startButton);
		startButton.setActionCommand("Start");
		startButton.addActionListener(new buttonPresser());
		this.add(instructButton);
		this.add(volume);
		ControlPanel.frame.setPreferredSize(new Dimension(600, 1000));
		ControlPanel.frame.setResizable(false);
		ControlPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ControlPanel.frame.setVisible(true);
		ControlPanel.frame.add(this);
		ControlPanel.frame.pack();
		
	}
	
	public static void main(String[] args) {
		menu = new MainMenu();
	}
	
	//sets commands for each button
	private class buttonPresser implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String eventName = e.getActionCommand();
			if (eventName.equals("Sound On")) {
				soundOn = false;
				volume.setText("Sound Off");
				volume.setActionCommand("Sound Off");
				repaint();
			}
			if (eventName.equals("Sound Off")) {
				soundOn = true;
				volume.setText("Sound On");
				volume.setActionCommand("Sound On");
				repaint();
			}
			if (eventName.equals("Start")) {
				ControlPanel.frame.remove(menu);
				panel = new ControlPanel();
			} 
			if (eventName.equals("How to play")) {
				howToPlay = new HowToPlay();
			}
		}
		
	}
	
}
