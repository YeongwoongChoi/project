package view;

import mgr.LectureMgr;
import mgr.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import entity.Guest;
import facade.DataEngineInterface;
import gui.Basket;
import gui.GUIManager;
import gui.ImageLecture;
import gui.Login;
import gui.MyPage;
import gui.Room;
import gui.TableSelection;
import gui.TeacherListPage;

@SuppressWarnings("unused")
public class LectureView {
	public static DataEngineInterface engine;

	public static void run(DataEngineInterface en) {
		engine = en;
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame mainFrame = new JFrame("StoreProgram");
		
		JTabbedPane tab = new JTabbedPane();
		JTabbedPane subTab = new JTabbedPane();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GUIManager.frameLocation(mainFrame);
		GUIManager.customCursor(mainFrame);
		// mainFrame.setLocation(450, 130);

		// Create and set up the content pane.
		TableSelection newContentPane = new TableSelection();
		newContentPane.addComponentsToPane();
		
		mainFrame.getContentPane().add(tab);
	
		ImageLecture lecturePage = new ImageLecture();
		
		MyPage myPage = new MyPage();
		Basket basketPage = new Basket();
		TeacherListPage teacherListPage = new TeacherListPage();
		Room roomPage = new Room();
		
		ImageIcon [] tabIcons = { new ImageIcon("home.png"), 
				new ImageIcon("mypage.png"), new ImageIcon("teacherList.png"),
				new ImageIcon("basket.png"), new ImageIcon("room.png") };
		
		tab.addTab("강의목록", tabIcons[0], subTab);
		
		subTab.addTab("리스트로 보기", newContentPane);
		subTab.addTab("사진으로 보기", lecturePage);
		tab.addTab("마이페이지", tabIcons[1], myPage);
		tab.addTab("강사목록", tabIcons[2], teacherListPage);
		tab.addTab("장바구니", tabIcons[3], basketPage);
		tab.addTab("강의실", tabIcons[4], roomPage);
		
		myPage.logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?",
						"로그아웃", JOptionPane.OK_CANCEL_OPTION);

				if (choice == JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(null, Login.g.name + "님, 안녕히가세요!",
							"처리 완료", JOptionPane.INFORMATION_MESSAGE);
					Login.g = null;	//로그아웃 후에는 로그인한 회원정보는 없음

					mainFrame.dispose();
					GUIManager.run(engine);	//로그인 창 출력
				}
				else
					return;
			}
		});
		
		// Display the window.
		mainFrame.pack();
		mainFrame.setSize(950, 850);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}