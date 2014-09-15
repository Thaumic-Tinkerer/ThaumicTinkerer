package thaumic.tinkerer.common.item;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;

import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketAspectPool;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.*;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rob on 9/13/2014.
 */
public class ItemDrink extends ItemBucketMilk implements ITTinkererItem {
    public IIcon icon;

    public ItemDrink()
    {
        setMaxStackSize(64);
        setHasSubtypes(true);
        setMaxDamage(0);
    }


    @SubscribeEvent
    public void onItemCraft(ItemCraftedEvent event) {
        ThaumicTinkerer.log.debug("top of onItemCraft");
        ItemStack result = event.crafting;
        IInventory matrix = event.craftMatrix;

        if (result != null && result.getItem() instanceof ItemDrink) {
            ThaumicTinkerer.log.debug("\trecipe is for ItemDrink");
            for (int i = 0; i < matrix.getSizeInventory(); i++) {
                ItemStack component = matrix.getStackInSlot(i);
                if (component != null && component.getItem() instanceof ItemEssence) {
                    ThaumicTinkerer.log.debug("\tfound ItemEssence");
                    AspectList aspects = getAspects(component);
                    if (aspects != null) {
                        ThaumicTinkerer.log.debug("\tadding aspects, size: "+aspects.size());
                        setAspects(result, aspects);
                        event.setResult(Event.Result.ALLOW);
                        return;
                    }
                    ThaumicTinkerer.log.debug("\tno aspects to add");
                }
            }
        }

        event.setResult(Event.Result.DENY);

        ThaumicTinkerer.log.debug("leaving onItemCraft");
    }



    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            --item.stackSize;
        }
        if (!world.isRemote) {
            //do effect
            AspectList aspects = getAspects(item);
            if (aspects != null && aspects.size() > 0) {
                //add points to player.
                for (Aspect as : aspects.getAspects())
                if (Thaumcraft.proxy.getPlayerKnowledge().hasDiscoveredAspect(player.getCommandSenderName(),as)) {
                    Thaumcraft.proxy.getPlayerKnowledge().addAspectPool(player.getCommandSenderName(), as, (short) aspects.getAmount(as));
                    ResearchManager.scheduleSave(player);
                    player.addChatMessage(new ChatComponentText("Added 8 points of " + as.getTag()));
                }

            }

        }

        return item.stackSize <= 0 ? new ItemStack(Items.glass_bottle) : item;
    }
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        AspectList aspects = getAspects(par1ItemStack);
        if (aspects != null && aspects.size() > 0) {
            for (Aspect as : aspects.getAspects()) {
                par3List.add("8x " + as.getTag());
            }
        }
    }
    @Override
    public void registerIcons(IIconRegister ir) {
        this.icon = IconHelper.forItem(ir, this);
    }
    public boolean shouldDisplayInTab() {
        return true;
    }
    public int getMaxItemUseDuration(ItemStack item) {
        return 32; //same duration as drinking milk
    }
    public EnumAction getItemUseAction(ItemStack p_77661_1_)    {
        return EnumAction.drink;
    }
    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return this.icon;
    }
    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return this.icon;
    }
    @Override
    public IIcon getIconFromDamage(int damage) {
        return this.icon;
    }

    public AspectList getAspects(ItemStack stack) {
        if (stack.hasTagCompound()) {
            AspectList aspects = new AspectList();
            aspects.readFromNBT(stack.getTagCompound());
            return aspects.size() > 0 ? aspects : null;
        }
        return null;
    }
    public void setAspects(ItemStack stack, AspectList list) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        list.writeToNBT(stack.getTagCompound());
    }

    public ArrayList<Object> getSpecialParameters() {
        return null;
    }
    public String getItemName() {
        return LibItemNames.DRINK;
    }
    public String getUnlocalizedName() {
        return LibItemNames.DRINK;
    }
    @Override
    public IRegisterableResearch getResearchItem() {
        return null;

        //return new TTResearchItem(LibResearch.KEY_DRINK, null, 2,-9,1, new ItemStack(this));
    }
    @Override
    public boolean shouldRegister() { return true;}

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DRINK, new ItemStack(this),
                "BP",
                'B', new ItemStack(Items.glass_bottle),
                'P', ItemApi.getItem("itemEssence",1)  ) ;
    }
}
