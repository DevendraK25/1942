import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Winner extends JPanel {
	
	private JLabel winner = new JLabel("Winner Winner!");
	public Graphics2D g2;
	static BufferedImage image;
	
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
	//creates winner screen
	public Winner() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf")));
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}
		
		winner.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		winner.setForeground(Color.BLACK);
		
		this.setLayout(new FlowLayout(0, 100, 100));
		this.add(winner);
		ControlPanel.frame.remove(MainMenu.panel);
		ControlPanel.frame.setPreferredSize(new Dimension(600, 1000));
		ControlPanel.frame.setResizable(false);
		ControlPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ControlPanel.frame.setVisible(true);
		ControlPanel.frame.add(this);
		ControlPanel.frame.pack();
	}
}
