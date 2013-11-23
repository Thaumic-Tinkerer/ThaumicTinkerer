package vazkii.tinkerer.common.item.foci;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.MovingObjectPosition;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.Utils;
import vazkii.tinkerer.common.ThaumicTinkerer;

public class ItemFocusSmelt extends ItemModFocus {

	public static Map<String, SmeltData> playerData = new HashMap();

	static class SmeltData {
		public MovingObjectPosition pos;
		public int progress;

		public SmeltData(MovingObjectPosition pos, int progress) {
			this.pos = pos;
			this.progress = progress;
		}

		public boolean equalPos(MovingObjectPosition pos) {
			return pos.blockX == this.pos.blockX && pos.blockY == this.pos.blockY && pos.blockZ == this.pos.blockZ;
		}
	}

	private static final AspectList visUsage = new AspectList().add(Aspect.FIRE, 120).add(Aspect.ENTROPY, 20);

	public ItemFocusSmelt(int par1) {
		super(par1);
	}

	@Override
	public boolean isUseItem() {
		return true;
	}

	@Override
	public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int time) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		if(!wand.consumeAllVis(stack, p, visUsage, false))
			return;
		
		MovingObjectPosition pos = Utils.getTargetBlock(p.worldObj, p, false);
		
		if(pos != null) {
			int id = p.worldObj.getBlockId(pos.blockX, pos.blockY, pos.blockZ);
			int meta = p.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);

			ItemStack blockStack = new ItemStack(id, 1, meta);
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(blockStack);

			if(result != null && result.getItem() instanceof ItemBlock) {
				boolean decremented = false;

				if(playerData.containsKey(p.username)) {
					SmeltData data = playerData.get(p.username);
					
					if(data.equalPos(pos)) {
						data.progress--;
						decremented = true;
						if(data.progress <= 0) {
							if(!p.worldObj.isRemote) {
								p.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, result.itemID, result.getItemDamage(), 1 | 2);
								p.worldObj.playSoundAtEntity(p, "fire.ignite", 0.6F, 1F);
								p.worldObj.playSoundAtEntity(p, "fire.fire", 1F, 1F);

								wand.consumeAllVis(stack, p, visUsage, true);
								playerData.remove(p.username);
								decremented = false;
							}
							
							for(int i = 0; i < 25; i++) {
								double x = pos.blockX + Math.random();
								double y = pos.blockY + Math.random();
								double z = pos.blockZ + Math.random();

								ThaumicTinkerer.tcProxy.wispFX2(p.worldObj, x, y, z, (float) Math.random() / 2F, 4, true, (float) -Math.random() / 10F);
							}
						}
					}
				}

				if(!decremented) {
					int potency = EnchantmentHelper.getEnchantmentLevel(Config.enchPotency.effectId, wand.getFocusItem(stack));
					playerData.put(p.username, new SmeltData(pos, 20 - (Math.min(3, potency) * 5)));
				} else for(int i = 0; i < 2; i++) {
					double x = pos.blockX + Math.random();
					double y = pos.blockY + Math.random();
					double z = pos.blockZ + Math.random();
					p.worldObj.playSoundAtEntity(p, "fire.fire", (float) Math.random() / 2F + 0.5F, 1F);
					
					ThaumicTinkerer.tcProxy.wispFX2(p.worldObj, x, y, z, (float) Math.random() / 2F, 4, true, (float) -Math.random() / 10F);
				}
				
				if(p.worldObj.isRemote)
					ThaumicTinkerer.tcProxy.beamCont(p.worldObj, p, pos.blockX + 0.5, pos.blockY + 0.5, pos.blockZ + 0.5, 2, 0xFF0000, true, 0F, null, 1);
			}
		}
	}

	@Override
	public String getSortingHelper(ItemStack itemstack) {
		return "SMELT";
	}
	
	@Override
	boolean hasOrnament() {
		return true;
	}

	@Override
	public int getFocusColor() {
		return 0xFF0000;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

}
