package gameEngine.common;

import org.lwjgl.glfw.GLFWErrorCallback;

import gameEngine.debug.Debug;

public class GLFWError extends GLFWErrorCallback{

	@Override
	public void invoke(int error, long description) {
		Debug.logError(error + ": " + description);
	}

}
