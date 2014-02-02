package vazkii.tinkerer.common.core.helper;

import net.minecraft.entity.player.EntityPlayer;

// From OpenBlocksLib: https://github.com/OpenMods/OpenModsLib
public class ExperienceHelper {

    public static int getPlayerXP(EntityPlayer player) {
        return (int)(getExperienceForLevel(player.experienceLevel) + player.experience * player.xpBarCap());
}

	public static void drainPlayerXP(EntityPlayer player, int amount) {
		addPlayerXP(player, -amount);
	}

	public static void addPlayerXP(EntityPlayer player, int amount) {
		int experience = getPlayerXP(player) + amount;
		player.experienceTotal = experience;
		player.experienceLevel = getLevelForExperience(experience);
		int expForLevel = getExperienceForLevel(player.experienceLevel);
		player.experience = (float)(experience - expForLevel) / (float)player.xpBarCap();
	}

	public static int getExperienceForLevel(int level) {
		if (level == 0) { return 0; }
		if (level > 0 && level < 16) {
			return level * 17;
		} else if (level > 15 && level < 31) {
			return (int)(1.5 * Math.pow(level, 2) - 29.5 * level + 360);
		} else {
			return (int)(3.5 * Math.pow(level, 2) - 151.5 * level + 2220);
		}
	}

	public static int getLevelForExperience(int experience) {
		int i = 0;
		while (getExperienceForLevel(i) <= experience) {
			i++;
		}
		return i - 1;
	}

}
