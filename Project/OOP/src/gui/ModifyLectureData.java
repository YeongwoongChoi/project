package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;

import entity.Lecture;
import mgr.Main;

public class ModifyLectureData extends JFrame implements Frame {
   private int textSize = 17;
   JButton backBtn;
   JButton okBtn;
   Image img;
   JLabel lectureImg;
   JLabel desLabel;
   JLabel moneyLabel;
   BackgroundPanel backPanel;
   private JPanel topPan = new JPanel();
   private RoundedPanel centerPan = new RoundedPanel();
   private RoundedPanel btmPan = new RoundedPanel();
   private RoundedPanel buttonPan = new RoundedPanel();
   private JPanel imagePan = new JPanel();
   private JPanel teacherPan = new JPanel();
   private JPanel levelPan = new JPanel();
   private JPanel lecNamePan = new JPanel();
   private JPanel lecPricePan = new JPanel();
   private JPanel lecRunTimePan = new JPanel();
   private JPanel countPan = new JPanel();
   private JPanel peoplePan = new JPanel();
   private JPanel desPan = new JPanel();
   private JTextArea desArea = new JTextArea(4, 5);
   private JPanel desTxtPan = new JPanel();
   private RoundedTextField lecNameField = new RoundedTextField(textSize);
   private RoundedTextField teacherNameField = new RoundedTextField(textSize);
   private RoundedTextField lecPriceField = new RoundedTextField(textSize);
   private RoundedTextField levelField = new RoundedTextField(textSize);
   private RoundedTextField countField = new RoundedTextField(textSize);
   private RoundedTextField peopleField = new RoundedTextField(textSize);
   private RoundedTextField timeField = new RoundedTextField(textSize);

   Container contentPane = getContentPane();
   Clip clip;
   String musicPath = "./music/mm.wav";

   public ModifyLectureData(String lectureCode) {
      Lecture lec = (Lecture) Main.lectureMgr.find(lectureCode); // 강의코드에 맞는 강의 찾고
      backPanel = new BackgroundPanel(new ImageIcon("./img/back.png").getImage());

      backBtn = new JButton(new ImageIcon("./img/backBtn.png"));
      backBtn.setBorderPainted(false);
      backBtn.setFocusPainted(false);
      backBtn.setContentAreaFilled(false);
      backBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            backBtn.setIcon(new ImageIcon("./img/gobackBtn.png"));
            // backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            backBtn.setIcon(new ImageIcon("./img/backBtn.png"));
            // backBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            backBtn.setIcon(new ImageIcon("./img/gobackBtn.png"));
            StopMusic();
         }
      });

      okBtn = new JButton(new ImageIcon("./img/okBtn.png"));
      okBtn.setBorderPainted(false);
      okBtn.setFocusPainted(false);
      okBtn.setContentAreaFilled(false);
      okBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            okBtn.setIcon(new ImageIcon("./img/gookBtn.png"));

         }

         @Override
         public void mouseExited(MouseEvent e) {
            okBtn.setIcon(new ImageIcon("./img/okBtn.png"));

         }

         @Override
         public void mousePressed(MouseEvent e) {
            okBtn.setIcon(new ImageIcon("./img/gookBtn.png"));
            StopMusic();
         }
      });

      setTitle("강의 상세정보");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      GUIManager.frameLocation(this);
      desArea.setColumns(7);
      desArea.setLineWrap(true);
      desArea.setWrapStyleWord(true);
      desArea.setSize(new Dimension(400, 80));
      Border border = BorderFactory.createLineBorder(new Color(45, 35, 35));
      desArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(15, 15, 15, 15)));
      Font font = new Font("Malgun Gothic", Font.PLAIN, 18);
      desArea.setFont(font);

      img = getImage(lectureCode);

      lecNamePan.setLayout(new BoxLayout(lecNamePan, BoxLayout.X_AXIS));
      lecNamePan.add(new JLabel("과목명: "));
      lecNameField.setText(lec.lectureName);
      lecNamePan.add(lecNameField);

      teacherPan.setLayout(new BoxLayout(teacherPan, BoxLayout.X_AXIS));
      teacherPan.add(new JLabel("담당 강사님 :"));
      teacherNameField.setText(lec.teacher);
      teacherPan.add(teacherNameField);

      lecPricePan.setLayout(new BoxLayout(lecPricePan, BoxLayout.X_AXIS));
      lecPricePan.add(new JLabel("가격 : "));
      lecPriceField.setText(Integer.toString(lec.price));
      lecPricePan.add(lecPriceField);
      lecPricePan.add(new JLabel("원"));

      levelPan.setLayout(new BoxLayout(levelPan, BoxLayout.X_AXIS));
      lecNameField.setText(lec.lectureName);
      levelPan.add(new JLabel("난이도 :"));
      levelField.setText(lec.level);
      levelPan.add(levelField);

      countPan.setLayout(new BoxLayout(countPan, BoxLayout.X_AXIS));
      lecNameField.setText(lec.lectureName);
      countPan.add(new JLabel("레슨 횟수 :"));
      countField.setText(Integer.toString(lec.lectureCount));
      countPan.add(countField);
      countPan.add(new JLabel("회"));

      peoplePan.setLayout(new BoxLayout(peoplePan, BoxLayout.X_AXIS));
      lecNameField.setText(lec.lectureName);
      peoplePan.add(new JLabel("수용 인원 :"));
      peopleField.setText(Integer.toString(lec.maximumPeople));
      peoplePan.add(peopleField);
      peoplePan.add(new JLabel("명"));

      lecRunTimePan.setLayout(new BoxLayout(lecRunTimePan, BoxLayout.X_AXIS));
      lecNameField.setText(lec.lectureName);
      lecRunTimePan.add(new JLabel("총 강의 시간 :"));
      timeField.setText(Integer.toString(lec.runningTime));
      lecRunTimePan.add(timeField);

      desLabel = new JLabel("강의 설명");
      desLabel.setOpaque(true);
      desLabel.setForeground(new Color(255, 255, 255));
      desLabel.setBackground(new Color(34, 47, 62));
      desPan.setLayout(new BoxLayout(desPan, BoxLayout.X_AXIS));
      // lecNameField.setText(lec.lectureName);
      desPan.add(desLabel);
      desPan.setPreferredSize(new Dimension(500, 30));
      // desPan.setBackground(new Color(34, 47, 62));

      desTxtPan.setLayout(new BoxLayout(desTxtPan, BoxLayout.X_AXIS));

      if (img != null) {
         img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
         lectureImg = new JLabel(new ImageIcon(img));
         imagePan.add(lectureImg);
      }

      for (String s : lec.description) {
         desArea.append(s + " ");
         desArea.append("\n");
      }

      desTxtPan.add(new JScrollPane(desArea), BorderLayout.CENTER);

      buttonPan.add(backBtn, BorderLayout.WEST);
      buttonPan.add(okBtn, BorderLayout.EAST);
      buttonPan.setBackground(new Color(34, 47, 62));

      topPan.setLayout(new BoxLayout(topPan, BoxLayout.Y_AXIS));
      topPan.add(imagePan);

      centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.Y_AXIS));
      centerPan.add(lecNamePan);
      centerPan.add(teacherPan);
      centerPan.add(lecPricePan);
      centerPan.add(levelPan);
      centerPan.add(countPan);
      centerPan.add(peoplePan);
      centerPan.add(lecPricePan);
      centerPan.add(lecRunTimePan);

      btmPan.setLayout(new BoxLayout(btmPan, BoxLayout.Y_AXIS));
      btmPan.add(desPan);
      btmPan.add(desTxtPan);
      btmPan.add(buttonPan);
      btmPan.setBackground(new Color(34, 47, 62));

      backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
      backPanel.add(topPan);
      backPanel.add(centerPan);
      backPanel.add(btmPan);
      backPanel.setLayout(new FlowLayout(1, 0, 20));
      backPanel.setPreferredSize(new Dimension(580, 900));

      backBtn.addActionListener(new ActionListener() { // 이전버튼을 누르면, 테이블 화면으로 이동
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });

      okBtn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, "수정하시겠습니까?", "수정 완료", JOptionPane.OK_CANCEL_OPTION);

            if (choice == JOptionPane.OK_OPTION) {
               String lectureName = lecNameField.getText();
               String teacher = teacherNameField.getText();
               int price = Integer.parseInt(lecPriceField.getText());
               String level = levelField.getText(); // 난이도
               int runningTime = Integer.parseInt(timeField.getText());
               ; // 1회당 진행시간
               int lectureCount = Integer.parseInt(lecPriceField.getText());
               ;// 레슨휫수
               int maximumPeople = Integer.parseInt(peopleField.getText());
               ; // 최대인원
               String description = desArea.getText();
               String[] desArr = description.split("\n");
               lec.lectureName = lectureName;
               lec.teacher = teacher;
               lec.price = price;
               lec.level = level;
               lec.runningTime = runningTime;
               lec.lectureCount = lectureCount;
               lec.maximumPeople = maximumPeople;
               lec.description.clear();
               for (int i = 0; i < desArr.length; i++) {
                  lec.description.add(desArr[i]);
               }

               dispose();
            }

         }

      });

      okBtn.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               okBtn.doClick();
            }
         }
      });

      contentPane.add(backPanel);

      pack();
      // setSize(850, 900);
      setLocationRelativeTo(null);
      setVisible(true);
      setResizable(false);

      StartSound(musicPath);

      
      this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) { 
               StopMusic();
            }
    });
   }

   public void StartSound(String file) {
      try {
         AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
         clip = AudioSystem.getClip();
         clip.open(ais);
         clip.start();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void StopMusic() {
      clip.stop();
      clip.close();
   }

   Image getImage(String lectureCode) {
      return new ImageIcon("lecture/" + lectureCode + ".jpg").getImage().getScaledInstance(400, 300,
            Image.SCALE_SMOOTH);
   }

   @Override
   public void addComponents(JPanel panel) {
      // TODO Auto-generated method stub

   }

}