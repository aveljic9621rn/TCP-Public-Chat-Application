package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import actions.ClientInfoActionListener;
import layout.GetConstraints;
import layout.MainFrameTheme;
import tcp.Server;

@SuppressWarnings("serial")
public class ClientInfo extends JFrame {
	private JComboBox<Server> servers;
	private JTextField tfUsername;
	private JButton btnJoin;
	private JPanel panel;

	private ClientInfo() {
		setUndecorated(true);
		setIconImage(new ImageIcon(getClass().getResource("/resources/client.png")).getImage());
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

		MetalLookAndFeel.setCurrentTheme(new MainFrameTheme());
	    try {
	      UIManager.setLookAndFeel(new MetalLookAndFeel());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    SwingUtilities.updateComponentTreeUI(this);
		setSize(400, 250);
		
		initialize();
		addListeners();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Client");
		setLocationRelativeTo(null);
	}
	
	private void initialize() {
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = GetConstraints.getConstraints(0, 0);

		JLabel lblUsername = new JLabel("Enter your username: ");
		lblUsername.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 13));
		panel.add(lblUsername, c);

		JLabel lblServer = new JLabel("Pick a server: ");
		lblServer.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 13));
		c = GetConstraints.getConstraints(0, 1);
		panel.add(lblServer, c);
		
		servers = new JComboBox<>();
		servers.addItem(Server.getInstance());
		servers.setBorder(new LineBorder(Color.black));
		servers.setBackground(new Color(26, 64, 56));
		servers.setForeground(Color.white);;
		servers.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
		servers.setMinimumSize(new Dimension(150, 35));
		c = GetConstraints.getConstraints(1, 1);
		panel.add(servers, c);
		
		tfUsername = new JTextField(20);
		tfUsername.setBorder(new LineBorder(Color.black));
		tfUsername.setBackground(new Color(26, 64, 56));		
		tfUsername.setForeground(Color.white);
		tfUsername.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
		tfUsername.setMinimumSize(new Dimension(148, 33));
		tfUsername.setCaretColor(Color.white);
		c = GetConstraints.getConstraints(1, 0);
		panel.add(tfUsername, c);

		btnJoin = new JButton("Join");
		btnJoin.setBorder(new LineBorder(Color.black));
		btnJoin.setBackground(new Color(26, 64, 56));		
		btnJoin.setForeground(Color.white);
		btnJoin.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 13));
		btnJoin.setPreferredSize(new Dimension(1, 33));

		add(BorderLayout.CENTER, panel);
		add(BorderLayout.SOUTH, btnJoin);
	}

	private void addListeners() {
		btnJoin.addActionListener(new ClientInfoActionListener(this));
		tfUsername.addActionListener(new ClientInfoActionListener(this));
	}
	
	public static void open() {
		ClientInfo openingClientFrame = new ClientInfo();
		openingClientFrame.setVisible(true);
	}

	public JComboBox<Server> getServers() {
		return servers;
	}

	public JTextField getTfUsername() {
		return tfUsername;
	}

}