package gameEngine.common;

import org.joml.*;
import org.joml.Math;

import gameEngine.components.Camera;

public class Maths {
	public static Matrix4f createTransformationMatrix(Vector3d translation, Vector3d rotation, Vector3d scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		
		matrix.translate((float)translation.x, (float)translation.y, (float)translation.z);
		matrix.rotateX((float)Math.toRadians(rotation.x));
		matrix.rotateY((float)Math.toRadians(rotation.y));
		matrix.rotateZ((float)Math.toRadians(rotation.z));
		matrix.scale((float)scale.x, (float)scale.y, (float)scale.z);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        
        Vector3d rot = camera.gameObject.transform.rotation;
        matrix.rotateX((float)Math.toRadians(rot.x));
        matrix.rotateY((float)Math.toRadians(rot.y));
        matrix.rotateZ((float)Math.toRadians(rot.z));
        Vector3d cameraPos = camera.gameObject.transform.position;
        Vector3f negativeCameraPos = new Vector3f((float)(-cameraPos.x),(float)(-cameraPos.y),(float)(-cameraPos.z));
        matrix.translate(negativeCameraPos);
        return matrix;
    }
	
	public static Quaterniond fromEulerAngle(Vector3d rot) {
		Quaterniond value = new Quaterniond();
		value.rotateY(-Math.toRadians(rot.y));
		value.rotateX(-Math.toRadians(rot.x));
		value.rotateZ(-Math.toRadians(rot.z));
		return value;
	}
}
