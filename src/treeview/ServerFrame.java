package treeview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.TreeNode;
import layout.MainFrameTheme;
import observer.Observer;
import tcp.Server;
import tree.Tree;
import tree.TreeModel;
import tree.TreePanel;
import treemodel.ClientNode;
import treemodel.ServerNode;
 
@SuppressWarnings("serial")
public class ServerFrame extends JFrame implements Observer {
	private static ServerFrame instance;

	private Server server;
	private JTextArea chatBox;
	private Tree tree;
	private TreeModel treeModel;
	private String current;

	private ServerFrame() {

	}

	public void initFrame() {
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

		MetalLookAndFeel.setCurrentTheme(new MainFrameTheme());
	    try {
	      UIManager.setLookAndFeel(new MetalLookAndFeel());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

		initialize();
		setTitle("Server: " + server);
		setSize(550, 350);
		setLocationRelativeTo(null);
		setBackground(new Color(255, 135, 50));
		setIconImage(new ImageIcon(getClass().getResource("/resources/server.png")).getImage());
		SwingUtilities.updateComponentTreeUI(this);
		
		this.addWindowListener(new WindowAdapter() {	
			@Override	
			public void windowClosing(WindowEvent windowEvent) {	
				if (Server.isStarted())	
					server.stop();	
			}	
		});	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

		instance.setVisible(true);
	}
	
	private void initialize() {
		server = Server.getInstance();
		server.addObserver(this);
		
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setLineWrap(true);
		chatBox.setBorder(new BevelBorder(1, new Color(0, 0, 0), new Color(0, 0, 0)));
		chatBox.setText("");
		chatBox.setForeground(Color.white);
		chatBox.setFont(new Font("Lucida Sans", Font.PLAIN, 11));
		chatBox.setBackground(new Color(26, 64, 56));
		chatBox.setPreferredSize(new Dimension(300, 253));
		DefaultCaret caret = (DefaultCaret) chatBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane jsp = new JScrollPane(chatBox);
		TreePanel panLeft = new TreePanel();
		
		JLabel lblChat = new JLabel();
		lblChat.setText("Chat");
		lblChat.setPreferredSize(new Dimension(300, 30));
		lblChat.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 15));
		
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(100, 300));
		panel.add(lblChat, BorderLayout.NORTH);
		panel.add(jsp, BorderLayout.CENTER);
		

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panLeft, panel);
		splitPane.setDividerLocation(160);
		splitPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		this.add(splitPane, BorderLayout.CENTER);	}

	public void initTree() {
		tree = new Tree();
		treeModel = new TreeModel();
		tree.setModel(treeModel);
	}

	public static ServerFrame getInstance() {
		if (instance == null) {
			instance = new ServerFrame();
		}
		return instance;
	}

	public Server getServer() {
		return server;
	}

	private String getUserNickname(String nick) {
		String tmp = "";
		int i = 1;
		while (nick.charAt(i) != '>') {
			tmp += nick.charAt(i);
			i++;
		}
		return tmp;
	}

	@Override
	public void update(Object o) {
		if (o instanceof String) {
			String str = (String) o;
			if (str.startsWith("First: ")) {
				String split[] = str.split("First: ");
				String userNick = getUserNickname(split[1]);
				if (split.length == 2) 
					chatBox.setText(userNick + "'s chat history.\n");
			} else if (str.startsWith("Delete: ")) {
				String split[] = str.split("Delete: ");

				((ServerNode) treeModel.getRoot()).removeChild(((ServerNode) treeModel.getRoot()).getChildByString(split[1]));
				SwingUtilities.updateComponentTreeUI(tree);
				if (((ServerNode) treeModel.getRoot()).getChildCount() == 0)
					chatBox.setText("");
				if (split[1].equals(current)) {
					if (((ServerNode) treeModel.getRoot()).getChildCount() != 0) {
						current = ((ClientNode)((ServerNode) treeModel.getRoot()).getChildAt(0)).getName();
						update("Edited: " + ((ClientNode)((ServerNode) treeModel.getRoot()).getChildAt(0)).getText());
					}else {
						current = "";
					}
				}
			} else if (str.startsWith("Add: ")) {
				String split[] = str.split("Add: ");

				ClientNode node = new ClientNode(split[1], (TreeNode) treeModel.getRoot());
				node.addObserver(this);
				((ServerNode) treeModel.getRoot()).addChild(node);
				expandAllNodes(tree);
				SwingUtilities.updateComponentTreeUI(tree);
			} else if (str.startsWith("Edited: ")) {
				String split[] = str.split("Edited: ");
				if (str.equals("Edited: ")) {
					chatBox.setText(current + "'s chat history.\n");
					return;
				}
				
				String userNick = getUserNickname(split[1]);
				if (!(userNick.equals(current)))
					return;
				if (split.length == 2) {
					chatBox.setText(userNick + "'s chat history.\n");
					chatBox.append(split[1]);
				} else
					chatBox.setText("");
			}
		}

	}

	private void expandAllNodes(Tree tree) {
	    int j = tree.getRowCount();
	    int i = 0;
	    while(i < j) {
	        tree.expandRow(i);
	        i += 1;
	        j = tree.getRowCount();
	    }
	}
	
	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public Tree getTree() {
		return tree;
	}

	public TreeModel getTreeModel() {
		return treeModel;
	}
}
