import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordChainGameClientMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserNickname;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;
	
	private ImageIcon screenImage = new ImageIcon("images/login.png");
	private Image loginBackground = screenImage.getImage();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WordChainGameClientMain frame = new WordChainGameClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WordChainGameClientMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(310, 500);
		setResizable(false); // 화면 조절 금지
		setLocationRelativeTo(null); // 화면의 정가운데 위치
		
		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
                g.drawImage(loginBackground, 0, 0, screenImage.getIconWidth(), screenImage.getIconHeight(), null);
                setOpaque(false); // 그림을 표시하게 설정, 투명하게 조절
                super.paintComponent(g);
            }
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NickName");
		lblNewLabel.setBounds(49, 241, 82, 33);
		contentPane.add(lblNewLabel);
		
		txtUserNickname = new JTextField();
		txtUserNickname.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserNickname.setBounds(138, 241, 116, 33);
		contentPane.add(txtUserNickname);
		txtUserNickname.setColumns(10);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(49, 284, 82, 33);
		contentPane.add(lblIpAddress);
		
		txtIpAddress = new JTextField();
		txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtIpAddress.setText("127.0.0.1");
		txtIpAddress.setColumns(10);
		txtIpAddress.setBounds(138, 284, 116, 33);
		contentPane.add(txtIpAddress);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(49, 327, 82, 33);
		contentPane.add(lblPortNumber);
		
		txtPortNumber = new JTextField();
		txtPortNumber.setText("30000");
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setColumns(10);
		txtPortNumber.setBounds(138, 327, 116, 33);
		contentPane.add(txtPortNumber);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(49, 384, 205, 38);
		contentPane.add(btnConnect);
		Myaction action = new Myaction();
		btnConnect.addActionListener(action);
		txtUserNickname.addActionListener(action);
		txtIpAddress.addActionListener(action);
		txtPortNumber.addActionListener(action);
		
		revalidate();
		repaint();
	}
	
	class Myaction implements ActionListener { // 내부클래스로 액션 이벤트 처리 클래스
		@Override
		public void actionPerformed(ActionEvent e) {
			String nickname = txtUserNickname.getText().trim();
			String ip_addr = txtIpAddress.getText().trim();
			String port_no = txtPortNumber.getText().trim();
			WaitingRoom view = new WaitingRoom(nickname, ip_addr, port_no);
			setVisible(false);
		}
	}
}


