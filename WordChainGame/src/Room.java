import java.io.Serializable;
import java.util.Vector;

public class Room implements Serializable {
	public int roomNumber = 0; // 방 번호
	public String roomTitle; // 방 이름
	public String roomManager; // 방장
	public int roomCount = 0; // 방 인원
	public int roundTime = 10; // 라운드 시간
	public int roundCount = 4; // 라운드 수
	public Vector<String> userVector; // 같은 방에 입장한 유저 정보 저장
	
	public Room() {
		userVector = new Vector<String>();
	}
}