package view;

import facade.DataEngineInterface;
import gui.GUIManager;
import gui.MyPage;
import gui.TableSelection;

import javax.swing.*;

public class CustomerView {
    public static DataEngineInterface engine;
    public static TableSelection [] tables;
    public static void run(DataEngineInterface en) {
        engine = en;
        tables = new TableSelection[]{new TableSelection("customerorder"),
                new TableSelection("restaurant"), new TableSelection("reserve") };
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame mainFrame = new JFrame("Customer");
        JTabbedPane tab = new JTabbedPane();
        JTabbedPane subTab = new JTabbedPane();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIManager.frameLocation(mainFrame);
        GUIManager.customCursor(mainFrame);

        final String [] labels = { "Ordered Dish", "Restaurants", "Your Reservation" };
        // Create and set up the content pane.
        for (int i = 0; i < tables.length; i++) {
            tables[i].addComponentsToPane();
            subTab.addTab(labels[i], tables[i]);
        }
        mainFrame.getContentPane().add(tab);
        MyPage myPage = new MyPage();

        ImageIcon [] tabIcons = { new ImageIcon("img/home.png"),
                new ImageIcon("img/mypage.png") };

        tab.addTab("Customer", tabIcons[0], subTab);
        tab.addTab("My Page", tabIcons[1], myPage);

        mainFrame.pack();
        mainFrame.setSize(1050, 850);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}