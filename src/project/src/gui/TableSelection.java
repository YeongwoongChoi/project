package gui;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class TableSelection extends JPanel {
	private static final long serialVersionUID = 1L;
	static LectureTableController tableController;
	
	
	// static BottomPane bottom;
	public TableSelection() {
		super(new BorderLayout());
	}

	public void addComponentsToPane(int flag) {
		tableController = new LectureTableController();
		tableController.init(flag);
		JScrollPane center = new JScrollPane(tableController.table);
		add(center, BorderLayout.CENTER);

		// bottom = new BottomPane();
		// bottom.init(GUIManager.engine.getColumnCount());
		// add(bottom, BorderLayout.PAGE_END);

		setupTopPane();
	}
	
	public void addComponentsToPane() {
		tableController = new LectureTableController();
		tableController.init(0);
		JScrollPane center = new JScrollPane(tableController.table);
		add(center, BorderLayout.CENTER);

		// bottom = new BottomPane();
		// bottom.init(GUIManager.engine.getColumnCount());
		// add(bottom, BorderLayout.PAGE_END);

		setupTopPane();
	}

	void setupTopPane() {
		JPanel topPane = new JPanel();
		JPanel bottomPane = new JPanel();
		
		JTextField kwdTextField = new JTextField("", 20);
		topPane.add(kwdTextField, BorderLayout.LINE_START);

		JButton search = new JButton(new ImageIcon("./img/searchBtn.png"));
		GUIManager.setButtonProperties(search);
		
		JButton initializeTable = new JButton(new ImageIcon("./img/resetBtn.png"));
		GUIManager.setButtonProperties(initializeTable);

		topPane.add(search, BorderLayout.EAST);
		topPane.add(initializeTable, BorderLayout.LINE_END);

		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kwd = kwdTextField.getText();
				tableController.setRenderer();
				tableController.loadData(kwd);
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

		initializeTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					kwdTextField.setText("");
					String kwd = kwdTextField.getText();
					tableController.loadData(kwd);
			}
		});

		initializeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				initializeTable.setIcon(new ImageIcon("./img/resetBtn_down.png"));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				initializeTable.setIcon(new ImageIcon("./img/resetBtn.png"));
				
			}
			@Override
			public void mousePressed(MouseEvent e){
				initializeTable.setIcon(new ImageIcon("./img/resetBtn_down.png"));
				
			}			
		});

		add(topPane, BorderLayout.PAGE_START);
		add(bottomPane, BorderLayout.PAGE_END);
	}
}