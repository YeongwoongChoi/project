package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import entity.Guest;
import entity.Lecture;
import entity.Teacher;

public class TeacherMyPage extends JPanel {

   class MyPanel extends JPanel {

      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
      }
   }
   //static JLabel teacherInfoLabel = new JLabel();
   Teacher t = Login.t;

   List<Lecture> lectureList = Login.t.lectureList;
   static DefaultTableModel tableModel;

   ImageIcon icon = new ImageIcon("./img/teacherPageBack.png");
   Image img = icon.getImage();

   public JButton logOut;
   JButton changeInfo;
   // private JButton showChart = new JButton("차트 보기");
   MyPanel backPanel = new MyPanel();
   JLabel nameLabel = new JLabel("이름: ", JLabel.LEFT);
   JLabel name = new JLabel(Login.t.name, JLabel.LEFT);
   JLabel emailLabel = new JLabel("이메일: ");
   JLabel email = new JLabel(Login.t.email);
   JLabel careerLabel = new JLabel("경력");

   JPanel centerLeft = new JPanel(new FlowLayout());
   JPanel centerRight = new JPanel(new BorderLayout());

   PieChart pie;
   JPanel Container = new JPanel(new FlowLayout());

   private JPanel topPane = new JPanel(new BorderLayout());
   private JPanel centerPane = new JPanel(new BorderLayout());
   private JPanel bottomPane = new JPanel(new BorderLayout());
   
   List<String> address = t.getCareer();
   static JLabel teacherInfoLabel = new JLabel();
   static List<JLabel> careerInfoLabels = new ArrayList<>();

   public TeacherMyPage() {
      super(new BorderLayout());
      setPane();
      setSize(950, 850);
   }

   void setPane() {
      add(backPanel);
      backPanel.setLayout(new BorderLayout());
      backPanel.setOpaque(false);

      logOut = new JButton(new ImageIcon("./img/logoutBtn.png"));
      GUIManager.setButtonProperties(logOut);

      changeInfo = new JButton(new ImageIcon("./img/modifyBtn.png"));
      GUIManager.setButtonProperties(changeInfo);

      topPane.add(logOut, BorderLayout.WEST);
      topPane.add(changeInfo, BorderLayout.EAST);
      topPane.setOpaque(false);
      backPanel.add(topPane, BorderLayout.NORTH);

      JTextArea descriptionArea = new JTextArea(8, 25);
      setTextAreaOptions(descriptionArea);

      descriptionArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
      descriptionArea.append("이름: " + Login.t.name + "\n\n");
      descriptionArea.append("휴대폰 번호: " + Login.t.phoneNumber + "\n\n");
      descriptionArea.append("이메일: " + Login.t.email + "\n\n");
      descriptionArea.append("경력\n");

      for (String s : Login.t.careerList) {
         descriptionArea.append(s + "\n");
      }
      centerLeft.add(descriptionArea);
      centerLeft.setOpaque(false);
      centerRight.setOpaque(false);
      centerPane.add(centerLeft, BorderLayout.WEST);
      centerPane.setOpaque(false);

      JLabel imageLabel = new JLabel();

      Image img = getImage(Login.t.teacherCode);

      if (img != null) {
         imageLabel = new JLabel(new ImageIcon(img));
         centerLeft.add(imageLabel, BorderLayout.EAST);
      }

      centerPane.add(centerRight, BorderLayout.CENTER);
      pie = new PieChart();
      centerRight.add(pie, BorderLayout.CENTER);

      backPanel.add(centerPane);

      addTableToPane();
      backPanel.add(bottomPane, BorderLayout.SOUTH);


      logOut.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            logOut.setIcon(new ImageIcon("./img/gologoutBtn.png"));

         }

         @Override
         public void mouseExited(MouseEvent e) {
            logOut.setIcon(new ImageIcon("./img/logoutBtn.png"));

         }

         @Override
         public void mousePressed(MouseEvent e) {
            logOut.setIcon(new ImageIcon("./img/gologoutBtn.png"));

         }
      });

      changeInfo.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            changeInfo.setIcon(new ImageIcon("./img/goModifyBtn.png"));
            // backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            changeInfo.setIcon(new ImageIcon("./img/modifyBtn.png"));
            // backBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            changeInfo.setIcon(new ImageIcon("./img/goModifyBtn.png"));

         }
      });
      changeInfo.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new ModifyTeacherInfo(t);
         }
      });

      centerRight.setSize(400, 600);
      setSize(950, 850);
      
      

   }
   
   static String addTeacherInfoToPane(StringBuilder sb, Teacher t) {
      sb.append("<html><p>이름: ");
      sb.append(t.name);
      sb.append("</p>");

      sb.append("<p>휴대폰 번호: ");
      sb.append(t.phoneNumber);
      sb.append("</p>");

      sb.append("<p>이메일: ");
      sb.append(t.email);
      sb.append("</p>");

      if (careerInfoLabels.size() == 0) {
         sb.append("<p>주소: ");

         for (String s : t.careerList) {
            sb.append(s + " ");
            careerInfoLabels.add(new JLabel(s));
         }
         sb.append("</p></html>");
      }
      return sb.toString();
   }

   void addTableToPane() {
      int i = 0;

      final String[] labels = { "번호", "강의명", "강사명", "가격", "" };
      String[][] contents = new String[lectureList.size()][];

      for (Lecture lec : lectureList) {
         contents[i] = lec.getUiTexts();
         i++;
      }

      tableModel = new DefaultTableModel(contents, labels) {
         public boolean isCellEditable(int row, int column) {
            if (column == 4)
               return true;
            return false;
         }
      };

      bottomPane.add(new JLabel("담당 강의 목록"), BorderLayout.NORTH);

      JTable lectureTable = new JTable(tableModel);
      lectureTable.setRowHeight(30);
      setTableSorter(lectureTable);

      lectureTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
      lectureTable.setFillsViewportHeight(true);
      LectureTableController.setColumnsAttributes(lectureTable.getColumnModel(), tableModel.getColumnCount());

      RenderFunction render = () -> {
         JButtonRenderer renderer = new JButtonRenderer();
         lectureTable.getColumnModel().getColumn(4).setCellRenderer(renderer);
         lectureTable.getColumnModel().getColumn(4).setCellEditor(renderer);
      };
      render.setRenderer();

      bottomPane.add(new JScrollPane(lectureTable), BorderLayout.CENTER);
   }

   void setTableSorter(JTable t) {
      t.setAutoCreateRowSorter(true);
      TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
      t.setRowSorter(sorter);

      List<RowSorter.SortKey> sortKeys = new ArrayList<>();
      sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

      sorter.setSortKeys(sortKeys);
      sorter.sort();
   }

   Image getImage(String teacherCode) {
      return new ImageIcon("./teacher/"+teacherCode + ".jpg").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
   }

   void setTextAreaOptions(JTextArea t) {
      t.setLineWrap(true);
      t.setWrapStyleWord(true);
      t.setOpaque(false);
      t.setEditable(false);
   }

   class JButtonRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
      // private List<Lecture> list = Login.t.lectureList;

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

         JButton button = new JButton("강의수정");
         button.setBackground(Color.YELLOW);
         return button;
      }

      @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
            int column) {
         JButton button = new JButton("강의 수정");

         button.addActionListener(new ActionListener() { // 버튼 누르면 수강 취소 여부
            // 확인
            public void actionPerformed(ActionEvent e) {
               int row = table.getSelectedRow();

               String lectureCode = (String) table.getModel().getValueAt(row, 0);

               int choice = JOptionPane.showConfirmDialog(null, "해당 과목을 수정 하시겠습니까?", "강의수정",
                     JOptionPane.OK_CANCEL_OPTION);

               switch (choice) {
               case JOptionPane.OK_OPTION:
                  // ModifyLectureData.StartMusic();
                  new ModifyLectureData(lectureCode);
               }
            }
         });
         return button;
      }

      @Override
      public Object getCellEditorValue() {
         return null;
      }
   }
}