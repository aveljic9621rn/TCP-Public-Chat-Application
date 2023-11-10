package tree;

import javax.swing.tree.DefaultTreeModel;

import treemodel.ServerNode;

@SuppressWarnings("serial")
public class TreeModel extends DefaultTreeModel {

	public TreeModel() {
		super(new ServerNode("Client list"));
	}

}
