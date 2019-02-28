package gameEngine.rendering.gui;

import org.joml.Vector2d;
import org.joml.Vector3d;

public enum UIAnchorPoint {
	TopLeft(new Vector2d(-1, -1)),
	TopCenter(new Vector2d(0, -1)),
	TopRight(new Vector2d(1, -1)),
	Left(new Vector2d(-1, 0)),
	Center(new Vector2d(0, 0)),
	Right(new Vector2d(1, 0)),
	BottomLeft(new Vector2d(-1, 1)),
	BottomCenter(new Vector2d(0, 1)),
	BottomRight(new Vector2d(1, 1));
	
	private Vector3d anchorOffset;
	
	public Vector3d getAnchorPosition() {
		return anchorOffset;
	}
	
	private UIAnchorPoint(Vector2d anchorPosition) {
		anchorOffset = new Vector3d(anchorPosition.x, anchorPosition.y, 0);
	}
}
