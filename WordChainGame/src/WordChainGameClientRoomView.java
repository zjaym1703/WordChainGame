import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class WordChainGameClientRoomView extends JFrame {

	private final int second = 30;
	
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private WaitingRoom waitingRoom;

	private JPanel contentPanel;
	private JPanel UserListPanel;
	
	private int userPanelX = 0;
	private int userPanelY = 0;

	// image
	private Image background = new ImageIcon("images/background1.png").getImage();
	private Image scoreBackground = new ImageIcon("images/score.png").getImage();
	private Image timerBackground = new ImageIcon("images/timer.png").getImage();
	private Image resizeBackground = timerBackground.getScaledInstance(120, 120, Image.SCALE_SMOOTH);

	private ImageIcon happyEmo = new ImageIcon("images/emoticon_happy.png");
	private ImageIcon loveEmo = new ImageIcon("images/emoticon_love.png");
	private ImageIcon supriseEmo = new ImageIcon("images/emoticon_suprise.png");
	private ImageIcon sleepEmo = new ImageIcon("images/emoticon_sleep.png");
	private ImageIcon AngryEmo = new ImageIcon("images/emoticon_angry.png");
	private ImageIcon sendButtonBasicImage = new ImageIcon("images/sendButtonBasic.png");
	private ImageIcon sendButtonEnteredImage = new ImageIcon("images/sendButtonEntered.png");
	private ImageIcon gameStartButtonBasicImage = new ImageIcon("images/gameStartButtonBasic.png");
	private ImageIcon gameStartButtonEnteredImage = new ImageIcon("images/gameStartButtonEntered.png");
	private JButton sendButton = new JButton(sendButtonBasicImage);

	private JTextField textField;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					WordChainGameClientRoomView window = new WordChainGameClientRoomView();
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public WordChainGameClientRoomView() {
//		this.waitingRoom = waitingRoom;
//		if (waitingRoom != null) {
//			ChatMsg obcm = new ChatMsg("", "160", "UserInfo Request");
//			waitingRoom.SendObject(obcm);
//		}
		initialize();
		
//		try {
//			socket = new Socket(ip_addr, Integer.parseInt(port_no));
//
//			oos = new ObjectOutputStream(socket.getOutputStream());
//			oos.flush();
//			ois = new ObjectInputStream(socket.getInputStream());
//
//			ChatMsg obcm = new ChatMsg(username, "301", "RoomName"); //임의로 설정해놓음, 추후에 변경해야함 
//			SendChatMsg(obcm);
//
//			ListenNetwork net = new ListenNetwork();
//			net.start();
//			//TextSendAction action = new TextSendAction();
//			//btnSend.addActionListener(action);
//			//txtInput.addActionListener(action);
//			//txtInput.requestFocus();
//			//ImageSendAction action2 = new ImageSendAction();
//			//imgBtn.addActionListener(action2);
//
//		} catch (NumberFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			//AppendText("connect error");
//		}

	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("kkutu");
		setSize(700, 700);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// content panel에 이미지 추가
		contentPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		JScrollPane chattingScrollPane = new JScrollPane();
		chattingScrollPane.setBounds(6, 443, 670, 178);
		contentPanel.add(chattingScrollPane);

		JLabel lblNewLabel = new JLabel("채팅");
		chattingScrollPane.setColumnHeaderView(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(6, 625, 590, 34);
		contentPanel.add(textField);
		textField.setColumns(10);

		sendButton.setBorderPainted(false);
		sendButton.setContentAreaFilled(false);
		sendButton.setFocusPainted(false);
		sendButton.setBounds(599, 622, sendButtonBasicImage.getIconWidth(), sendButtonBasicImage.getIconHeight());
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				sendButton.setIcon(sendButtonEnteredImage);
				sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				sendButton.setIcon(sendButtonBasicImage);
				sendButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(sendButton);

		JLabel roomNameLabel = new JLabel("RoomName");
		roomNameLabel.setBounds(17, 21, 135, 16);
		contentPanel.add(roomNameLabel);

		JLabel peopleLabel = new JLabel("참가인원수");
		peopleLabel.setBounds(545, 21, 86, 16);
		contentPanel.add(peopleLabel);

		JLabel countLabel = new JLabel("60초");
		countLabel.setBounds(643, 21, 38, 16);
		contentPanel.add(countLabel);

		JLabel gameTypeLabel = new JLabel("게임종류");
		gameTypeLabel.setBounds(431, 21, 61, 16);
		contentPanel.add(gameTypeLabel);

		JPanel scorePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(scoreBackground, 0, 0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		scorePanel.setBounds(150, 95, 397, 93);
		scorePanel.setLayout(null);
		contentPanel.add(scorePanel);

		JLabel wordLabel = new JLabel("단어");
		wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		wordLabel.setForeground(new Color(251, 255, 250));
		wordLabel.setBounds(0, 36, 391, 51);
		scorePanel.add(wordLabel);

		JPanel timerPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(timerBackground, 0, 0, this.getWidth(), this.getHeight(), null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		timerPanel.setBounds(611, 107, 70, 70);
		timerPanel.setLayout(null);

		TimerLabel scoreLabel = new TimerLabel(second);
		scoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		scoreLabel.setForeground(new Color(251, 255, 250));
		scoreLabel.setBounds(6, 15, 58, 49);
		Thread threadNum = new Thread(scoreLabel);
		threadNum.start();
		timerPanel.add(scoreLabel);
		contentPanel.add(timerPanel);

		// 이미지 버튼 추가
		happyEmo = imageSetSize(happyEmo, 30, 30);
		JButton happyemoButton = new JButton();
		happyemoButton.setIcon(happyEmo);
		happyemoButton.setBounds(6, 406, 35, 35);
		happyemoButton.setBorderPainted(false); // JButton의 border 없애기
		happyemoButton.setContentAreaFilled(false); // JButton의 내용영역 채우기 안함
		happyemoButton.setFocusPainted(false); // JButton이 선택 되었을 때 생기는 테두리 없애기
		happyemoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				happyemoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				happyemoButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(happyemoButton);

		loveEmo = imageSetSize(loveEmo, 30, 30);
		JButton loveButton = new JButton();
		loveButton.setIcon(loveEmo);
		loveButton.setBounds(42, 406, 35, 35);
		loveButton.setBorderPainted(false);
		loveButton.setContentAreaFilled(false);
		loveButton.setFocusPainted(false);
		loveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				loveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				loveButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(loveButton);

		supriseEmo = imageSetSize(supriseEmo, 30, 30);
		JButton supriseButton = new JButton();
		supriseButton.setIcon(supriseEmo);
		supriseButton.setBounds(78, 406, 35, 35);
		supriseButton.setBorderPainted(false);
		supriseButton.setContentAreaFilled(false);
		supriseButton.setFocusPainted(false);
		supriseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				supriseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				supriseButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(supriseButton);

		sleepEmo = imageSetSize(sleepEmo, 30, 30);
		JButton sleepeButton = new JButton();
		sleepeButton.setIcon(sleepEmo);
		sleepeButton.setBounds(114, 406, 35, 35);
		sleepeButton.setBorderPainted(false);
		sleepeButton.setContentAreaFilled(false);
		sleepeButton.setFocusPainted(false);
		sleepeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				sleepeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				sleepeButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(sleepeButton);

		AngryEmo = imageSetSize(AngryEmo, 30, 30);
		JButton angryButton = new JButton();
		angryButton.setIcon(AngryEmo);
		angryButton.setBounds(150, 406, 35, 35);
		angryButton.setBorderPainted(false);
		angryButton.setContentAreaFilled(false);
		angryButton.setFocusPainted(false);
		angryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				angryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				angryButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(angryButton);

		gameStartButtonEnteredImage = imageSetSize(gameStartButtonEnteredImage, 65, 50);
		gameStartButtonBasicImage = imageSetSize(gameStartButtonBasicImage, 65, 50);
		JButton gameStartBtn = new JButton(gameStartButtonBasicImage);
		gameStartBtn.setBorderPainted(false);
		gameStartBtn.setContentAreaFilled(false);
		gameStartBtn.setFocusPainted(false);
		gameStartBtn.setBounds(42, 120, 65, 50);
		gameStartBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				gameStartBtn.setIcon(gameStartButtonEnteredImage);
				gameStartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				gameStartBtn.setIcon(gameStartButtonBasicImage);
				gameStartBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		contentPanel.add(gameStartBtn);
		
		//사용자리스트 
		UserListPanel = new JPanel();
		UserListPanel.setBounds(31, 229, 645, 165);
		UserListPanel.setLayout(null);
		UserListPanel.setBackground(new Color(0,0,0,0));
		
		UserPanel user = new UserPanel(userPanelX,userPanelY);
		user.setName("끄투");
		user.setScore(1000);
		UserListPanel.add(user);
		
		contentPanel.add(UserListPanel);
	}

	ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage(); // ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg);
		return xyimg;
	}
	
	public void addUserPanel(Vector<UserDTO> list) {
		for(int i=0;i<list.size();i++) {
			UserPanel user = new UserPanel(userPanelX,userPanelY);
			user.setName(list.get(i).getName());
			user.setScore(list.get(i).getScore());
			UserListPanel.add(user);
			
			userPanelX += user.WIDTH + 5;
			userPanelX += user.HEIGHT;
		}
	}
	public void SendChatMsg(ChatMsg obj) {
		try {
			oos.writeObject(obj.code);
			oos.writeObject(obj.UserName);
			oos.writeObject(obj.data);
			if (obj.code.equals("300")) { 
				//oos.writeObject(obj.imgbytes);
			}
			oos.flush();
		} catch (IOException e) {
			//AppendText("SendChatMsg Error");
			e.printStackTrace();
			try {
				oos.close();
				socket.close();
				ois.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// textArea.append("�޼��� �۽� ����!!\n");
			// System.exit(0);
		}
	}
	
	
	
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				ChatMsg cm = ReadChatMsg();
				if (cm==null)
					break;
				if (socket == null)
					break;
				String msg;
				msg = String.format("[%s] %s", cm.UserName, cm.data);
				switch (cm.code) {
				case "100":
					//사용자 list 받음 
				case "200": // chat message
					//AppendText(msg);
					break;
				case "301": // 게임룸 입장
//					Vector<UserDTO> userList = (Vector<UserDTO>) cm.data;
//					addUserPanel(userList);
					break;
				}

			}
		}
	}
	
	public ChatMsg ReadChatMsg() {
		Object obj = null;
		String msg = null;
		ChatMsg cm = new ChatMsg("", "", "");

			try {
				obj = ois.readObject();
				cm.code = (String) obj;
				obj = ois.readObject();
				cm.UserName = (String) obj;
				obj = ois.readObject();
				cm.data = (String) obj;
				if (cm.code.equals("300")) {
					obj = ois.readObject();
					//cm.imgbytes = (byte[]) obj;
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				//AppendText("ReadChatMsg Error");
				e.printStackTrace();
				try {
					oos.close();
					socket.close();
					ois.close();
					socket = null;
					return null;
				} catch (IOException e1) {
					e1.printStackTrace();
					try {
						oos.close();
						socket.close();
						ois.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}

					socket = null;
					return null;
				}

				// textArea.append("�޼��� �۽� ����!!\n");
				// System.exit(0);
			}


		return cm;
	}
	

	class TimerLabel extends JLabel implements Runnable {

		int second;

		public TimerLabel(int second) {
			setText(Integer.toString(second));
			setHorizontalAlignment(JLabel.CENTER);
			this.second = second;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (second > 0) {
					second -= 1;
					setText(Integer.toString(second));
				} else {

					break;
				}
			}
		}

	}
}
