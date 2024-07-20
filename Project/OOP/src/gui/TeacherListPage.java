package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import entity.Lecture;
import entity.Teacher;
import facade.DataEngineInterface;
import gui.MyPage.JButtonRenderer;
import mgr.Main;
import mgr.Manager;
import mgr.TeacherMgr;

public class TeacherListPage extends JPanel{
   private static final long serialVersionUID = 1L;
   static TeacherTableControll tableController;
   static DefaultTableModel tableModel;
   List<Teacher> teacherList = Main.teacherMgr.mList;
   JPanel topPane = new JPanel();
   JPanel bottomPane = new JPanel();

      public TeacherListPage() {
      super(new BorderLayout());
       addComponentsToPane();
   }

      public void addComponentsToPane() {
         tableController = new TeacherTableControll();
         tableController.init();
         JScrollPane center = new JScrollPane(tableController.table);
         add(center, BorderLayout.CENTER);
         setupTopPane();
      }
      
      Image getImage() {
    	  return new ImageIcon("./img/배너2.jpg").getImage()
    			  .getScaledInstance(900, 350, Image.SCALE_SMOOTH);
      }
 
      void setupTopPane() {
         
         JTextField kwdTextField = new JTextField("", 20);
         topPane.add(kwdTextField, BorderLayout.LINE_START);


 		JButton search = new JButton(new ImageIcon("./img/searchBtn.png"));
 		search.setBorderPainted(false);
 		search.setFocusPainted(false);
 		search.setContentAreaFilled(false);
 		JButton initializeTable = new JButton(new ImageIcon("./img/resetBtn.png"));
 		initializeTable.setBorderPainted(false);
 		initializeTable.setFocusPainted(false);
 		initializeTable.setContentAreaFilled(false);

         topPane.add(search, BorderLayout.EAST);
         topPane.add(initializeTable, BorderLayout.LINE_END);

         search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String kwd = kwdTextField.getText();

               tableController.loadData(kwd);
            }
         });
         
 		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				search.setIcon(new ImageIcon("./img/searchBtn_down.png"));
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				search.setIcon(new ImageIcon("./img/searchBtn.png"));
				
			}
			@Override
			public void mousePressed(MouseEvent e){
				search.setIcon(new ImageIcon("./img/searchBtn_down.png"));
				
			}			
		});


         initializeTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               String kwd;
                  kwdTextField.setText("");
                  kwd = kwdTextField.getText();
                  tableController.loadData(kwd);
               }
         });
         
         initializeTable.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseEntered(MouseEvent e) {
 				initializeTable.setIcon(new ImageIcon("./img/resetBtn_down.png"));
 				
 			}
 			@Override
 			public void mouseExited(MouseEvent e) {
 				initializeTable.setIcon(new ImageIcon("./img/resetBtn.png"));
 				
 			}
 			@Override
 			public void mousePressed(MouseEvent e){
 				initializeTable.setIcon(new ImageIcon("./img/resetBtn_down.png"));
 				
 			}			
 		});

         add(topPane, BorderLayout.PAGE_START);
         add(bottomPane, BorderLayout.PAGE_END);
         bottomPane.setSize(950, 400);
         bottomPane.add(new JLabel(new ImageIcon(getImage())));
         setSize(950, 850);
      }
}