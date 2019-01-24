package game.renderEngine;

import org.joml.*;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import game.models.*;
import game.shaders.StaticShader;
import game.textures.ModelTexture;
import gameEngine.components.Camera;
import gameEngine.components.Entity;
import gameEngine.components.Light;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class DisplayManager {

	// The window handle
	private long window;
	public static DisplayManager manager;

	private final String windowName = "Game Window";
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	
	public int getCurrentWidth() {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, width, height);
		return width.get();
	}
	
	public int getCurrentHeight() {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, width, height);
		return height.get();
	}

	public long getWindowID() {
		return window;
	}
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		//glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		//glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, windowName, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated
		// or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	public void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		//glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
		Loader loader = new Loader();
		Camera camera = new Camera();
		//At tutorial 12
		Mesh mesh = OBJLoader.loadObjModel("Tree", loader);//loader.loadToVAO(vertices, indices, textureCoords);
		ModelTexture texture = new ModelTexture(loader.loadTexture("Tree"));
		TexturedMesh texturedMesh = new TexturedMesh(mesh, texture);
		Entity block = new Entity(texturedMesh, new Vector3d(0,-2,-1), new Vector3d(0,0,0), new Vector3d(1, 1, 1));
		
		Light light = new Light(new Vector3f(-3, 4, -1), new Vector3f(0.5f, 0.5f, 0.5f));
		MasterRenderer mRenderer = new MasterRenderer();
		StaticShader shader = new StaticShader();
		Renderer render = new Renderer(shader);
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!glfwWindowShouldClose(window)) {
			//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			//block.move(new Vector3d(0.0005,0,-0.05));
			//block.rotate(new Vector3d(0,1,0));
			if(glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
				camera.move(new Vector3d(0, 0, -0.1));
			if(glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
				camera.move(new Vector3d(0, 0, 0.1));
			if(glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
				camera.move(new Vector3d(-0.1, 0, 0));
			if(glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
				camera.move(new Vector3d(0.1, 0, 0));
			if(glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS)
				camera.rotate(new Vector3d(0, -1, 0));
			if(glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS)
				camera.rotate(new Vector3d(0, 1, 0));
			
			/*for(Entity cube : entityArray) {
				mRenderer.processEntity(cube);
			}*/
			//block.setPosition(new Vector3d(-2,-1,-2));
			//mRenderer.processEntity(block);
			//mRenderer.render(light, camera);
			
			block.transform.position = new Vector3d(2,-1,-2);
			render.Clear();
			shader.Start();
			shader.LoadViewMatrix(camera);
			shader.LoadLight(light);
			render.Render(block);
			shader.Stop();
			
			glfwSwapBuffers(window); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
		loader.clean();
		mRenderer.clean();
	}
/*
	public static void main(String[] args) {
		DisplayManager.manager = new DisplayManager();
		DisplayManager.manager.run();
	}*/
}