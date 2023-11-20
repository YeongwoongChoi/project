package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import facade.DataEngineInterface;

interface Frame {
    void addComponents(JPanel panel);
}

public class GUIManager {
    public static void customCursor(JFrame f) {
        Toolkit tool = Toolkit.getDefaultToolkit();
        Image cursorImage = tool.getImage("img/cursor.png");
        Point point = new Point(0, 0);
        tool.getBestCursorSize(32, 32);
        Cursor cursor = tool.createCustomCursor(cursorImage, point, "foot");
        f.setCursor(cursor);
    }

    public static void frameLocation(JFrame frame) {		//User의 화면비율에 따른 창 출력
        Dimension frameSize = frame.getSize();
        Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setLocation((monitorSize.width - frameSize.width + 100)/3, (monitorSize.height - frameSize.height)/6);
    }

    public static <T extends JButton> void setMouseListener(T c, String on, String off) {
        ImageIcon entered = new ImageIcon(on);
        ImageIcon exited = new ImageIcon(off);

        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                c.setIcon(entered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                c.setIcon(exited);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                c.setIcon(entered);
            }
        });
    }

    public static void setButtonProperties(JButton b) {
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
    }

    public static void run(DataEngineInterface engine) {
        Login loginProgram = new Login();
        frameLocation(loginProgram);
        loginProgram.run(engine);
    }
}