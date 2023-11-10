package tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import treeview.ServerFrame;

@SuppressWarnings("serial")
public class TreePanel extends JPanel{

	public TreePanel() {
		setLayout(new BorderLayout(5,5));
		setPreferredSize(new Dimension(230,100));

	    JScrollPane scrollPane = new JScrollPane(ServerFrame.getInstance().getTree());
	    scrollPane.setBackground(new Color(26, 64, 56));
	    add(scrollPane);
	}
}
