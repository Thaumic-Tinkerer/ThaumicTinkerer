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
 * File Created @ [Nov 25, 2013, 2:19:00 PM (GMT)]
 */
package vazkii.tinkerer.common.lib;

public final class LibObfuscation {

	/** EntityPlayer **/
	public static final String[] ITEM_IN_USE = new String[] { "itemInUse", "field_71074_e", "f" };
	public static final String[] ITEM_IN_USE_COUNT = new String[] { "itemInUseCount", "field_71072_f", "g" };

	/** Potion **/
	public static final String[] IS_BAD_EFFECT = new String[] { "isBadEffect", "field_76418_K", "J" };

	/** EntityAIAvoidEntity **/
	public static final String[] TARGET_ENTITY_CLASS = new String[] { "targetEntityClass", "field_75381_h", "i" };

	/** EntityAINearestAttackableTarget **/
	public static final String[] TARGET_CLASS = new String[] { "targetClass", "field_75307_b", "a" };

	/** EntityCreeper **/
	public static final String[] TIME_SINCE_IGNITED = new String[] {  "timeSinceIgnited", "field_70833_d" , "bq" };
}
