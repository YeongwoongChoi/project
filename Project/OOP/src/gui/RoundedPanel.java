package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
	protected int strokeSize = 1;
	protected Color shadowColor = Color.BLACK;
	protected boolean shadowed = true;
	protected boolean highQuality = true;
	protected Dimension arcs = new Dimension(30, 30);
	protected int shadowGap = 5;
	protected int shadowOffset = 4;
	protected int shadowAlpha = 150;

	protected Color backgroundColor = Color.LIGHT_GRAY;

	public RoundedPanel() {
		super();
		setOpaque(false);
	}

	@Override
	public void setBackground(Color c) {
		backgroundColor = c;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();
		int shadowGap = this.shadowGap;
		Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(),
				shadowAlpha);
		Graphics2D graphics = (Graphics2D) g;

		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		if (shadowed) {
			graphics.setColor(shadowColorA);
			graphics.fillRoundRect(shadowOffset, shadowOffset, width - strokeSize - shadowOffset,
					height - strokeSize - shadowOffset, arcs.width, arcs.height);
		} else {
			shadowGap = 1;
		}

		graphics.setColor(backgroundColor);
		graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
		graphics.setStroke(new BasicStroke(strokeSize));
		graphics.setColor(getForeground());
		graphics.drawRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
		graphics.setStroke(new BasicStroke());
	}
}
