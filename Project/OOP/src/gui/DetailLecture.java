package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import entity.Lecture;
import entity.Review;
import mgr.Main;

class DetailLecture extends JFrame implements Frame {
	
	Image img;
	JLabel lectureImg;
	
	JButton submitLecture = new JButton(new ImageIcon("./img/requestBtn.png"));
	JButton intoBasket = new JButton(new ImageIcon("./img/basketBtn.png"));
	JButton gotoPrevPage = new JButton(new ImageIcon("./img/back2Btn.png"));
	JButton showTeacherInfo = new JButton(new ImageIcon("./img/teacherListBtn.png"));
	
	private JPanel topPanel = new JPanel();
	private JPanel topUpperPanel = new JPanel();
	private RoundedPanel centerPanel = new RoundedPanel();
	private RoundedPanel centerLeftPanel = new RoundedPanel();
	private RoundedPanel centerRightPanel= new RoundedPanel();
	private RoundedPanel bottomPanel = new RoundedPanel();
	private JPanel lectureInfoPanel = new JPanel();
	private RoundedPanel reviewPanel = new RoundedPanel();
	private JPanel buttonPanel = new JPanel();
	List <Lecture> list;
	
	@Override
	public void addComponents(JPanel panel) {
		panel.add(topPanel, BorderLayout.NORTH);
		
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		
		topPanel.add(topUpperPanel, BorderLayout.NORTH);
		//topPanel.add(topBottomPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);
		
		if (img != null) {
			lectureImg = new JLabel(new ImageIcon(img));
			topUpperPanel.setBorder(BorderFactory.createEmptyBorder());
			topUpperPanel.add(lectureImg);
		}
		/*rightTopPanel.add(lectureName, BorderLayout.NORTH);
		rightTopPanel.add(teacher, BorderLayout.CENTER);*/
		
		bottomPanel.add(lectureInfoPanel, BorderLayout.NORTH);
		bottomPanel.add(reviewPanel, BorderLayout.CENTER);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		buttonPanel.add(gotoPrevPage, FlowLayout.LEFT);
		buttonPanel.add(submitLecture);
		buttonPanel.add(intoBasket);
		buttonPanel.add(showTeacherInfo);
	}
	
	Image getImage(String lectureCode) {
			return new ImageIcon("lecture/" + lectureCode + ".jpg").getImage()
					.getScaledInstance(400, 230, Image.SCALE_SMOOTH);
	}
	
	void setTextAreaOptions(JTextArea t) {
		t.setLineWrap(true);
		t.setWrapStyleWord(true);
        t.setOpaque(false);
        t.setEditable(false);
	}
	
	boolean checkExistenceOnList(List <Lecture> list, String code) {
		for (Lecture lec: list) {	//로그인된 회원의 수강목록을 순회하면서,
			if (lec.matches(code)) {		//동일한 과목코드를 가진 강좌가 있으면 추가하지 않고 리턴
				JOptionPane.showMessageDialog(null, "동일한 과목이 이미 신청되어 있습니다!", "실패", 
				JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	void setRoundedPanels() {
		centerPanel.setBackground(new Color(0xc2daf0));
		centerPanel.setBorder(BorderFactory.createEmptyBorder());
		
		centerLeftPanel.setBounds(0,0,445, 200);
		centerLeftPanel.setBorder(new TitledBorder(
				new TitledBorder(new LineBorder(Color.BLACK, 0), "-강의 정보-"), "", TitledBorder.RIGHT, TitledBorder.BOTTOM));
		
		centerRightPanel.setBounds(0,0,445, 200);
		centerRightPanel.setBorder(new TitledBorder(
				new TitledBorder(new LineBorder(Color.BLACK, 0), " -강의 설명-"), "", TitledBorder.RIGHT, TitledBorder.BOTTOM));
		
		centerLeftPanel.setBackground(Color.WHITE);
		centerRightPanel.setBackground(Color.WHITE);
		reviewPanel.setBounds(0,0,900, 230);
		reviewPanel.setBackground(new Color(255,255,255));
		
	}
	
	public DetailLecture(String lectureCode) {
		
		Main.setGlobalFont(new Font("나눔고딕", Font.PLAIN, 22));
		setTitle("강의 상세정보");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GUIManager.frameLocation(this);
		GUIManager.customCursor(this);
		//JPanel lectureContainer = new JPanel();
		
		RoundedPanel rPanel = new RoundedPanel();
		
		rPanel.setBounds(0, 0, getWidth(), getHeight());
		rPanel.setBackground(new Color(0xc2daf0));
		
		topPanel.setOpaque(false);
		setRoundedPanels();
		
		Lecture lec = (Lecture) Main.lectureMgr.find(lectureCode);	//강의코드에 맞는 강의 찾고,
		img = getImage(lectureCode);
		
		topPanel.setLayout(new BorderLayout());
		
		/*lectureName.setText(lec.lectureName);
		teacher.setText(lec.teacher + " 강사");*/
		
		JTextArea lectureInfoArea = new JTextArea(7, 20);
		setTextAreaOptions(lectureInfoArea);
		
		lectureInfoArea.setFont(new Font("나눔고딕", Font.PLAIN, 18));
		lectureInfoArea.append("강의코드: " + lec.code + "\n");
		lectureInfoArea.append("강의명: " + lec.lectureName + "\n");
		lectureInfoArea.append("강사 이름: " + lec.teacher +  "\n");
		lectureInfoArea.append("가격: " + lec.price + "\n");
		lectureInfoArea.append("난이도: " + lec.level + "\n");
		lectureInfoArea.append("레슨 횟수(1회당 진행시간): " + lec.lectureCount + 
				"(" + lec.runningTime + "분)\n\n");
		
		JTextArea descriptionArea = new JTextArea(7, 20);
		setTextAreaOptions(descriptionArea);
		
		descriptionArea.setFont(new Font("나눔고딕", Font.PLAIN, 20));
		centerLeftPanel.add(lectureInfoArea);
		
		for (String s: lec.description) {
			descriptionArea.append(s);
			if (s.endsWith(".") || s.endsWith("?") || s.endsWith("!"))
				descriptionArea.append(" ");
		}
		
		descriptionArea.append("\n");
		centerRightPanel.add(descriptionArea);
		
		reviewPanel.setLayout(new BorderLayout());
		lectureInfoPanel.setLayout(new BorderLayout(3, 0));
		buttonPanel.setLayout(new FlowLayout());
		
		JTextArea reviewArea = new JTextArea(5, 15);
        setTextAreaOptions(reviewArea);

		reviewPanel.add(new JScrollPane(reviewArea), BorderLayout.CENTER);
		
		float sumRating = 0;
		int countReviews = lec.reviewList.size();
		
		for (Review r: lec.reviewList) {
			String [] review = r.getUiTexts();	
			sumRating += r.rating;
			
			for (int i=0; i<review.length; i++) {
				reviewArea.append(review[i] + " ");
			}
			reviewArea.append("\n");
			for (String s: r.getReviewTexts()) {
					reviewArea.append(s + " ");
			}
			reviewArea.append("\n\n");
			
		}
		
		Image star = new ImageIcon("star.jpg").getImage().getScaledInstance
				(30, 30, Image.SCALE_SMOOTH);
		
		lectureInfoPanel.add(new JLabel("리뷰: 총 " + countReviews + "개\n"), BorderLayout.PAGE_START);
		
		lectureInfoPanel.add(new JLabel(new ImageIcon(star)), BorderLayout.LINE_START);
		lectureInfoPanel.add(new JLabel(Math.round(sumRating / 
				(float)(countReviews)*100)/100.0 + " / 5.0"), BorderLayout.CENTER);
		reviewArea.setCaretPosition(0);
		
		setLayout(new BorderLayout());
		
		Border b = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		topPanel.setLayout(new FlowLayout());
		bottomPanel.setLayout(new BorderLayout());
		
		//centerPanel.setBorder(b);
		bottomPanel.setBorder(b);
	
		setContentPane(rPanel);
		addComponents(rPanel);
		topPanel.setPreferredSize(new Dimension(900, 240));
		centerLeftPanel.setPreferredSize(new Dimension(450, 200));
		centerRightPanel.setPreferredSize(new Dimension(450, 200));
		reviewPanel.setPreferredSize(new Dimension(900, 230));
		
		gotoPrevPage.addActionListener(new ActionListener() {		//이전버튼을 누르면, 테이블 화면으로 이동
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		submitLecture.addActionListener(new ActionListener() {	//수강신청 버튼을 누르면,
			@Override
			public void actionPerformed(ActionEvent e) {
				
				list = Login.g.lectureList;
				int choice = JOptionPane.showConfirmDialog(null, "수강 신청하시겠습니까?", 
						"수강 신청", JOptionPane.OK_CANCEL_OPTION);
				
				if (choice == JOptionPane.OK_OPTION &&
					checkExistenceOnList(list, lectureCode)) {
					
					JOptionPane.showMessageDialog(null, "신청되었습니다!", "완료", 
							JOptionPane.INFORMATION_MESSAGE);
					list.add(lec);		//수강중인 과목이 아니면, 수강목록에 추가
					MyPage.submittedTableModel.addRow(lec.getUiTexts());
				}
				else
					return;
			}
		});
		
		intoBasket.addActionListener(new ActionListener() {		//장바구니 추가버튼을 누르면,
			@Override
			public void actionPerformed(ActionEvent e) {
				list = Login.g.basketList;
				
				int choice = JOptionPane.showConfirmDialog(null,
						"해당 과목을 장바구니에 추가하시겠습니까?", "장바구니 추가", 
						JOptionPane.OK_CANCEL_OPTION);
				
				if (choice == JOptionPane.OK_OPTION &&
					checkExistenceOnList(list, lectureCode)) {
					
					if (checkExistenceOnList(Login.g.lectureList, lectureCode)) {
						JOptionPane.showMessageDialog(null, "과목이 장바구니에 추가되었습니다!", "완료",
								JOptionPane.INFORMATION_MESSAGE);
						list.add(lec);
						Basket.basketTableModel.addRow(lec.getUiTexts());
					}
					else
						return;
				}
				else
					return;
			}
		});
		
		submitLecture.setBorderPainted(false);
		submitLecture.setFocusPainted(false);
		submitLecture.setContentAreaFilled(false);

		submitLecture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				submitLecture.setIcon(new ImageIcon("./img/requestBtn_down.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				submitLecture.setIcon(new ImageIcon("./img/requestBtn.png"));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				submitLecture.setIcon(new ImageIcon("./img/requestBtn_down.png"));

			}
		});

		intoBasket.setBorderPainted(false);
		intoBasket.setFocusPainted(false);
		intoBasket.setContentAreaFilled(false);

		intoBasket.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				intoBasket.setIcon(new ImageIcon("./img/basketBtn_down.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				intoBasket.setIcon(new ImageIcon("./img/basketBtn.png"));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				intoBasket.setIcon(new ImageIcon("./img/basketBtn_down.png"));

			}
		});

		gotoPrevPage.setBorderPainted(false);
		gotoPrevPage.setFocusPainted(false);
		gotoPrevPage.setContentAreaFilled(false);

		gotoPrevPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				gotoPrevPage.setIcon(new ImageIcon("./img/back2Btn_down.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				gotoPrevPage.setIcon(new ImageIcon("./img/back2Btn.png"));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				gotoPrevPage.setIcon(new ImageIcon("./img/back2Btn_down.png"));

			}
		});
		
		showTeacherInfo.setBorderPainted(false);
		showTeacherInfo.setFocusPainted(false);
		showTeacherInfo.setContentAreaFilled(false);

		showTeacherInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				showTeacherInfo.setIcon(new ImageIcon("./img/teacherListBtn_down.png"));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				showTeacherInfo.setIcon(new ImageIcon("./img/teacherListBtn.png"));

			}

			@Override
			public void mousePressed(MouseEvent e) {
				showTeacherInfo.setIcon(new ImageIcon("./img/teacherListBtn_down.png"));

			}
		});
		
		showTeacherInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String teacherCode = Main.teacherMgr.find(lec.teacher).teacherCode;
				new DetailTeacher(teacherCode);
			}
		});
		
		setSize(950, 850);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
}