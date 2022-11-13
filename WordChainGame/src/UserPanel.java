import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;

class UserPanel extends JPanel{

	private final int width = 120;
	private final int height = 165;
	
	//상태정보
	private String name;
	private int score;
	private String status;
	
	//이미지 
	private ImageIcon kkutuImage = new ImageIcon("images/kkutu.png");
	
	//이모티콘 이미지
	private ImageIcon happyEmo = new ImageIcon("images/emoticon_happy.png");
	private ImageIcon loveEmo = new ImageIcon("images/emoticon_love.png");
	private ImageIcon supriseEmo = new ImageIcon("images/emoticon_suprise.png");
	private ImageIcon sleepEmo = new ImageIcon("images/emoticon_sleep.png");
	private ImageIcon AngryEmo = new ImageIcon("images/emoticon_angry.png");
	
	//컴포넌트 		
	private JLabel imagLabel;
	private JLabel nameLabel;
	private JLabel scoreLabel;
	private JLabel emoticonLabel;

	public UserPanel(int x,int y) {
		setOpaque(true);
		setBounds(x,y,width,height);
		setForeground(Color.gray);
		setLayout(null);
		
		//imageview 추가 
		imagLabel = new JLabel(kkutuImage);
		imagLabel.setBounds(6, 6, 108, 101);
		add(imagLabel);
		
		nameLabel = new JLabel("이름");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(0, 119, 120, 16);
		add(nameLabel);
		
		scoreLabel = new JLabel("점수");
		scoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBounds(6, 143, 108, 16);
		add(scoreLabel);
		
		emoticonLabel  = new JLabel();
		emoticonLabel.setBounds(83, 6, 30, 30);
		add(emoticonLabel);
		
	}

	
	public void setEmoticonStatus(String status) {
		this.status = status;
		
		switch(status) {
			case "happy": 
				emoticonLabel.setIcon(happyEmo);
				return;
			case "love":
				emoticonLabel.setIcon(loveEmo);
				return;
			case "suprise":
				emoticonLabel.setIcon(supriseEmo);
				return;
			case "sleep":
				emoticonLabel.setIcon(sleepEmo);
				return;
			case "angry":
				emoticonLabel.setIcon(AngryEmo);
				return;
		}
		
		
	}
	public void setName(String name) {
		this.name = name;
		nameLabel.setText(name);
	}
	
	public void setScore(int score) {
		this.score = score;
		scoreLabel.setText(Integer.toString(score));
	}
}
