package bletch.pixelmoninformation.jei.anvil;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.BlankRecipeCategory;
import bletch.pixelmoninformation.jei.PixelmonJei;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;

@ParametersAreNonnullByDefault
public class AnvilCategory extends BlankRecipeCategory<AnvilWrapper> {

	public static final String CATEGORY = "anvil";
    
	private static final ResourceLocation TEXTURE_LOCOCATION_ICON = new ResourceLocation(ModDetails.MOD_ID, "textures/gui/jeicategorytabs.png");
	private static final ResourceLocation TEXTURE_LOCOCATION_GUI = new ResourceLocation(ModDetails.MOD_ID, "textures/gui/gui_" + CATEGORY + ".png");

	public AnvilCategory() {
    	super(CATEGORY, 
    			PixelmonJei.getGuiHelper().createDrawable(TEXTURE_LOCOCATION_ICON, 0, 16, 16, 16),
    			PixelmonJei.getGuiHelper().createDrawable(TEXTURE_LOCOCATION_GUI, 0, 0, 120, 70));
    }
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AnvilWrapper recipeWrapper, IIngredients ingredients) {
		
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		int slotIndex = 0;
		
		// Initialize the input slots
		guiStacks.init(slotIndex++, true, 22, 42);
		
		// Initialize the output slots
		guiStacks.init(slotIndex++, false, 80, 42);
		
		// populate the slots
        guiStacks.set(ingredients);
	}

}
