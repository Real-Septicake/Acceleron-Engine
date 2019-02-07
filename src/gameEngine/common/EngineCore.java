package gameEngine.common;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import java.sql.Time;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import game.scripts.MainGame;
import gameEngine.components.essentials.GameObject;
import gameEngine.debug.Debug;
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
	long lastFrameTime = 0;
	double timeDelta;
	
	private void gameLoop(Game game) {
		
		GL.createCapabilities();
		
		long windowID = WindowManager.manager.getWindowID();
		
		InputManager.setup(windowID);
		game.startGame();
		
		while (!glfwWindowShouldClose(windowID)) {
			
			if(lastFrameTime == 0) {
				timeDelta = 0;
				lastFrameTime = System.currentTimeMillis();
			}
			else {
				timeDelta = (System.currentTimeMillis() - lastFrameTime) / 1000d;
				lastFrameTime = System.currentTimeMillis();
			}
			
			UpdateHandler.RunUpdate(timeDelta);
				
			MasterRenderer.render();
			
			GameObject.clearOld();
	        
			frames++;
			
	        if (System.currentTimeMillis() - timer > 1000) {
	        	boolean RENDER_TIME = true;
	            if (RENDER_TIME) {
	            	glfwSetWindowTitle(WindowManager.manager.getWindowID(), WindowManager.manager.windowName + String.format(" FPS: %s", frames));
	            }
	            frames = 0;
	            timer += 1000;
	        }
			
	        glfwSwapBuffers(windowID); // swap the color buffers
	        
	        //Get all of the glfw events
	        glfwPollEvents();
			
			GL11.glViewport(0, 0, WindowManager.manager.getCurrentWidth(), WindowManager.manager.getCurrentHeight());
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
