package game.shaders;

import org.joml.Matrix4f;

import gameEngine.common.Maths;
import gameEngine.components.Camera;
import gameEngine.components.Light;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/com/Game/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/com/Game/shaders/fragmentShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix4f(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix4f(location_projectionMatrix, projection);
	}
	
	public void LoadViewMatrix(Camera camera) {
		super.loadMatrix4f(location_viewMatrix, Maths.createViewMatrix(camera));
	}
	
	public void LoadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
	}
}
