package bletch.pixelmoninformation.jei.brewing;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.BlankRecipeCategory;
import bletch.pixelmoninformation.jei.PixelmonJei;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;

@ParametersAreNonnullByDefault
public class BrewingCategory extends BlankRecipeCategory<BrewingWrapper> {

	public static final String CATEGORY = "brewer";

	private static final ResourceLocation TEXTURE_LOCOCATION_ICON = new ResourceLocation(ModDetails.MOD_ID, "textures/gui/jeicategorytabs.png");
	private static final ResourceLocation TEXTURE_LOCOCATION_GUI = new ResourceLocation(ModDetails.MOD_ID, "textures/gui/gui_" + CATEGORY + ".png");

    public BrewingCategory() {
    	super(CATEGORY, 
    			PixelmonJei.getGuiHelper().createDrawable(TEXTURE_LOCOCATION_ICON, 16, 32, 16, 16),
    			PixelmonJei.getGuiHelper().createDrawable(TEXTURE_LOCOCATION_GUI, 0, 0, 102, 61));
    }
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BrewingWrapper recipeWrapper, IIngredients ingredients) {
		
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		int slotIndex = 0;
		
		// Initialize the input slots
		guiStacks.init(slotIndex++, true, 25, 1);

		guiStacks.init(slotIndex++, true, 2, 35);
		guiStacks.init(slotIndex++, true, 25, 42);
		guiStacks.init(slotIndex++, true, 48, 35);
		
		// Initialize the output slots
		guiStacks.init(slotIndex++, false, 79, 21);
		
		// populate the slots
        guiStacks.set(ingredients);
	}
}
