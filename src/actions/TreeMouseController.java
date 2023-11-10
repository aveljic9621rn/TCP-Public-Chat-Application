package actions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import treemodel.ClientNode;
import treeview.ServerFrame;

public class TreeMouseController implements MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = ServerFrame.getInstance().getTree().getLastSelectedPathComponent();
        if (e.getClickCount() == 2 && obj != null && obj instanceof ClientNode) {
        	ClientNode node = (ClientNode)obj;
    		ServerFrame.getInstance().setCurrent(node.getName());
    		if (node.getText().equals("")) 
    			ServerFrame.getInstance().update("First: <" + node.getName() + ">");
    		else ServerFrame.getInstance().update("Edited: " + node.getText());
        }
    }

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
