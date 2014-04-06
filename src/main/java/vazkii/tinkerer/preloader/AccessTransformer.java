package vazkii.tinkerer.preloader;

import java.io.IOException;

/**
 * Created by Katrina on 06/04/14.
 */
public class AccessTransformer extends cpw.mods.fml.common.asm.transformers.AccessTransformer {
    public AccessTransformer() throws IOException {
        super("ThaumicTinkerer_at.cfg");
    }
}
