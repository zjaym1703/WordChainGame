import java.io.Serializable;

public class UserDTO implements Serializable {
	private String name;
	private int score;
	private String status;
	private String mode; // 게임모드 
	
	public UserDTO(String name, int score, String status,String mode) {
		this.name = name;
		this.score = score;
		this.status = status;
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
}
