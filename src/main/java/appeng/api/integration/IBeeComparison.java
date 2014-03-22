package appeng.api.integration;

import forestry.api.genetics.IIndividual;

/**
 * An interface to get access to the individual settings for AE's Internal Bee
 * Comparison handler.
 * 
 * Assessable via: ( IBeeComparison )
 * IAEItemStack.getTagCompound().getSpecialComparison()
 * 
 * If you don't have the forestry API, just delete this file when using the API.
 */
public interface IBeeComparison
{

	/**
	 * @return the Forestry IIndividual for this comparison object
	 */
	IIndividual getIndividual();

}