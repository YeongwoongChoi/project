package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import facade.DataEngineInterface;

interface Frame {
	void addComponents(JPanel panel);
}
public class GUIManager {
	
	public static void customCursor(JFrame f) {
		Toolkit tool = Toolkit.getDefaultToolkit();
		Image cursorImage = tool.getImage("./img/cursor.png");
		Point point = new Point(0, 0);
		tool.getBestCursorSize(32, 32);
		Cursor cursor = tool.createCustomCursor(cursorImage, point, "foot");
		f.setCursor(cursor);

		/*tool = Toolkit.getDefaultToolkit();
		cursorImage = tool.getImage("./img/cursor.png");
		
		cursor = tool.createCustomCursor(cursorImage, new Point(10, 10), "Eraser");
		f.setCursor(cursor);*/
	}
	
	public static void frameLocation(JFrame frame) {		//User의 화면비율에 따른 창 출력
		Dimension frameSize = frame.getSize();
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setLocation((monitorSize.width - frameSize.width + 100)/3, (monitorSize.height - frameSize.height)/6);
	}
	
	public static void setButtonProperties(JButton b) {
		b.setBorderPainted(false);
		b.setFocusPainted(false);
		b.setContentAreaFilled(false);
	}
	
	public static void run(DataEngineInterface engine) {
		Login log = new Login();
		
		frameLocation(log);
		log.run(engine);
	}
}