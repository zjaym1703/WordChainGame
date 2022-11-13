import java.awt.EventQueue;
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

public class WordChainGameClientRoomView extends JFrame{

	private JPanel contentPanel;
	
	//image
	private Image background = new ImageIcon("images/background1.png").getImage();
	private Image scoreBackground = new ImageIcon("images/score.png").getImage();
	private Image timerBackground = new ImageIcon("images/timer.png").getImage();
	private Image resizeBackground = timerBackground.getScaledInstance(120, 120,Image.SCALE_SMOOTH);
	
	private ImageIcon happyEmo = new ImageIcon("images/emoticon_happy.png");
	private ImageIcon loveEmo = new ImageIcon("images/emoticon_love.png");
	private ImageIcon supriseEmo = new ImageIcon("images/emoticon_suprise.png");
	private ImageIcon sleepEmo = new ImageIcon("images/emoticon_sleep.png");
	private ImageIcon AngryEmo = new ImageIcon("images/emoticon_angry.png");
	private ImageIcon sendButtonBasicImage = new ImageIcon("images/sendButtonBasic.png");
	private ImageIcon sendButtonEnteredImage = new ImageIcon("images/sendButtonEntered.png");
	private JButton sendButton = new JButton(sendButtonBasicImage);
	
	private JTextField textField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordChainGameClientRoomView window = new WordChainGameClientRoomView();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WordChainGameClientRoomView() {
		initialize();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("kkutu");
		setSize(700, 700);
		setResizable(false);
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//content panel에 이미지 추가
		contentPanel = new JPanel() {
			public void paintComponent(Graphics g) {
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
                setOpaque(false);
                super.paintComponent(g);
            }
		};
		contentPanel.setBorder(new EmptyBorder(5, 5, 5 ,5));
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
		scorePanel.setBounds(45, 78, 417, 113);
		scorePanel.setLayout(null);
		contentPanel.add(scorePanel);
		
		JLabel wordLabel = new JLabel("단어");
		wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		wordLabel.setForeground(new Color(251, 255, 250));
		wordLabel.setBounds(6, 56, 405, 51);
		scorePanel.add(wordLabel);
		
		JPanel timerPanel = new JPanel() {
			public void paintComponent(Graphics g) {
                g.drawImage(timerBackground, 0, 0, this.getWidth(), this.getHeight(), null);
                setOpaque(false);
                super.paintComponent(g);
            }
		};
		timerPanel.setBounds(521, 78, 120, 120);
		timerPanel.setLayout(null);
		
		JLabel scoreLabel = new JLabel("30");
		scoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setForeground(new Color(251, 255, 250));
		scoreLabel.setBounds(6, 35, 108, 50);
		timerPanel.add(scoreLabel);
		contentPanel.add(timerPanel);
		
		//이미지 버튼 추가
		happyEmo = imageSetSize(happyEmo,30,30);
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
		
		loveEmo = imageSetSize(loveEmo,30,30);
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
		
		supriseEmo = imageSetSize(supriseEmo,30,30);
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
		
		sleepEmo = imageSetSize(sleepEmo,30,30);
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
		
		AngryEmo = imageSetSize(AngryEmo,30,30);
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
	}
	
	ImageIcon imageSetSize(ImageIcon icon, int i, int j) { // image Size Setting
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
	}
}
