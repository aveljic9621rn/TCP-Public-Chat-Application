package tree;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;

import actions.TreeMouseController;

@SuppressWarnings("serial")
public class Tree extends JTree {

	public Tree() {
		addMouseListener(new TreeMouseController()); 
	    setCellRenderer(new TreeCellRenderer());
	    setEditable(false);
	    setBackground(new Color(26, 64, 56));
	    setFont(new Font("Lucida Calligraphy", Font.PLAIN, 10));
		setBorder(new BevelBorder(1, new Color(0, 0, 0), new Color(0, 0, 0)));
	}
	
}
