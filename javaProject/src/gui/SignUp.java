package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entity.Guest;
import entity.Lecture;
import entity.Teacher;
import mgr.Main;
import mgr.Manager;
import view.LectureView;
import view.TeacherView;

public class SignUp extends JFrame implements Frame {

   Guest g = new Guest();

   Manager<Guest> gMgr = Main.guestMgr;
   Manager<Teacher> tMgr = Main.teacherMgr;

   private Image img = new ImageIcon("./img/loginBack.png").getImage().getScaledInstance(250, 250,
         Image.SCALE_DEFAULT);;

   JButton okBtn;
   JButton cancelBtn;

   ImageIcon loginImage = new ImageIcon(img);
   JLabel login = new JLabel(loginImage);

   Container contentPane = getContentPane();
   BackgroundPanel backPanel;
   JPanel topPan = new JPanel();
   JPanel btnPan = new JPanel(new BorderLayout());
   JPanel fieldPan = new JPanel();
   JPanel buttonPan = new JPanel();

   JLabel pwLabel = new JLabel("비밀번호(필수)");
   JLabel pwVLabel = new JLabel("비밀번호 확인(필수)");
   JLabel topLabel = new JLabel("회원가입");
   JLabel mLabel = new JLabel("회원가입을 진행하세요!");

   private RoundedTextField nameField = new RoundedTextField(21);
   RoundedPasswordField passwordField = new RoundedPasswordField(21);
   RoundedPasswordField vPasswordField = new RoundedPasswordField(21);
   private RoundedTextField addressField = new RoundedTextField(21);
   private RoundedTextField phoneNumberField = new RoundedTextField(21);

   public SignUp() {
      backPanel = new BackgroundPanel(new ImageIcon("./img/loginBackground.jpg").getImage());

      okBtn = new JButton(new ImageIcon("./img/comBtn.png"));
      okBtn.setBorderPainted(false);
      okBtn.setFocusPainted(false);
      okBtn.setContentAreaFilled(false);
      okBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            okBtn.setIcon(new ImageIcon("./img/comBtn_down.png"));

         }

         @Override
         public void mouseExited(MouseEvent e) {
            okBtn.setIcon(new ImageIcon("./img/comBtn.png"));

         }

         @Override
         public void mousePressed(MouseEvent e) {
            okBtn.setIcon(new ImageIcon("./img/comBtn_down.png"));

         }
      });

      cancelBtn = new JButton(new ImageIcon("./img/cancelBtn.png"));
      cancelBtn.setBorderPainted(false);
      cancelBtn.setFocusPainted(false);
      cancelBtn.setContentAreaFilled(false);
      cancelBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            cancelBtn.setIcon(new ImageIcon("./img/cancelBtn_down.png"));

         }

         @Override
         public void mouseExited(MouseEvent e) {
            cancelBtn.setIcon(new ImageIcon("./img/cancelBtn.png"));

         }

         @Override
         public void mousePressed(MouseEvent e) {
            cancelBtn.setIcon(new ImageIcon("./img/cancelBtn_down.png"));

         }
      });

      backPanel.setLayout(new BorderLayout());
      topPan.setLayout(new BoxLayout(topPan, BoxLayout.Y_AXIS));
      login.setAlignmentX(Component.CENTER_ALIGNMENT);
      login.setOpaque(false);
      topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      mLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

      topLabel.setBorder(new EmptyBorder(10, 20, 20, 20));
      topPan.setBorder(new EmptyBorder(0, 0, 20, 0));
      topPan.add(topLabel);
      topPan.add(login);
      topPan.add(mLabel);
      topPan.setOpaque(false);

      fieldPan.setBorder(new EmptyBorder(10, 10, 10, 10)); // 상좌하우
      fieldPan.setLayout(new BoxLayout(fieldPan, BoxLayout.Y_AXIS));

      fieldPan.add(nameField);
      nameField.setForeground(Color.GRAY);
      nameField.setFont(nameField.getFont().deriveFont(18.0f));
      nameField.setText("이름");

      fieldPan.add(pwLabel);
      fieldPan.add(passwordField);

      passwordField.setForeground(Color.GRAY);
      passwordField.setFont(passwordField.getFont().deriveFont(18.0f));

      pwLabel.setFont(pwLabel.getFont().deriveFont(13.0f));
      fieldPan.add(pwVLabel);
      pwVLabel.setFont(pwVLabel.getFont().deriveFont(13.0f));
      fieldPan.add(vPasswordField);

      vPasswordField.setForeground(Color.GRAY);
      vPasswordField.setFont(vPasswordField.getFont().deriveFont(18.0f));

      fieldPan.add(addressField);
      addressField.setForeground(Color.GRAY);
      addressField.setFont(addressField.getFont().deriveFont(18.0f));
      addressField.setText("주소");
      fieldPan.setOpaque(false);

      fieldPan.add(phoneNumberField);
      phoneNumberField.setForeground(Color.GRAY);
      phoneNumberField.setFont(addressField.getFont().deriveFont(18.0f));
      phoneNumberField.setText("휴대폰 번호 ex) 010-0000-0000");

      buttonPan.add(cancelBtn, BorderLayout.WEST);
      buttonPan.add(okBtn, BorderLayout.EAST);
      buttonPan.setOpaque(false);
      buttonPan.setBorder(new EmptyBorder(40, 40, 20, 40));

      backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
      backPanel.add(topPan);
      backPanel.add(fieldPan);
      backPanel.add(btnPan);
      backPanel.add(buttonPan);
      backPanel.setLayout(new FlowLayout(1, 0, 20));
      backPanel.setPreferredSize(new Dimension(550, 800));

      
      cancelBtn.addActionListener(new ActionListener() { // 이전버튼을 누르면, 테이블 화면으로 이동
            @Override
            public void actionPerformed(ActionEvent e) {
               dispose();
            }
         });
      okBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            // JOptionPane.showMessageDialog(null, "가입 완료.");

            Guest g = new Guest();
            Guest og = new Guest();
            int type = 0;
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());
            String vPassword = new String(vPasswordField.getPassword());
            String address = addressField.getText();
            String[] addresses = address.split(" ");
            String phoneNumber = phoneNumberField.getText();

            for (int i = 0; i < addresses.length; i++) {
               g.address.add(addresses[i]);
            }

            if (nullCheck(name)) {
               JOptionPane.showMessageDialog(null, "이름을 입력해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
            } else if (!password.contentEquals(vPassword)) {
               JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다. 확인해주세요", "로그인 오류", JOptionPane.ERROR_MESSAGE);
            } else if (isDuplicate(phoneNumber)) {
               JOptionPane.showMessageDialog(null, "해당 휴대폰 번호로 가입되어있는 아이디가 존재합니다.", "로그인 오류",
                     JOptionPane.ERROR_MESSAGE);
            } else { // 잘못된 정보면 에러 메시지박스 출력
               g.type = type;
               g.name = name;
               g.pwd = password;
               g.phoneNumber = phoneNumber;
               g.completeLecture = new ArrayList<Lecture>();
               g.lectureList = new ArrayList<Lecture>();
               g.basketList = new ArrayList<Lecture>();
               Main.guestMgr.addMember(g);
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
               JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "가입이 완료되었습니다");
               dispose();
            }
         }
      });

      nameField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {

         }

         public void removeUpdate(DocumentEvent e) {
            if (nameField.getText().length() <= 0) {
               nameField.setForeground(Color.BLUE);
               nameField.setFont(nameField.getFont().deriveFont(18.0f));
            }
         }

         public void insertUpdate(DocumentEvent e) {
         }

      });

      nameField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
            String name = nameField.getText();
            if (name != null) {
               nameField.setText(name);
            }
            if (name.contentEquals("이름")) {
               nameField.setText("");
            }
         }

         public void focusLost(FocusEvent e) {
            String name = nameField.getText();
            if (name.contentEquals("")) {
               nameField.setForeground(Color.GRAY);
               nameField.setText("이름");
            }
         }
      });

      addressField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
         }

         public void removeUpdate(DocumentEvent e) {
            if (addressField.getText().length() <= 0) {
               addressField.setForeground(Color.BLUE);
            }
         }

         public void insertUpdate(DocumentEvent e) {

         }

      });

      addressField.addFocusListener(new FocusListener() {

         public void focusGained(FocusEvent e) {
            String address = addressField.getText();
            if (address != null) {
               addressField.setText(address);
            }
            if (address.contentEquals("주소")) {
               addressField.setText("");
            }
         }

         public void focusLost(FocusEvent e) {
            String address = addressField.getText();
            if (address.contentEquals("")) {
               addressField.setForeground(Color.GRAY);
               addressField.setText("주소");
            }
         }
      });

      phoneNumberField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {

         }

         public void removeUpdate(DocumentEvent e) {
            if (phoneNumberField.getText().length() <= 0) {
               phoneNumberField.setForeground(Color.BLUE);
               phoneNumberField.setFont(nameField.getFont().deriveFont(18.0f));
            }
         }

         public void insertUpdate(DocumentEvent e) {
         }

      });

      phoneNumberField.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
            String name = phoneNumberField.getText();
            if (name != null) {
               phoneNumberField.setText(name);
            }
            if (name.contentEquals("휴대폰 번호 ex) 010-0000-0000")) {
               phoneNumberField.setText("");
            }
         }

         public void focusLost(FocusEvent e) {
            String name = phoneNumberField.getText();
            if (name.contentEquals("")) {
               phoneNumberField.setForeground(Color.GRAY);
               phoneNumberField.setText("휴대폰 번호 ex) 010-0000-0000");
            }
         }
      });

      setTitle("회원 가입");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      GUIManager.frameLocation(this);
      GUIManager.customCursor(this);
      contentPane.add(backPanel);
      pack();
      setSize(950, 850);
      setLocationRelativeTo(null);

      setResizable(false);
      setVisible(true);

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

   boolean nullCheck(Object o) {
      return (o == null) ? true : false;
   }

   boolean isAcceptable(String phoneNumber) {
      // 전체 회원 리스트를 조회하여 입력정보가 해당 객체의 정보와 일치하는지 검사
      for (Guest g : gMgr.mList) {
         String[] guestInfo = g.getLoginInfo();

         if (guestInfo[0].equals(phoneNumber)) {
            return true;
         }
      }
      return false;
   }

   @Override
   public void addComponents(JPanel panel) {
      // TODO Auto-generated method stub

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
}