package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import entity.Guest;
import entity.Lecture;
import mgr.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
interface RenderFunction {
	abstract void setRenderer();
}

public class MyPage extends JPanel {
	static String[] memberLevel = { "VVIP", "VIP", "HELLO", "FAMILY" };
	List<Lecture> submittedList = Login.g.lectureList;
	List<Lecture> completedList = Login.g.completeLecture;
	public JButton logOut;
	private JButton changeInfo = new JButton("회원정보수정");
	static DefaultTableModel submittedTableModel;
	DefaultTableModel completedTableModel;

	MyPanel backPane = new MyPanel();
	JPanel topPane = new JPanel();
	JPanel centerPane = new JPanel();
	private JPanel centerRightPane = new JPanel();
	JPanel centerTopPane = new JPanel();
	JPanel addressPane = new JPanel();
	JPanel centerBottomPane = new JPanel();
	JPanel bottomPane = new JPanel();
	JPanel bottomUpperPane = new JPanel();
	JPanel bottomLowerPane = new JPanel();
	Image img = new ImageIcon("./img/teacherPageBack.png").getImage();
	static JLabel infoLabel = new JLabel();

	Guest g = Login.g;
	List<String> address = g.getAddress();
	static List<JLabel> addressInfoLabels = new ArrayList<>();

	class MyPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, 950, 500, this);
		}
	}

	class JButtonRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JButton button = new JButton("수강취소");
			button.setBackground(Color.YELLOW);

			return button;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			JButton button = new JButton("수강취소");

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					String lectureName = (String) table.getModel().getValueAt(row, 1);

					int choice = JOptionPane.showConfirmDialog(null, lectureName + " 과목 수강을 취소하시겠습니까?", "수강 취소",
							JOptionPane.OK_CANCEL_OPTION);

					if (choice == JOptionPane.OK_OPTION) {
						JOptionPane.showMessageDialog(null, "취소 처리가 완료되었습니다.", "처리 완료",
								JOptionPane.INFORMATION_MESSAGE);

						Lecture lec = Main.lectureMgr.find(lectureName);
						submittedList.remove(lec);
						MyPage.submittedTableModel.removeRow(row);
					} else
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

	public MyPage() {
		super(new BorderLayout());
		setPane();
	}

	Image getImage(String fileName) {
		return new ImageIcon("./img/" + fileName + ".png").getImage().getScaledInstance(275, 200, Image.SCALE_SMOOTH);
	}

	boolean checkExistenceOnList(List<Lecture> list, String code) {
		for (Lecture lec : list) { // 로그인된 회원의 수강목록을 순회하면서,
			if (lec.matches(code)) { // 동일한 과목코드를 가진 강좌가 있으면 추가하지 않고 리턴
				JOptionPane.showMessageDialog(null, "동일한 과목이 이미 신청되어 있습니다!", "실패", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	static String addInfoToPane(StringBuilder sb, String memberLevel, Guest g) {

		sb.append("<html><p>이름: ");
		sb.append(g.name);
		sb.append("</p>");

		sb.append("<p>등급: ");
		sb.append(memberLevel);
		sb.append("</p>");

		sb.append("<p>전화번호: ");
		sb.append(g.phoneNumber);
		sb.append("</p>");

		if (addressInfoLabels.size() == 0) {
			sb.append("<p>주소: ");

			for (String s : g.address) {
				sb.append(s + " ");
				addressInfoLabels.add(new JLabel(s));
			}
			sb.append("</p></html>");
		}
		return sb.toString();
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

	void addTableToPane() {
		int i = 0, j = 0;

		final String[] labels = { "번호", "강의명", "강사명", "가격", "" };

		String[][] submitContents = new String[submittedList.size()][];
		String[][] completedContents = new String[completedList.size()][];

		for (Lecture lec : submittedList) {
			submitContents[i] = lec.getUiTexts();
			i++;
		}

		for (Lecture lec : completedList) {
			completedContents[j] = lec.getUiTexts();
			j++;
		}

		submittedTableModel = new DefaultTableModel(submitContents, labels) {
			public boolean isCellEditable(int row, int column) {
				if (column == 4)
					return true;
				return false;
			}
		};

		completedTableModel = new DefaultTableModel(completedContents, labels) {
			public boolean isCellEditable(int row, int column) {
				if (column == 4)
					return true;
				return false;
			}
		};

		JTable submittedTable = new JTable(submittedTableModel);
		submittedTable.getTableHeader().setReorderingAllowed(false);
		setTableSorter(submittedTable, submittedTableModel);
		setTableOptions(submittedTable);

		JTable completedTable = new JTable(completedTableModel);
		completedTable.getTableHeader().setReorderingAllowed(false);
		setTableSorter(completedTable, completedTableModel);
		setTableOptions(completedTable);

		bottomUpperPane.add(new JLabel("수강 목록"), BorderLayout.PAGE_START);
		bottomUpperPane.add(new JScrollPane(submittedTable), BorderLayout.CENTER);

		bottomLowerPane.add(new JLabel("수강 기간 만료 강의"), BorderLayout.PAGE_START);
		bottomLowerPane.add(new JScrollPane(completedTable), BorderLayout.CENTER);
	}

	void setTableOptions(JTable t) {
		Dimension d = new Dimension(550, 150);
		DefaultTableModel tm = (DefaultTableModel) t.getModel();
		TableColumnModel column = t.getColumnModel();

		t.setFont(new Font("Malgun Gothic", Font.PLAIN, 22));
		t.setRowHeight(30);

		t.setPreferredScrollableViewportSize(d);
		t.setFillsViewportHeight(true);
		LectureTableController.setColumnsAttributes(column, tm.getColumnCount());

		RenderFunction f = () -> {
			JButtonRenderer renderer = new JButtonRenderer();
			column.getColumn(4).setCellRenderer(renderer);
			column.getColumn(4).setCellEditor(renderer);
		};
		f.setRenderer();
	}

	void setPane() {

		int i = (int) (Math.random() * 4);
		final String[] memberLevel = { "VVIP", "VIP", "HELLO", "FAMILY" };
		Image[] guestProfile = { getImage(memberLevel[0]), getImage(memberLevel[1]), getImage(memberLevel[2]),
				getImage(memberLevel[3]) };

		backPane.setLayout(new BorderLayout());
		backPane.setOpaque(false);
		add(backPane);
	  
	      
		backPane.add(topPane, BorderLayout.NORTH);
		backPane.add(centerPane, BorderLayout.WEST);
		backPane.add(bottomPane, BorderLayout.SOUTH);

		topPane.setLayout(new BorderLayout());
		topPane.setOpaque(false);

		centerTopPane.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		centerTopPane.setSize(450, 200);
		centerTopPane.setPreferredSize(new Dimension(650, 200));
		centerTopPane.setOpaque(false);

		centerRightPane.add(new JLabel(new ImageIcon(guestProfile[i])), BorderLayout.CENTER);
	    centerRightPane.setOpaque(false);    //회원 등급 사진
	    centerPane.add(centerRightPane, BorderLayout.EAST);

		bottomPane.setLayout(new BorderLayout(2, 2));
		bottomUpperPane.setLayout(new BorderLayout());
		bottomLowerPane.setLayout(new BorderLayout());
		bottomPane.add(bottomUpperPane, BorderLayout.CENTER);
		bottomPane.add(bottomLowerPane, BorderLayout.SOUTH);
		bottomPane.setOpaque(false);
		bottomUpperPane.setOpaque(false);
		bottomLowerPane.setOpaque(false);

		centerPane.setLayout(new BorderLayout());
		centerPane.setOpaque(false);
		logOut = new JButton(new ImageIcon("./img/logoutBtn.png"));
		GUIManager.setButtonProperties(logOut);

		JButton changeInfo = new JButton(new ImageIcon("./img/modifyBtn.png"));
		GUIManager.setButtonProperties(changeInfo);

		topPane.add(logOut, BorderLayout.WEST);
		topPane.add(changeInfo, BorderLayout.EAST);

		logOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				logOut.setIcon(new ImageIcon("./img/gologoutBtn.png"));
				// backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logOut.setIcon(new ImageIcon("./img/logoutBtn.png"));
				// backBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
				new ModifyInfo(g);
			}
		});

		topPane.setSize(950, 50);
		centerPane.setSize(950, 300);
		bottomPane.setSize(950, 500);
		centerBottomPane.setLayout(new GridLayout(2, 3));
		centerBottomPane.setSize(330, 100);
		centerPane.add(centerTopPane, BorderLayout.CENTER);
		centerPane.add(centerBottomPane, BorderLayout.SOUTH);
		centerPane.add(centerRightPane, BorderLayout.LINE_END);

		centerBottomPane.setLayout(new BorderLayout());
		centerBottomPane.setPreferredSize(new Dimension(100, 30));
		centerBottomPane.setOpaque(false);

		StringBuilder sb = new StringBuilder();
		String info = addInfoToPane(sb, memberLevel[i], this.g);

		centerTopPane.add(new JLabel(info));

		String[] s = { "수강 강의수", "수강완료 강의수", "장바구니 강의수" };

		JComboBox<String> cmb = new JComboBox<>(s);
		JLabel countLabel = new JLabel();

		int[] countLectures = { submittedList.size(), completedList.size(), g.basketList.size() };

		centerBottomPane.add(countLabel, BorderLayout.CENTER);

		centerBottomPane.add(cmb, BorderLayout.LINE_START);
		cmb.setSize(50, 30);

		cmb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cmb = (JComboBox<String>) e.getSource();
				int i = cmb.getSelectedIndex();
				countLabel.setText(countLectures[i] + "개");
			}
		});
		centerRightPane.setSize(350, 300);

		addTableToPane();
	}
}