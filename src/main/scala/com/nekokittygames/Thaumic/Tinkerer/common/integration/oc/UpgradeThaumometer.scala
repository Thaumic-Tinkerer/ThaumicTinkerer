package com.nekokittygames.Thaumic.Tinkerer.common.integration.oc

import java.util

import li.cil.oc.api.Network
import li.cil.oc.api.driver.EnvironmentHost
import li.cil.oc.api.machine.Arguments
import li.cil.oc.api.machine.Callback
import li.cil.oc.api.machine.Context
import li.cil.oc.api.network.Node
import li.cil.oc.api.network.Visibility
import li.cil.oc.api.prefab
import net.minecraft.util.BlockPos
import thaumcraft.common.lib.aura.AuraHandler

/**
  * Created by katsw on 21/11/2015.
  */
class UpgradeThaumometer(val owner:EnvironmentHost) extends prefab.ManagedEnvironment {

  override val node = Network.newNode(this, Visibility.Network).
    withComponent("thaumometer").
    create()
  val position=new BlockPos(owner.xPosition(),owner.yPosition(),owner.zPosition())


  @Callback(doc="""function():Array -- Tries to get the current aura aspects""")
  def getAura(context:Context,arguments:Arguments): Array[AnyRef] =
  {
    var chunk=AuraHandler.getAuraChunk(owner.world().provider.getDimensionId,position.getX >> 4,position.getZ >>4)
    if(chunk!=null)
      {
        val aspectList=chunk.getCurrentAspects
        //val testMap=new util.HashMap[String,Integer]()
        //for(aspect <- aspectList.getAspects)
        //  {
        //    testMap.put(aspect.getTag,new Integer(aspectList.getAmount(aspect)))
        //  }
        val resArray:Array[AnyRef]=aspectList.getAspects.map(f => Array[AnyRef](f.getTag,new Integer(aspectList.getAmount(f))))
        Array[AnyRef](resArray)
      }
    else
      {
        Array[AnyRef](null)
      }
  }


}
