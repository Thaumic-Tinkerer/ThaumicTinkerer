/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 4:41:02 PM (GMT)]
 */
package vazkii.tinkerer.client.model.kami;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.core.helper.ClientHelper;

public class ModelSpinningCubes extends ModelBase {

	ModelRenderer spinningCube;

	public ModelSpinningCubes() {
		spinningCube = new ModelRenderer(this, 42, 0);
		spinningCube.addBox(0F, 0F, 0F, 1, 1, 1);
		spinningCube.setRotationPoint(0F, 0F, 0F);
		spinningCube.setTextureSize(64, 64);
		spinningCube.mirror = true;
	}

	public void render() {
		// NO-OP
	}

	public void renderSpinningCubes(int cubes, int repeat, int origRepeat) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		final float modifier = 6F;
		final float rotationModifier = 0.25F;
		final float radiusBase = 0.7F;
		final float radiusMod = 0.1F;

		double ticks = ClientHelper.clientPlayer().ticksExisted - 0.75 * (origRepeat - repeat);
		float offsetPerCube = 360 / cubes;

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.025F, 0.85F, -0.025F);
		for(int i = 0; i < cubes; i++) {
			float offset = offsetPerCube * i;
			float deg = (int) (ticks / rotationModifier % 360F + offset);
			float rad = deg * (float) Math.PI / 180F;
			float radiusX = (float) (radiusBase + radiusMod * Math.sin(ticks / modifier));
			float radiusZ = (float) (radiusBase + radiusMod * Math.cos(ticks / modifier));
			float x =  (float) (radiusX * Math.cos(rad));
			float z = (float) (radiusZ * Math.sin(rad));
			float y = (float) Math.cos((ticks + 50 * i) / 5F) / 10F;

			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, z);
			float xRotate = (float) Math.sin(ticks * rotationModifier) / 2F;
			float yRotate = (float) Math.max(0.6F, Math.sin(ticks * 0.1F) / 2F + 0.5F);
			float zRotate = (float) Math.cos(ticks * rotationModifier) / 2F;

			GL11.glRotatef(deg, xRotate, yRotate, zRotate);
			if(repeat < origRepeat) {
				GL11.glColor4f(1F, 1F, 1F, 0.2F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else GL11.glColor4f(1F, 1F, 1F, 1F);

			int light = 15728880;
	        int lightmapX = light % 65536;
	        int lightmapY = light / 65536;

	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
			spinningCube.render(1F / 16F);

			if(repeat < origRepeat)
				GL11.glDisable(GL11.GL_BLEND);

			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		if(repeat != 0)
			renderSpinningCubes(cubes, repeat - 1, origRepeat);
	}

}
