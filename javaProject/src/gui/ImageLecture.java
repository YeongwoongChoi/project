package gui;

import java.awt.*;
import javax.swing.*;

import entity.Lecture;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mgr.Main;

public class ImageLecture extends JPanel {
	
	private JPanel mainPanel = new JPanel(new GridLayout(5, 4, 2, 2));
	
	private JPanel topPanel = new JPanel();
	private List <JLabel> lectureImages = new ArrayList <>();
	
	private HashMap <Integer, JLabel> targetImages = new HashMap<>();
	private HashMap <Integer, JLabel> removedImages = new HashMap<>();
	
	public ImageLecture() {
		super(new BorderLayout());
		setPane();
	}
	
	Image getImage(String lectureCode) {
		return new ImageIcon("lecture/" + lectureCode + ".jpg").getImage()
				.getScaledInstance(230, 200, Image.SCALE_SMOOTH);
	}
	
	void loadImages(JPanel panel) {
	
		class MyMouseListener extends MouseAdapter {
			
			String code;
			MyMouseListener(String code) {
				this.code = code;
			}
			public void mouseClicked(MouseEvent e) {
				new DetailLecture(code);
			}
		}
		
		for (int i=0; i<Main.lectureMgr.mList.size(); i++) {
			String code;
			JLabel label;
			
			int j = i + 1;
			
			if (j / 10 == 0)
				code = "000"+ j;
			else
				code = "00" + j;
			
			label = new JLabel(new ImageIcon(getImage(code)));
			label.setName(code);
			label.addMouseListener(new MyMouseListener(code));
			lectureImages.add(label);
			panel.add(label);
			
			j++;
		}
	}
	
	void addSearchMenu(JPanel panel) {
		
		List <Lecture> list = Main.lectureMgr.mList;
		
		panel.setLayout(new FlowLayout());
		
		JTextField kwdTextField = new JTextField(20);
		panel.add(kwdTextField, FlowLayout.LEFT);

		JButton search = new JButton(new ImageIcon("./img/searchBtn.png"));
		GUIManager.setButtonProperties(search);
		
		JButton initializeResult = new JButton(new ImageIcon("./img/resetBtn.png"));
		GUIManager.setButtonProperties(initializeResult);

		panel.add(search);
		panel.add(initializeResult);

		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kwd = kwdTextField.getText();
				
				for (Lecture lec: list) {
					int i = list.indexOf(lec);
					JLabel target = lectureImages.get(i);
					
					if (lec.matches(kwd)) {
						targetImages.put(i, target);
					}
					else {
						removedImages.put(i, target);
						mainPanel.remove(target);
					}
				}
			}
		});
		
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				search.setIcon(new ImageIcon("./img/searchBtn_down.png"));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				search.setIcon(new ImageIcon("./img/searchBtn.png"));
				
			}
			@Override
			public void mousePressed(MouseEvent e){
				search.setIcon(new ImageIcon("./img/searchBtn_down.png"));
				
			}			
		});

		initializeResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				kwdTextField.setText("");

				for (Map.Entry<Integer, JLabel> entry: targetImages.entrySet()) {
					mainPanel.add(entry.getValue());
				}
				
				for (Map.Entry<Integer, JLabel> entry : removedImages.entrySet()) {
					mainPanel.add(entry.getValue(), entry.getKey() + 0);
				}
				
				removedImages.clear();
				targetImages.clear();
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		initializeResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				initializeResult.setIcon(new ImageIcon("./img/resetBtn_down.png"));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				initializeResult.setIcon(new ImageIcon("./img/resetBtn.png"));
				
			}
			@Override
			public void mousePressed(MouseEvent e){
				initializeResult.setIcon(new ImageIcon("./img/resetBtn_down.png"));
				
			}			
		});
	}
	void setPane() {
		
		add(topPanel, BorderLayout.PAGE_START);

		add(mainPanel, BorderLayout.CENTER);
		loadImages(mainPanel);
		
		addSearchMenu(topPanel);
		
		JScrollPane scrollable = new JScrollPane(mainPanel);
		scrollable.setAutoscrolls(true);
		scrollable.setPreferredSize(new Dimension(920, 800));
		
		add(scrollable);
		
		setSize(950, 850);
		setVisible(true);
	}
}
