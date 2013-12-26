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
 * File Created @ [Dec 25, 2013, 00:18:06 PM (GMT)]
 */
package vazkii.tinkerer.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelWings extends ModelBiped {

	ModelRenderer Wing1;
	ModelRenderer Wing2;
	
	public ModelWings() {
		super(1F);
		textureWidth = 64;
		textureHeight = 32;
		
		Wing1 = new ModelRenderer(this, 16, -12);
		Wing1.addBox(0F, 0F, 0F, 0, 7, 12);
		Wing1.setRotationPoint(-2F, 1F, 2F);
		setRotation(Wing1, 0F, -0.6108652F, 0F);
		Wing1.offsetX = 0.1F;
		bipedBody.addChild(Wing1);

    	Wing2 = new ModelRenderer(this, 16, -12);
		Wing2.addBox(0.1F, 0F, 0F, 0, 7, 12);
		Wing2.setRotationPoint(2F, 1F, 2F);
		Wing2.offsetX = -0.1F;
		setRotation(Wing2, 0F, 0.4468043F, 0F);
		bipedBody.addChild(Wing2);
	}
	
    @Override
    public void render(Entity entity, float v1, float v2, float v3, float v4, float v5, float v6) {
    	setRotationAngles(v1, v2, v3, v4, v5, v6, entity);
    	bipedHead.showModel = false;
    	bipedHeadwear.showModel = false;
    	bipedLeftLeg.showModel = false;
    	bipedRightLeg.showModel = false;

    	super.render(entity, v1, v2, v3, v4, v5, v6);
    }
    
    @Override
    public void setRotationAngles(float v1, float v2, float v3, float v4, float v5, float v6, Entity entity) {
        EntityLivingBase living = (EntityLivingBase) entity;
    	isSneak = living != null ? living.isSneaking() : false;
        if(living != null) {
        	
        }
    	
        
        super.setRotationAngles(v1, v2, v3, v4, v5, v6, entity);
    }
    
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
