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
 * File Created @ [Nov 24, 2013, 7:31:51 PM (GMT)]
 */
package vazkii.tinkerer.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.core.helper.EnumMobAspect;
import vazkii.tinkerer.common.lib.LibObfuscation;

public class ItemBloodSword extends ItemSword implements IRepairable {

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		
		return super.getItemUseAction(par1ItemStack);
	}

	private static final int DAMAGE = 10;

	public ItemBloodSword() {
		super(EnumHelper.addToolMaterial("TT_BLOOD", 0, 950, 0, 0, ThaumcraftApi.toolMatThaumium.getEnchantability()));
		MinecraftForge.EVENT_BUS.register(this);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", DAMAGE, 0));
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 0.25, 1));
        return multimap;
    }

	static int handleNext = 0;


	public void addDrops(LivingDropsEvent event, ItemStack dropStack) {
		EntityItem entityitem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, dropStack);
		entityitem.delayBeforeCanPickup = 10;
		event.drops.add(entityitem);
	}

	@SubscribeEvent
	public void onDrops(LivingDropsEvent event){
		if (event.source.damageType.equals("player")) {

			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			ItemStack stack = player.getCurrentEquippedItem();
			if (stack != null && stack.getItem() == this  && stack.stackTagCompound!=null && stack.stackTagCompound.getInteger("Activated")==1) {
				Aspect[] aspects = EnumMobAspect.getAspectsForEntity(event.entity);
                //ScanResult sr=new ScanResult((byte)2,0,0,event.entity,"");
                //AspectList as=ScanManager.getScanAspects(sr,event.entity.worldObj);
				//if(as!=null && as.size()!=0){
                if(aspects!=null){
					event.drops.removeAll(event.drops);
					//for(Aspect a:as.getAspects()){
                    for(Aspect a:aspects){
						addDrops(event, ItemMobAspect.getStackFromAspect(a));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onDamageTaken(LivingAttackEvent event) {
		if(event.entity.worldObj.isRemote)
			return;

		boolean handle = handleNext == 0;
		if(!handle)
			handleNext--;

		if(event.entityLiving instanceof EntityPlayer && handle) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack itemInUse = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, LibObfuscation.ITEM_IN_USE);
			if(itemInUse != null && itemInUse.getItem()==this) {
				
				event.setCanceled(true);
				handleNext = 3;
				player.attackEntityFrom(DamageSource.magic, 3);
			}
		}

		if(handle) {
			Entity source = event.source.getSourceOfDamage();
			if(source != null && source instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) source;
				ItemStack itemInUse = attacker.getHeldItem();
				if(itemInUse != null && itemInUse.getItem()==this)
					attacker.attackEntityFrom(DamageSource.magic, 2);
			}
		}
	}

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World par2World,
                                      EntityPlayer par3EntityPlayer) {

        ItemStack cache=super.onItemRightClick(stack, par2World, par3EntityPlayer);
        if(par3EntityPlayer.isSneaking() && !par2World.isRemote){
            if(stack.stackTagCompound ==null){
                stack.stackTagCompound = new NBTTagCompound();
            }
            if(stack.stackTagCompound.getInteger("Activated")==0){
                par3EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.bloodSword.activateEssentiaHarvest"));
                stack.stackTagCompound.setInteger("Activated",1);
            }else{
                par3EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.bloodSword.deactivateEssentiaHarvest"));
                stack.stackTagCompound.setInteger("Activated",0);
            }
        }
        return cache;
    }
}
