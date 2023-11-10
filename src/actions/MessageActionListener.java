package actions;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import tcp.Client;
import treeview.ClientFrame;

public class MessageActionListener implements ActionListener {
	private JTextField messageBox;
	private Client client;	
	private JTextArea chatBox;
	
	public MessageActionListener(ClientFrame cf) {
		messageBox = cf.getMessageBox();
		client = cf.getClient();
		chatBox = cf.getChatBox();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (messageBox.getText().length() < 1 || messageBox.getText().trim().equals("Type a message...")) {
			return;
		} else if (messageBox.getText().equals("/clear")) {
			chatBox.setText("Cleared all messages\n");
			Timer t = new Timer(1500, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					chatBox.setText("");
				}
			});
			t.setRepeats(false);
			t.start();
			messageBox.setText("Type a message...");
		} else {
			client.getOutSocket().println("<" + client.getNickname() + ">:  " + messageBox.getText());
			messageBox.setText("Type a message...");
		}
		if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == messageBox)
			messageBox.setText("");
	}

}
