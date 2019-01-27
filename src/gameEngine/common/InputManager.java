package gameEngine.common;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Dictionary;
import java.util.Hashtable;

public class InputManager {
	
	private static Dictionary<Integer, Boolean> currentKeys = new Hashtable<Integer, Boolean>();
	
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
}
