package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import entity.Lecture;
import entity.Teacher;
import mgr.Main;

public class DetailTeacher extends JFrame implements Frame {
   static DefaultTableModel tableModel;

   Teacher t;
   List<Lecture> lectureList;
   JPanel topPane = new JPanel();
   JPanel centerPane = new JPanel();
   JPanel bottomPane = new JPanel();
   
   JLabel nameLabel = new JLabel("이름: ");
   JLabel careerLabel = new JLabel("경력 ");
  
   public DetailTeacher(String code) {
      t = (Teacher) Main.teacherMgr.find(code);
      JLabel name = new JLabel(t.name);
      lectureList = t.lectureList;
      
      setTitle("강의 상세정보");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      GUIManager.frameLocation(this);
      
      add(topPane, BorderLayout.NORTH);
      add(centerPane, BorderLayout.WEST);
      add(bottomPane, BorderLayout.SOUTH);
      
      topPane.setLayout(new BorderLayout(5, 5));
      centerPane.setLayout(new GridLayout(2, 1));
      bottomPane.setLayout(new BorderLayout(2, 2));

      centerPane.setLayout(new BorderLayout(2, 2));
      JPanel namePan = new JPanel();
      JPanel careerPan = new JPanel();
      JLabel careerLabel = new JLabel();
      
      JPanel careerPic = new JPanel();//사진
      careerPic.add(careerLabel);
      
      centerPane.add(namePan, BorderLayout.NORTH);
      centerPane.add(careerPan, BorderLayout.WEST);   
      centerPane.add(careerPic, BorderLayout.EAST);
      namePan.add(nameLabel, BorderLayout.NORTH);
      namePan.add(name, BorderLayout.EAST);
      careerPan.add(careerLabel, BorderLayout.NORTH);
      JTextArea careerArea = new JTextArea(); // JTextArea 생성

      Image img = getImage(t.teacherCode); 
      if (img != null) {
            careerLabel = new JLabel(new ImageIcon(img));
            centerPane.add(careerLabel);
         }
      
      for (int i = 0; i < t.careerList.size(); i++) {

         careerArea.append(t.careerList.get(i) + "\n");
      }

      setTextAreaOptions(careerArea);
      careerArea.setSize(600, 500);
      careerArea.setEditable(false); // 실행시 JtextArea edit 금지 (글을 쓸 수 없음) true면 가능
      centerPane.add(careerArea, BorderLayout.SOUTH);
      
      JPanel lectureContainer = new JPanel();
      
      setContentPane(lectureContainer);

      addComponents(lectureContainer);
      setSize(950, 850);
      setVisible(true);
      setResizable(false);
      setLocationRelativeTo(null);
      addTableToPane();
   }
   
   Image getImage(String lectureCode) {
      return new ImageIcon("teacher/" + t.teacherCode + ".jpg").getImage().getScaledInstance(200, 200,
            Image.SCALE_SMOOTH);
   }
   
   void setTextAreaOptions(JTextArea text) {
      text.setLineWrap(true);
      text.setWrapStyleWord(true);
      text.setOpaque(false);
      text.setEditable(false);
   }
   
   @Override
   public void addComponents(JPanel panel) {
      // TODO Auto-generated method stub
      panel.add(topPane);
      panel.add(centerPane);
      panel.add(bottomPane);
      }
   
   void addTableToPane() {
      int i = 0;

      final String[] labels = { "번호", "강의명", "강사명", "가격" };
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
      bottomPane.add(new JLabel("수강 목록"), BorderLayout.NORTH);

      JTable lectureTable = new JTable(tableModel);

      lectureTable.setRowHeight(30);
      setTableSorter(lectureTable);

      //lectureTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
      lectureTable.setPreferredScrollableViewportSize(new Dimension(930,200));
      lectureTable.setFillsViewportHeight(true);
      LectureTableController.setColumnsAttributes(lectureTable.getColumnModel(), tableModel.getColumnCount());
      
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
}