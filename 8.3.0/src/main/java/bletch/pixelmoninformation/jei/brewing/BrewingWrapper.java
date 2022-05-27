package bletch.pixelmoninformation.jei.brewing;

import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

@ParametersAreNonnullByDefault
public class BrewingWrapper implements IRecipeWrapper {
	
	private final BrewingEntry entry;
	
	public BrewingWrapper(BrewingEntry entry) {
		this.entry = entry;
	}

	public BrewingEntry getEntry() {
		return this.entry;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, entry.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, entry.getOutput());
	}
}
