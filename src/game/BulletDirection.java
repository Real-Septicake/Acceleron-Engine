package game;

import org.joml.Vector3d;

public enum BulletDirection {
	
	Up(new Vector3d(0, 1, 0)),
	Down(new Vector3d(0, -1, 0)),
	Left(new Vector3d(-1, 0, 0)),
	Right(new Vector3d(1, 0, 0));
	
	private Vector3d direction;
	
	public Vector3d getDirection() { return direction; }
	
	
	private BulletDirection(Vector3d direction) {
		this.direction = direction;
	}
}
