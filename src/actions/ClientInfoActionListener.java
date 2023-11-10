package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import exceptions.ExceptionFrame;
import tcp.Server;
import treeview.ClientFrame;
import view.ClientInfo;

@SuppressWarnings("serial")
public class ClientInfoActionListener extends AbstractAction implements ActionListener {
	ClientInfo ci;
	JComboBox<Server> servers;
	JTextField tf;

	public ClientInfoActionListener(ClientInfo ci) {
		putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER);
		this.ci = ci;
		servers = ci.getServers();
		tf = ci.getTfUsername();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String username = tf.getText();
		
		if (username.equals("")) {
			new ExceptionFrame("Please enter username!");
			return;
		}
		if (username.contains("<") || username.contains(">")) {
			new ExceptionFrame("Username should not contain '<' or '>'!");
			return;
		}
		if (!Server.IsNameViable(username)) {
			new ExceptionFrame("Username is already taken!");
			return;
		}
		ci.setVisible(false);
		ci.dispose();
		
		ClientFrame cf = new ClientFrame(username);
		cf.setVisible(true);
	}
}

