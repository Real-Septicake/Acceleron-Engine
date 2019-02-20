package gameEngine.common;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import gameEngine.debug.Debug;

public class InputManager {
	
	private static HashMap<Integer, Boolean> currentKeys = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> lastKeys = new HashMap<Integer, Boolean>();
	
	public static void inputUpdate() {
		lastKeys = new HashMap<Integer, Boolean>(currentKeys);
	}
	
	public static void setup(long windowID) {
		glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
			updateKey(key, action);
		});
	}
	
	public static void updateKey(int key, int action) {
		if(action == GLFW_RELEASE || action == GLFW_PRESS)
			currentKeys.put(key, action == GLFW_PRESS);
	}
	
	public static boolean keyDown(int id) {
		if(currentKeys.get(id) == null)
			return false;
		
		boolean b = currentKeys.get(id);
		return b;
	}
	
	public static boolean keyIsDown(int id) {
		if (currentKeys.get(id) == null) {
			return false;
		}
		
		if (currentKeys.get(id) == true && (lastKeys.get(id) == null || lastKeys.get(id) == false)) {
			return true;
		}
		return false;
	}
	
	public static boolean keyUp(int id) {
		if (currentKeys.get(id) == null) {
			return false;
		}
		
		if (currentKeys.get(id) == false && (lastKeys.get(id) != null && lastKeys.get(id) == true)) {
			return true;
		}
		return false;
	}
}
