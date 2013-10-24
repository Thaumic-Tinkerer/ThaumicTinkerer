package vazkii.research;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import thaumcraft.client.ClientProxy;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.tiles.TileResearchTable;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "ResearchHelper", name = "ResearchHelper", version = "1.0", dependencies = "required-after:Thaumcraft")
public class ResearchHelper {

  @EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(Thaumcraft.instance, new ClientProxy() {
			
			@Override
			public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
				System.out.println("ID: " + ID);
				if(ID == 10)
					return new GuiResearchTableExtra((TileResearchTable) world.getBlockTileEntity(x, y, z));
				
				return super.getClientGuiElement(ID, player, world, x, y, z);
			}
			
		});
	}
}