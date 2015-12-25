package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JTextField;

import database.DatabaseManager;

import javax.swing.JLabel;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ProgramMain extends JFrame {

	private JLayeredPane contentPane;
	private CardLayout container;
	private JPanel panel_Connect;
	private JTextField tF_pass;
	private DatabaseManager databaseManager;  
	private JTextField tF_user;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 450);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmConnect = new JMenuItem("Connect...");
		mntmConnect.addActionListener(new MntmConnectActionListener());
		mnFile.add(mntmConnect);
		
		contentPane = new JLayeredPane();
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		panel_Connect = new JPanel();
		contentPane.add(panel_Connect, "panel_Connect");
		panel_Connect.setLayout(null);
		
		tF_user = new JTextField();
		tF_user.setBounds(246, 122, 114, 20);
		panel_Connect.add(tF_user);
		tF_user.setColumns(10);
		
		JLabel lblUsename = new JLabel("Usename:");
		lblUsename.setBounds(141, 124, 87, 16);
		panel_Connect.add(lblUsename);
		
		tF_pass = new JTextField();
		tF_pass.setBounds(246, 154, 114, 20);
		panel_Connect.add(tF_pass);
		tF_pass.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(141, 156, 87, 16);
		panel_Connect.add(lblPassword);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new BtnConnectActionListener());
		btnConnect.setBounds(192, 277, 98, 26);
		panel_Connect.add(btnConnect);
		
		JPanel panel_Tables = new JPanel();
		contentPane.add(panel_Tables, "panel_Tables");
		panel_Tables.setLayout(null);
		
		container = (CardLayout) contentPane.getLayout();
		
	}
	
	private class MntmConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			databaseManager.disconnect();
			container.show(contentPane, "panel_Connect");
		}
	}
	private class BtnConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			databaseManager = new DatabaseManager(tF_user.getText(),
					tF_pass.getText());
			databaseManager.connect();
			container.show(contentPane, "panel_Tables");
		}
	}
}
