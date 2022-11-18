// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	// 100:게임입장, 400:로그아웃, 401:게임 퇴장 200:채팅메시지, 301: 방입장, 302:방생성, 307: 사용자 리스트 뿌리기, 500: Mouse Event
	public String code;
	public String UserName;
	public String data;
//	public ImageIcon img;
//	public MouseEvent mouse_e;
//	public int pen_size; // pen size
	public String roomTitle; // 방 이름
	public int roomNumber; // 방 번호

	public ChatMsg(String UserName, String code, String msg) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
	}
	
	public void SetRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	
	public void SetRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
//	public void SetTest(Room room) {
//		this.room = room;
//		for(int i=0; i < this.room.userVector.size(); i++) {
//			System.out.println("Client ChatMsg : " + this.room.userVector.get(i));
//		}
//	}
}