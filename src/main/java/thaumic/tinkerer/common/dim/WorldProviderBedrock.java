package thaumic.tinkerer.common.dim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

public class WorldProviderBedrock extends WorldProvider {

    private float[] colorsSunriseSunset = new float[4];

    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.beach, this.dimensionId);
        this.dimensionId = ConfigHandler.bedrockDimensionID;
        this.hasNoSky = false;
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderBedrock(this.worldObj, this.worldObj.getSeed(), false);
    }

    public int getAverageGroundLevel() {
        return 1;
    }

    @Override
    public boolean doesXZShowFog(int par1, int par2) {
        return false;
    }

    public String getDimensionName() {
        return "Bedrock";
    }

    public boolean renderStars() {
        return true;
    }

    public float getStarBrightness(World world, float f) {
        return 10.0F;
    }

    public boolean renderClouds() {
        return true;
    }

    public boolean renderVoidFog() {
        return false;
    }

    public boolean renderEndSky() {
        return false;
    }

    public float setSunSize() {
        return 10.0F;
    }

    public float setMoonSize() {
        return 8.0F;
    }

    public boolean canRespawnHere() {
        return false;
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return 0F;
    }

    public boolean canCoordinateBeSpawn(int par1, int par2) {
        return false;
    }

    public ChunkCoordinates getEntrancePortalLocation() {
        return new ChunkCoordinates(50, 5, 0);
    }

    protected void generateLightBrightnessTable() {
        float f = 12.0F;
        for (int i = 0; i <= 15; i++) {
            float f1 = 12.0F - i / 15.0F;
            this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
        }
    }

    @SideOnly(Side.CLIENT)
    public String getWelcomeMessage() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float par1, float par2) {
        float f2 = 0.4F;
        float f3 = MathHelper.cos(par1 * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0.0F;
        if ((f3 >= f4 - f2) && (f3 <= f4 + f2)) {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
            //noinspection UnusedAssignment
            f6 *= f6;
            this.colorsSunriseSunset[0] = (f5 * 0.3F + 0.7F);
            this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + 0.2F);
            this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + 0.2F);
            this.colorsSunriseSunset[3] = f6;
            return this.colorsSunriseSunset;
        }
        return null;
    }

    public float calculateCelestialAngle(long par1, float par3) {
        int j = (int) (par1 % 24000L);
        float f1 = (j + par3) / 24000.0F - 0.25F;
        if (f1 < 0.0F) {
            f1 += 1.0F;
        }
        if (f1 > 1.0F) {
            f1 -= 1.0F;
        }
        float f2 = f1;
        f1 = 1.0F - (float) ((Math.cos(f1 * 3.141592653589793D) + 1.0D) / 2.0D);
        f1 = f2 + (f1 - f2) / 3.0F;
        return f1;
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2) {
        int i = 10518688;
        float f2 = MathHelper.cos(par1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }
        float f3 = (i >> 16 & 0xFF) / 255.0F;
        float f4 = (i >> 8 & 0xFF) / 255.0F;
        float f5 = (i & 0xFF) / 255.0F;
        f3 *= (f2 * 0.0F + 0.15F);
        f4 *= (f2 * 0.0F + 0.15F);
        f5 *= (f2 * 0.0F + 0.15F);
        return Vec3.createVectorHelper(f3, f4, f5);
    }
}
