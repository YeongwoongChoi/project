package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entity.Guest;
import entity.Lecture;
import entity.Teacher;
import mgr.Main;
import mgr.Manager;

public class ModifyTeacherInfo extends JFrame {

   Manager<Guest> gMgr = Main.guestMgr;
   Manager<Teacher> tMgr = Main.teacherMgr;
   BackgroundPanel backPanel;

   Teacher t;
   Container contentPane = getContentPane();

   private JPanel topPan = new JPanel();
   private JPanel centerPan = new JPanel();
   private JPanel btmPan = new JPanel();
   private JPanel leftPan = new JPanel();
   // private JPanel fieldPan = new JPanel();

   private JLabel nameLabel = new JLabel("이름 : ");
   private JLabel name;
   private JLabel pwdLabel = new JLabel("비밀번호 ");
   private JLabel pwdVLabel = new JLabel("비밀번호 확인 ");
   private JLabel emailLabel = new JLabel("이메일");
   private JLabel jobLabel = new JLabel("직무");
   private JLabel phoneLabel = new JLabel("휴대폰 번호");
   // private JLabel mainLabel = new JLabel("회원정보 수정");

   RoundedTextField phoneField = new RoundedTextField(20);
   RoundedTextField emailField = new RoundedTextField(20);
   RoundedTextField phoneNumberField = new RoundedTextField(20);
   RoundedTextField jobField = new RoundedTextField(20);
   RoundedPasswordField passwordField = new RoundedPasswordField(20);
   RoundedPasswordField vPasswordField = new RoundedPasswordField(20);

   JButton confirmButton;
   JButton cancelButton;

   public ModifyTeacherInfo(Teacher t) {
      this.t = t;

      backPanel = new BackgroundPanel(new ImageIcon("./img/modifyTeacher.jpg").getImage());

      name = new JLabel(t.name);

      topPan.add(nameLabel);
      topPan.add(name);
      topPan.setOpaque(false);
      topPan.setBorder(new EmptyBorder(40, 100, 40, 100)); // 상좌하우
      topPan.setAlignmentX(Component.CENTER_ALIGNMENT);

      centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.Y_AXIS));
      centerPan.setAlignmentX(Component.CENTER_ALIGNMENT);
      centerPan.add(emailLabel);
      centerPan.add(emailField);
      emailField.setText(t.email);
      jobLabel.setBorder(new EmptyBorder(20, 0, 0, 0)); // 상좌하우
      centerPan.add(jobLabel);
      centerPan.add(jobField);
      jobField.setText(t.job);

      pwdLabel.setBorder(new EmptyBorder(20, 0, 0, 0)); // 상좌하우
      centerPan.add(pwdLabel);
      centerPan.add(passwordField);

      pwdVLabel.setBorder(new EmptyBorder(20, 0, 0, 0)); // 상좌하우
      centerPan.add(pwdVLabel);
      centerPan.add(vPasswordField);

      phoneLabel.setBorder(new EmptyBorder(20, 0, 0, 0)); // 상좌하우
      centerPan.add(phoneLabel);
      centerPan.add(phoneField);
      phoneField.setText(t.phoneNumber);

      centerPan.setOpaque(false);
      nameLabel.setOpaque(false);
      name.setOpaque(false);
      emailLabel.setOpaque(false);
      jobField.setOpaque(false);
      jobLabel.setOpaque(false);
      pwdLabel.setOpaque(false);
      passwordField.setOpaque(false);
      pwdVLabel.setOpaque(false);
      vPasswordField.setOpaque(false);

      confirmButton = new JButton(new ImageIcon("./img/comBtn.png"));
      confirmButton.setBorderPainted(false);
      confirmButton.setFocusPainted(false);
      confirmButton.setContentAreaFilled(false);

      cancelButton = new JButton(new ImageIcon("./img/cancelBtn.png"));
      cancelButton.setBorderPainted(false);
      cancelButton.setFocusPainted(false);
      cancelButton.setContentAreaFilled(false);

      btmPan.setAlignmentX(Component.CENTER_ALIGNMENT);
      btmPan.setLayout(new BorderLayout());
      btmPan.setBorder(new EmptyBorder(40, 10, 40, 20)); // 상좌하우
      btmPan.add(cancelButton, BorderLayout.WEST);
      btmPan.add(confirmButton, BorderLayout.EAST);
      btmPan.setOpaque(false);
      btmPan.setBorder(new EmptyBorder(80, 40, 40, 40));
      setTitle("회원정보 수정");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

      backPanel.add(topPan);
      backPanel.add(leftPan);
      backPanel.add(centerPan);
      backPanel.add(btmPan);
      backPanel.setLayout(new FlowLayout(1, 0, 10));

      confirmButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            confirmButton.setIcon(new ImageIcon("./img/comBtn_down.png"));

         }

         @Override
         public void mouseExited(MouseEvent e) {
            confirmButton.setIcon(new ImageIcon("./img/comBtn.png"));

         }

         @Override
         public void mousePressed(MouseEvent e) {
            confirmButton.setIcon(new ImageIcon("./img/comBtn_down.png"));

         }
      });

      confirmButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

            String password = new String(passwordField.getPassword());
            String vPassword = new String(vPasswordField.getPassword());
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            String job = jobField.getText();

            if (isDuplicate(phoneNumber)) {
               JOptionPane.showMessageDialog(null, "해당 휴대폰 번호로 가입되어있는 아이디가 존재합니다.", "로그인 오류",
                     JOptionPane.ERROR_MESSAGE);
            } 
            else if (!password.contentEquals(vPassword)) {
               JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다. 확인해주세요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
            } 
            else {

               t.pwd = password;
               t.phoneNumber = phoneNumber;
               t.email = email;
               t.job = job;

               Login.t = t;
               t.setModifiedInfo(t);

               new Thread() {
                  public void run() {
                     for (int i = 0; i < 3; i++) {
                        try {
                           Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        JOptionPane.getRootFrame().dispose();
                     }
                  }
               }.start();
               JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "수정 완료");

               StringBuilder sb = new StringBuilder();
               String info = TeacherMyPage.addTeacherInfoToPane(sb, Login.t);
               TeacherMyPage.teacherInfoLabel.setText(info);
               dispose();
               return;
            }
         }
      });

      cancelButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            cancelButton.setIcon(new ImageIcon("./img/cancelBtn_down.png"));

         }

         @Override
         public void mouseExited(MouseEvent e) {
            cancelButton.setIcon(new ImageIcon("./img/cancelBtn.png"));

         }

         @Override
         public void mousePressed(MouseEvent e) {
            cancelButton.setIcon(new ImageIcon("./img/cancelBtn_down.png"));

         }
      });

      cancelButton.addActionListener(new ActionListener() { // 이전버튼을 누르면, 테이블 화면으로 이동
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      GUIManager.frameLocation(this);
      contentPane.add(backPanel);
      // setLayout(new FlowLayout(3));
      pack();
      setSize(550, 800);
      setLocationRelativeTo(null);
      // setResizable(false);
      setVisible(true);

   }

   void setTextAreaOptions(JTextArea t) {
      t.setLineWrap(true);
      t.setWrapStyleWord(true);
      t.setOpaque(true);
      t.setEditable(true);
   }

   public class RoundedPasswordField extends JPasswordField {
      private Shape shape;

      public RoundedPasswordField(int size) {
         super(size);
         setOpaque(false); // As suggested by @AVD in comment.
      }

      protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
         super.paintComponent(g);
      }

      protected void paintBorder(Graphics g) {
         g.setColor(new Color(246, 229, 141));
         g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
      }

      public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
         }
         return shape.contains(x, y);
      }
   }

   boolean isDuplicate(String phoneNumber) {
      for (Teacher t : tMgr.mList) {
         if (phoneNumber.contentEquals(t.phoneNumber)) {
            return true;
         }
      }
      for (Guest g : gMgr.mList) {
         if (phoneNumber.contentEquals(g.phoneNumber)) {
            return true;
         }
      }
      return false;
   }

}