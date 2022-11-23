import java.io.Serializable;

public class Room implements Serializable {
	public int roomNumber; // 방 번호
	public String roomTitle; // 방 이름
	public String roomManager; // 방장
	public int roomCount = 0; // 방 인원
	public int roundTime = 10; // 라운드 시간
	public int roundCount = 4; // 라운드 수
	public String userList = ""; // 같은 방에 입장한 유저 정보 저장
	public boolean start = false; // 게임 시작했는지 flag
	
	public void setUserList(String userName) {
		userList += userName + "@";
	}
}