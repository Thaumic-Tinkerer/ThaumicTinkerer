package thaumic.tinkerer.common.peripheral.OpenComputers;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.items.equipment.ItemElementalPickaxe;
import thaumcraft.common.tiles.TileArcaneBore;

/**
 * Created by Katrina on 22/04/14.
 */
public class DriverArcaneBore extends DriverTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return TileArcaneBore.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
		return new Enviroment((TileArcaneBore) world.getTileEntity(x, y, z));
	}

	public static final class Enviroment extends ManagedTileEntityEnvironment<TileArcaneBore> {

		public Enviroment(TileArcaneBore tileEntity) {
			super(tileEntity, "arcanebore");
		}

		@Callback(doc = "function():boolean -- does the bore have a pickaxe")
		public Object[] hasPickaxe(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.hasPickaxe };
		}

		@Callback(doc = "function():boolean -- does the bore have a focus")
		public Object[] hasFocus(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.hasFocus };
		}

		@Callback(doc = "function():boolean -- is the pickaxe near broken?")
		public Object[] isPickaxeBroken(final Context context, final Arguments arguments) {
			ItemStack pickaxe = tileEntity.getStackInSlot(1);
			boolean nearBroken = pickaxe != null && pickaxe.getItemDamage() + 1 == pickaxe.getMaxDamage();
			return new Object[]{ nearBroken };
		}

		@Callback(doc = "function():boolean -- Is the bore working?")
		public Object[] isWorking(final Context context, final Arguments arguments) {
			ItemStack pickaxe = tileEntity.getStackInSlot(1);
			boolean nearBroken = pickaxe != null && pickaxe.getItemDamage() + 1 == pickaxe.getMaxDamage();
			return new Object[]{ this.tileEntity.gettingPower() && this.tileEntity.hasFocus && this.tileEntity.hasPickaxe && pickaxe.isItemStackDamageable() && !nearBroken };
		}

		@Callback(doc = "function():number -- Gets bore's radius")
		public Object[] getRadius(final Context context, final Arguments arguments) {
			return new Object[]{ 1 + (tileEntity.area + tileEntity.maxRadius) * 2 };
		}

		@Callback(doc = "function():number -- Gets bore's speed")
		public Object[] getSpeed(final Context context, final Arguments arguments) {
			return new Object[]{ tileEntity.speed };
		}

		@Callback(doc = "function():boolean -- Will the bore get native clusters?")
		public Object[] hasNativeClusters(final Context context, final Arguments arguments) {
			ItemStack pickaxe = tileEntity.getStackInSlot(1);
			return new Object[]{ pickaxe != null && pickaxe.getItem() instanceof ItemElementalPickaxe };
		}

		@Callback(doc = "function():number -- What level fortune on pick")
		public Object[] getFortune(final Context context, final Arguments arguments) {
			ItemStack pickaxe = tileEntity.getStackInSlot(1);
			return new Object[]{ EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, pickaxe) };
		}

		@Callback(doc = "function():boolean -- Does the pick have silk touch?")
		public Object[] hasSilkTouch(final Context context, final Arguments arguments) {
			ItemStack pickaxe = tileEntity.getStackInSlot(1);
			return new Object[]{ EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, pickaxe) > 0 };
		}

	}
}
