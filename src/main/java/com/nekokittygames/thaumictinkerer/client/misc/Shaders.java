package com.nekokittygames.thaumictinkerer.client.misc;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.shader.Shader;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Shaders {

    private static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    private static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
    private static boolean canShader = true;
    private static boolean checkedShader = false;
    private static boolean lighting = false;
    private static int change_alpha = 0;


    public static int getChangeAlphaShader() {
        return change_alpha;
    }

    public static void initShaders() {
        if (Minecraft.getMinecraft().getResourceManager() instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(iResourceManager -> {
                deleteProgram(change_alpha);
                change_alpha = 0;
                loadShaders();
            });
        }
    }

    private static void loadShaders() {
        if (!enableShaders())
            return;

        change_alpha = createProgram(LibClientResources.Shaders.CHANGE_ALPHA_VERT, LibClientResources.Shaders.CHANGE_ALPHA_FRAG);


        ThaumicTinkerer.logger.info("Looks like we can shade with the best of them!");


    }

    public static void deleteProgram(int program) {
        if (program != 0)
            ARBShaderObjects.glDeleteObjectARB(program);
    }

    public static void releaseShader() {
        if (lighting)
            GlStateManager.enableLighting();
        useShader(0);
    }

    private static void useShader(int shaderId) {
        useShader(shaderId, null);
    }

    public static void useShader(int shaderId, Consumer<Integer> callback) {
        if (!enableShaders())
            return;
        lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        GlStateManager.disableLighting();
        ARBShaderObjects.glUseProgramObjectARB(shaderId);

        if (shaderId != 0 && callback != null) {
            callback.accept(shaderId);
        }
    }

    private static int createProgram(String vert, String frag) {
        int vertId = 0;
        int fragId = 0;
        int program;
        ;
        if (vert != null)
            vertId = createShader(vert, VERT);
        if (frag != null)
            fragId = createShader(frag, FRAG);
        program = ARBShaderObjects.glCreateProgramObjectARB();
        if (program == 0)
            return 0;
        if (vert != null)
            ARBShaderObjects.glAttachObjectARB(program, vertId);
        if (frag != null)
            ARBShaderObjects.glAttachObjectARB(program, fragId);
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            ThaumicTinkerer.logger.error(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return 0;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            ThaumicTinkerer.logger.error(ARBShaderObjects.glGetInfoLogARB(program, ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
            return 0;
        }
        ARBShaderObjects.glDeleteObjectARB(fragId);
        ARBShaderObjects.glDeleteObjectARB(vertId);
        return program;
    }

    private static int createShader(String fileName, int type) {
        int shaderId = 0;
        try {
            shaderId = ARBShaderObjects.glCreateShaderObjectARB(type);
            if (shaderId == 0)
                return 0;
            String shaderSource = readFileAsString(fileName);
            ARBShaderObjects.glShaderSourceARB(shaderId, shaderSource);
            ARBShaderObjects.glCompileShaderARB(shaderId);
            if (ARBShaderObjects.glGetObjectParameteriARB(shaderId, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
                ThaumicTinkerer.logger.error(ARBShaderObjects.glGetInfoLogARB(shaderId, ARBShaderObjects.glGetObjectParameteriARB(shaderId, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));
                return 0;
            }
            return shaderId;

        } catch (Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shaderId);
            ThaumicTinkerer.logger.error("Error registering shader ", e);
            return 0;
        }
    }

    private static String readFileAsString(String filename) throws Exception {
        InputStream in = Shader.class.getResourceAsStream(filename);

        if (in == null)
            return "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static boolean enableShaders() {
        if (checkedShader)
            return canShader;
        checkedShader = true;
        canShader = OpenGlHelper.areShadersSupported() && TTConfig.ShadersEnabled && !hasConflictingMods();
        return canShader;
    }

    private static boolean hasConflictingMods() {
        return Loader.isModLoaded("optifine");
    }
}
