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
 * File Created @ [8 Sep 2013, 15:48:07 (GMT)]
 */
package thaumic.tinkerer.common.core.handler;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibMisc;
import thaumic.tinkerer.common.registry.ItemStackCompatator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModCreativeTab extends CreativeTabs {

    public static ModCreativeTab INSTANCE;
    //Holds the registered items and blocks before they are sorted
    public ArrayList<ItemStack> creativeTabQueue = new ArrayList<ItemStack>();
    ItemStack displayItem;
    List list = new ArrayList();

    public ModCreativeTab() {
        super(LibMisc.MOD_ID);
        //addWand();
    }

    @Override
    public ItemStack getIconItemStack() {

        return displayItem;
    }

    @Override
    public Item getTabIconItem() {

        return ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class);
    }

    @Override
    public void displayAllReleventItems(List list) {
        list.addAll(this.list);

    }

    public void addWand() {
        ItemStack wand = new ItemStack(ConfigItems.itemWandCasting);
        ((ItemWandCasting) wand.getItem()).setRod(wand, ConfigItems.WAND_ROD_SILVERWOOD);
        ((ItemWandCasting) wand.getItem()).setCap(wand, ConfigItems.WAND_CAP_THAUMIUM);
        ((ItemWandCasting) wand.getItem()).storeAllVis(wand, new AspectList().add(Aspect.AIR, 10000).add(Aspect.EARTH, 10000).add(Aspect.FIRE, 10000).add(Aspect.WATER, 10000).add(Aspect.ORDER, 10000).add(Aspect.ENTROPY, 10000));
        if (list != null)
            list.add(wand);
        displayItem = wand;

        if (ConfigHandler.enableKami) {
            ItemStack wand1 = new ItemStack(ConfigItems.itemWandCasting);
            ((ItemWandCasting) wand1.getItem()).setRod(wand1, ThaumicTinkerer.proxy.rodIchor);
            ((ItemWandCasting) wand1.getItem()).setCap(wand1, ThaumicTinkerer.proxy.capIchor);
            ((ItemWandCasting) wand1.getItem()).storeAllVis(wand1, new AspectList().add(Aspect.AIR, 100000).add(Aspect.EARTH, 100000).add(Aspect.FIRE, 100000).add(Aspect.WATER, 100000).add(Aspect.ORDER, 100000).add(Aspect.ENTROPY, 100000));
            if (list != null)
                list.add(wand1);
            displayItem = wand1;
        }
    }

    public void addItem(Item item) {
        item.getSubItems(item, this, creativeTabQueue);
    }

    public void addBlock(Block block) {
        block.getSubBlocks(Item.getItemFromBlock(block), this, creativeTabQueue);
    }

    public void addAllItemsAndBlocks() {
        Collections.sort(creativeTabQueue, new ItemStackCompatator());
        list.addAll(creativeTabQueue);
    }

}
