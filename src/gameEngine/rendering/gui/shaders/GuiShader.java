package gameEngine.rendering.gui.shaders;

import org.joml.Matrix4f;

import gameEngine.rendering.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {
	private static final String VERTEX_FILE = "/gameEngine/rendering/gui/shaders/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "/gameEngine/rendering/gui/shaders/guiFragmentShader.txt";
     
    private int location_transformationMatrix;
 
    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
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
