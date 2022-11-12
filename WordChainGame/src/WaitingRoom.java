import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.JList;
import java.awt.Canvas;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Choice;
import java.awt.Panel;
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;

public class WaitingRoom extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private String [] fruits = {"apple", "banana", "kiwi", "mango", "pear", "peach",
			"berry", "strawberry", "blackberry", "strawberry", "blackberry", "strawberry", "blackberry", "strawberry", "blackberry"};
	private JScrollPane userScrollPane, roomScrollPane;
	private JList userList;
	private JPanel contentPanel, roomlistPanel;
	private JLabel roomL;

	private ImageIcon screenImage = new ImageIcon("images/waitingBackground.png");
	private Image introBackground = screenImage.getImage();
	private ImageIcon logoImage = new ImageIcon("images/gameLogo.png");
	private JButton gameLogo = new JButton(logoImage);
	private ImageIcon startButtonBasicImage = new ImageIcon("images/startButtonBasic.png");
	private JButton startButton = new JButton(startButtonBasicImage);
	private ImageIcon startButtonEnteredImage = new ImageIcon("images/startButtonEntered.png");
	

	public WaitingRoom(String nickname, String ip_addr, String port_no)  {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 700);
		setTitle("kkutu");
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPanel = new JPanel() {
			public void paintComponent(Graphics g) {
                g.drawImage(introBackground, 0, 0, this.getWidth(), this.getHeight(), null);
                setOpaque(false); // 그림을 표시하게 설정, 투명하게 조절
                super.paintComponent(g);
            }
		};
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		// 게임 로고 붙이기
		gameLogo.setBorderPainted(false); // JButton의 border 없애기
		gameLogo.setContentAreaFilled(false); // JButton의 내용영역 채우기 안함
		gameLogo.setFocusPainted(false); // JButton이 선택 되었을 때 생기는 테두리 없애기
		gameLogo.setBounds(50, 10, logoImage.getIconWidth(), logoImage.getIconHeight());
		contentPanel.add(gameLogo);

		// 현재 접속자 리스트
		userScrollPane = new JScrollPane();
		userScrollPane.setBounds(30, 150, 200, 250);
		userList = new JList(fruits);
		userList.setFont(new Font("나눔바른고딕", Font.PLAIN, 12));
		userList.setToolTipText("\uAC8C\uC784\uC811\uC18D\uC790 \uBAA9\uB85D");
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setBackground(SystemColor.control);
		userScrollPane.setViewportView(userList);
		contentPanel.add(userScrollPane);
		
		// 사용자 프로필
		
		
		// 방만들기 버튼
		startButton.setBorderPainted(false); // JButton의 border 없애기
		startButton.setContentAreaFilled(false); // JButton의 내용영역 채우기 안함
		startButton.setFocusPainted(false); // JButton이 선택 되었을 때 생기는 테두리 없애기
		startButton.setBounds(23, 420, startButtonBasicImage.getIconWidth(), startButtonBasicImage.getIconHeight());
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(startButton);

		// 방리스트
		roomL = new JLabel("게임 방 목록");
		roomL.setBounds(254, 22, 128, 24);
		roomL.setFont(new Font("나눔고딕", Font.BOLD, 20));
		contentPanel.add(roomL);
		
		roomlistPanel = new JPanel(new GridLayout(20, 1, 10, 10)); // 20개
		roomlistPanel.setBackground(Color.WHITE);
		roomScrollPane = new JScrollPane(roomlistPanel);
		roomScrollPane.setBounds(254, 48, 405, 582);
		roomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPanel.add(roomScrollPane);
		

	}
}