package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import facade.DataEngineInterface;
import gui.GUIManager;
import gui.Login;
import gui.TableSelection;
import gui.TeacherListPage;
import gui.TeacherMyPage;

public class TeacherView {

	public static DataEngineInterface engine;

	public static void run(DataEngineInterface en) {
		engine = en;
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowTeacherGUI();
			}
		});
	}

	private static void createAndShowTeacherGUI() {

		JFrame mainFrame = new JFrame("StoreProgram");
		
		JTabbedPane tab = new JTabbedPane();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GUIManager.frameLocation(mainFrame);
		GUIManager.customCursor(mainFrame);
		// mainFrame.setLocation(450, 130);

		// Create and set up the content pane.
		TableSelection newContentPane = new TableSelection();
		int flag = 1;
		newContentPane.addComponentsToPane(flag);

		mainFrame.getContentPane().add(tab);

		TeacherMyPage tPage = new TeacherMyPage();
		TeacherListPage teacherLP = new TeacherListPage();
		
	    ImageIcon icon = new ImageIcon("home.png");
	    ImageIcon icon2 = new ImageIcon("mypage.png");
	    ImageIcon icon3 = new ImageIcon("teacherList.png");

	    
		tab.addTab("강의목록", icon, newContentPane);
		tab.addTab("마이페이지", icon2, tPage);
		tab.addTab("강사목록", icon3, teacherLP);

		tPage.logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.OK_CANCEL_OPTION);

				if (choice == JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(null, Login.t.name + "님, 안녕히가세요!", "처리 완료",
							JOptionPane.INFORMATION_MESSAGE);
					Login.t = null; // 로그아웃 후에는 로그인한 회원정보는 없음

					mainFrame.dispose();
					GUIManager.run(engine); // 로그인 창 출력
				} else
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
