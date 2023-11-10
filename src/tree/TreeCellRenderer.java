package tree;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellRenderer;

import treemodel.ClientNode;
import treemodel.ServerNode;

@SuppressWarnings("serial")
public class TreeCellRenderer extends DefaultTreeCellRenderer{

	public TreeCellRenderer() {
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JComponent c = (JComponent) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
		if (selected) {
			c.setOpaque(true);
			c.setBackground(new Color(104, 163, 151));
			c.setForeground(Color.black);
		    SwingUtilities.updateComponentTreeUI(this);
		}
		else {
			c.setOpaque(true);
			c.setBackground(new Color(26, 64, 56));
			c.setForeground(Color.white);
		    SwingUtilities.updateComponentTreeUI(this);
		}
		
		if (value instanceof ClientNode) {
			ImageIcon img = null;
			File tmp = new File("src/resources/client15.png");
			if (tmp.exists()) {
				img = new ImageIcon("src/resources/client15.png");
				setIcon(img);
			} else
				System.err.println("Resource not found: " + "src/resources/client15.png");
		} else if (value instanceof ServerNode) {
			ImageIcon img = null;
			File tmp = new File("src/resources/server15.png");
			if (tmp.exists()) {
				img = new ImageIcon("src/resources/server15.png");
				setIcon(img);
			} else
				System.err.println("Resource not found: " + "src/resources/server15.png");
		}
		return this;
	}
}
