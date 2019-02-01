package gameEngine.rendering.gui.text;

import org.joml.Matrix4f;

import gameEngine.rendering.shaders.*;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = "gameEngine/rendering/gui/text/fontVertex.txt";
	private static final String FRAGMENT_FILE = "gameEngine/rendering/gui/text/fontFragment.txt";
	
    private int location_transformationMatrix;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix4f(location_transformationMatrix, matrix);
    }

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}


}
