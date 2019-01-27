package gameEngine.common;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import game.scripts.MainGame;
import gameEngine.components.GameObject;
import gameEngine.rendering.*;

public class EngineCore {

	public void initializeEngine(MainGame game) {
		WindowManager.manager = new WindowManager();
		WindowManager.manager.init();
		gameLoop(game);
		
		closeGame();
	}
	
	int frames = 0;
	long timer = System.currentTimeMillis();
	LowLevelLoader loader;
	
	public LowLevelLoader getLoader() {
		return loader;
	}
	
	private void gameLoop(Game game) {
		
		GL.createCapabilities();

		loader = new LowLevelLoader();
		
		new MasterRenderer(loader);
		
		long windowID = WindowManager.manager.getWindowID();
		
		InputManager.setup(windowID);
		game.startGame();
		
		while (!glfwWindowShouldClose(windowID)) {
			
			UpdateHandler.RunUpdate();
				
			MasterRenderer.render();
	        
	        glfwSwapBuffers(windowID); // swap the color buffers
	        
	        // Poll for window events. The key callback above will only be
	        // invoked during this call.
	        glfwPollEvents();
			GL11.glViewport(0, 0, WindowManager.manager.getCurrentWidth(), WindowManager.manager.getCurrentHeight());
			
			frames++;
			
	        if (System.currentTimeMillis() - timer > 1000) {
	        	boolean RENDER_TIME = true;
	            if (RENDER_TIME) {
	            	glfwSetWindowTitle(WindowManager.manager.getWindowID(), WindowManager.manager.windowName + String.format(" FPS: %s", frames));
	            }
	            frames = 0;
	            timer += 1000;
	        }
	        GameObject.clearOld();
		}
		
		MasterRenderer.clean();
	}

	private void closeGame() {
		glfwFreeCallbacks(WindowManager.manager.getWindowID());
		glfwDestroyWindow(WindowManager.manager.getWindowID());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
