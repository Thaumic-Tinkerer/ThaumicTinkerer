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
 * File Created @ [Dec 11, 2013, 10:03:09 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.peripheral;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;
import thaumcraft.common.tiles.TileArcaneBore;


public class PeripheralArcaneBore implements IPeripheral {

	TileArcaneBore bore;

	public PeripheralArcaneBore(TileArcaneBore bore) {
		this.bore = bore;
	}

	@Override
	public String getType() {
		return "tt_arcanebore";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "hasPickaxe", "hasFocus", "isPickaxeBroken", "isWorking", "getRadius", "getSpeed", "hasNativeClusters", "getFortune", "hasSilkTouch" };
	}


    @Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		ItemStack pickaxe = bore.getStackInSlot(1);
		boolean nearBroken = pickaxe != null && pickaxe.getItemDamage() + 1 == pickaxe.getMaxDamage();

		switch(method) {
			case 0 : return new Object[] { bore.hasPickaxe };
			case 1 : return new Object[] { bore.hasFocus };
			case 2 : return new Object[] { nearBroken };
			case 3 : return new Object[] { bore.gettingPower() && bore.hasFocus && bore.hasPickaxe && pickaxe.isItemStackDamageable() && !nearBroken };
			case 4 : return new Object[] { 1 + (bore.area + bore.maxRadius) * 2 };
			case 5 : return new Object[] { bore.speed };
			case 6 : return new Object[] { pickaxe != null && pickaxe.getItem() instanceof ItemElementalPickaxe };
			case 7 : return new Object[] { EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, pickaxe) };
			case 8 : return new Object[] { EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, pickaxe) > 0 };
		}

		return null;
	}



	@Override
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public void detach(IComputerAccess computer) {
		// NO-OP
	}

    @Override
    public boolean equals(IPeripheral other) {
        return this.equals((Object)other);
    }



}
