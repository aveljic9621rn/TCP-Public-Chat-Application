package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import layout.MainFrameTheme;
import tcp.Server;
import treeview.ServerFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	private static MainFrame instance;
	private JButton btnClient;
	private JButton btnServer;
	private JPanel panel;
	
	private MainFrame() {
		setSize(450, 250);
		initialize();
		addActions();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("ChatUp");
		setIconImage(new ImageIcon(getClass().getResource("/resources/mainframeicon.png")).getImage());
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		panel.setBackground(new Color(26, 64, 56));
		setContentPane(panel);
		
		MetalLookAndFeel.setCurrentTheme(new MainFrameTheme());
	    try {
	      UIManager.setLookAndFeel(new MetalLookAndFeel());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    SwingUtilities.updateComponentTreeUI(this);

	    setVisible(true);
	}
	
	public static void open() {
		MainFrame.getInstance().setVisible(true);
		MainFrame.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static MainFrame getInstance() {
		if (instance == null)
			instance = new MainFrame();
		return instance;
	}
	
	private GridBagConstraints getConstraints(int x, int y) {
		GridBagConstraints constrain = new GridBagConstraints();
		constrain.gridx = x;
		constrain.gridy = y;
		constrain.insets = new Insets(20, 20, 0, 0);
		constrain.anchor = GridBagConstraints.WEST;
		return constrain;
	}
	
	private void initialize() {
		panel = new JPanel(new GridBagLayout());
		this.add(BorderLayout.CENTER, panel);

		JLabel lblClient = new JLabel("Create new Client: ");
		lblClient.setForeground(new Color(255, 255, 255));
		lblClient.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 13));
		GridBagConstraints c = getConstraints(0, 0);
		panel.add(lblClient, c);

		JLabel lblServer = new JLabel("Create new Server: ");
		lblServer.setForeground(new Color(255, 255, 255));
		lblServer.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 13));
		c = getConstraints(0, 1);
		panel.add(lblServer, c);
		
		btnClient = new JButton("Client");
		btnClient.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
		btnClient.setBorder(new LineBorder(Color.BLACK));
		btnClient.setPreferredSize(new Dimension(200, 35));
		c = getConstraints(1, 0);
		panel.add(btnClient, c);
		btnClient.setEnabled(false);
		
		btnServer = new JButton("Server");
		btnServer.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
		btnServer.setBorder(new LineBorder(Color.BLACK));
		btnServer.setPreferredSize(new Dimension(200, 35));
		c = getConstraints(1, 1);
		panel.add(btnServer, c);
	}
	
	private void addActions() {
		btnClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClientInfo.open();
			}
		});
		
		btnServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!Server.isStarted()) {
					ServerFrame.getInstance().initTree();
					ServerFrame.getInstance().initFrame();
				}
				btnClient.setEnabled(true);
			}
		});
	}
}
