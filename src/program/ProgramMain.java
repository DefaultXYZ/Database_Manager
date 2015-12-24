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

@SuppressWarnings("serial")
public class ProgramMain extends JFrame {

	private JLayeredPane contentPane;
	private JPanel panel_1;
	private CardLayout container;
	private JPanel panel;
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
		
		panel = new JPanel();
		contentPane.add(panel, "panel");
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, "panel_1");
		panel_1.setLayout(null);
		
		container = (CardLayout) contentPane.getLayout();
	}
	
	private class MntmConnectActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
