package bletch.pixelmoninformation.jei.anvil;

import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

@ParametersAreNonnullByDefault
public class AnvilWrapper implements IRecipeWrapper {

	private final AnvilEntry entry;
	
    public AnvilWrapper(AnvilEntry entry) {
        this.entry = entry;
    }

	public AnvilEntry getEntry() {
		return this.entry;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, entry.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, entry.getOutput());
	}

}
