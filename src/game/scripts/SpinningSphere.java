package game.scripts;

import org.joml.Quaterniond;
import org.joml.Vector3d;

import gameEngine.common.Maths;
import gameEngine.rendering.MasterRenderer;

public class SpinningSphere {
	public static void render(double rotation) {
		
		Quaterniond rot = Maths.fromEulerAngle(new Vector3d(0,rotation,0));
		
		for (float x = -10; x <= 10; x+= 0.25f) {
			for (float y = -10; y <= 10; y+= 0.25f) {
				for (float z = -10; z <= 10; z+= 0.25f) {
					float distance = x * x + y * y + z * z;
					if((distance <= 100 && distance > 98) || (distance <= 5 && distance > 3)) {
						if(distance <= 99 && distance > 98) {
							MasterRenderer.drawMesh(GameManager.sphereTextured.getTexturedMesh(), new Vector3d(x, y, z).rotate(rot).add(new Vector3d(0, 10, -15)), new Vector3d(0, rotation, 0), new Vector3d(2, 2, 2), 0);
						}
						else if(distance > 99) {
							MasterRenderer.drawMesh(GameManager.sphereTextured.getTexturedMesh(), new Vector3d(x, y, z).rotate(rot).add(new Vector3d(0, 10, -15)), new Vector3d(0, rotation, 0), new Vector3d(2, 2, 2), 1);
						}
						else if(distance <= 5) {
							MasterRenderer.drawMesh(GameManager.sphereTextured.getTexturedMesh(), new Vector3d(x, y, z).rotate(rot).add(new Vector3d(0, 10, -15)), new Vector3d(0, rotation, 0), new Vector3d(2, 2, 2), 2);
						}
						else {
							MasterRenderer.drawMesh(GameManager.sphereTextured.getTexturedMesh(), new Vector3d(x, y, z).rotate(rot).add(new Vector3d(0, 10, -15)), new Vector3d(0, rotation, 0), new Vector3d(2, 2, 2), 3);
						}
						//count++;
					}
				}
			}
		}
	}
}
