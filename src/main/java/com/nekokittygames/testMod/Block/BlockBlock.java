package com.nekokittygames.testMod.Block;

import com.nekokittygames.Thaumic.api.BoundJarAPI;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;

import java.util.UUID;

/**
 * Created by katsw on 24/05/2015.
 */
public class BlockBlock extends Block {
    public BlockBlock() {
        super(Material.dragonEgg);
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        AspectList blockData= BoundJarAPI.getAspects(new UUID(112L, 112L));
        playerIn.addChatMessage(new ChatComponentText(blockData.getAspects()[0].getName()));
    }
}
