// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	// 100:게임입장, 400:로그아웃, 200:채팅메시지, 300:Image, 500: Mouse Event
	public String code;
	public String UserName;
	public Object data;
	public ImageIcon img;
	public MouseEvent mouse_e;
	public int pen_size; // pen size

	public ChatMsg(String UserName, String code, Object msg) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
	}
}