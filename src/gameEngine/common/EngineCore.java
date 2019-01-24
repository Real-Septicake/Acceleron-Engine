package gameEngine.common;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.*;
import org.lwjgl.opengl.GL;

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
		game.StartGame();
	}

	private void StartGame() {
		DisplayManager.manager = new DisplayManager();
		DisplayManager.manager.init();
		GameLoop();

		CloseGame();
	}

	private void GameLoop() {

		GL.createCapabilities();

		Loader loader = new Loader();
		Camera camera = new Camera();
		// At tutorial 12

		Mesh mesh = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("Tree"));
		TexturedMesh texturedMesh = new TexturedMesh(mesh, texture);
		Entity tree = new Entity(new Vector3d(2, -1, -2), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		MeshRenderer renderer = (MeshRenderer)tree.addComponent(MeshRenderer.class);
		renderer.mesh = texturedMesh;
		
		Mesh sphereMesh = OBJLoader.loadObjModel("Sphere", loader);
		ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphere_UV"));
		TexturedMesh sphereTextured = new TexturedMesh(sphereMesh, sphereTexture);
		Entity sphereEntity = new Entity(new Vector3d(0, 0, 0), new Vector3d(0, 0, 0),
				new Vector3d(2, 2, 2));
		renderer = (MeshRenderer)sphereEntity.addComponent(MeshRenderer.class);
		renderer.mesh = sphereTextured;

		Light light = new Light(new Vector3f(-300, 300, 300), new Vector3f(0.5f, 0.5f, 0.5f));
		StaticShader shader = new StaticShader();
		Renderer render = new Renderer(shader);

		long windowID = DisplayManager.manager.getWindowID();

		while (!glfwWindowShouldClose(windowID)) {

			// Forward
			if (glfwGetKey(windowID, GLFW_KEY_W) == GLFW_PRESS)
				camera.move(new Vector3d(0, 0, -0.1));
			// Backward
			if (glfwGetKey(windowID, GLFW_KEY_S) == GLFW_PRESS)
				camera.move(new Vector3d(0, 0, 0.1));
			// Left
			if (glfwGetKey(windowID, GLFW_KEY_A) == GLFW_PRESS)
				camera.move(new Vector3d(-0.2, 0, 0));
			// Right
			if (glfwGetKey(windowID, GLFW_KEY_D) == GLFW_PRESS)
				camera.move(new Vector3d(0.2, 0, 0));
			// Look left
			if (glfwGetKey(windowID, GLFW_KEY_Q) == GLFW_PRESS)
				camera.rotate(new Vector3d(0, -1, 0));
			// Look right
			if (glfwGetKey(windowID, GLFW_KEY_E) == GLFW_PRESS)
				camera.rotate(new Vector3d(0, 1, 0));
			// Look up
			if (glfwGetKey(windowID, 90) == GLFW_PRESS)
				camera.rotate(new Vector3d(0.3, 0, 0));
			// Look down
			if (glfwGetKey(windowID, 88) == GLFW_PRESS)
				camera.rotate(new Vector3d(-0.3, 0, 0));
			// Down
			if (glfwGetKey(windowID, 340) == GLFW_PRESS)
				camera.move(new Vector3d(0, -0.2, 0));
			// Up
			if (glfwGetKey(windowID, 32) == GLFW_PRESS)
				camera.move(new Vector3d(0, 0.2, 0));

			render.Clear();
			shader.Start();

			shader.LoadViewMatrix(camera);
			shader.LoadLight(light);

			sphereEntity.transform.position = new Vector3d(0, 0, -20);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(16, 0, -20);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(16, 0, -36);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(0, 0, -36);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(0, 256, -20);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(16, 256, -20);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(16, 256, -36);
			render.Render(sphereEntity);

			sphereEntity.transform.position = new Vector3d(0, 256, -36);
			render.Render(sphereEntity);

			render.Render(tree);

			shader.Stop();

			glfwSwapBuffers(windowID); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
		
		shader.Clean();
	}

	private void CloseGame() {
		glfwFreeCallbacks(DisplayManager.manager.getWindowID());
		glfwDestroyWindow(DisplayManager.manager.getWindowID());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
