import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class WordChainGameClientRoomView extends JFrame {
	private static final long serialVersionUID = 1L;


	private WaitingRoom waitingRoom;
	private String data;

	private String UserName; // 유저 이름
	private int userScore;
	private int roomNumber = 1; // 방 번호
	private String roomTitle; // 방 제목
	private String roomManager; // 방장
	private int roomCount = 0; // 들어온 방 인원
	private int roundTime; // 라운드 시간
	private int roundCount; // 라운드 수
	private boolean start; // 게임 시작 여부

	private int turnNumber = 0;
	private Vector<UserDTO> user;
	private UserDTO u;

	private JPanel contentPanel;
	public JPanel UserListPanel;
	private JLabel peopleLabel;
	public Vector<UserPanel> UserPanelList = new Vector();

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

	private ImageIcon exitButtonBasicImage = new ImageIcon("images/exitBasicButton.png");
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private ImageIcon exitButtonEnteredImage = new ImageIcon("images/exitEnteredButton.png");

	private JTextField textInputField;
	private JTextPane textArea;
	public TimerLabel timerLabel;
	public Thread threadNum;

	public JLabel wordLabel;
	public JLabel countLabel;
	
	boolean runFlag = true;

	/**
	 * Create the application.
	 */
	public WordChainGameClientRoomView(WaitingRoom waitingRoom, String data, String userName) {
		this.waitingRoom = waitingRoom;
		this.data = data;
		
		// 방번호#방제목#방장#들어온 인원수#라운드시간#라운드수#개인점수#게임시작상태#user1@user2@
		String d[] = data.split("#");
		this.roomNumber = Integer.parseInt(d[0]); // 방 번호
		this.roomTitle = d[1]; // 방 제목
		this.roomManager = d[2]; // 방장
		this.roomCount = Integer.parseInt(d[3]); // 들어온 인원수
		this.roundTime = Integer.parseInt(d[4]); // 라운드 시간
		this.roundCount = Integer.parseInt(d[5]); // 라운드 수
		this.userScore = Integer.parseInt(d[6]); //// 개인 점수
		this.start = Boolean.parseBoolean(d[7]); // 게임 시작 상태
		this.UserName = userName; //// 개인 이름

		initialize();

		initListener();

	}

	private void initListener() {
		TextSendAction action = new TextSendAction();
		sendButton.addActionListener(action);
		textInputField.addActionListener(action);
		textInputField.requestFocus();
		// ImageSendAction action2 = new ImageSendAction();
		// imgBtn.addActionListener(action2);
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

		textArea = new JTextPane();
		textArea.setBounds(0, 0, 1, 16);
		textArea.setEditable(true);
		chattingScrollPane.setViewportView(textArea);

		textInputField = new JTextField();
		textInputField.setBounds(6, 625, 590, 34);
		contentPanel.add(textInputField);
		textInputField.setColumns(10);

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

		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.setBounds(585, 400, exitButtonBasicImage.getIconWidth(), exitButtonBasicImage.getIconHeight());
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 버튼 위로 올라갈 때
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 버튼 밖으로 나갈 때
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		contentPanel.add(exitButton);

		JLabel roomNameLabel = new JLabel(roomTitle);
		roomNameLabel.setBounds(17, 21, 135, 16);
		contentPanel.add(roomNameLabel);

		peopleLabel = new JLabel(roomCount + " / 6");
		peopleLabel.setBounds(545, 21, 86, 16);
		contentPanel.add(peopleLabel);

		countLabel = new JLabel(roundTime + "초"); //300초 입력 게임 시작하면 바뀜 
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

		wordLabel = new JLabel("단어");
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

		int second =30;
		timerLabel = new TimerLabel(second);
		timerLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		timerLabel.setForeground(new Color(251, 255, 250));
		timerLabel.setBounds(6, 15, 58, 49);

		timerPanel.add(timerLabel);
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
		if (roomManager.equals(UserName)) { // 방장만 시작 버튼 누르기
			gameStartBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					start = true;
					System.out.println(UserName+"방번호 : "+roomNumber);
					ChatMsg obcm = new ChatMsg(UserName, "301", "Game started");
					obcm.SetRoomNumber(roomNumber);
					obcm.onStart = true;
					waitingRoom.SendObject(obcm);
					
					ChatMsg obcm2 = new ChatMsg(UserName, "303", "Playing Game");
					obcm2.SetRoomNumber(roomNumber);
					waitingRoom.SendObject(obcm2);
					
					//Thread threadNum = new Thread(scoreLabel);
					//threadNum.start();
				}
				
			});
		}
		contentPanel.add(gameStartBtn);

		// 사용자리스트
		UserListPanel = new JPanel();
		UserListPanel.setBounds(31, 229, 645, 165);
		UserListPanel.setLayout(null);
		UserListPanel.setBackground(new Color(0, 0, 0, 0));

		contentPanel.add(UserListPanel);

		RoomExitCreateAction roomExitCreateAction = new RoomExitCreateAction();
		exitButton.addActionListener(roomExitCreateAction);

		revalidate();
		repaint();
	}

	class RoomExitCreateAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String roomNumber = data.split("#")[0]; // 방 번호 입력받아서
			int roomNum = Integer.parseInt(roomNumber); // int형으로 바꿈
			ChatMsg obcm = new ChatMsg(UserName, "401", "Exit Room");
			obcm.SetRoomNumber(roomNum);
			waitingRoom.SendObject(obcm);
			setVisible(false);
			ChatMsg obcm2 = new ChatMsg(UserName, "100", "Waiting room go");
			waitingRoom.SendObject(obcm2);
			waitingRoom.VisibleWaitingRoom();
		}
	}

	ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage(); // ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg);
		return xyimg;
	}
	
	// 방의 정보 동기화
	public void settingRoomInfo(String list) {
		String data[] = list.split("#");
		roomCount = Integer.parseInt(data[3]);
		peopleLabel.setText(roomCount + " / 6");
	}
	
	public void gameTimerStart() {
		Thread gameThread = new Thread(new Runnable() {

			int gameTime = 300;
			@Override
			public void run() {
				while(true) {
					if(gameTime>0) {
						gameTime-=1;
						countLabel.setText(Integer.toString(gameTime)+"초");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}else {
						//서버로 게임 종료 알리기
						notifyGameEnd();
						break;
					}
				}
				
			}
			
		});
		gameThread.start();
		
	}
	
	public void notifyGameEnd() {
		ChatMsg obcm = new ChatMsg(UserName, "308", "GameEnd");
		obcm.SetRoomNumber(roomNumber);
		waitingRoom.SendChatMsg(obcm);
	}
	
	// 타이머 스레드 실행시키는 함수
	public void startTimer() {
		System.out.print("타이머 스레드 호출");
		runFlag = true;

		threadNum = new Thread(new Runnable() {
			
			int second = 30;
			
			@Override
			public void run() {
				while(runFlag) {
					try {
						if (second > 0) {
							second -= 1;
							timerLabel.setText(Integer.toString(second));
							Thread.sleep(1000);		
						}else {
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						return;
					}
				}	
			}
		});

		threadNum.start();
	}
	
	// 타이머 스레드 중지시키는 함수
	public void stopTimer() {
		System.out.print("타이머 스레드 중지");
		runFlag = false;
		threadNum.interrupt();
	}
	
	// 방에서 퇴장한 유저 삭제하는 함수
	public void deleteUser(String deleteUser, String list) {
		String userList[] = list.split("@"); // 나가고 남은 사람들 리스트

		if (userList != null) {
			for (int i = 0; i < user.size(); i++) {
				UserDTO userInfo = user.get(i);
				if (userInfo.getName().equals(deleteUser)) {
					user.remove(userInfo);
				}
			}
			addUserPanel(user);
		}
	}

	public void addUser(String list) {
		String data[] = list.split("#");
		String userList[] = data[data.length - 1].split("@");

		user = new Vector<UserDTO>();
		// 받아온 문자열

		// 방번호#방제목#방장#들어온 인원수#라운드시간#라운드수#개인점수#게임시작상태#user1@user2@
		// 1#hi#2#0#user1@user2@
		if (data != null) {
			for (int i = 0; i < userList.length; i++) {
				u = new UserDTO(userList[i], userScore, "O");
				user.add(u);
			}
			addUserPanel(user);
		}

	}

	public void addUserPanel(Vector<UserDTO> list) {
		UserListPanel.removeAll();
		int userPanelX = 0;
		int userPanelY = 0;

		for (int i = 0; i < list.size(); i++) {
			UserPanel user = new UserPanel(userPanelX, userPanelY);
			user.setName(list.get(i).getName());
			user.setScore(list.get(i).getScore());

			UserListPanel.add(user);
			UserPanelList.add(user); // 외부에서 패널 접근하기 위한 함
			userPanelX += user.width + 5;
		}

		contentPanel.revalidate();
		contentPanel.repaint();
	}

	// 화면에 출력
	public void AppendText(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.black);
		textArea.setCharacterAttributes(aset, false);
		textArea.replaceSelection(msg + "\n");
	}

	public void AppendTextColor(String msg, Color c) {
		// 색상 선택
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		textArea.setCharacterAttributes(aset, false);
		textArea.replaceSelection(msg + "\n");

	}

	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == sendButton || e.getSource() == textInputField) {
				String msg = null;
				msg = textInputField.getText();
				System.out.println("메시지 전송 : " + msg);
				if (waitingRoom.isTurn) { // 내 차례일
					System.out.println("답 ::" + msg);
					SendAnswerMessage(msg);
					// 턴 변경
					//setTurnUser(UserName);
				} else {
					System.out.println("일반 채팅 ::" + msg);
					SendMessage(msg);
				}

				textInputField.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				textInputField.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}

	// 턴 변경
	public void setTurnUser(String userName) {
		UserPanel u = null;
		for (int i = 0; i < UserPanelList.size(); i++) {
			u = (UserPanel) UserPanelList.elementAt(i);
			if (u.getName().equals(userName)) {
				Color c = new Color(224, 252, 219);
				u.setBackground(c);
			} else {
				u.setBackground(Color.gray);
			}
			revalidate();
			repaint();
		}
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		ChatMsg obcm = new ChatMsg(UserName, "200", msg);
		obcm.SetRoomNumber(roomNumber);
		waitingRoom.SendChatMsg(obcm);
	}

	// Server에게 network으로 답 전송
	public void SendAnswerMessage(String msg) {
		ChatMsg obcm = new ChatMsg(waitingRoom.UserName, "203", msg);
		obcm.SetRoomNumber(roomNumber);
		waitingRoom.SendChatMsg(obcm);
	}

	public class TimerLabel extends JLabel{

		int second;
		//public boolean runFlag = true;

		public TimerLabel(int second) {
			setText(Integer.toString(second));
			setHorizontalAlignment(JLabel.CENTER);
			this.second = second;
		}
	}
	
}
