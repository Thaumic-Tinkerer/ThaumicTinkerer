package cofh.api.tileentity;

import codechicken.lib.raytracer.IndexedCuboid6;

import java.util.List;

public interface ISubSelectionBoxProvider {

	public void addTraceableCuboids(List<IndexedCuboid6> cuboids);

}
