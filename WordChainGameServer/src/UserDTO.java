import java.io.Serializable;

public class UserDTO implements Serializable {
	private String name;
	private int score;
	private String status;
	
	public UserDTO(String name, int score, String status) {
		this.name = name;
		this.score = score;
		this.status = status;
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
	
	
}
