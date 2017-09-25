package password;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class TablePassword {
	private JFrame frame = new JFrame("Table Password");
	private String[] columnNames = { "Password", "" };
	private Object[][] data;

	{
		ArrayList<String> passList = PassList.getPassList();
		data = new Object[passList.size()][2];

		for (int i = 0; i < passList.size(); i++) {
			data[i][0] = passList.get(i);
			data[i][1] = "copy " + (i + 1);
		}
	}

	private TableModel model = new DefaultTableModel(data, columnNames) {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column) {
			return column == 1;
		}
	};
	private JTable table = new JTable(model);

	public TablePassword() {
		createFrame();

	}

	private void createFrame() {
		table.getColumnModel().getColumn(1).setCellRenderer(new ClientsTableButtonRenderer());

		table.getColumnModel().getColumn(1).setCellEditor(new ClientsTableRenderer(new JCheckBox()));

		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(false);

		table.setRowHeight(30);
		table.setAlignmentX(655);
		table.setRowMargin(10);

		JScrollPane scroll = new JScrollPane(table);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(scroll);
		frame.setSize(300, 200);
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	class ClientsTableButtonRenderer extends JButton implements TableCellRenderer {
		public ClientsTableButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setForeground(Color.black);
			setBackground(UIManager.getColor("Button.background"));
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	public class ClientsTableRenderer extends DefaultCellEditor {
		private JButton button;
		private String label;
		private boolean clicked;
		private int row, col;
		private JTable table;

		public ClientsTableRenderer(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.table = table;
			this.row = row;
			this.col = column;

			button.setForeground(Color.black);
			button.setBackground(UIManager.getColor("Button.background"));
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			clicked = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (clicked) {
				StringSelection selection = new StringSelection(table.getValueAt(row, 0).toString());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
				JOptionPane.showMessageDialog(button, "Password Copied!");
			}
			clicked = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

}