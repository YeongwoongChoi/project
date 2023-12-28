package gui;

import driver.CustomerManager;
import driver.Main;
import entity.Customer;
import entity.Restaurant;
import facade.DataEngineInterface;
import view.CustomerView;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

class DetailedRestaurant extends JFrame implements Frame {
	
	Image img;
	JLabel lectureImg;

	final char LF = '\n';
	JButton makeReservation = new JButton(new ImageIcon("img/reserve_off.png"));
	JButton gotoPrevPage = new JButton(new ImageIcon("img/prevPage_off.png"));
	HashMap<Integer, Integer []> monthToDays = new HashMap<>();

	private JPanel topPanel = new JPanel();
	private JPanel topUpperPanel = new JPanel();
	private RoundPanel centerPanel = new RoundPanel();
	private RoundPanel centerLeftPanel = new RoundPanel();
	private RoundPanel centerRightPanel= new RoundPanel();
	private RoundPanel centerBottomPanel = new RoundPanel();
	private RoundPanel bottomPanel = new RoundPanel();
	private JPanel restaurantInfoPanel = new JPanel();
	private JPanel datePanel = new JPanel();
	private JPanel peoplePanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	@Override
	public void addComponents(JPanel panel) {
		panel.add(topPanel, BorderLayout.PAGE_START);
		panel.add(centerPanel, BorderLayout.NORTH);
		panel.add(centerBottomPanel, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.PAGE_END);
		
		topPanel.add(topUpperPanel, BorderLayout.NORTH);
		//topPanel.add(topBottomPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);
		centerBottomPanel.setLayout(new BorderLayout());
		
		if (img != null) {
			lectureImg = new JLabel(new ImageIcon(img));
			topUpperPanel.setBorder(BorderFactory.createEmptyBorder());
			topUpperPanel.add(lectureImg);
		}
		/*rightTopPanel.add(lectureName, BorderLayout.NORTH);
		rightTopPanel.add(teacher, BorderLayout.CENTER);*/
		centerBottomPanel.add(restaurantInfoPanel, BorderLayout.NORTH);
		centerBottomPanel.add(datePanel, BorderLayout.CENTER);
		centerBottomPanel.add(peoplePanel, BorderLayout.SOUTH);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(gotoPrevPage, FlowLayout.LEFT);
		buttonPanel.add(makeReservation);
	}
	
	Image getImage(String restaurantCode) {
			return new ImageIcon("restaurant/" + restaurantCode + ".jpg").getImage()
					.getScaledInstance(400, 230, Image.SCALE_SMOOTH);
	}
	
	void setTextAreaOptions(JTextArea t) {
		t.setLineWrap(true);
		t.setWrapStyleWord(true);
        t.setOpaque(false);
        t.setEditable(false);
	}
	
	void setRoundPanels() {
		centerPanel.setBackground(new Color(0xc2daf0));
		centerPanel.setBorder(BorderFactory.createEmptyBorder());
		
		centerLeftPanel.setBounds(0,0,500, 200);
		centerLeftPanel.setBorder(new TitledBorder(
				new TitledBorder(new LineBorder(Color.BLACK, 0), "- Restaurant Info -"), "", TitledBorder.RIGHT, TitledBorder.BOTTOM));
		
		centerRightPanel.setBounds(0,0,500, 200);
		centerRightPanel.setBorder(new TitledBorder(
				new TitledBorder(new LineBorder(Color.BLACK, 0), " - Restaurant Description -"), "", TitledBorder.RIGHT, TitledBorder.BOTTOM));
		
		centerLeftPanel.setBackground(Color.WHITE);
		centerRightPanel.setBackground(Color.WHITE);
		centerBottomPanel.setBounds(0, 0, 1000, 250);
	}

	Integer [] getDayArray(final int endDay) {
		Integer [] dayArray = new Integer[endDay];
		for (int i = 1; i <= endDay; i++)
			dayArray[i - 1] = i;
		return dayArray;
	}

	void initializeMap(final int year) {
		int [] endDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		monthToDays.put(1, getDayArray(endDays[0]));

		if (Year.isLeap(year))
			endDays[1] = 29;

		for (int month = 2; month <= 12; month++)
			monthToDays.put(month, getDayArray(endDays[month - 1]));
	}
	String getRestaurantInfo(Restaurant r) {
		StringBuilder sb = new StringBuilder();
		sb.append("Restaurant Code: ").append(r.restaurantCode).append(LF);
		sb.append("Restaurant Name: ").append(r.restaurantName).append(LF);
		sb.append("Dishes that it serves: ").append(r.typeOfDishes).append(LF);
		sb.append("Phone Number: ").append(r.phoneNumber).append(LF);
		sb.append("Location: ").append(r.location).append(LF).append(LF);
		return sb.toString();
	}

	String getFormattedDateTime(Object rawYear, Object rawMonth, Object rawDay, Object rawTime) {
		LocalDateTime dt = LocalDateTime.of(LocalDate.of((int)rawYear, (int)rawMonth, (int)rawDay), (LocalTime)rawTime);
		if (dt.isBefore(LocalDateTime.now()))
			return null;
		return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	void setTimeSelection(JComboBox <LocalTime> cmb) {
		LocalTime start = LocalTime.of(0, 0);
		LocalTime end = LocalTime.of(23, 50);
		cmb.addItem(start);
		do {
			start = start.plusMinutes(10);
			cmb.addItem(start);
		} while (!start.equals(end));
	}

	boolean checkExistenceOnList(Customer c, String [] reservationInfo) {
		for (String [] info: c.reservationList) {
			if (equals(info, reservationInfo))
				return true;
		}
		return false;
	}

	private boolean equals(final String[] one, final String[] another) {
		for (int i = 0; i < 3; i++) {
			if (!one[i].equals(another[i]))
				return false;
		}
		return true;
	}

	public DetailedRestaurant(final String restaurantCode) {
		Main.setGlobalFont(new Font("NanumGothic", Font.PLAIN, 22));
		setTitle("Restaurant Info");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUIManager.frameLocation(this);
		GUIManager.customCursor(this);
		
		RoundPanel rPanel = new RoundPanel();
		
		rPanel.setBounds(0, 0, getWidth(), getHeight());
		rPanel.setBackground(new Color(0xc2daf0));
		
		topPanel.setOpaque(false);
		setRoundPanels();
		
		Restaurant r = Main.restaurantMgr.find(new String[]{restaurantCode});

		img = getImage(restaurantCode);
		topPanel.setLayout(new BorderLayout());
		
		JTextArea restaurantInfoArea = new JTextArea(7, 20);
		setTextAreaOptions(restaurantInfoArea);
		
		restaurantInfoArea.setFont(new Font("NanumGothic", Font.PLAIN, 18));
		restaurantInfoArea.append(getRestaurantInfo(r));
		
		JTextArea descriptionArea = new JTextArea(7, 20);
		setTextAreaOptions(descriptionArea);
		
		descriptionArea.setFont(new Font("NanumGothic", Font.PLAIN, 20));
		centerLeftPanel.add(restaurantInfoArea);
		centerRightPanel.add(descriptionArea);

		restaurantInfoPanel.setLayout(new BorderLayout(3, 15));
		buttonPanel.setLayout(new FlowLayout());

		Image star = new ImageIcon("img/star.jpg").getImage().getScaledInstance
				(30, 30, Image.SCALE_SMOOTH);

		datePanel.add(new JLabel("Date and Time Selection: "), BorderLayout.LINE_START);

		final Integer [] year = { 2023, 2024, 2025 };
		JComboBox <Integer> yearSelection = new JComboBox<>(year);
		initializeMap((int)yearSelection.getSelectedItem());

		JComboBox <Integer> monthSelection = new JComboBox<>();
		for (Integer month: monthToDays.keySet())
			monthSelection.addItem(month);

		JComboBox <Integer> daySelection = new JComboBox<>(monthToDays.get(monthSelection.getSelectedItem()));
		JComboBox <LocalTime> timeSelection = new JComboBox<>();
		setTimeSelection(timeSelection);

		monthSelection.addActionListener(e -> {
			if (daySelection.getItemCount() > 0)
				daySelection.removeAllItems();
			for (Integer d: monthToDays.get(monthSelection.getSelectedItem()))
				daySelection.addItem(d);
		});

		datePanel.add(monthSelection);
		datePanel.add(daySelection);
		datePanel.add(yearSelection);
		datePanel.add(timeSelection);

		peoplePanel.add(new JLabel("Number of people"), BorderLayout.LINE_START);
		JTextField numberOfPeople = new JTextField(5);
		peoplePanel.add(numberOfPeople);

		restaurantInfoPanel.add(new JLabel(new ImageIcon(star)), BorderLayout.LINE_START);
		restaurantInfoPanel.add(new JLabel("Rating: " + r.rating + " / 5.0"));
		
		setLayout(new BorderLayout());
		
		Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		topPanel.setLayout(new FlowLayout());
		bottomPanel.setLayout(new BorderLayout());

		bottomPanel.setBorder(b);
	
		setContentPane(rPanel);
		addComponents(rPanel);
		topPanel.setPreferredSize(new Dimension(1000, 240));
		centerLeftPanel.setPreferredSize(new Dimension(500, 200));
		centerRightPanel.setPreferredSize(new Dimension(500, 200));

		gotoPrevPage.addActionListener(e -> dispose());

		// If you press the reservation button,
		makeReservation.addActionListener(e -> {
			TableController reservationTableController = CustomerView.tables[2].tableController;

			Customer c = Login.c;
			String dateTime = getFormattedDateTime(yearSelection.getSelectedItem(), monthSelection.getSelectedItem(), daySelection.getSelectedItem(), timeSelection.getSelectedItem());
			final String [] reservationInfo = { restaurantCode, r.restaurantName, dateTime, numberOfPeople.getText() };
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to book?",
					"Booking", JOptionPane.OK_CANCEL_OPTION);

			if (choice == JOptionPane.OK_OPTION) {
				if (checkExistenceOnList(c, reservationInfo)) {
					JOptionPane.showMessageDialog(null, "You already made a reservation for this restaurant at the same time", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else if (dateTime == null) {
					JOptionPane.showMessageDialog(null, "Reservation time should be after the current time", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "You succeeds in booking", "Complete",
							JOptionPane.INFORMATION_MESSAGE);
					c.reservationList.add(reservationInfo);
					reservationTableController.tableModel.addRow(reservationInfo);
					reservationTableController.dataMgr.addNewItem(c, reservationInfo);
				}
			}
		});

		GUIManager.setButtonProperties(makeReservation);
		GUIManager.setMouseListener(makeReservation, "img/reserve_on.png", "img/reserve_off.png");

		GUIManager.setButtonProperties(gotoPrevPage);
		GUIManager.setMouseListener(gotoPrevPage, "img/prevPage_on.png", "img/prevPage_off.png");

		setSize(1050, 850);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}