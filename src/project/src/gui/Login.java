package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entity.Guest;
import entity.Teacher;
import facade.DataEngineInterface;
import mgr.*;
import view.LectureView;
import view.TeacherView;

@SuppressWarnings("serial")

class ImageDraw extends JPanel {

	Image img = new ImageIcon("./img/loginBackground.jpg").getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}

public class Login extends JFrame implements Frame {
	Manager<Guest> gMgr = Main.guestMgr;
	Manager<Teacher> tMgr = Main.teacherMgr;

	public static Guest g; // 해당 객체는 다른 패키지의 클래스에서 이용
	public static Teacher t;

	final int center = 325;
	final int width = 320, height = width;
	private Image img = new ImageIcon("./img/loginBack.png").getImage().getScaledInstance(width, height,
			Image.SCALE_DEFAULT);
	ImageIcon loginImage = new ImageIcon(img);
	JLabel login = new JLabel(loginImage);

	JLabel[] subTitle = { new JLabel("전화번호"), new JLabel("비밀번호") };
	JTextField phoneNumber = new JTextField(30);
	JPasswordField password = new JPasswordField(30);
	JButton loginButton = new JButton(new ImageIcon("./img/Login_up.png"));
	JButton submitButton = new JButton(new ImageIcon("./img/signup_up.png"));

	public void addComponents(JPanel panel) {
		panel.add(login).setBounds(center, 60, width, height);
		panel.add(subTitle[0]).setBounds(center - 122, center + 85, width, 35);
		panel.add(phoneNumber).setBounds(center, center + 85, width, 35);
		panel.add(subTitle[1]).setBounds(center - 122, center + 155, width, 35);
		panel.add(password).setBounds(center, center + 155, width, 35);
		panel.add(loginButton).setBounds(center - 10, center + 240, width, 40);
		panel.add(submitButton).setBounds(center + 178, center + 240, width, 40);
	}

	boolean isAcceptable(String phoneNumber, String pwd) {
		// 전체 회원 리스트를 조회하여 입력정보가 해당 객체의 정보와 일치하는지 검사
		for (Guest g : gMgr.mList) {
			String[] guestInfo = g.getLoginInfo();

			if (guestInfo[0].equals(phoneNumber) && guestInfo[1].equals(pwd))
				return true;
		}
		
		for (Teacher t : tMgr.mList) {
			String[] teacherInfo = t.getLoginInfo();

			if (teacherInfo[0].equals(phoneNumber) && teacherInfo[1].equals(pwd))
				return true;
		}
		return false;
	}

	boolean nullCheck(Object o1, Object o2) {
		return (o1 == null && o2 != null) ? true : false;
	}

	public void run(DataEngineInterface engine) {
		GUIManager.customCursor(this);
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mainContainer = new JPanel();
		JPanel background = new ImageDraw();

		phoneNumber.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {}
			  
			  public void removeUpdate(DocumentEvent e) {
				  if (phoneNumber.getText().length() == 0){
				       phoneNumber.setForeground(Color.BLUE);
				     }
			  }
			  public void insertUpdate(DocumentEvent e) {
			  	if (phoneNumber.getText().length() > 1)
			  		phoneNumber.setForeground(Color.BLACK);
			  }
			});
		
		setContentPane(background);
		setLayout(null);

		addComponents(background);

		loginButton.addActionListener(new ActionListener() { // 로그인 버튼을 누르면,
			@Override
			public void actionPerformed(ActionEvent e) {

				String targetPhoneNumber = phoneNumber.getText();
				String targetPassword = new String(password.getPassword());
				
				if (isAcceptable(targetPhoneNumber, targetPassword)) {
					g = gMgr.find(targetPhoneNumber);
					t = tMgr.find(targetPhoneNumber);// 로그인된 회원을 기록.

					if (nullCheck(g, t)) {
						TeacherView.run(engine);
						dispose();
					} else if (g.type == 0) {
						LectureView.run(engine); // 회원이면 강좌목록 화면으로
						dispose();
					} else {
						AdminGUI.run(engine);
						dispose();
					}
				} else { // 잘못된 정보면 에러 메시지박스 출력
					JOptionPane.showMessageDialog(null, "올바른 정보를 입력하세요!", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		GUIManager.setButtonProperties(loginButton);
		loginButton.setSize(150, 60);

		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setIcon(new ImageIcon("./img/Login_down.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setIcon(new ImageIcon("./img/Login_up.png"));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				loginButton.setIcon(new ImageIcon("./img/Login_down.png"));
			}
		});

		GUIManager.setButtonProperties(submitButton);
		submitButton.setSize(150, 60);

		submitButton.addActionListener(new ActionListener() { // 로그인 버튼을 누르면,
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp();
			}
		});

		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				submitButton.setIcon(new ImageIcon("./img/signup_down.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				submitButton.setIcon(new ImageIcon("./img/signup_up.png"));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				submitButton.setIcon(new ImageIcon("./img/signup_down.png"));
			}
		});

		setSize(950, 850);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}