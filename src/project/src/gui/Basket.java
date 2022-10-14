package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import entity.Lecture;
import gui.MyPage.JButtonRenderer;
import mgr.Main;


public class Basket extends JPanel {

	List<Lecture> submittedList = Login.g.lectureList;
	List<Lecture> basketList = Login.g.basketList;
	static DefaultTableModel basketTableModel;
	
	private JPanel topPane = new JPanel();
	private JPanel centerPane = new JPanel();
	private JPanel bottomPane = new JPanel();
	
	public Basket() {
		super(new BorderLayout());
		setPane();
	}
	
	Image getImage() {
		return new ImageIcon("./img/배너1.jpg").getImage()
				.getScaledInstance(900, 350, Image.SCALE_SMOOTH);
	}
	
	class JButtonRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) {
			
			JButton button = new JButton("수강신청");
			button.setBackground(Color.ORANGE);
			
			return button;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			
			JButton button = new JButton("수강신청");
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					String lectureName = (String) table.getModel().getValueAt(row, 1);

					int choice = JOptionPane.showConfirmDialog(null, lectureName + 
							" 과목을 수강하시겠습니까?", "수강 신청",
							JOptionPane.OK_CANCEL_OPTION);

					if (choice == JOptionPane.OK_OPTION &&
							checkExistenceOnList(submittedList, lectureName)) {
						
						JOptionPane.showMessageDialog(null, "신청되었습니다.", 
								"처리 완료", JOptionPane.INFORMATION_MESSAGE);
						Lecture lec = Main.lectureMgr.find(lectureName);
						submittedList.add(lec);
						MyPage.submittedTableModel.addRow(lec.getUiTexts());
						Basket.basketTableModel.removeRow(row);
						basketList.remove(lec);
					}
					else
						return;
				}
			});
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}
	}
	
	boolean checkExistenceOnList(List <Lecture> list, String code) {
		for (Lecture lec: list) {	//로그인된 회원의 수강목록을 순회하면서,
			if (lec.matches(code)) {		//동일한 과목코드를 가진 강좌가 있으면 추가하지 않고 리턴
				JOptionPane.showMessageDialog(null, "동일한 과목이 이미 신청되어 있습니다!", "실패", 
				JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	void setTableSorter(JTable t, DefaultTableModel tm) {
		t.setAutoCreateRowSorter(true);
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tm);
		t.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}
	
	void setTableOptions(JTable t) {
		Dimension d = new Dimension(900, 350);
		DefaultTableModel tm = (DefaultTableModel) t.getModel();
		TableColumnModel column = t.getColumnModel();
		
		t.setPreferredScrollableViewportSize(d);
		t.setFillsViewportHeight(true);
		LectureTableController.setColumnsAttributes(column, 
				tm.getColumnCount());
		
		RenderFunction render = () -> {
			JButtonRenderer renderer = new JButtonRenderer();
			column.getColumn(4).setCellRenderer(renderer);
			column.getColumn(4).setCellEditor(renderer);
		};
		render.setRenderer();
	}
	
	void addTableToPane() {
		int i = 0;

		final String[] labels = { "번호", "강의명", "강사명", "가격", "" };
		String[][] basketContents = new String[basketList.size()][];
		
		for (Lecture lec: basketList) {
			basketContents[i] = lec.getUiTexts();
			i++;
		}
		basketTableModel = new DefaultTableModel(basketContents, labels) {
			public boolean isCellEditable(int row, int column) {
				if (column == 4)
					return true;
				return false;
			}
		};
		
		JTable basketTable = new JTable(basketTableModel);
		basketTable.setFont(new Font("Malgun Gothic", Font.PLAIN, 22));
		basketTable.setRowHeight(30);
		basketTable.getTableHeader().setReorderingAllowed(false);
		setTableSorter(basketTable, basketTableModel);
		setTableOptions(basketTable);
		topPane.add(new JLabel("현재 장바구니 목록"), FlowLayout.LEFT);
		centerPane.add(new JScrollPane(basketTable));
	}
	
	void setPane() {
		add(topPane, BorderLayout.NORTH);
		add(centerPane, BorderLayout.CENTER);
		add(bottomPane, BorderLayout.SOUTH);
		
		topPane.setSize(950, 100);
		centerPane.setSize(950, 350);
		bottomPane.setSize(950, 400);
		//bottomPane.setLayout(new GridLayout(1,2));
		addTableToPane();
	
		bottomPane.add(new JLabel(new ImageIcon(getImage())));
		
	}
}
