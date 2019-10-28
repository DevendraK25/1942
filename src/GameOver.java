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

public class GameOver extends JPanel {
	
	JLabel gameover = new JLabel("Game Over!!");
	JButton button = new JButton();
	public Graphics2D g2;
	static BufferedImage image;
	
	//OCCURS WHEN PLAYER DIES
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
	//creates game over screen
	public GameOver() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf")));
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}
		button.setPreferredSize(new Dimension(200, 40));
		button.setText("Restart");
		button.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		button.addActionListener(new ButtonPresser());
		button.setActionCommand("Restart");
		button.setForeground(Color.RED);
		
		gameover.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		gameover.setForeground(Color.RED);
		
		this.setLayout(new FlowLayout(0, 180, 100));
		this.add(gameover);
		this.add(button);
		ControlPanel.frame.remove(MainMenu.panel);
		ControlPanel.frame.setPreferredSize(new Dimension(600, 1000));
		ControlPanel.frame.setResizable(false);
		ControlPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ControlPanel.frame.setVisible(true);
		ControlPanel.frame.add(this);
		ControlPanel.frame.pack();
	}
	
	//Button set to restart game
	private static class ButtonPresser implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String eventName = e.getActionCommand();
			if (eventName.equals("Restart")) {
				ControlPanel.frame.remove(MainMenu.gameover);
				MainMenu menu = new MainMenu();
				MainMenu.menu = menu;
				ControlPanel.frame.add(MainMenu.menu);
			}
		}
		
	}
	
}
