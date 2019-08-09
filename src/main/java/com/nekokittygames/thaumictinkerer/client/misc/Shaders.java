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
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Shaders helper class
 */
public class Shaders {

    private static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    private static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
    private static boolean canShader = true;
    private static boolean checkedShader = false;
    private static boolean lighting = false;
    private static int change_alpha = 0;


    /**
     * Gets the Shader id for the shader that changes the alpha value
     *
     * @return shader id
     */
    public static int getChangeAlphaShader() {
        return change_alpha;
    }

    /**
     * Initialized shaders, deleting any previously loaded if called again
     */
    public static void initShaders() {
        if (Minecraft.getMinecraft().getResourceManager() instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(iResourceManager -> {
                deleteProgram(change_alpha);
                change_alpha = 0;
                loadShaders();
            });
        }
    }

    /**
     * loads shaders from disk if enabled
     */
    private static void loadShaders() {
        if (areShadersDisabled())
            return;

        change_alpha = createProgram(LibClientResources.Shaders.CHANGE_ALPHA_VERT, LibClientResources.Shaders.CHANGE_ALPHA_FRAG);

        ThaumicTinkerer.logger.info("Looks like we can shade with the best of them!");


    }

    /**
     * deletes a shader
     *
     * @param program shader id of the shader to delete
     */
    private static void deleteProgram(int program) {
        if (program != 0)
            ARBShaderObjects.glDeleteObjectARB(program);
    }

    /**
     * releases the current shader
     */
    public static void releaseShader() {
        if (lighting)
            GlStateManager.enableLighting();
        useShader(0);
    }

    /**
     * helper function to use a shader
     *
     * @param shaderId shader Id to use
     */
    private static void useShader(int shaderId) {
        useShader(shaderId, null);
    }

    /**
     * start a shader to be in use
     * @param shaderId shader id to use
     * @param callback callback function to obtain arguments for shader
     */
    public static void useShader(int shaderId, Consumer<Integer> callback) {
        if (areShadersDisabled())
            return;
        lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
        GlStateManager.disableLighting();
        ARBShaderObjects.glUseProgramObjectARB(shaderId);

        if (shaderId != 0 && callback != null) {
            callback.accept(shaderId);
        }
    }

    /**
     * Creates the shader program from the two seperate shaders
     *
     * @param vertexFilename   vertex shader filename
     * @param fragmentFilename fragment shader filename
     * @return shader program id
     */
    private static int createProgram(String vertexFilename, String fragmentFilename) {
        int vertId = 0;
        int fragId = 0;
        int program;
        if (vertexFilename != null)
            vertId = createShader(vertexFilename, VERT);
        if (fragmentFilename != null)
            fragId = createShader(fragmentFilename, FRAG);
        program = ARBShaderObjects.glCreateProgramObjectARB();
        if (program == 0)
            return 0;
        if (vertexFilename != null)
            ARBShaderObjects.glAttachObjectARB(program, vertId);
        if (fragmentFilename != null)
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

    /**
     * Creates a shader from a filename
     * @param fileName filename to load
     * @param type type of shader to load
     * @return shader Id
     */
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

    /**
     * helper function to read a file into a string
     * @param filename filename to read
     * @return string containing file contents
     * @throws Exception if file is unreadable
     */
    private static String readFileAsString(String filename) throws Exception {
        InputStream in = Shader.class.getResourceAsStream(filename);

        if (in == null)
            return "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * are shaders disabled
     *
     * @return <c>true</c> if the shaders are disabled, and are in config
     */
    private static boolean areShadersDisabled() {
        if (checkedShader)
            return !canShader;
        checkedShader = true;
        canShader = OpenGlHelper.areShadersSupported() && TTConfig.ShadersEnabled && !hasConflictingMods();
        return !canShader;
    }

    /**
     * Is there any mods that would interfere with the shaders running?
     * @return true if the mods conflict
     */
    private static boolean hasConflictingMods() {
        return Loader.isModLoaded("optifine");
    }
}
