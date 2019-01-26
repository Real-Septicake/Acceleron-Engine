package gameEngine.common;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import game.models.*;
import game.renderEngine.*;
import game.textures.ModelTexture;
import gameEngine.components.*;

public class EngineCore {

	public static void main(String[] args) {
		EngineCore game = new EngineCore();
		game.startGame();
	}

	private void startGame() {
		WindowManager.manager = new WindowManager();
		WindowManager.manager.init();
		gameLoop();

		closeGame();
	}

	private void gameLoop() {

		GL.createCapabilities();

		LowLevelLoader loader = new LowLevelLoader();
		
		GameObject cameraGm = new GameObject(new Vector3d(0, 2, 0), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		cameraGm.name = "Camera / Player Object";
		Camera camera = (Camera) cameraGm.addComponent(Camera.class);
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

		Mesh mesh = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("Tree"));
		TexturedMesh texturedTree = new TexturedMesh(mesh, treeTexture);
		
		Mesh sphereMesh = OBJLoader.loadObjModel("Sphere", loader);
		ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphere_UV"));
		TexturedMesh sphereTextured = new TexturedMesh(sphereMesh, sphereTexture);
		
		Mesh floorMesh = OBJLoader.loadObjModel("Floor", loader);
		ModelTexture floorTexture = new ModelTexture(loader.loadTexture("floor UV"));
		TexturedMesh floorTextured = new TexturedMesh(floorMesh, floorTexture);

		long windowID = WindowManager.manager.getWindowID();
		double currentRotation = 0;
		
		while (!glfwWindowShouldClose(windowID)) {

			Vector3d movement = new Vector3d(0,0,0);
			Vector3d rotation = new Vector3d(0,0,0);
			// Forward
			if (glfwGetKey(windowID, GLFW_KEY_W) == GLFW_PRESS)
				movement.z = -1;
			// Backward
			if (glfwGetKey(windowID, GLFW_KEY_S) == GLFW_PRESS)
				movement.z = 1;
			// Left
			if (glfwGetKey(windowID, GLFW_KEY_A) == GLFW_PRESS)
				movement.x = -1;
			// Right
			if (glfwGetKey(windowID, GLFW_KEY_D) == GLFW_PRESS)
				movement.x = 1;
			// Look left
			if (glfwGetKey(windowID, GLFW_KEY_Q) == GLFW_PRESS)
				rotation.y = -1;
			// Look right
			if (glfwGetKey(windowID, GLFW_KEY_E) == GLFW_PRESS)
				rotation.y = 1;
			// Look up
			if (glfwGetKey(windowID, GLFW_KEY_Z) == GLFW_PRESS)
				rotation.x = 1;
			// Look down
			if (glfwGetKey(windowID, GLFW_KEY_X) == GLFW_PRESS)
				rotation.x = -1;
			// Down
			if (glfwGetKey(windowID, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
				movement.y = -1;
			// Up
			if (glfwGetKey(windowID, GLFW_KEY_SPACE) == GLFW_PRESS)
				movement.y = 1;
			
			cameraGm.transform.rotation.add(rotation);
			movement.rotate(Maths.fromEulerAngle(cameraGm.transform.rotation));
			
			cameraGm.transform.position.add(movement.mul(0.2));
			currentRotation += 0.25;
			
			//int count = 0;
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
			//System.out.println(count);
			
			for (int i = 1; i <= 5; i++) {
				MasterRenderer.drawMesh(texturedTree, new Vector3d(10 + (i * (i / 10f)) * 10, 0, -15), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1).mul(i));
			}
			
			MasterRenderer.drawMesh(floorTextured, new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), new Vector3d(100, 1, 100));
			
			MasterRenderer.render(camera);

			glfwSwapBuffers(windowID); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			GL11.glViewport(0, 0, WindowManager.manager.getCurrentWidth(), WindowManager.manager.getCurrentHeight());
		}
		
		MasterRenderer.clean();
		//shader.Clean();
	}

	private void closeGame() {
		glfwFreeCallbacks(WindowManager.manager.getWindowID());
		glfwDestroyWindow(WindowManager.manager.getWindowID());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
