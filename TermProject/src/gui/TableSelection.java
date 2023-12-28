package gui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class TableSelection extends JPanel {
	public TableController tableController;
	private String tableName;

	public TableSelection(String tableName) {
		super(new BorderLayout());
		this.tableName = tableName;
	}

	public void addComponentsToPane() {
		tableController = new TableController(tableName);
		tableController.init();
		JScrollPane center = new JScrollPane(tableController.table);
		add(center, BorderLayout.CENTER);
		setupTopPane();
	}

	// When "Search" button clicked,
	void searchKeyword(JTextField f) {
		String keyword = f.getText().strip();
		if (keyword.isBlank())
			initializeTable(f);
		else
			tableController.loadAll(keyword, tableName);
	}

	// When "Reset" button clicked or keyword was empty,
	void initializeTable(JTextField f) {
		f.setText("");
		tableController.loadData(Login.c);
	}

	void setupTopPane() {
		JPanel topPane = new JPanel();
		JPanel bottomPane = new JPanel();
		
		JTextField kwdTextField = new JTextField("", 27);
		topPane.add(kwdTextField, BorderLayout.LINE_START);

		JButton search = new JButton(new ImageIcon("img/search_off.png"));
		GUIManager.setButtonProperties(search);
		
		JButton resetTable = new JButton(new ImageIcon("img/reset_off.png"));
		GUIManager.setButtonProperties(resetTable);

		topPane.add(search, BorderLayout.EAST);
		topPane.add(resetTable, BorderLayout.LINE_END);

		GUIManager.setMouseListener(search, "img/search_on.png", "img/search_off.png");
		kwdTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				kwdTextField.setToolTipText("Enter the preference for the dishes. e.g. American");
			}
		});

		kwdTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						searchKeyword(kwdTextField);
						break;
					case KeyEvent.VK_ESCAPE:
						initializeTable(kwdTextField);
						break;
				}
			}
		});
		search.addActionListener(e -> searchKeyword(kwdTextField));
		resetTable.addActionListener(e -> initializeTable(kwdTextField));
		GUIManager.setMouseListener(resetTable, "img/reset_on.png", "img/reset_off.png");
		add(topPane, BorderLayout.PAGE_START);
		add(bottomPane, BorderLayout.PAGE_END);
	}
}