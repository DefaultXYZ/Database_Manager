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
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.util.Vector;

@SuppressWarnings("serial")
public class ProgramMain extends JFrame {

	private final static String TAG_CONNECT = "CONNECT";
	private final static String TAG_DATABASES = "DATABASES";
	private final static String TAG_TABLES = "TABLES";
	
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
	private JButton btn_back;
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
		mnIt_databases.setEnabled(false);
		mn_file.add(mnIt_databases);
		
		mnIt_tables = new JMenuItem("Tables");
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
		panel_slider.add(panel_databases, TAG_DATABASES);
		panel_databases.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 47, 181, 309);
		panel_databases.add(scrollPane);
		
		list_databases = new JList<String>();
		list_databases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list_databases);
		
		JLabel lbl_databases = new JLabel("Databases");
		lbl_databases.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_databases.setBounds(12, 19, 89, 16);
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
		
		// For accessing to all panels
		container = (CardLayout) panel_slider.getLayout();
		
		panel_tables = new JPanel();
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
		
		btn_back = new JButton("Back");
		btn_back.addActionListener(new Btn_backActionListener());
		btn_back.setBounds(12, 12, 98, 26);
		panel_tables.add(btn_back);
		
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
			lbl_status.setText("Connecting...");
			databaseManager = new DatabaseManager(tF_user.getText(),
					tF_pass.getText());
			databaseManager.connect();
			// If connected, get all databases
			if(databaseManager.isConnected()) {
				lbl_status.setText("Connection is successful");
				fillListDatabases();
				mnIt_databases.setEnabled(true);
				container.show(panel_slider, TAG_DATABASES);
			} else {
				lbl_status.setText("Connection is failed");
			}
		}
	}
	
	// Return to Connection Panel
	private class MntmConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			databaseManager.disconnect();
			lbl_status.setText("Disconnected");
			mnIt_databases.setEnabled(false);
			mnIt_tables.setEnabled(false);
			container.show(panel_slider, TAG_CONNECT);
		}
	}
	
	// Creating new database
	private class BtnCreateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String newName = JOptionPane.showInputDialog("Enter new name:");
			if(newName != null) {
				databaseManager.createDB(newName);
				fillListDatabases();
				lbl_status.setText("Database " + newName + " created");
			}
		}
	}
	
	// Deleting database
	private class Btn_dropActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!list_databases.isSelectionEmpty()) {
				String db = list_databases.getSelectedValue();
				databaseManager.dropDB(db);
				fillListDatabases();
				lbl_status.setText("Database " + db + " deleted");
			}
		}
	}
	
	// Using database
	private class Btn_useActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(!list_databases.isSelectionEmpty()) {
				String db = list_databases.getSelectedValue();
				databaseManager.useDB(db);
				fillListTables();
				mnIt_tables.setEnabled(true);
				container.show(panel_slider, TAG_TABLES);
				lbl_status.setText("Using database " + db);
			}
		}
	}
	
	// Refresh List of Databases
	private class Btn_refreshActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			fillListDatabases();
		}
	}
	
	// Back to Databases
	private class Btn_backActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			mnIt_tables.setEnabled(false);
			list_tables.clearSelection();
			fillListDatabases();
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
