package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import facade.DataEngineInterface;
import facade.UIData;
import mgr.TeacherMgr;

class JButtonRenderer1 extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JButton button = new JButton("상세");
		button.setBackground(Color.YELLOW);
		return button;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JButton button = new JButton("상세");

		button.addActionListener(new ActionListener() { // 버튼 누르면 수강 취소 여부
			// 확인
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				String teacherCode = (String) table.getModel().getValueAt(row, 0);

				System.out.println("~~~~~~~~~~~");
				new DetailTeacher(teacherCode);
			}
		});
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
}

public class TeacherTableControll implements ListSelectionListener {

	DefaultTableModel tableModel;
	JTable table;
	int selectedIndex = -1;
	DataEngineInterface dataMgr;

	private JButtonRenderer1 renderer = new JButtonRenderer1();

	void setRenderer() {
		table.getColumnModel().getColumn(5).setCellRenderer(renderer);
		table.getColumnModel().getColumn(5).setCellEditor(renderer);
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

	static void setColumnsAttributes(TableColumnModel c, int column) {
		int[] mainWidth = { 50, 50, 245, 130, 225, 70 };

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		 renderer.setHorizontalAlignment(SwingConstants.CENTER);
		 
		for (int i = 0; i < column; i++) {
			c.getColumn(i).setPreferredWidth(mainWidth[i]);
			c.getColumn(i).setCellRenderer(renderer);
		}
	}

	@SuppressWarnings("serial")
	public void init() {
		TeacherMgr engine = new TeacherMgr();
		engine.readAll("teacher.txt");
		dataMgr = engine;
		String label[] = { "번호", "이름", "직업", "전화번호", "이메일", " " };
		tableModel = new DefaultTableModel(label, 0);

		/*
		 * tableModel = new DefaultTableModel(dataMgr.getColumnNames(), 0) { public
		 * boolean isCellEditable(int row, int column) { // (기존에 작성한) 셀 내용 수정X if
		 * (column == 4) return true; return false; } }; // 행의 개수 0
		 */
		loadData("");

		table = new JTable(tableModel);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);

		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.yellow);
		header.setReorderingAllowed(false);

		table.setPreferredScrollableViewportSize(new Dimension(950, 300));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);

		setTableSorter(table, tableModel);
		setColumnsAttributes(table.getColumnModel(), tableModel.getColumnCount());
		setRenderer();
	}

	void loadData(String kwd) {
		List<?> result = dataMgr.search(kwd);

		tableModel.setRowCount(0);

		for (Object m : result) {
			tableModel.addRow(((UIData) m).getUiTexts());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		table.getSelectedRow();
	}

}