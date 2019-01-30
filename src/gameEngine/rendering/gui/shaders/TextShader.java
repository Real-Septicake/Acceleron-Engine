package gameEngine.rendering.gui.shaders;

import org.joml.Matrix4f;

import gameEngine.rendering.shaders.ShaderProgram;

public class TextShader extends ShaderProgram{

	private static final String VERTEX_SHADER = "/gameEngine/rendering/gui/shaders/textVertexShader.txt";
	private static final String FRAGMENT_SHADER = "/gameEngine/rendering/gui/shaders/textFragmentShader.txt";
	
	private int location_transformationMatrix;
	
	public TextShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix4f(location_transformationMatrix, matrix);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
}
