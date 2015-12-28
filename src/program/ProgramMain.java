package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JLayeredPane;

import java.awt.CardLayout;
import javax.swing.JTextField;

import database.DatabaseManager;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.util.Vector;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@SuppressWarnings("serial")
public class ProgramMain extends JFrame {

	private final static String TAG_CONNECT = "CONNECT";
	private final static String TAG_DATABASES = "DATABASES";
	private final static String TAG_TABLES = "TABLES";
	private final static String TAG_ADD_TABLE = "ADD_TABLE";
	
	private JLayeredPane panel_slider;
	private CardLayout container;
	private JPanel panel_connect;
	private JTextField tF_pass;
	private DatabaseManager databaseManager;  
	private JTextField tF_user;
	private JPanel contentPane;
	private JLabel lbl_status;
	private JPanel panel_statusBar;
	private JList<String> list_databases;
	private JPanel panel_databases;
	private JPanel panel_tables;
	private JList<String> list_tables;
	private JMenuItem mnIt_connect;
	private JMenuItem mnIt_databases;
	private JMenuItem mnIt_tables;
	private JButton btn_backTable;
	private JPanel panel_addTable;
	private JButton btn_addRow;
	private DefaultTableModel modelNewDBTable;
	private JTable table_createTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProgramMain frame = new ProgramMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProgramMain() {
		setResizable(false);
		setTitle("Default - Database Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 450);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mn_file = new JMenu("File");
		menuBar.add(mn_file);
		
		mnIt_connect = new JMenuItem("Connect...");
		mnIt_connect.addActionListener(new MntmConnectActionListener());
		mn_file.add(mnIt_connect);
		
		mnIt_databases = new JMenuItem("Databases");
		mnIt_databases.addActionListener(new MnIt_databasesActionListener());
		mnIt_databases.setEnabled(false);
		mn_file.add(mnIt_databases);
		
		mnIt_tables = new JMenuItem("Tables");
		mnIt_tables.addActionListener(new MnIt_tablesActionListener());
		mnIt_tables.setEnabled(false);
		mn_file.add(mnIt_tables);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		// Contains all panels
		panel_slider = new JLayeredPane();
		panel_slider.setLayout(new CardLayout(0, 0));
		contentPane.add(panel_slider);
		
		panel_connect = new JPanel();
		panel_connect.addComponentListener(new Panel_connectComponentListener());
		panel_slider.add(panel_connect, TAG_CONNECT);
		panel_connect.setLayout(null);
		
		tF_user = new JTextField();
		tF_user.setBounds(246, 122, 114, 20);
		panel_connect.add(tF_user);
		tF_user.setColumns(10);
		
		JLabel lbl_username = new JLabel("Username:");
		lbl_username.setBounds(141, 124, 87, 16);
		panel_connect.add(lbl_username);
		
		tF_pass = new JTextField();
		tF_pass.setBounds(246, 154, 114, 20);
		panel_connect.add(tF_pass);
		tF_pass.setColumns(10);
		
		JLabel lbl_password = new JLabel("Password:");
		lbl_password.setBounds(141, 156, 87, 16);
		panel_connect.add(lbl_password);
		
		JButton btn_connect = new JButton("Connect");
		btn_connect.addActionListener(new BtnConnectActionListener());
		btn_connect.setBounds(192, 277, 98, 26);
		panel_connect.add(btn_connect);
		
		panel_databases = new JPanel();
		panel_databases.addComponentListener(new Panel_databasesComponentListener());
		panel_slider.add(panel_databases, TAG_DATABASES);
		panel_databases.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 93, 181, 263);
		panel_databases.add(scrollPane);
		
		list_databases = new JList<String>();
		list_databases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list_databases);
		
		JLabel lbl_databases = new JLabel("Databases");
		lbl_databases.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_databases.setBounds(12, 65, 89, 16);
		panel_databases.add(lbl_databases);
		
		JButton btn_create = new JButton("Create");
		btn_create.addActionListener(new BtnCreateActionListener());
		btn_create.setBounds(205, 44, 98, 26);
		panel_databases.add(btn_create);
		
		JButton btn_drop = new JButton("Drop");
		btn_drop.addActionListener(new Btn_dropActionListener());
		btn_drop.setBounds(315, 44, 98, 26);
		panel_databases.add(btn_drop);
		
		JButton btn_use = new JButton("Use");
		btn_use.addActionListener(new Btn_useActionListener());
		btn_use.setBounds(205, 82, 98, 26);
		panel_databases.add(btn_use);
		
		JButton btn_refresh = new JButton("Refresh");
		btn_refresh.addActionListener(new Btn_refreshActionListener());
		btn_refresh.setBounds(315, 82, 98, 26);
		panel_databases.add(btn_refresh);
		
		JButton btn_backDb = new JButton("Back");
		btn_backDb.addActionListener(new Btn_backDbActionListener());
		btn_backDb.setBounds(12, 12, 98, 26);
		panel_databases.add(btn_backDb);
		
		// For accessing to all panels
		container = (CardLayout) panel_slider.getLayout();
		
		panel_tables = new JPanel();
		panel_tables.addComponentListener(new Panel_tablesComponentListener());
		panel_slider.add(panel_tables, TAG_TABLES);
		panel_tables.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 81, 165, 275);
		panel_tables.add(scrollPane_1);
		
		list_tables = new JList<String>();
		scrollPane_1.setViewportView(list_tables);
		
		JLabel lbl_tables = new JLabel("Tables");
		lbl_tables.setBounds(12, 53, 55, 16);
		panel_tables.add(lbl_tables);
		
		btn_backTable = new JButton("Back");
		btn_backTable.addActionListener(new Btn_backActionListener());
		btn_backTable.setBounds(12, 12, 98, 26);
		panel_tables.add(btn_backTable);
		
		JButton btn_createTable = new JButton("Create");
		btn_createTable.addActionListener(new Btn_createTableActionListener());
		btn_createTable.setBounds(199, 12, 98, 26);
		panel_tables.add(btn_createTable);
		
		JButton btnDrop = new JButton("Drop");
		btnDrop.addActionListener(new BtnDropActionListener());
		btnDrop.setBounds(324, 12, 98, 26);
		panel_tables.add(btnDrop);
		
		panel_addTable = new JPanel();
		panel_addTable.addComponentListener(new Panel_addTableComponentListener());
		panel_slider.add(panel_addTable, TAG_ADD_TABLE);
		panel_addTable.setLayout(null);
		
		btn_addRow = new JButton("Add row");
		btn_addRow.addActionListener(new Btn_addRowActionListener());
		btn_addRow.setBounds(134, 13, 98, 26);
		panel_addTable.add(btn_addRow);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(12, 50, 470, 316);
		panel_addTable.add(scrollPane_2);
		
		
		table_createTable = new JTable();
		table_createTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		modelNewDBTable = new DefaultTableModel();
		modelNewDBTable.addColumn("Name");
		modelNewDBTable.addColumn("Type");
		modelNewDBTable.addColumn("Size");
		table_createTable.setModel(modelNewDBTable);
		
		// Column Name
		TableColumn columnName = table_createTable.getColumnModel().getColumn(0);
		columnName.setCellEditor(new DefaultCellEditor(new JTextField()));
		// Column Type
		TableColumn columnType = table_createTable.getColumnModel().getColumn(1);
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.addItem("CHAR");
		comboBox.addItem("INT");
		columnType.setCellEditor(new DefaultCellEditor(comboBox));
		// Column Size
		TableColumn columnSize = table_createTable.getColumnModel().getColumn(2);
		columnSize.setCellEditor(new DefaultCellEditor(new JTextField()));
		modelNewDBTable.addRow(new Object[]{});
		table_createTable.setRowHeight(20);
		
		scrollPane_2.setViewportView(table_createTable);
		
		JButton btn_createNewTable = new JButton("Create");
		btn_createNewTable.addActionListener(new Btn_createNewTableActionListener());
		btn_createNewTable.setBounds(384, 13, 98, 26);
		panel_addTable.add(btn_createNewTable);
		
		JButton btn_deleteRow = new JButton("Delete row");
		btn_deleteRow.addActionListener(new Btn_deleteRowActionListener());
		btn_deleteRow.setBounds(254, 13, 98, 26);
		panel_addTable.add(btn_deleteRow);
		
		JButton btn_backToTables = new JButton("Back");
		btn_backToTables.addActionListener(new Btn_backToTablesActionListener());
		btn_backToTables.setBounds(12, 15, 89, 23);
		panel_addTable.add(btn_backToTables);
		
		panel_statusBar = new JPanel();
		panel_statusBar.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		contentPane.add(panel_statusBar);
		FlowLayout fl_panel_statusBar = new FlowLayout(FlowLayout.LEFT, 5, 0);
		panel_statusBar.setLayout(fl_panel_statusBar);
		
		lbl_status = new JLabel("Disconnected");
		lbl_status.setHorizontalAlignment(SwingConstants.CENTER);
		panel_statusBar.add(lbl_status);
		
	}
	
	// Try to connect
	private class BtnConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// Class for managing databases
			databaseManager = new DatabaseManager(tF_user.getText(),
					tF_pass.getText());
			databaseManager.connect();
			// If connected, get all databases
			if(databaseManager.isConnected()) {
				lbl_status.setText("Connection is successful");
				container.show(panel_slider, TAG_DATABASES);
			} else {
				lbl_status.setText("Connection is failed");
			}
		}
	}
	
	// Return to Connection Panel
	private class MntmConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			container.show(panel_slider, TAG_CONNECT);
		}
	}
	
	// Creating new database
	private class BtnCreateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// Enter name for database
			String newName = JOptionPane.showInputDialog("Enter new name:");
			if(newName != null) {
				// Execute query
				databaseManager.createDB(newName);
				// Refresh list of databases
				fillListDatabases();
				// Show status
				lbl_status.setText("Database " + newName + " created");
			}
		}
	}
	
	// Deleting database
	private class Btn_dropActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!list_databases.isSelectionEmpty()) {
				// Get selected database
				String db = list_databases.getSelectedValue();
				// Execute query
				databaseManager.dropDB(db);
				// Refresh
				fillListDatabases();
				// Status
				lbl_status.setText("Database " + db + " deleted");
			}
		}
	}
	
	// Using database
	private class Btn_useActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!list_databases.isSelectionEmpty()) {
				// Get selected database
				String db = list_databases.getSelectedValue();
				// Execute query
				databaseManager.useDB(db);
				container.show(panel_slider, TAG_TABLES);
				lbl_status.setText("Using database " + db);
			}
		}
	}
	
	// Refresh List of Databases
	private class Btn_refreshActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			fillListDatabases();
			lbl_status.setText("List of databases refreshed");
		}
	}
	
	// Back to Databases
	private class Btn_backActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			container.show(panel_slider, TAG_DATABASES);
			lbl_status.setText("Connected");
		}
	}
	
	// Back to Connect
	private class Btn_backDbActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			container.show(panel_slider, TAG_CONNECT);
		}
	}
	
	// Create new database Table
	private class Btn_createTableActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			container.show(panel_slider, TAG_ADD_TABLE);
		}
	}
	
	// Add new row
	private class Btn_addRowActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			modelNewDBTable.addRow(new Object[]{});
		}
	}
	
	// Creating table from JTable
	private class Btn_createNewTableActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// Get table name
			String tableName = JOptionPane.showInputDialog("Enter table name:");
			
			Vector<String> rowName = new Vector<>();
			Vector<String> rowType = new Vector<>();
			Vector<String> rowSize = new Vector<>();
			// Get data from table
			for(int i = 0; i < table_createTable.getRowCount(); ++i) {
				rowName.addElement(table_createTable.getValueAt(i, 0).toString());
				rowType.addElement(table_createTable.getValueAt(i, 1).toString());
				rowSize.addElement(table_createTable.getValueAt(i, 2).toString());
			}
			// Execute query
			databaseManager.createTable(tableName, rowName, rowType, rowSize);
			// Show status
			lbl_status.setText("Table " + tableName + " was created");
			// Show Panel Tables
			container.show(panel_slider, TAG_TABLES);
		}
	}
	
	// Delete row
	private class Btn_deleteRowActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			modelNewDBTable.removeRow(table_createTable.getSelectedRow());
		}
	}
	
	// Drop table
	private class BtnDropActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String table = list_tables.getSelectedValue();
			databaseManager.dropTable(table);
			fillListTables();
			lbl_status.setText("Table " + table + " was deleted");
		}
	}
	
	// Menu Item - Database
	private class MnIt_databasesActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			container.show(panel_slider, TAG_DATABASES);
			mnIt_tables.setEnabled(false);
		}
	}
	
	// Menu Item - Tables
	private class MnIt_tablesActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			container.show(panel_slider, TAG_TABLES);
			mnIt_tables.setEnabled(true);
		}
	}
	
	// Back From Creating new table to Tables
	private class Btn_backToTablesActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Show Panel Tables
			container.show(panel_slider, TAG_TABLES);
		}
	}
	
	// When Panel Create new Table Hide
	private class Panel_addTableComponentListener extends ComponentAdapter {
		@Override
		public void componentHidden(ComponentEvent arg0) {
			// Clear table
			for(int i = 0; i < modelNewDBTable.getRowCount(); ++i) {
				modelNewDBTable.removeRow(i);
			}
			modelNewDBTable.addRow(new Object[]{});
		}
	}
	
	// When Panel Tables Shown
	private class Panel_tablesComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			// Refresh list of Tables
			fillListTables();
			mnIt_tables.setEnabled(true);
		}
	}
	
	// When Panel Connection Shown
	private class Panel_connectComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			// Disconnect
			databaseManager.disconnect();
			// Show status
			lbl_status.setText("Disconnected");
			// Change enabling in menu
			mnIt_databases.setEnabled(false);
			mnIt_tables.setEnabled(false);
		}
	}
	
	// When Panel Databases shown
	private class Panel_databasesComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			fillListDatabases();
			mnIt_tables.setEnabled(false);
			mnIt_databases.setEnabled(true);
		}
	}
	
	// Getting list of databases
	private void fillListDatabases() {
		// For sending list to JList
		DefaultListModel<String> model = new DefaultListModel<>();
		// Contains database list
		Vector<String> databases = databaseManager.showDB();
		if(databases != null) {
			// Getting list of databases
			for(String db : databases) {
				model.addElement(db);
			}
		}
		// JList contains list model
		list_databases.setModel(model);
	}
	
	// Getting list of tables
	private void fillListTables() {
		if(databaseManager.isDatabaseUsed()) {
			DefaultListModel<String> model = new DefaultListModel<>();
			Vector<String> tables = databaseManager.showTables();
			if(tables != null) {
				for(String table : tables) {
					model.addElement(table);
				}
			}
			list_tables.setModel(model);
		}
	}
}
