import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CreateRoom extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JFrame createRoom;
	
	private ImageIcon screenImage = new ImageIcon("images/createRoom.png");
	private Image createRoomBackground = screenImage.getImage();
	private ImageIcon sureButtonImage = new ImageIcon("images/createRoomAdd.png");
	private JButton sureButton = new JButton(sureButtonImage);
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public String title; // 방 제목
	public String personNumber; // 인원
	public String type; // 게임 유형
	public String roundNumber; // 라운드 수
	public String roundTime; // 라운드 시간
	
	public CreateRoom(JFrame createRoomFrame) {
		this.createRoom = createRoomFrame;
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(108, 13, 167, 18);
		add(textField);
		textField.setColumns(10);
	
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(108, 45, 167, 18);
		add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(108, 79, 167, 18);
		add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(108, 113, 167, 18);
		add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(108, 145, 167, 18);
		add(textField_4);
		
		sureButton.setBorderPainted(false);
		sureButton.setContentAreaFilled(false);
		sureButton.setFocusPainted(false);
		sureButton.setBounds(176, 170, 100, 39);
		sureButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				sureButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sureButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				title = textField.getText();
				personNumber = textField_1.getText();
				type = textField_2.getText();
				roundNumber = textField_3.getText();
				roundTime = textField_4.getText();
				createRoom.setVisible(false);
			}
		});
		add(sureButton);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(createRoomBackground, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
