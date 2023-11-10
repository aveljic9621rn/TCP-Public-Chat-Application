package treeview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.DefaultCaret;
import actions.MessageActionListener;
import actions.MessageFocusListener;
import layout.MainFrameTheme;
import observer.Observer;
import tcp.Client;

@SuppressWarnings("serial")
public class ClientFrame extends JFrame implements Observer {
	private Client client;
	private JTextArea chatBox;
	private JTextField messageBox;
	private JButton sendMessage;

	public ClientFrame(String nickname) {
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

		MetalLookAndFeel.setCurrentTheme(new MainFrameTheme());
	    try {
	      UIManager.setLookAndFeel(new MetalLookAndFeel());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    
		setTitle(nickname + "'s chat");
		setSize(470, 300);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(getClass().getResource("/resources/client.png")).getImage());
		
		initialize();
		
		try {
			client = new Client(nickname);
			client.addObserver(this);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				messageBox.requestFocus();
			}
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				client.getOutSocket().println("EXITING_NOW");
			}
		});
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		addListeners();
	}
	
	private void initialize() {
		messageBox = new JTextField(30);
		messageBox.setText("Type a message...");
		messageBox.setPreferredSize(new Dimension(300, 30));
		messageBox.setFont(new Font("Lucida Sans", Font.PLAIN, 11));
		
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setLineWrap(true);
		chatBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		chatBox.setBackground(new Color(26, 64, 56));
		chatBox.setForeground(Color.white);
		chatBox.setFont(new Font("Lucida Sans", Font.PLAIN, 11));
		DefaultCaret caret = (DefaultCaret) chatBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		sendMessage = new JButton("Send Message");
		sendMessage.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 11));

		this.add(new JScrollPane(chatBox), BorderLayout.CENTER);
		
		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.LINE_START;
		left.fill = GridBagConstraints.HORIZONTAL;
		left.weightx = 512;
		left.weighty = 1;

		GridBagConstraints right = new GridBagConstraints();
		right.insets = new Insets(0, 10, 0, 0);
		right.anchor = GridBagConstraints.LINE_END;
		right.fill = GridBagConstraints.NONE;
		right.weightx = 1;
		right.weighty = 1;

		messageBox.setBorder(new EmptyBorder(0, 10, 0, 0));
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		southPanel.add(messageBox, left);
		southPanel.add(sendMessage, right);

		this.add(BorderLayout.SOUTH, southPanel);
	}
	
	private void addListeners() {
		messageBox.addActionListener(new MessageActionListener(this));
		messageBox.addFocusListener(new MessageFocusListener(this));
		sendMessage.addActionListener(new MessageActionListener(this));
	}

	@Override
	public void update(Object o) {
		if (o == null)
			return;
		if (!(o instanceof String))
			return;
		if (((String) o).equals("EXITING_NOW")) {
			setVisible(false);
			dispose();
			return;
		}
		String s = (String) o;
		chatBox.append(s + "\n");
	}

	public Client getClient() {
		return client;
	}

	public JTextField getMessageBox() {
		return messageBox;
	}

	public JTextArea getChatBox() {
		return chatBox;
	}

	public JButton getSendMessage() {
		return sendMessage;
	}
	
}
