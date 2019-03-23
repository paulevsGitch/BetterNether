package paulevs.betternether.entities.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import paulevs.betternether.entities.EntityFirefly;

public class ModelFirefly extends ModelBase implements IModelTechne
{
	//fields
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer leg5;
	ModelRenderer leg6;
	ModelRenderer body;
	ModelRenderer glow;

	public ModelFirefly()
	{
		textureWidth = 32;
		textureHeight = 32;

		leg1 = new ModelRenderer(this, 20, 0);
		leg1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
		leg1.setRotationPoint(-1.5F, 22.5F, -2F);
		leg1.setTextureSize(32, 32);
		leg1.mirror = true;
		setRotation(leg1, 0F, -0.7853982F, 0.535372F);
		leg2 = new ModelRenderer(this, 20, 0);
		leg2.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
		leg2.setRotationPoint(-1.5F, 22.5F, 0F);
		leg2.setTextureSize(32, 32);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0.535372F);
		leg3 = new ModelRenderer(this, 20, 0);
		leg3.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
		leg3.setRotationPoint(-1.5F, 22.5F, 2F);
		leg3.setTextureSize(32, 32);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0.7853982F, 0.535372F);
		leg4 = new ModelRenderer(this, 20, 0);
		leg4.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
		leg4.setRotationPoint(1.5F, 22.5F, -2F);
		leg4.setTextureSize(32, 32);
		leg4.mirror = true;
		setRotation(leg4, 0F, -2.356194F, 0.535372F);
		leg5 = new ModelRenderer(this, 20, 0);
		leg5.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
		leg5.setRotationPoint(1.5F, 22.5F, 0F);
		leg5.setTextureSize(32, 32);
		leg5.mirror = true;
		setRotation(leg5, 0F, 3.141593F, 0.535372F);
		leg6 = new ModelRenderer(this, 20, 0);
		leg6.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
		leg6.setRotationPoint(1.5F, 22.5F, 2F);
		leg6.setTextureSize(32, 32);
		leg6.mirror = true;
		setRotation(leg6, 0F, 2.356194F, 0.535372F);
		body = new ModelRenderer(this, 0, 0);
		body.addBox(0F, 0F, 0F, 5, 5, 5);
		body.setRotationPoint(-2.5F, 18F, -2.5F);
		body.setTextureSize(32, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		glow = new ModelRenderer(this, 0, 10);
		glow.addBox(0F, 0F, 0F, 6, 6, 6);
		glow.setRotationPoint(-3F, 17.5F, -3F);
		glow.setTextureSize(32, 32);
		glow.mirror = true;
		setRotation(glow, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GL11.glPushMatrix();
		((EntityFirefly) entity).transform();
		leg1.render(f5);
		leg2.render(f5);
		leg3.render(f5);
		leg4.render(f5);
		leg5.render(f5);
		leg6.render(f5);
		((EntityFirefly) entity).bindColor();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.disableAlpha();
		body.render(f5);
		glow.render(f5);
		GlStateManager.enableAlpha();
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}
}
