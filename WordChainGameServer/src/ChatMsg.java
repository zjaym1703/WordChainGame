// ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	// 100:게임입장, 400:로그아웃, 401:게임 퇴장 200:채팅메시지, 301: 방입장, 302:방생성, 307: 사용자 리스트 뿌리기, 308:방 입장 가능여부
	public String code;
	public String UserName;
	public String data;
	public String roomTitle; // 방 이름
	public int roomNumber; // 방 번호
	public String deleteUser; // 삭제 할 유저
	public boolean onStart; // 게임 시작한 방
	public int roomCount;
	public String currentQ;
	public String answerUsername;

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
	
	public void SetDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}
	
	public void SetCurrentQ(String currentQ) {
		this.currentQ = currentQ;
	}
}