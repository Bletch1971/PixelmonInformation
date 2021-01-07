package bletch.pixelmoninformation.jei.infuser;

import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

@ParametersAreNonnullByDefault
public class InfuserWrapper implements IRecipeWrapper {

	private final InfuserEntry entry;
	
    public InfuserWrapper(InfuserEntry entry) {
        this.entry = entry;
    }

	public InfuserEntry getEntry() {
		return this.entry;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, entry.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, entry.getOutput());
	}

}
