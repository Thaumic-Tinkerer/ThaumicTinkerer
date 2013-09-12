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
 * File Created @ [12 Sep 2013, 17:56:17 (GMT)]
 */
package vazkii.tinkerer.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMagnet extends ModelBase {

	ModelRenderer panel;
	ModelRenderer magnet;
	ModelRenderer box1;
	ModelRenderer box2;
	ModelRenderer box3;
	ModelRenderer box4;
	ModelRenderer box5;

	public ModelMagnet() {
		textureWidth = 64;
		textureHeight = 64;

		panel = new ModelRenderer(this, 0, 0);
		panel.addBox(0F, 0F, 0F, 14, 2, 14);
		panel.setRotationPoint(-7F, 22F, -7F);
		panel.setTextureSize(64, 64);
		setRotation(panel, 0F, 0F, 0F);
		magnet = new ModelRenderer(this, 0, 16);
		magnet.addBox(0F, 0F, 0F, 4, 13, 4);
		magnet.setRotationPoint(-2F, 9F, -2F);
		magnet.setTextureSize(64, 64);
		setRotation(magnet, 0F, 0F, 0F);
		box1 = new ModelRenderer(this, 28, 19);
		box1.addBox(0F, -2F, 0F, 6, 14, 0);
		box1.setRotationPoint(3F, 10F, -3F);
		box1.setTextureSize(64, 64);
		setRotation(box1, 0F, -1.570796F, 0F);
		box2 = new ModelRenderer(this, 28, 33);
		box2.addBox(0F, 0F, 0F, 6, 14, 0);
		box2.setRotationPoint(-3F, 8F, 3F);
		box2.setTextureSize(64, 64);
		setRotation(box2, 0F, 1.570796F, 0F);
		box3 = new ModelRenderer(this, 40, 19);
		box3.addBox(0F, 0F, 0F, 6, 14, 0);
		box3.setRotationPoint(3F, 8F, 3F);
		box3.setTextureSize(64, 64);
		setRotation(box3, 0F, 3.141593F, 0F);
		box4 = new ModelRenderer(this, 40, 33);
		box4.addBox(0F, 0F, 0F, 6, 14, 0);
		box4.setRotationPoint(-3F, 8F, -3F);
		box4.setTextureSize(64, 64);
		setRotation(box4, 0F, 0F, 0F);
		box5 = new ModelRenderer(this, 28, 49);
		box5.addBox(0F, 0F, 0F, 6, 0, 6);
		box5.setRotationPoint(-3F, 8F, -3F);
		box5.setTextureSize(64, 64);
		setRotation(box5, 0F, 0F, 0F);
	}

	public void render() {
		final float scale = 1F / 16F;

		panel.render(scale);
		magnet.render(scale);
		box1.render(scale);
		box2.render(scale);
		box3.render(scale);
		box4.render(scale);
		box5.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}