package gameEngine.rendering.shaders;

import java.util.List;

import org.joml.*;

import gameEngine.common.Maths;
import gameEngine.components.rendering.*;

public class StaticShader extends ShaderProgram {

	private static final int MAX_LIGHTS = 8;
	private static final String VERTEX_FILE = "/gameEngine/rendering/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "/gameEngine/rendering/shaders/fragmentShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_numOfRows;
	private int location_atlasOffset;
	
	private int location_lightPosition[];
	private int location_lightColor[];
	
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
		location_numOfRows = super.getUniformLocation("numOfRows");
		location_atlasOffset = super.getUniformLocation("atlasOffset");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		
		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			
		}
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix4f(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix4f(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix4f(location_viewMatrix, Maths.createViewMatrix(camera));
	}
	
	public void loadAtlasRows(int rows) {
		super.loadFloat(location_numOfRows, rows);
	}
	
	public void loadAtlasOffset(double[] offset) {
		super.load2DVectorFromArray(location_atlasOffset, offset);
	}
	
	public void loadLight(List<Light> light) {
		for (int i = 0; i < MAX_LIGHTS; i++) {
			if(i < light.size()) {
				super.loadVector(location_lightPosition[i], light.get(i).gameObject.transform.position);
				super.loadVector(location_lightColor[i], light.get(i).color);
			}
			else {
				super.loadVector(location_lightPosition[i], new Vector3d(0,0,0));
				super.loadVector(location_lightColor[i], new Vector3d(0,0,0));
			}
		}
	}
}
