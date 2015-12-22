package program;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PanelConnect extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public PanelConnect() {
		setLayout(null);
		
		JLabel label = new JLabel("User:");
		label.setBounds(12, 14, 73, 16);
		add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(85, 12, 114, 20);
		add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(85, 54, 114, 20);
		add(textField_1);
		
		JLabel label_1 = new JLabel("Password:");
		label_1.setBounds(12, 56, 73, 16);
		add(label_1);
		
		JButton button = new JButton("Confirm");
		button.setBounds(65, 107, 98, 26);
		add(button);
	}
}
