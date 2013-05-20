/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [20 May 2013, 22:15:39 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.Collection;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.aura.AuraNode;
import thaumcraft.common.items.ItemThaumometer;
import vazkii.tinkerer.util.helper.MiscHelper;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluxDetector extends ItemThaumometer {

	private static final String TAG_OBJECT_TAGS_CMP = "tagsCmp";
	private static final String TAG_OBJECT_TAG = "tag%s";
	private static final String TAG_OBJECT_TAG_REPLACE = "tag";

	public ItemFluxDetector(int par1) {
		super(par1);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		AuraNode node = MiscHelper.getClosestNode(par2World, par3Entity.posX, par3Entity.posY, par3Entity.posZ);
		setTagCompoundForNode(par1ItemStack, node);
	}

	private void setTagCompoundForNode(ItemStack stack, AuraNode node) {
		NBTTagCompound cmp = new NBTTagCompound();

		if(node == null) {
			if(stack.hasTagCompound())
				cmp.setCompoundTag(TAG_OBJECT_TAGS_CMP, new NBTTagCompound());
		} else {
			NBTTagCompound cmp1 = new NBTTagCompound();
			ObjectTags flux = node.flux;
			for(EnumTag tag : node.flux.getAspects())
				cmp1.setInteger(String.format(TAG_OBJECT_TAG, tag.id), flux.getAmount(tag));
			cmp.setCompoundTag(TAG_OBJECT_TAGS_CMP, cmp1);
		}
	}

	public static ObjectTags getTags(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound cmp = stack.getTagCompound();
			NBTTagCompound cmp1 = cmp.getCompoundTag(TAG_OBJECT_TAGS_CMP);
			if(cmp1 != null) {
				ObjectTags tags = new ObjectTags();
				Collection<NBTBase> cmpTags = cmp1.getTags();
				for(NBTBase tag : cmpTags) {
					if(tag instanceof NBTTagInt) {
						NBTTagInt integer = (NBTTagInt) tag;
						int tagID = Integer.parseInt(tag.getName().replaceAll(TAG_OBJECT_TAG_REPLACE, ""));
						int amount = integer.data;
						tags.add(EnumTag.get(tagID), amount);
					}
				}

				return tags;
			}
 		}

		return null;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return iconRing;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return false;
	}
}
