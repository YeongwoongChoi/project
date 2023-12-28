package gui;
import java.awt.*;
import javax.swing.*;

import driver.Main;
import entity.Customer;
import view.CustomerView;

public class MyPage extends JPanel {
    final String OPEN_PARAGRAPH = "<p>";
    final String CLOSE_PARAGRAPH = "</p>";
    final String OPEN_CONTENTS = "<html>";
    final String CLOSE_CONTENTS = "</html>";
    final String BREAK_LINE = "<br>";

	MyPanel backPane = new MyPanel();
	JPanel topPane = new JPanel();
	JPanel centerPane = new JPanel();
	private JPanel centerRightPane = new JPanel();
	JPanel centerTopPane = new JPanel();
	JPanel centerBottomPane = new JPanel();
	JPanel bottomPane = new JPanel();
	JPanel bottomUpperPane = new JPanel();
	JPanel bottomLowerPane = new JPanel();
	Image img = new ImageIcon("img/teacherPageBack.png").getImage();
	Customer c = Login.c;

	class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, 950, 500, this);
		}
	}

	public MyPage() {
		super(new BorderLayout());
		setPane();
	}

	String addInfoToPane(StringBuilder sb, Customer c) {
        sb.append(OPEN_CONTENTS).append(OPEN_PARAGRAPH).append("Name: ").append(c.name).append(CLOSE_PARAGRAPH);
        sb.append(OPEN_PARAGRAPH).append("ID: ").append(c.getIdentifier()).append(CLOSE_PARAGRAPH);
        sb.append(OPEN_PARAGRAPH).append("Sex: ").append(c.sex).append(CLOSE_PARAGRAPH);
        sb.append(OPEN_PARAGRAPH).append("Age: ").append(c.age).append(CLOSE_PARAGRAPH);
        sb.append(OPEN_PARAGRAPH).append("Earned Point: ").append(c.earnedPoint).append(CLOSE_PARAGRAPH);
		sb.append(OPEN_PARAGRAPH).append("Phone number: ");
        var phoneNumbers = Main.customerMgr.idToContacts.get(c.getIdentifier());

        for (String phoneNumber: phoneNumbers)
            sb.append(phoneNumber).append(BREAK_LINE);

        sb.append(CLOSE_PARAGRAPH).append(CLOSE_CONTENTS);
		return sb.toString();
	}

	void setPane() {
		backPane.setLayout(new BorderLayout());
		backPane.setOpaque(false);
		add(backPane);
		backPane.add(topPane, BorderLayout.NORTH);
		backPane.add(centerPane, BorderLayout.WEST);
		backPane.add(bottomPane, BorderLayout.SOUTH);

		topPane.setLayout(new BorderLayout());
		topPane.setOpaque(false);

		centerTopPane.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		centerTopPane.setSize(450, 200);
		centerTopPane.setPreferredSize(new Dimension(650, 200));
		centerTopPane.setOpaque(false);

	    centerPane.add(centerRightPane, BorderLayout.EAST);

		bottomPane.setLayout(new BorderLayout(2, 2));
		bottomUpperPane.setLayout(new BorderLayout());
		bottomLowerPane.setLayout(new BorderLayout());
		bottomPane.add(bottomUpperPane, BorderLayout.CENTER);
		bottomPane.add(bottomLowerPane, BorderLayout.SOUTH);
		bottomPane.setOpaque(false);
		bottomUpperPane.setOpaque(false);
		bottomLowerPane.setOpaque(false);

		JButton logOut = new JButton(new ImageIcon("img/exit_off.png"));
		centerPane.setLayout(new BorderLayout());
		centerPane.setOpaque(false);
		GUIManager.setButtonProperties(logOut);

		JButton changeInfo = new JButton(new ImageIcon("img/modify_off.png"));
		GUIManager.setButtonProperties(changeInfo);

		topPane.add(logOut, BorderLayout.WEST);
		topPane.add(changeInfo, BorderLayout.EAST);

        GUIManager.setMouseListener(logOut, "img/exit_on.png", "img/exit_off.png");
		logOut.addActionListener(e -> {
			int selected = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (selected == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(null, "Good bye, " + c.name + ". Have a good one!",
						"Success", JOptionPane.INFORMATION_MESSAGE);
				Login.c = null;
				((JFrame) getTopLevelAncestor()).dispose();
				GUIManager.run(CustomerView.engine);
			}
		});
        GUIManager.setMouseListener(changeInfo, "img/modify_on.png", "img/modify_off.png");
		changeInfo.addActionListener(e -> new ModifyInfo(c));

		topPane.setSize(950, 50);
		centerPane.setSize(950, 300);
		bottomPane.setSize(950, 500);
		centerBottomPane.setLayout(new GridLayout(2, 3));
		centerBottomPane.setSize(330, 100);
		centerPane.add(centerTopPane, BorderLayout.CENTER);
		centerPane.add(centerBottomPane, BorderLayout.SOUTH);
		centerPane.add(centerRightPane, BorderLayout.LINE_END);

		centerBottomPane.setLayout(new BorderLayout());
		centerBottomPane.setPreferredSize(new Dimension(100, 30));
		centerBottomPane.setOpaque(false);

		StringBuilder sb = new StringBuilder();
		String info = addInfoToPane(sb, c);

		centerTopPane.add(new JLabel(info));
		centerRightPane.setSize(350, 300);
	}
}