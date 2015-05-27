package com.nekokittygames.Thaumic.Tinkerer.common.core.misc

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by Katrina on 26/05/2015.
 */
object ItemNBT {


  def getItemStackTag(itemStack:ItemStack)=
  {
    var stack:NBTTagCompound=itemStack.getTagCompound
    if(stack==null) {
      itemStack.setTagCompound(new NBTTagCompound)
      stack=itemStack.getTagCompound
    }
    stack
  }

}
