package gameEngine.common;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.joml.Vector2d;

import gameEngine.debug.Debug;
import gameEngine.rendering.WindowManager;

public class InputManager {
	
	private static HashMap<Integer, Boolean> currentKeys = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> lastKeys = new HashMap<Integer, Boolean>();
	
	private static Vector2d mousePosition = new Vector2d(0, 0);
	private static Vector2d oldMousePosition = new Vector2d(0, 0);
	private static Vector2d mouseDelta = new Vector2d(0, 0);
	private static HashMap<Integer, Boolean> currentMouseButtons = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> lastMouseButtons = new HashMap<Integer, Boolean>();
	private static boolean mouseLocked = true;
	
	//Run an update on inputs, send old ones to the last state storage
	public static void inputUpdate() {
		
		lastKeys = new HashMap<Integer, Boolean>(currentKeys);
		lastMouseButtons = new HashMap<Integer, Boolean>(lastMouseButtons);
		
		Vector2d mouseOldPos = (mouseLocked) ? new Vector2d(WindowManager.manager.getCurrentWidth() / 2, WindowManager.manager.getCurrentHeight() / 2) : oldMousePosition;
		
		mouseDelta = new Vector2d(mousePosition.x - mouseOldPos.x, mousePosition.y - mouseOldPos.y);
		oldMousePosition = new Vector2d(mousePosition.x, mousePosition.y);
		if (mouseLocked) {
			glfwSetCursorPos(WindowManager.manager.getWindowID(), mouseOldPos.x, mouseOldPos.y);
		}
	}
	
	//Setup the callbacks that are used for getting inputs
	public static void setup(long windowID) {
		glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
			updateKey(key, action);
		});
		
		glfwSetCursorPosCallback(windowID, (window, xpos, ypos) -> {
			mouseMoveHandler(window, xpos, ypos);
		});
		
		glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> { 
			mouseButtonHandler(window, button, action);
		});
		
		setMouseLocked(true);
	}
	
	//Update a keyboard input
	private static void updateKey(int key, int action) {
		if(action == GLFW_RELEASE || action == GLFW_PRESS)
			currentKeys.put(key, action == GLFW_PRESS);
	}
	
	//Update the mouse position
	private static void mouseMoveHandler(long window, double xpos, double ypos) {
		mousePosition = new Vector2d(xpos, ypos);
	}
	
	//Update the mouse buttons
	private static void mouseButtonHandler(long window, int key, int action) {
		if(action == GLFW_RELEASE || action == GLFW_PRESS)
			currentMouseButtons.put(key, action == GLFW_PRESS);
	}
	
	public static void setMouseLocked(boolean isLocked) {
		if (isLocked) {
			glfwSetInputMode(WindowManager.manager.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		}
		mouseLocked = isLocked;
	}
	
	public static boolean isMouseLocked() {
		return mouseLocked;
	}
	
	//Get if a mouse button is down
	public static boolean mouseButtonDown(int id) {
		if(currentMouseButtons.get(id) == null)
			return false;
		
		boolean b = currentMouseButtons.get(id);
		return b;
	}
	
	//Get if a mouse button is now down
	public static boolean mouseButtonIsDown(int id) {
		if (currentMouseButtons.get(id) == null) {
			return false;
		}
		
		if (currentMouseButtons.get(id) == true && (lastMouseButtons.get(id) == null || lastMouseButtons.get(id) == false)) {
			return true;
		}
		return false;
	}
	
	//Get if a mouse button is now up
	public static boolean mouseButtonUp(int id) {
		if (currentMouseButtons.get(id) == null) {
			return false;
		}
		
		if (currentMouseButtons.get(id) == false && (lastMouseButtons.get(id) != null && lastMouseButtons.get(id) == true)) {
			return true;
		}
		return false;
	}
	
	//Get the mouse position
	public static Vector2d mousePosition() {
		return new Vector2d(mousePosition.x, mousePosition.y);
	}
	
	//Get the mouse delta
	public static Vector2d mouseDelta() {
		return new Vector2d(mouseDelta.x, mouseDelta.y);
	}
	
	//Get if a key input is down
	public static boolean keyDown(int id) {
		if(currentKeys.get(id) == null)
			return false;
		
		boolean b = currentKeys.get(id);
		return b;
	}
	
	//Get if a key input is now down
	public static boolean keyIsDown(int id) {
		if (currentKeys.get(id) == null) {
			return false;
		}
		
		if (currentKeys.get(id) == true && (lastKeys.get(id) == null || lastKeys.get(id) == false)) {
			return true;
		}
		return false;
	}
	
	//Get if a key input is now up
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
