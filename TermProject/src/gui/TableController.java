package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import driver.CustomerManager;
import entity.Customer;
import facade.DataEngineInterface;
import view.CustomerView;

class JButtonRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    // If you press the button,
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JButton [] buttons = { new JButton("Detail"), new JButton("Cancel") };
        final String tableName = table.getName();

        switch (tableName) {
            case CustomerManager.RESTAURANT_TABLE:
                return buttons[0];
            case CustomerManager.RESERVE_TABLE:
                return buttons[1];
            default:
                return null;
        }
    }

    private boolean checkExistenceOnList(final String [] reservationInfo) {
        for (String [] info: Login.c.reservationList) {
            if (info[0].equals(reservationInfo[1]) && info[1].equals(reservationInfo[3]) && info[2].equals(reservationInfo[2]))
                return true;
        }
        return false;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton [] buttons = { new JButton("Detail"), new JButton("Cancel") };
        final String tableName = table.getName();

        switch (tableName) {
            case CustomerManager.RESTAURANT_TABLE:
                buttons[0].addActionListener(e -> {
                    int selectedRow = table.getSelectedRow();
                    String restaurantCode = (String) table.getModel().getValueAt(selectedRow, 0);
                    new DetailedRestaurant(restaurantCode);
                });
                return buttons[0];
            case CustomerManager.RESERVE_TABLE:
                TableController reservationTableController = CustomerView.tables[2].tableController;
                buttons[1].addActionListener(e -> {
                    int selectedRow = table.getSelectedRow();
                    String restaurantCode = (String) table.getModel().getValueAt(selectedRow, 0);
                    int choice = JOptionPane.showConfirmDialog(null,
                            "Do you want to cancel the reservation?", "Cancel",
                            JOptionPane.OK_CANCEL_OPTION);

                    String [] reservationInfo = { Login.c.getIdentifier(), restaurantCode,
                            (String) table.getModel().getValueAt(selectedRow, 2),
                            (String) table.getModel().getValueAt(selectedRow, 1) };

                    if (choice == JOptionPane.OK_OPTION && checkExistenceOnList(reservationInfo)) {
                        LocalDateTime reservedTime = LocalDateTime.parse(reservationInfo[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        if (reservedTime.isBefore(LocalDateTime.now()))
                            JOptionPane.showMessageDialog(null, "It already passed", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        else {
                            JOptionPane.showMessageDialog(null, "Cancellation process is completed", "Complete",
                                    JOptionPane.INFORMATION_MESSAGE);
                            reservationTableController.tableModel.removeRow(selectedRow);
                            reservationTableController.dataMgr.remove(reservationInfo);
                        }
                    }
                });
                return buttons[1];
            default:
                return null;
        }
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}

public class TableController implements ListSelectionListener {
    DefaultTableModel tableModel;
    JTable table;
    DataEngineInterface dataMgr;
    private String tableName;
    private JButtonRenderer renderer = new JButtonRenderer();

    TableController(String tableName) {
        this.tableName = tableName;
    }
    void setRenderer(final int columns) {
        table.getColumnModel().getColumn(columns - 1).setCellRenderer(renderer);
        table.getColumnModel().getColumn(columns - 1).setCellEditor(renderer);
    }

    static void setColumnsAttributes(TableColumnModel c, final String tableName, final int column) {
        int [] columnWidth = null;
        switch (tableName) {
            case CustomerManager.ORDER_TABLE:
                columnWidth = new int[] { 350, 80, 150, 115 };
                break;
            case CustomerManager.RESERVE_TABLE:
                columnWidth = new int[] { 200, 300, 280, 150, 165, 150 };
                break;
            case CustomerManager.RESTAURANT_TABLE:
                columnWidth = new int[] { 200, 250, 220, 200, 150 };
                break;
        }
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < column; i++) {
            c.getColumn(i).setPreferredWidth(columnWidth[i]);
            c.getColumn(i).setCellRenderer(renderer);
        }
    }

    void setTableSorter() {
        table.setAutoCreateRowSorter(true);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    boolean hasButtonName(String columnName) {
        return columnName.contains("Detail") || columnName.contains("Cancel");
    }

    public void init() {
        dataMgr = CustomerView.engine;
        tableModel = new DefaultTableModel(dataMgr.getColumnNames(tableName), 0) {
            public boolean isCellEditable(int row, int column) {
                return hasButtonName(tableModel.getColumnName(column))
                        ? (column == dataMgr.getColumnCount(tableName) - 1)
                        : (column == dataMgr.getColumnCount(tableName));
            }
        };
        loadData(Login.c);
        table = new JTable(tableModel);

        setTableSorter();
        table.setName(tableName);
        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(this);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.yellow);
        header.setReorderingAllowed(false);

        table.setRowHeight(30);
        table.setFont(new Font("Malgun Gothic", Font.PLAIN, 22));
        table.setPreferredScrollableViewportSize(new Dimension(1050, 225));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setColumnsAttributes(table.getColumnModel(), tableName, tableModel.getColumnCount());
        setRenderer(tableModel.getColumnCount());
    }

    void addAllRows(ResultSet rs) throws SQLException {
        tableModel.setRowCount(0);
        int columns = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String [] row = new String[columns];
            for (int i = 1; i <= columns; i++)
                row[i - 1] = rs.getString(i);
            tableModel.addRow(row);
            if (tableName.equals(CustomerManager.RESERVE_TABLE))
                Login.c.reservationList.add(row);
        }
    }

    void loadData(Customer c) {
        ResultSet rs = dataMgr.search(c, tableName);
        try { addAllRows(rs); } catch (SQLException e) { e.printStackTrace(); }
    }

    void loadAll(String keyword, final String tableName) {
        ResultSet rs = dataMgr.searchByKeyword(keyword, tableName);
        try { addAllRows(rs); } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        table.getSelectedRow();
    }
}