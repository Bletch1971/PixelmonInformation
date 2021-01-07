package bletch.pixelmoninformation.jei.mechanicalanvil;

import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

@ParametersAreNonnullByDefault
public class MechanicalAnvilWrapper implements IRecipeWrapper {

	private final MechanicalAnvilEntry entry;
	
	public MechanicalAnvilWrapper(MechanicalAnvilEntry entry) {
		this.entry = entry;
	}

	public MechanicalAnvilEntry getEntry() {
		return this.entry;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(VanillaTypes.ITEM, entry.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, entry.getOutput());
	}
    
}
