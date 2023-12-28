package gui;

import driver.Main;
import driver.Manager;
import entity.Customer;
import entity.Owner;
import facade.DataEngineInterface;
import view.CustomerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ImageDraw extends JPanel {
    Image img = new ImageIcon("img/loginBackground.jpg").getImage();

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}

public class Login extends JFrame implements Frame {
    Manager <Customer> cManager = Main.customerMgr;

    public static Customer c;

    final int CENTER = 325, WIDTH = 320, HEIGHT = WIDTH, ID_FIELD_MAX = 15, PHONE_FIELD_MAX = 12;

    final JLabel mainTitle = new JLabel("Integrated Management System");
    final JLabel [] subTitle = { new JLabel("Your ID"), new JLabel("Your phone number") };
    JTextField identifier = new JTextField(ID_FIELD_MAX);
    JTextField phoneNumber = new JTextField(PHONE_FIELD_MAX);
    JButton logInButton = new JButton(new ImageIcon("img/login_off.png"));
    JButton exitButton = new JButton(new ImageIcon("img/exit_off.png"));
    JButton signUpButton = new JButton(new ImageIcon("img/signUp_off.png"));


    Customer findMember(String [] info) {
        return cManager.find(info);
    }

    public void run(DataEngineInterface engine) {
        GUIManager.customCursor(this);

        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel background = new ImageDraw();
        setContentPane(background);
        setLayout(null);
        addComponents(background);

        logInButton.addActionListener(e -> {
            String [] targetInfo = { identifier.getText(), phoneNumber.getText() };
            if ((c = findMember(targetInfo)) == null)
                JOptionPane.showMessageDialog(null, "Wrong Account Information",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
            else {
                CustomerView.run(engine);
                dispose();
            }
        });

        GUIManager.setButtonProperties(logInButton);
        logInButton.setSize(150, 60);
        GUIManager.setMouseListener(logInButton, "img/login_on.png", "img/login_off.png");

        GUIManager.setButtonProperties(signUpButton);
        signUpButton.setSize(150, 60);
        signUpButton.addActionListener(e -> new SignUp());
        GUIManager.setMouseListener(signUpButton, "img/signUp_on.png", "img/signUp_off.png");

        GUIManager.setButtonProperties(exitButton);
        exitButton.setSize(60, 40);
        GUIManager.setMouseListener(exitButton, "img/exit_on.png", "img/exit_off.png");

        exitButton.addActionListener(e -> {
            int selected = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (selected == JOptionPane.OK_OPTION)
                dispose();
        });
        setSize(1050, 850);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    @Override
    public void addComponents(JPanel panel) {
        panel.add(mainTitle).setBounds(CENTER, 40, 2 * WIDTH, HEIGHT);
        panel.add(subTitle[0]).setBounds(CENTER - 100, CENTER + 85, WIDTH, 35);
        panel.add(subTitle[1]).setBounds(CENTER - 100, CENTER + 155, WIDTH, 35);
        panel.add(identifier).setBounds(CENTER + 150, CENTER + 85, WIDTH, 35);
        panel.add(phoneNumber).setBounds(CENTER + 150, CENTER + 155, WIDTH, 35);
        panel.add(logInButton).setBounds(CENTER - 50, CENTER + 240, WIDTH, 40);
        panel.add(signUpButton).setBounds(CENTER + 150, CENTER + 240, WIDTH, 40);
        panel.add(exitButton).setBounds(2 * CENTER + 150, 30, WIDTH, 30);
    }
}