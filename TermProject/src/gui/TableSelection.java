package gui;

import java.awt.BorderLayout;
import javax.swing.*;

public class TableSelection extends JPanel {
	private static final long serialVersionUID = 1L;
	static TableController tableController;
	
	
	// static BottomPane bottom;
	public TableSelection() {
		super(new BorderLayout());
	}

	public void addComponentsToPane() {
		tableController = new TableController();
		tableController.init(0);
		JScrollPane center = new JScrollPane(tableController.table);
		add(center, BorderLayout.CENTER);
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

		search.addActionListener(e -> {
			String kwd = kwdTextField.getText();
			tableController.setRenderer();
			tableController.loadData(kwd);
		});

		GUIManager.setMouseListener(search, "img/searchBtn_down.png", "img/searchBtn.png");

		initializeTable.addActionListener(e -> {
				kwdTextField.setText("");
				String kwd = kwdTextField.getText();
				tableController.loadData(kwd);
		});

		GUIManager.setMouseListener(initializeTable, "img/resetBtn_down.png", "img/resetBtn.png");
		add(topPane, BorderLayout.PAGE_START);
		add(bottomPane, BorderLayout.PAGE_END);
	}
}