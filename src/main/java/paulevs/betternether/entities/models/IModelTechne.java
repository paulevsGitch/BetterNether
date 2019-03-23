package paulevs.betternether.entities.models;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public interface IModelTechne
{
	default void setRotation(ModelRenderer model, float x, float y, float z)
	{
		Vector3f eulerYzx = new Vector3f(x, y, z);
        Vector3f eulerZyx = ConvertEulerYzxToZyx(eulerYzx);
        model.rotateAngleX = eulerZyx.x;
        model.rotateAngleY = eulerZyx.y;
        model.rotateAngleZ = eulerZyx.z;
	}
	
	default Vector3f ConvertEulerYzxToZyx(Vector3f eulerYzx)
	{
        // Create a matrix from YZX ordered Euler angles
        float a = MathHelper.cos(eulerYzx.x);
        float b = MathHelper.sin(eulerYzx.x);
        float c = MathHelper.cos(eulerYzx.y);
        float d = MathHelper.sin(eulerYzx.y);
        float e = MathHelper.cos(eulerYzx.z);
        float f = MathHelper.sin(eulerYzx.z);
        Matrix4f matrix = new Matrix4f();
        matrix.m00 = c * e;
        matrix.m01 = b * d - a * c * f;
        matrix.m02 = b * c * f + a * d;
        matrix.m10 = f;
        matrix.m11 = a * e;
        matrix.m12 = -b * e;
        matrix.m20 = -d * e;
        matrix.m21 = a * d * f + b * c;
        matrix.m22 = a * c - b * d * f;
        matrix.m33 = 1.0F;
        // Create ZYX ordered Euler angles from the matrix
        Vector3f eulerZyx = new Vector3f();
        eulerZyx.y = (float) Math.asin(clamp(-matrix.m20, -1, 1));
        if (MathHelper.abs(matrix.m20) < 0.99999)
        {
            eulerZyx.x = (float) Math.atan2(matrix.m21, matrix.m22);
            eulerZyx.z = (float) Math.atan2(matrix.m10, matrix.m00);
        }
        else
        {
            eulerZyx.x = 0.0F;
            eulerZyx.z = (float) Math.atan2(-matrix.m01, matrix.m11);
        }
        return eulerZyx;
    }
	
	default float clamp(float x, float min, float max)
	{
		if (x < min)
			return min;
		else if (x > max)
			return max;
		else
			return x;
	}
}
