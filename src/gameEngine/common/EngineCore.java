package gameEngine.common;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

import java.util.Timer;

import org.joml.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import game.models.Mesh;
import game.models.TexturedMesh;
import game.renderEngine.*;
import gameEngine.rendering.shaders.*;
import game.textures.ModelTexture;
import gameEngine.components.Camera;
import gameEngine.components.Entity;
import gameEngine.components.Light;
import gameEngine.components.MeshRenderer;

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
		
		Entity cameraGm = new Entity(new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		Camera camera = (Camera) cameraGm.addComponent(Camera.class);
		// At tutorial 12

		Mesh mesh = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("Tree"));
		TexturedMesh texturedMesh = new TexturedMesh(mesh, texture);
		Entity tree = new Entity(new Vector3d(0, 0, -3), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		MeshRenderer renderer = (MeshRenderer)tree.addComponent(MeshRenderer.class);
		renderer.mesh = texturedMesh;
		
		Mesh sphereMesh = OBJLoader.loadObjModel("Sphere", loader);
		ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphere_UV"));
		TexturedMesh sphereTextured = new TexturedMesh(sphereMesh, sphereTexture);

		Light light = new Light(new Vector3f(-300, 300, 300), new Vector3f(0.5f, 0.5f, 0.5f));
		//StaticShader shader = new StaticShader();
		//Renderer render = new Renderer(shader);

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
			
			cameraGm.transform.position.add(movement.mul(0.2));
			cameraGm.transform.rotation.add(rotation);

			for (int i = 0; i < 4; i++) {
				MasterRenderer.drawMesh(sphereTextured, new Vector3d(-5, i * 5, -10), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
				MasterRenderer.drawMesh(sphereTextured, new Vector3d(-5, i * 5, -20), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
				MasterRenderer.drawMesh(sphereTextured, new Vector3d(5, i * 5, -10), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
				MasterRenderer.drawMesh(sphereTextured, new Vector3d(5, i * 5, -20), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
			}
			
			for (float x = -5; x <= 5; x+= 0.25f) {
				for (float y = -5; y <= 5; y+= 0.25f) {
					for (float z = -5; z <= 5; z+= 0.25f) {
						if(x * x + y * y + z * z <= 25.5 && x * x + y * y + z * z > 24) {
							MasterRenderer.drawMesh(sphereTextured, new Vector3d(x, 5 + y, -15 + z), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
						}
					}
				}
			}
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
