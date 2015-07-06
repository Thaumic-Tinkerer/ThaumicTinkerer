package com.nekokittygames.Thaumic.Tinkerer.client.renders.tiles

import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileRepairer
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{RenderHelper, GlStateManager}
import net.minecraft.client.renderer.entity.{RenderManager, RenderItem}
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.{ItemSkull, Item}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import org.lwjgl.opengl.GL11

/**
 * Created by fiona on 06/07/2015.  A horrible experiment in rendering with something inside it.
 */
class TileRepairerRenderer extends TileEntitySpecialRenderer{
  override def renderTileEntityAt(te: TileEntity, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int): Unit = {
    val tileEntity:TileRepairer = te.asInstanceOf[TileRepairer]
    val stack=tileEntity.inventory
    GlStateManager.pushMatrix

    val d3: Double =  x
    val d4: Double =  y
    val d5: Double =  z+.5
    GlStateManager.translate(d3 + 0.5D, d4 + 0.5D, d5 + 0.5D)
    GlStateManager.rotate(180.0F , 0.0F, 1.0F, 0.0F)
    GlStateManager.translate(0.0F, 0.0F, 0.4375F)

    if (stack!=null)
      {
        val entityitem: EntityItem = new EntityItem(tileEntity.getWorld, 0.0D, 0.0D, 0.0D, stack)
        val item: Item = entityitem.getEntityItem.getItem
        GL11.glTranslatef(0,(Math.sin(tileEntity.ticksExisted/10F)*0.1F).toFloat,0)
        val deg = tileEntity.ticksExisted*0.75F%360F
        GL11.glRotatef(deg,0,1,0)
        entityitem.getEntityItem.stackSize = 1
        entityitem.hoverStart = 0.0F
        GlStateManager.pushMatrix
        GlStateManager.disableLighting
        var textureatlassprite: TextureAtlasSprite = null
        GlStateManager.scale(0.5F, 0.5F, 0.5F)

        if (!Minecraft.getMinecraft.getRenderItem.shouldRenderItemIn3D(entityitem.getEntityItem) ) {
          GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F)
        }
        GlStateManager.pushAttrib
        RenderHelper.enableStandardItemLighting
        Minecraft.getMinecraft.getRenderItem.renderItemModel(entityitem.getEntityItem)
        RenderHelper.disableStandardItemLighting
        GlStateManager.popAttrib

        if (textureatlassprite != null && textureatlassprite.getFrameCount > 0) {
          textureatlassprite.updateAnimation
        }
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();



        }
    GlStateManager.popMatrix
  }
}
