/**
 * This class was created by <Vazkii>. It can be integrated in any of your
 * minecraft projects at will. If your mod is open source, this header must
 * be present.
 * 
 * This class is licensed under a CC-BY 3.0 license.
 * http://creativecommons.org/licenses/by/3.0/
 */
package vazkii.tinkerer.client.model;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.common.ForgeDirection;

public class ModelCubeWorld extends ModelBase {

    byte[][][][] modelData;
    int[] pointers;

    final ModelRenderer cube;

    /**
     * @param stream An InputStream where the model can be loaded from. You can get one using
     * {@link Class#getResourceAsStream(String)}, for loading resources in the mod's package.
     * Input streams are diverse, so this constructor can be called from a URL connection, a file
     * in the local file system, or even an incoming packet.
     */
    public ModelCubeWorld(InputStream stream) {
        cube = new ModelRenderer(this, 0, 0).addBox(0F, 0F, 0F, 1, 1, 1);

        int i;
        int it = 0;
        int area = 0;

        byte[] sizesBuffer = new byte[12];

        try {
            while ((i = stream.read()) != -1) {
                final byte val = (byte) (i & 0xFF);
                if (it < 12)
                    sizesBuffer[it] = val;
                else {
                    if (it == 12) {
                        final int x = sizesBuffer[0] | sizesBuffer[1] << 8 | sizesBuffer[2] << 16 | sizesBuffer[3] << 24;
                        final int y = sizesBuffer[4] | sizesBuffer[5] << 8 | sizesBuffer[6] << 16 | sizesBuffer[7] << 24;
                        final int z = sizesBuffer[8] | sizesBuffer[9] << 8 | sizesBuffer[10] << 16 | sizesBuffer[11] << 24;

                        modelData = new byte[x][y][z][3];
                        area = x * y;

                        sizesBuffer = null;
                    }

                    final int it1 = it - 12;
                    final int it1d = it1 / 3;

                    final int z = it1d / area;

                    final int it2d = it1d - area * z; // Exploiting the lack of rounding in integer values
                    final int x = it2d % modelData.length;
                    final int y = it2d / modelData.length;

                    if (x >= modelData.length || y >= modelData[0].length || z >= modelData[0][0].length) {
                        it++;
                        continue;
                    }

                    modelData[x][y][z][it1 % 3] = val;
                }

                it++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        calculatePointers(false);
    }

    /**
     * Returns the data of the model, in a four dimensional byte array.<br><br>
     * Dimension 0 - The X coordinates<br>
     * Dimension 1 - The Y coordinates<br>
     * Dimension 2 - The Z coordinates<br>
     * Dimension 3 - Always of length 3, contains, respectively, red, green and blue values.
     * 
     */
    public byte[][][][] getModelData() {
        return modelData;
    }

    /**
     * Recalculates the pointers for the rendering. Must be done if you change the model data, otherwise it
     * won't make use of the new data.
     * @param renderHidden If true, the renderer will draw every single cube, even cubes that are obstructed
     * on all 6 sides. Not recommended!
     */
    public void calculatePointers(final boolean renderHidden) {
        List<Integer> newPointers = new ArrayList();

        for (int x = 0; x < modelData.length; x++)
            for (int y = 0; y < modelData[0].length; y++)
                for (int z = 0; z < modelData[0][0].length; z++) {
                    if (cubeExists(x, y, z)) {
                        if (!renderHidden) {
                            int hiddenSides = 0;
                            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
                                if (cubeExists(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
                                    ++hiddenSides;

                            if (hiddenSides == 6)
                                continue;
                        }

                        newPointers.add(x & 1023 | (y & 1023) << 10 | (z & 1023) << 20);
                    }
                }

        pointers = new int[newPointers.size()];
        int it = 0;
        for (int i : newPointers) {
            pointers[it] = i;
            it++;
        }
    }

    /**
     * Returns true if a cube exists at the coordinates passed in.
     */
    public boolean cubeExists(final int x, final int y, final int z) {
        final int xs = modelData.length;
        final int ys = modelData[0].length;
        final int zs = modelData[0][0].length;

        if (x >= xs || x < 0 || y >= ys || y < 0 || z >= zs || z < 0)
            return false;

        final byte[] values = modelData[x][y][z];
        return values[0] != 0 || values[1] != 0 || values[2] != 0;
    }

    /**
     * Actually renders the model, requires previous translation. The model is rendered in a scale of
     * 0.0625F, or 1/16, the side of each voxel is one 16th of the side of a regular minecraft block.
     */
    public void render() {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_RESCALE_NORMAL);

        final float scale = 0.0625F;
        glScalef(scale, scale, scale);
        glRotatef(-90F, 1F, 0F, 0F);

        for (int i : pointers) {
            final int x = i & 1023;
            final int y = i >> 10 & 1023;
            final int z = i >> 20 & 1023;

            glTranslatef(x, y, z);

            final byte[] color = modelData[x][y][z];
            glColor3ub(color[0], color[1], color[2]);

            cube.render(1F);

            glTranslatef(-x, -y, -z);
        }

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_RESCALE_NORMAL);
        glPopMatrix();
    }
}