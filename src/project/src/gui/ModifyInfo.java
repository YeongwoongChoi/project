package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import entity.Guest;

public class ModifyInfo extends JFrame {

   private JPanel topPanel = new JPanel();
   private JLabel pwdLabel = new JLabel("비밀번호 ");
   private JLabel pwdCLabel = new JLabel("비밀번호 확인 ");
   private JPanel centerPanel = new JPanel();
   private JLabel mainLabel = new JLabel("회원정보 수정");

   JTextArea phoneNumberArea = new JTextArea(1, 15);
   JTextArea addressArea = new JTextArea(1, 15);
   JTextArea nameArea = new JTextArea(1, 15);
   JPasswordField pwdArea = new JPasswordField();
   JPasswordField pwdCArea = new JPasswordField();
   
   JButton confirmButton = new JButton("수정 완료");

   Guest guest;

   void setTextAreaOptions(JTextArea t) {
      t.setLineWrap(true);
      t.setWrapStyleWord(true);
      t.setOpaque(true);
      t.setEditable(true);
   }

   void addComponents() {
      add(topPanel, BorderLayout.NORTH);
      add(centerPanel, BorderLayout.CENTER);

      topPanel.add(mainLabel, BorderLayout.NORTH);
      topPanel.setBackground(Color.getHSBColor(154, 254, 25));

      // centerPanel 에 10,1 로 차례대로 넣기
      centerPanel.add(new JLabel("이름 "));
      centerPanel.add(nameArea);
      centerPanel.add(new JLabel("전화번호  "));
      centerPanel.add(phoneNumberArea);
      centerPanel.add(new JLabel("주소 :"));
      centerPanel.add(addressArea);
      centerPanel.add(pwdLabel);
      centerPanel.add(pwdArea);
      centerPanel.add(pwdCLabel);
      centerPanel.add(pwdCArea);

      // text에 내용 넣기
      nameArea.append(guest.name);
      phoneNumberArea.append(guest.phoneNumber);
      for (String s : guest.address) {
         addressArea.append(s + " ");
      }

      // text 읽고 쓰기 가능
      setTextAreaOptions(addressArea);
      setTextAreaOptions(phoneNumberArea);
      setTextAreaOptions(nameArea);

      add(confirmButton, BorderLayout.SOUTH);
   }

   private void modifyAddress(List<JLabel> labels) {

      for (JLabel label : labels) {
         if (labels.size() > guest.address.size()) {
            for (int i = labels.size() - 1; i > guest.address.size(); i--) {
               label.setText("");
            }
         }

         int j = 0;

         for (String s : guest.address) {
            label = labels.get(j);
            label.setText(s);
            j++;
         }
      }
   }

   public ModifyInfo(Guest g) {
      this.guest = g;

      setTitle("회원정보 수정");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      GUIManager.frameLocation(this);
      setLayout(new BorderLayout(3, 3));
      centerPanel.setLayout(new GridLayout(10, 1));
   
      addComponents();

      confirmButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

             guest.phoneNumber = phoneNumberArea.getText();
             guest.name = nameArea.getText();
             String Gpwd = new String(pwdArea.getPassword());
             String GCpwd = new String(pwdCArea.getPassword());
             
             if(Gpwd.equals("")) {
                pwdLabel.setText("비밀번호 : 비밀번호를 입력하십시오");
                pwdLabel.setForeground(Color.red);
                return;
             }
             if(GCpwd.equals("")) {
                pwdCLabel.setText("비밀번호 확인: 비밀번호를 입력하십시오");
                pwdCLabel.setForeground(Color.red);
                return;
             }
             if(!GCpwd.equals(Gpwd)) {
                pwdCLabel.setText("비밀번호 확인: 비밀번호가 다릅니다");
                pwdCLabel.setForeground(Color.red);
                return;
             }
             String modifiedAddress = addressArea.getText();
             StringTokenizer st = new StringTokenizer(modifiedAddress);
             guest.address.clear();
             while (st.hasMoreTokens()) {
                guest.address.add(st.nextToken());
             }
             modifyAddress(MyPage.addressInfoLabels);
             
             
             Login.g = g; // 초기화
             g.setModifiedInfo(guest);
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
             String info = MyPage.addInfoToPane(sb, MyPage.memberLevel[ (int)(Math.random()*4)], Login.g);
             System.out.println(Login.g.name);
             MyPage.infoLabel.setText(info);
             
             dispose();
             return;
          }

       });
       setSize(450, 500);
       setVisible(true);
   }
}