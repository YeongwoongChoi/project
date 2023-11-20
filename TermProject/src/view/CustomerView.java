package view;

import facade.DataEngineInterface;
import gui.GUIManager;
//import gui.MyPage;
import gui.Login;
import gui.TableSelection;

import javax.swing.*;

public class CustomerView {
    public static DataEngineInterface engine;

    public static void run(DataEngineInterface en) {
        engine = en;
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame mainFrame = new JFrame("Customer");
        JTabbedPane tab = new JTabbedPane();
        JTabbedPane subTab = new JTabbedPane();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIManager.frameLocation(mainFrame);
        GUIManager.customCursor(mainFrame);

        // Create and set up the content pane.
        /*TableSelection newContentPane = new TableSelection();
        newContentPane.addComponentsToPane();

        mainFrame.getContentPane().add(tab);

        //ImageLecture lecturePage = new ImageLecture();

        //MyPage myPage = new MyPage();
        //TeacherListPage teacherListPage = new TeacherListPage();
        //Room roomPage = new Room();

        ImageIcon [] tabIcons = { new ImageIcon("home.png"),
                new ImageIcon("mypage.png"), new ImageIcon("teacherList.png"),
                new ImageIcon("basket.png"), new ImageIcon("room.png") };

        tab.addTab("List of reservations", tabIcons[0], subTab);

        subTab.addTab("By List", newContentPane);
        /*subTab.addTab("By Picture", lecturePage);
        tab.addTab("My Page", tabIcons[1], myPage);
        tab.addTab("강사목록", tabIcons[2], teacherListPage);
        tab.addTab("장바구니", tabIcons[3], basketPage);
        tab.addTab("강의실", tabIcons[4], roomPage);

        myPage.logOut.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want log out?",
                    "Logout", JOptionPane.OK_CANCEL_OPTION);

            if (choice == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Good bye, " + Login.c.name,
                        "Logout Success", JOptionPane.INFORMATION_MESSAGE);
                Login.c = null;

                mainFrame.dispose();
                GUIManager.run(engine);
            }
        });*/
        mainFrame.pack();
        mainFrame.setSize(950, 850);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}