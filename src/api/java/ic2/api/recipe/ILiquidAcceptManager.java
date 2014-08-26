package ic2.api.recipe;

import net.minecraftforge.fluids.Fluid;

import java.util.Set;

public interface ILiquidAcceptManager {
    boolean acceptsFluid(Fluid fluid);

    Set<Fluid> getAcceptedFluids();
}
