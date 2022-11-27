import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.ListSelectionModel;

public class WaitingRoom extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	public String UserName;
	public boolean isTurn = false; //턴 입력
	private Socket socket; // 연결소켓
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public WordChainGameClientRoomView gameRoomView;
	public WaitingRoom waitingRoom;
	private BGM bgm = null;
//	private CreateRoom createRoom = null;
//	private JFrame createRoomFrame = null; // 방 만들기 프레임
	private JScrollPane userScrollPane, roomScrollPane;
	private JList<String> userList, roomList;
	
	private JPanel contentPanel, roomlistPanel;
	private JLabel roomL, usernameL;
	
	// 수정하고 있는 부분
	private String [] header = { "번호", "방 제목", "인원", "게임 유형", "라운드 수", "라운드 시간" };
	private Vector<String> row; // 행 추가 하는 부분
	private DefaultTableModel model = new DefaultTableModel(header, 0); // header데이터로 컬럼 모델 생성

	private ImageIcon screenImage = new ImageIcon("images/waitingBackground.png");
	private Image introBackground = screenImage.getImage();
	private ImageIcon logoImage = new ImageIcon("images/gameLogo.png");
	private JButton gameLogo = new JButton(logoImage);
	private ImageIcon startButtonBasicImage = new ImageIcon("images/startButtonBasic.png");
	private JButton startButton = new JButton(startButtonBasicImage);
	private ImageIcon startButtonEnteredImage = new ImageIcon("images/startButtonEntered.png");
	
	public WaitingRoom(String userName, String ip_addr, String port_no)  {
		this.UserName = userName;
		this.waitingRoom = this;
		this.bgm = new BGM();
//		bgm.playMusic("backgroundMusic.wav"); // bgm틀기
		
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
		userList = new JList<String>();
		userList.setFont(new Font("나눔바른고딕", Font.PLAIN, 12));
		userList.setToolTipText("\uAC8C\uC784\uC811\uC18D\uC790 \uBAA9\uB85D");
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setBackground(SystemColor.control);
		userScrollPane.setViewportView(userList);
		contentPanel.add(userScrollPane);
		
		// 사용자 프로필
		usernameL = new JLabel();
		usernameL.setBounds(125, 554, 105, 31);
		usernameL.setFont(new Font("굴림", Font.BOLD, 15));
		contentPanel.add(usernameL);
		
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
		roomList = new JList<String>();
		roomlistPanel = new JPanel(new GridLayout(20, 1, 10, 10)); // 20개
		roomlistPanel.setBackground(Color.WHITE);
		roomScrollPane = new JScrollPane(roomlistPanel);
		roomScrollPane.setBounds(254, 48, 405, 582);
		roomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		roomScrollPane.setViewportView(roomList);
		contentPanel.add(roomScrollPane);
		
		roomList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JList list = (JList) e.getSource();
				if(e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					ChatMsg obcm = new ChatMsg(UserName, "301", UserName + " enter GameRoom");
					obcm.SetRoomNumber(index);
					SendObject(obcm);
				}
			}
		});
		
		revalidate();
		repaint();
		
//		// 수정중인 부분
//		JTable table = new JTable(model) {
//			@Override
//			public boolean isCellEditable(int row, int column) { // 수정 불가
//				return false;
//			}
//		};
//		roomScrollPane = new JScrollPane(table);
//		roomScrollPane.setBounds(254, 48, 405, 582);
//		roomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		roomScrollPane.setViewportView(table);
//		contentPanel.add(roomScrollPane);
//		//
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			// SendMessage("/login " + UserName);
			ChatMsg obcm = new ChatMsg(UserName, "100", "New User Enter");
			SendObject(obcm);

			ListenNetwork net = new ListenNetwork();
			net.start();
			RoomCreateAction roomCreateAction = new RoomCreateAction();
			startButton.addActionListener(roomCreateAction);
			
			
//			TextSendAction action = new TextSendAction();
//			btnSend.addActionListener(action);
//			txtInput.addActionListener(action);
//			txtInput.requestFocus();
//			ImageSendAction action2 = new ImageSendAction();
//			imgBtn.addActionListener(action2);
//			MyMouseEvent mouse = new MyMouseEvent();
//			panel.addMouseMotionListener(mouse);
//			panel.addMouseListener(mouse);
//			MyMouseWheelEvent wheel = new MyMouseWheelEvent();
//			panel.addMouseWheelListener(wheel);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
//			AppendText("connect error");
		}
	}
	
	public void VisibleWaitingRoom()  {
		setVisible(true);
		revalidate();
		repaint();
	}
	
	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s]\n%s", cm.UserName, cm.data);
					} else
						continue;
					
					switch (cm.code) {
					case "100": // 대기실에서 모든 접속자 인원
						String [] enterUsers = cm.data.split(","); // 모든 접속자들을 ,단위로 자르기

						userList.setListData(enterUsers);
						usernameL.setText(UserName);
						if (cm.roomTitle != null) { // 현재 만들어진 방 리스트에 뿌려주기
							String [] title = cm.roomTitle.split(",");
							roomList.setListData(title);
						}
//						//추가한 부분
//						if (cm.roomTitle != null) { // 현재 만들어진 방 리스트에 뿌려주기
//							String [] title = cm.roomTitle.split(",");
//							for(int i=0; i < title.length; i++) {
//								row = new Vector<String>();
//								row.addElement(title[i]);
//							}
//							model.addRow(row);
//						}
//						//
						break;
					case "200":
						msg = String.format("[%s] %s\n", cm.UserName, cm.data);

						if(cm.UserName.equals(UserName)) {
							gameRoomView.AppendTextColor(msg,Color.RED);
						}else {
							gameRoomView.AppendText(msg);
						}
						break;

					case "203": // 답 입력 성공 여부 받
						break;

					case "301": // 방 입장하는 부분
						// 유저의 창을 닫고 게임방 입장
						setVisible(false);
						gameRoomView = new WordChainGameClientRoomView(waitingRoom, cm.data, UserName); // 게임입장
						gameRoomView.addUser((String)cm.data);
						gameRoomView.settingRoomInfo((String)cm.data);
						break;
					case "302": // 게임 방 생성되면 리스트에 뿌리기
						if(cm.roomTitle != null) {
							String [] title = cm.roomTitle.split(",");	
							roomList.setListData(title);
						}
						break;
					case "303":
						//게임 타이머 300초 설정 & 시작
						
						gameRoomView.gameTimerStart();
						break;
					case "304": //제시어 전달 받음 
						String word = (String)cm.data;
						gameRoomView.wordLabel.setText(word);
						break;
					case "306": // 턴 입력 받음 
						String turnMember = cm.data;
						//타이머 스레드 시작
						System.out.println(turnMember+"님의 턴입니다");
						if(UserName.equals(turnMember)) {
							waitingRoom.isTurn = true;
						}else {
							waitingRoom.isTurn = false;
						}
						gameRoomView.setTurnUser(turnMember);
						break;
					case "307": // 사용자 입장 알림 받음
						gameRoomView.addUser((String)cm.data);
						gameRoomView.settingRoomInfo((String)cm.data);
						break;
					case "308": // 입장 가능 여부
						if (cm.data.equals("Full"))
							JOptionPane.showMessageDialog(rootPane, "인원이 다 찼습니다");
						else if (cm.data.equals("Started"))
							JOptionPane.showMessageDialog(rootPane, "게임이 진행중입니다");
						break;
					case "309": //시간 전달
						//실행중인 timer가 있으면 종료 후 다시 시작
						if(gameRoomView.threadNum!=null) { //진행중인 timer 스레드 있으면 멈
							gameRoomView.stopTimer();
						}
						gameRoomView.startTimer();
						break;
					case "401":
						String [] roomRemainUsers = cm.data.split("@");
						for(int i = 0; i < roomRemainUsers.length; i++) {
							gameRoomView.deleteUser(cm.deleteUser, cm.data);
						}
						break;
					}
				} catch (IOException e) {
//					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						socket.close();
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			}
		}
	}
	
	public void SendChatMsg(ChatMsg obj) {
		System.out.println(obj.UserName+" : "+obj.data);
		try {
			oos.writeObject(obj);
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
				e1.printStackTrace();
			}

			// textArea.append("�޼��� �۽� ����!!\n");
			// System.exit(0);
		}
	}

	class RoomCreateAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String title = JOptionPane.showInputDialog("방제목을 입력하세요"); // 방 제목 입력받는 팝업창
			ChatMsg obcm = new ChatMsg(UserName, "302", "Create Room");
			obcm.roomTitle = title;
			SendObject(obcm);
			
			
//			createRoom = new CreateRoom(createRoomFrame, waitingRoom);
//            createRoomFrame = new JFrame();
//            createRoomFrame.setTitle("방 생성");
//            createRoomFrame.setContentPane(createRoom);
//            createRoomFrame.setBounds(100, 200, 310, 255);
//            createRoomFrame.setVisible(true);
//            createRoomFrame.setLocationRelativeTo(null);
//            createRoomFrame.setResizable(false);
            
		}
	}

//	// Mouse Event 수신 처리
//	public void DoMouseEvent(ChatMsg cm) {
//		Color c;
//		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다.
//			return;
//		c = new Color(255, 0, 0); // 다른 사람 것은 Red
//		gc2.setColor(c);
//		gc2.fillOval(cm.mouse_e.getX() - pen_size/2, cm.mouse_e.getY() - cm.pen_size/2, cm.pen_size, cm.pen_size);
//		gc.drawImage(panelImage, 0, 0, panel);
//	}
//
//	public void SendMouseEvent(MouseEvent e) {
//		ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
//		cm.mouse_e = e;
//		cm.pen_size = pen_size;
//		SendObject(cm);
//	}
//
//	class MyMouseWheelEvent implements MouseWheelListener {
//		@Override
//		public void mouseWheelMoved(MouseWheelEvent e) {
//			// TODO Auto-generated method stub
//			if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
//				if (pen_size < 20)
//					pen_size++;
//			} else {
//				if (pen_size > 2)
//					pen_size--;
//			}
//			lblMouseEvent.setText("mouseWheelMoved Rotation=" + e.getWheelRotation() 
//				+ " pen_size = " + pen_size + " " + e.getX() + "," + e.getY());
//
//		}
//		
//	}
//	// Mouse Event Handler
//	class MyMouseEvent implements MouseListener, MouseMotionListener {
//		@Override
//		public void mouseDragged(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mouseDragged " + e.getX() + "," + e.getY());// 좌표출력가능
//			Color c = new Color(0,0,255);
//			gc2.setColor(c);
//			gc2.fillOval(e.getX()-pen_size/2, e.getY()-pen_size/2, pen_size, pen_size);
//			// panelImnage는 paint()에서 이용한다.
//			gc.drawImage(panelImage, 0, 0, panel);
//			SendMouseEvent(e);
//		}
//
//		@Override
//		public void mouseMoved(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mouseMoved " + e.getX() + "," + e.getY());
//		}
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mouseClicked " + e.getX() + "," + e.getY());
//			Color c = new Color(0,0,255);
//			gc2.setColor(c);
//			gc2.fillOval(e.getX()-pen_size/2, e.getY()-pen_size/2, pen_size, pen_size);
//			gc.drawImage(panelImage, 0, 0, panel);
//			SendMouseEvent(e);
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mouseEntered " + e.getX() + "," + e.getY());
//			// panel.setBackground(Color.YELLOW);
//
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mouseExited " + e.getX() + "," + e.getY());
//			// panel.setBackground(Color.CYAN);
//
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mousePressed " + e.getX() + "," + e.getY());
//
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			lblMouseEvent.setText(e.getButton() + " mouseReleased " + e.getX() + "," + e.getY());
//			// 드래그중 멈출시 보임
//
//		}
//	}
//
//	// keyboard enter key 치면 서버로 전송
//	class TextSendAction implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// Send button을 누르거나 메시지 입력하고 Enter key 치면
//			if (e.getSource() == btnSend || e.getSource() == txtInput) {
//				String msg = null;
//				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
//				msg = txtInput.getText();
//				SendMessage(msg);
//				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
//				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
//				if (msg.contains("/exit")) // 종료 처리
//					System.exit(0);
//			}
//		}
//	}
//
//	// 화면에 출력
//	public void AppendText(String msg) {
//		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
//		
//		StyledDocument doc = textArea.getStyledDocument();
//		SimpleAttributeSet left = new SimpleAttributeSet();
//		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
//		StyleConstants.setForeground(left, Color.BLACK);
//	    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//		try {
//			doc.insertString(doc.getLength(), msg+"\n", left );
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		int len = textArea.getDocument().getLength();
//		textArea.setCaretPosition(len);
//
//	}
//	
//	// 화면 우측에 출력
//	public void AppendTextR(String msg) {
//		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.	
//		StyledDocument doc = textArea.getStyledDocument();
//		SimpleAttributeSet right = new SimpleAttributeSet();
//		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
//		StyleConstants.setForeground(right, Color.BLUE);	
//	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
//		try {
//			doc.insertString(doc.getLength(),msg+"\n", right );
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		int len = textArea.getDocument().getLength();
//		textArea.setCaretPosition(len);
//
//	}

	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			ChatMsg obcm = new ChatMsg(UserName, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
//			AppendText("oos.writeObject() error");
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
//			AppendText("SendObject Error");
		}
	}
}