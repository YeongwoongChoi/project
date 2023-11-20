package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import facade.DataEngineInterface;
import facade.UIData;
import view.CustomerView;

class JButtonRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	// 상세정보 버튼을 클릭하였을 때,

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JButton button = new JButton("상세정보");
		
		if (table.getModel().getValueAt(row, 2).equals("강형욱")) {
			button.setToolTipText("★강력추천★");
		}
		return button;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JButton button = new JButton("상세정보");
		
		 if (table.getModel().getValueAt(row, 2).equals("강형욱")) {
	         button.setToolTipText("★강력추천★");
		 }
		 
		 button.addActionListener(new ActionListener() { // 해당 버튼을 클릭하면 강좌코드에
														// 해당하는 강좌의 상세정보로 이동
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				String lectureCode = (String) table.getModel().getValueAt(row, 0);
				//new DetailLecture(lectureCode);
			}
		});
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}
}

public class TableController implements ListSelectionListener {

	DefaultTableModel tableModel;
	JTable table;
	int selectedIndex = -1;
	DataEngineInterface dataMgr;
	private JButtonRenderer renderer = new JButtonRenderer();

	void setRenderer() {
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		table.getColumnModel().getColumn(4).setCellEditor(renderer);
	}

	static void setColumnsAttributes(TableColumnModel c, int column) {
		int[] mainWidth = { 50, 380, 80, 70, 115, 105 };

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			 renderer.setHorizontalAlignment(SwingConstants.CENTER);
			 
		for (int i = 0; i < column; i++) {
			c.getColumn(i).setPreferredWidth(mainWidth[i]);
			c.getColumn(i).setCellRenderer(renderer);
		}
		
	}

	public void init(int flag) {
		dataMgr = CustomerView.engine;

		tableModel = new DefaultTableModel(dataMgr.getColumnNames(), 0) {
			public boolean isCellEditable(int row, int column) { // (기존에 작성한) 셀 내용 수정X
				if (column == 4)
					return true;
				return false;
			}
		}; // 행의 개수 0
		loadData("");

		table = new JTable(tableModel);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.black);
		header.setForeground(Color.yellow);
		header.setReorderingAllowed(false);      
		
		table.setRowHeight(30);
		table.setFont(new Font("Malgun Gothic", Font.PLAIN, 22));
		table.setPreferredScrollableViewportSize(new Dimension(950, 225));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
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