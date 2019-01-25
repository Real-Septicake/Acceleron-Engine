package gameEngine.common;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;
import org.joml.Math;
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
		DisplayManager.manager = new DisplayManager();
		DisplayManager.manager.init();
		gameLoop();

		closeGame();
	}

	private void gameLoop() {

		GL.createCapabilities();

		Loader loader = new Loader();
		
		Entity cameraGm = new Entity(new Vector3d(0, 2, 0), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		Camera camera = (Camera) cameraGm.addComponent(Camera.class);
		// At tutorial 12

		Mesh mesh = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("Tree"));
		TexturedMesh texturedTree = new TexturedMesh(mesh, treeTexture);
		//Entity tree = new Entity(new Vector3d(0, 0, -3), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		//MeshRenderer renderer = (MeshRenderer)tree.addComponent(MeshRenderer.class);
		//renderer.mesh = texturedMesh;
		
		Mesh sphereMesh = OBJLoader.loadObjModel("Sphere", loader);
		ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphere_UV"));
		TexturedMesh sphereTextured = new TexturedMesh(sphereMesh, sphereTexture);
		
		Mesh floorMesh = OBJLoader.loadObjModel("Floor", loader);
		ModelTexture floorTexture = new ModelTexture(loader.loadTexture("floor UV"));
		TexturedMesh floorTextured = new TexturedMesh(floorMesh, floorTexture);

		Light light = new Light(new Vector3f(-300, 300, 300), new Vector3f(0.5f, 0.5f, 0.5f));

		long windowID = DisplayManager.manager.getWindowID();
		
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
			if (glfwGetKey(windowID, 90) == GLFW_PRESS)
				rotation.x = 1;
			// Look down
			if (glfwGetKey(windowID, 88) == GLFW_PRESS)
				rotation.x = -1;
			// Down
			if (glfwGetKey(windowID, 340) == GLFW_PRESS)
				movement.y = -1;
			// Up
			if (glfwGetKey(windowID, 32) == GLFW_PRESS)
				movement.y = 1;
			
			cameraGm.transform.rotation.add(rotation);
			movement = Maths.fromEulerAngle(cameraGm.transform.rotation) * movement;
			
			cameraGm.transform.position.add(movement.mul(0.2));

			for (float x = -10; x <= 10; x+= 0.25f) {
				for (float y = -10; y <= 10; y+= 0.25f) {
					for (float z = -10; z <= 10; z+= 0.25f) {
						float distance = x * x + y * y + z * z;
						if((distance <= 100 && distance > 98) || (distance <= 5 && distance > 3)) {
							MasterRenderer.drawMesh(sphereTextured, new Vector3d(x, 10 + y, -15 + z), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
						}
					}
				}
			}
			
			for (int i = 1; i <= 5; i++) {
				MasterRenderer.drawMesh(texturedTree, new Vector3d(10 + (i * (i / 10f)) * 10, 0, -15), new Vector3d(0,0,0), new Vector3d(1,1,1).mul(i));
			}
			
			MasterRenderer.drawMesh(floorTextured, new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), new Vector3d(100, 0.01, 100));
			
			MasterRenderer.render(light, camera);

			glfwSwapBuffers(windowID); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			GL11.glViewport(0, 0, DisplayManager.manager.getCurrentWidth(), DisplayManager.manager.getCurrentHeight());
		}
		
		MasterRenderer.clean();
		//shader.Clean();
	}

	private void closeGame() {
		glfwFreeCallbacks(DisplayManager.manager.getWindowID());
		glfwDestroyWindow(DisplayManager.manager.getWindowID());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
