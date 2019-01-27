package game.scripts;

import org.joml.*;

import game.textures.ModelTexture;
import gameEngine.common.*;
import gameEngine.components.*;
import gameEngine.rendering.MasterRenderer;
import gameEngine.rendering.meshData.*;

public class GameManager extends StaticScript {

	private double currentRotation;
	
	private TexturedMeshLowLevel floorTextured;
	private TexturedMeshLowLevel sphereTextured;
	private TexturedMeshLowLevel texturedTree;
	
	@Override
	public void update() {
		currentRotation += 0.25;
		
		Quaterniond rot = Maths.fromEulerAngle(new Vector3d(0,currentRotation,0));
		for (float x = -10; x <= 10; x+= 0.25f) {
			for (float y = -10; y <= 10; y+= 0.25f) {
				for (float z = -10; z <= 10; z+= 0.25f) {
					float distance = x * x + y * y + z * z;
					if((distance <= 100 && distance > 98) || (distance <= 5 && distance > 3)) {
						//count++;
						MasterRenderer.drawMesh(sphereTextured, new Vector3d(x, y, z).rotate(rot).add(new Vector3d(0, 10, -15)), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
					}
				}
			}
		}
			
		for (int i = 1; i <= 5; i++) {
			MasterRenderer.drawMesh(texturedTree, new Vector3d(10 + (i * (i / 10f)) * 10, 0, -15), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1).mul(i));
		}
		
		MasterRenderer.drawMesh(floorTextured, new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), new Vector3d(100, 1, 100));
	}

	@Override
	public void start() {
		LowLevelLoader loader = MainGame.gameEngine.getLoader();
		GameObject cameraGm = new GameObject(new Vector3d(0, 2, 0), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		cameraGm.name = "Camera / Player Object";
		cameraGm.addComponent(Camera.class);
		cameraGm.addComponent(CameraMover.class);
		// At tutorial 12
		
		GameObject lightGm1 = new GameObject(new Vector3d(-100, 100, 100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		Light light = (Light) lightGm1.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm1.name = "Light Object #1";
		
		GameObject lightGm2 = new GameObject(new Vector3d(-100, 100, -100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm2.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm2.name = "Light Object #2";
		
		GameObject lightGm3 = new GameObject(new Vector3d(100, 100, 100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm3.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm3.name = "Light Object #3";
		
		GameObject lightGm4 = new GameObject(new Vector3d(100, 100, -100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm4.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm4.name = "Light Object #4";

		MeshLowLevel mesh = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("Tree"));
		texturedTree = new TexturedMeshLowLevel(mesh, treeTexture);
		
		MeshLowLevel sphereMesh = OBJLoader.loadObjModel("Sphere", loader);
		ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphere_UV"));
		sphereTextured = new TexturedMeshLowLevel(sphereMesh, sphereTexture);
		
		MeshLowLevel floorMesh = OBJLoader.loadObjModel("Floor", loader);
		ModelTexture floorTexture = new ModelTexture(loader.loadTexture("floor UV"));
		floorTextured = new TexturedMeshLowLevel(floorMesh, floorTexture);
	}

}
