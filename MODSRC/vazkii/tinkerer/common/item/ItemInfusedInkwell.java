package vazkii.tinkerer.common.item;


import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import thaumcraft.common.container.ContainerResearchTable;
import thaumcraft.common.container.SlotLimitedByClass;
import thaumcraft.common.items.ItemInkwell;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfusedInkwell extends ItemInkwell {

	public ItemInfusedInkwell(int par1) {
		super(par1);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setMaxDamage(800);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}
	
	@ForgeSubscribe
	public void containerOpened(PlayerOpenContainerEvent event) {
		Container container = event.entityPlayer.openContainer;
		if(container instanceof ContainerResearchTable) {
			SlotLimitedByClass slot = (SlotLimitedByClass) container.inventorySlots.get(2);
			ReflectionHelper.setPrivateValue(SlotLimitedByClass.class, slot, ItemInfusedInkwell.class, 0);
		}
	}

}
