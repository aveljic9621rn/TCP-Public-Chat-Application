package actions;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import treeview.ClientFrame;

public class MessageFocusListener implements FocusListener{
	private JTextField messageBox;
	private JButton btnSendMessage;

	public MessageFocusListener(ClientFrame cf) {
		btnSendMessage = cf.getSendMessage();
		messageBox = cf.getMessageBox();
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		if (messageBox.getText().trim().equals("Type a message...")) 
			messageBox.setText("");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (btnSendMessage.isFocusOwner() || messageBox.getText().trim().equals("")) 
			messageBox.setText("Type a message...");
	}
}
