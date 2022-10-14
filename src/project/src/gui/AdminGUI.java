package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import facade.DataEngineInterface;
import mgr.Main;

public class AdminGUI {

	static DataEngineInterface engine;

	public static void run(DataEngineInterface en) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowAdminGUI();
			}
		});
	}

	private static void createAndShowAdminGUI() {
		// Create and set up the window.
		JFrame mainFrame = new JFrame("StoreProgram");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUIManager.frameLocation(mainFrame);
		Container contentPane = mainFrame.getContentPane();
		contentPane.setPreferredSize(new Dimension(300, 500));
		BorderLayout border = new BorderLayout();
		contentPane.setLayout(border);

		JButton gstBtn = new JButton("고객 리스트 보기");
		JButton modifyBtn = new JButton("강의 수정");
		JButton lecBtn = new JButton("강의 리스트 보기");
		JButton addBtn = new JButton("강의 추가");
		JButton logoutBtn = new JButton("로그아웃");

		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(50));
		gstBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(gstBtn);
		box.add(Box.createVerticalStrut(50));
		modifyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(modifyBtn);
		box.add(Box.createVerticalStrut(50));
		lecBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(lecBtn);
		box.add(Box.createVerticalStrut(50));
		addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(addBtn);
		box.add(Box.createVerticalStrut(50));
		logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(logoutBtn);
		contentPane.add(box, BorderLayout.CENTER);

		// Create and set up the content pane.

		// Display the window.
		mainFrame.pack();
		mainFrame.setVisible(true);

		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();

				if (command.equals("로그아웃")) {
					Main main = new Main();
					main.run();
					mainFrame.dispose();
				}
			}
		});
	}
}