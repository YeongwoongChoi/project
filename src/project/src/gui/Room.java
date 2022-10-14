package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.*;

public class Room extends JPanel {

	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	Image [] roomImages = new Image[6];
	JLabel [] roomLabels = new JLabel[roomImages.length];
	
	Image getImage(String fileName) {
		return new ImageIcon("room/" + fileName + ".jpg").getImage()
				.getScaledInstance(420, 220, Image.SCALE_SMOOTH);
	}
	
	
	void addComponents() {
		
		topPanel.add(new JLabel("강의가 진행되는 강의실 사진입니다."), FlowLayout.LEFT);
		
		for (int i=0; i<roomImages.length; i++) {
			String lectureCode = "000" + (i + 1);
			
			roomImages[i] = getImage(lectureCode);
			roomLabels[i] = new JLabel(new ImageIcon(roomImages[i]));
			roomLabels[i].setText((i + 1) + "번 강의");
			roomLabels[i].setFont(new Font("나눔고딕", Font.PLAIN, 17));
			roomLabels[i].setHorizontalTextPosition(JLabel.CENTER);
			roomLabels[i].setVerticalTextPosition(JLabel.BOTTOM);
			roomLabels[i].setToolTipText("클릭하면 상세정보");
			
			centerPanel.add(roomLabels[i]);
			
			roomLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					new DetailLecture(lectureCode);
				}
			});
		}
	}
	
	public Room() {
		
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.PAGE_START);
		add(centerPanel, BorderLayout.CENTER);
		setSize(950, 850);
		
		topPanel.setSize(950, 100);
		centerPanel.setSize(950, 750);
		centerPanel.setLayout(new GridLayout(3, 2, 1, 1));
		
		addComponents();
		setVisible(true);
	}
}