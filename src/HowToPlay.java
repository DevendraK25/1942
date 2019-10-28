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

public class HowToPlay extends JPanel {

	private JButton back = new JButton();
	
	//Creates the how to play screen
	public HowToPlay() {
		JLabel instructions = new JLabel();
		JLabel instructions1 = new JLabel();
		JLabel instructions2 = new JLabel();
		JLabel instructions3 = new JLabel();
		JLabel instructions4 = new JLabel();
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ARCADECLASSIC.ttf")));
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}
		this.setLayout(new FlowLayout(100, 40, 50));
		instructions.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		instructions.setText("Use arrows to move and spacebar to shoot");
		instructions1.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		instructions1.setText("Use mouse to move and left click to shoot");
		instructions2.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		instructions3.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		instructions3.setText("Use right click to detonate bomb");
		instructions3.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		instructions4.setText("Collect powerups and bomb to get stronger");
		instructions4.setFont(new Font("ARCADECLASSIC", Font.BOLD, 25));
		back.setText("back");
		back.setFont(new Font("ARCADECLASSIC", Font.BOLD, 30));
		back.setPreferredSize(new Dimension(200,40));
		back.addActionListener(new buttonPresser());
		back.setActionCommand("back");
		this.add(back);
		this.add(instructions);
		this.add(instructions1);
		this.add(instructions2);
		this.add(instructions3);
		this.add(instructions4);
		ControlPanel.frame.remove(MainMenu.menu);
		ControlPanel.frame.setPreferredSize(new Dimension(600, 1000));
		ControlPanel.frame.setResizable(false);
		ControlPanel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ControlPanel.frame.setVisible(true);
		ControlPanel.frame.add(this);
		ControlPanel.frame.pack();
	}
	
	//sets the back button
	private static class buttonPresser implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String eventName = e.getActionCommand();
			if (eventName.equals("back")) {
				MainMenu.menu.removeAll();
				MainMenu.menu = null;
				MainMenu menu = new MainMenu();
				MainMenu.menu = menu;
			}
		}

	}
}
