package pz.pacman.engine.entities;

public class TopScorerData {

	private String nickname;
	private int points;
	private int time;
	
	public TopScorerData(String nickname, int points, int time) {
		this.nickname = nickname;
		this.points = points;
		this.time = time;
	}

	public String getNickname() {
		return nickname;
	}

	public int getPoints() {
		return points;
	}

	public int getTime() {
		return time;
	}	
}
