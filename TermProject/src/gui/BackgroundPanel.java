package gui;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
	private Image backgroundImg;


    public BackgroundPanel(Image img) {
		this.backgroundImg = img;
		setLayout(null);
		
	}
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImg,0,0,null);
		setOpaque(false);
	}
}