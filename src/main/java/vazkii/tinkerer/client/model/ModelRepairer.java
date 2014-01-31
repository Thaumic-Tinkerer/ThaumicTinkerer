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
 * File Created @ [12 Sep 2013, 17:18:53 (GMT)]
 */
package vazkii.tinkerer.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelRepairer extends ModelBase {

	ModelRenderer Base;
	ModelRenderer Support1;
	ModelRenderer Support2;
	ModelRenderer Support3;
	ModelRenderer Support4;
	ModelRenderer Top1;
	ModelRenderer Top2;
	ModelRenderer Top3;
	ModelRenderer Top4;
	ModelRenderer Opening1;
	ModelRenderer Opening2;
	ModelRenderer Opening3;
	ModelRenderer Opening4;
	ModelRenderer Glass1;
	ModelRenderer Glass2;
	ModelRenderer Glass3;
	ModelRenderer Glass4;
	ModelRenderer Glass5;

	public ModelRepairer() {
		textureWidth = 64;
		textureHeight = 64;

		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(0F, 0F, 0F, 16, 1, 16);
		Base.setRotationPoint(-8F, 23F, -8F);
		Base.setTextureSize(64, 64);
		setRotation(Base, 0F, 0F, 0F);
		Support1 = new ModelRenderer(this, 0, 17);
		Support1.addBox(0F, 0F, 0F, 2, 14, 2);
		Support1.setRotationPoint(-8F, 9F, 6F);
		Support1.setTextureSize(64, 64);
		setRotation(Support1, 0F, 0F, 0F);
		Support2 = new ModelRenderer(this, 0, 17);
		Support2.addBox(0F, 0F, 0F, 2, 14, 2);
		Support2.setRotationPoint(6F, 9F, 6F);
		Support2.setTextureSize(64, 64);
		setRotation(Support2, 0F, 0F, 0F);
		Support3 = new ModelRenderer(this, 0, 17);
		Support3.addBox(0F, 0F, 0F, 2, 14, 2);
		Support3.setRotationPoint(-8F, 9F, -8F);
		Support3.setTextureSize(64, 64);
		setRotation(Support3, 0F, 0F, 0F);
		Support4 = new ModelRenderer(this, 0, 17);
		Support4.addBox(0F, 0F, 0F, 2, 14, 2);
		Support4.setRotationPoint(6F, 9F, -8F);
		Support4.setTextureSize(64, 64);
		setRotation(Support4, 0F, 0F, 0F);
		Top1 = new ModelRenderer(this, 11, 19);
		Top1.addBox(0F, 0F, 0F, 16, 1, 2);
		Top1.setRotationPoint(-8F, 8F, -8F);
		Top1.setTextureSize(64, 64);
		setRotation(Top1, 0F, 0F, 0F);
		Top2 = new ModelRenderer(this, 11, 19);
		Top2.addBox(0F, 0F, 0F, 16, 1, 2);
		Top2.setRotationPoint(-8F, 8F, 6F);
		Top2.setTextureSize(64, 64);
		setRotation(Top2, 0F, 0F, 0F);
		Top3 = new ModelRenderer(this, 11, 23);
		Top3.addBox(0F, 0F, 0F, 2, 1, 12);
		Top3.setRotationPoint(6F, 8F, -6F);
		Top3.setTextureSize(64, 64);
		setRotation(Top3, 0F, 0F, 0F);
		Top4 = new ModelRenderer(this, 11, 23);
		Top4.addBox(0F, 0F, 0F, 2, 1, 12);
		Top4.setRotationPoint(-8F, 8F, -6F);
		Top4.setTextureSize(64, 64);
		setRotation(Top4, 0F, 0F, 0F);
		Opening1 = new ModelRenderer(this, 48, 30);
		Opening1.addBox(0F, 0F, 0F, 5, 1, 1);
		Opening1.setRotationPoint(-2.5F, 14F, 7F);
		Opening1.setTextureSize(64, 64);
		setRotation(Opening1, 0F, 0F, 0F);
		Opening2 = new ModelRenderer(this, 48, 30);
		Opening2.addBox(0F, 0F, 0F, 5, 1, 1);
		Opening2.setRotationPoint(-2.5F, 17F, 7F);
		Opening2.setTextureSize(64, 64);
		setRotation(Opening2, 0F, 0F, 0F);
		Opening3 = new ModelRenderer(this, 48, 24);
		Opening3.addBox(0F, 0F, 0F, 1, 3, 1);
		Opening3.setRotationPoint(-2F, 14.5F, 7F);
		Opening3.setTextureSize(64, 64);
		setRotation(Opening3, 0F, 0F, 0F);
		Opening4 = new ModelRenderer(this, 48, 24);
		Opening4.addBox(0F, 0F, 0F, 1, 3, 1);
		Opening4.setRotationPoint(1F, 14.5F, 7F);
		Opening4.setTextureSize(64, 64);
		setRotation(Opening4, 0F, 0F, 0F);
		Glass1 = new ModelRenderer(this, -11, 37);
		Glass1.addBox(0F, 0F, 0F, 12, 0, 12);
		Glass1.setRotationPoint(-6F, 8.5F, -6F);
		Glass1.setTextureSize(64, 64);
		setRotation(Glass1, 0F, 0F, 0F);
		Glass2 = new ModelRenderer(this, 1, 38);
		Glass2.addBox(0F, 0F, 0F, 0, 14, 12);
		Glass2.setRotationPoint(7.5F, 9F, -6F);
		Glass2.setTextureSize(64, 64);
		setRotation(Glass2, 0F, 0F, 0F);
		Glass3 = new ModelRenderer(this, 1, 38);
		Glass3.addBox(0F, 0F, 0F, 0, 14, 12);
		Glass3.setRotationPoint(-7.5F, 9F, 6F);
		Glass3.setTextureSize(64, 64);
		setRotation(Glass3, 0F, 3.141593F, 0F);
		Glass4 = new ModelRenderer(this, 40, 34);
		Glass4.addBox(0F, 0F, 0F, 12, 14, 0);
		Glass4.setRotationPoint(-6F, 9F, -7.5F);
		Glass4.setTextureSize(64, 64);
		setRotation(Glass4, 0F, 0F, 0F);
		Glass5 = new ModelRenderer(this, 33, 50);
		Glass5.addBox(0F, 0F, 0F, 12, 14, 0);
		Glass5.setRotationPoint(6F, 9F, 7.5F);
		Glass5.setTextureSize(64, 64);
		setRotation(Glass5, 0F, 3.141593F, 0F);
	}

	public void render() {
		final float scale = 1F / 16F;

		Base.render(scale);
		Support1.render(scale);
		Support2.render(scale);
		Support3.render(scale);
		Support4.render(scale);
		Top1.render(scale);
		Top2.render(scale);
		Top3.render(scale);
		Top4.render(scale);
		Opening1.render(scale);
		Opening2.render(scale);
		Opening3.render(scale);
		Opening4.render(scale);

	}

	public void renderGlass() {
		final float scale = 1F / 16F;

		Glass1.render(scale);
		Glass2.render(scale);
		Glass3.render(scale);
		Glass4.render(scale);
		Glass5.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
