package game;

import org.joml.Vector3d;

public class Player {

	public Vector3d position;
	public final double movementSpeed = 3f;
	public int teamId;
	
	public Player (Vector3d pos, int teamId) {
		this.position = pos;
		this.teamId = teamId;
	}

}
