package com.nekokittygames.Thaumic.Tinkerer.common.libs;

/**
 * Miscalaneous IDs
 * Created by Katrina on 17/05/2015.
 */
object LibMisc {

    final val MOD_ID= "ThaumicTinkerer"
    final val MOD_NAME = "Thaumic Tinkerer"
    final val VERSION = "${version}"

    final val NETWORK_CHANNEL = MOD_ID

    final val DEPENDENCIES = "required-after:Forge;required-after:Thaumcraft;before:MagicBees;before:advthaum;after:IC2;after:ThaumicTinkererKami;after:Waila;after:ForgeMultipart;after:ComputerCraft"

    final val COMMON_PROXY = "com.nekokittygames.Thaumic.Tinkerer.common.core.proxy.TTCommonProxy"
    final val CLIENT_PROXY = "com.nekokittygames.Thaumic.Tinkerer.client.core.proxy.TTClientProxy"
    final val GUI_FACTORY = "com.nekokittygames.Thaumic.Tinkerer.client.gui.TTGuiFactory"
}
