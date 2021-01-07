package bletch.pixelmoninformation.jei.infuser;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.BlankRecipeCategory;
import bletch.pixelmoninformation.jei.PixelmonJei;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;

@ParametersAreNonnullByDefault
public class InfuserCategory extends BlankRecipeCategory<InfuserWrapper> {

	public static final String CATEGORY = "infuser";
    
	private static final ResourceLocation TEXTURE_LOCOCATION_ICON = new ResourceLocation(ModDetails.MOD_ID, "textures/gui/jeicategorytabs.png");
	private static final ResourceLocation TEXTURE_LOCOCATION_GUI = new ResourceLocation(ModDetails.MOD_ID, "textures/gui/gui_" + CATEGORY + ".png");

	public InfuserCategory() {
    	super(CATEGORY, 
    			PixelmonJei.getGuiHelper().createDrawable(TEXTURE_LOCOCATION_ICON, 0, 32, 16, 16),
    			PixelmonJei.getGuiHelper().createDrawable(TEXTURE_LOCOCATION_GUI, 0, 0, 144, 55));
    }
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, InfuserWrapper recipeWrapper, IIngredients ingredients) {
		
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		int slotIndex = 0;
		
		// Initialize the input slots
		guiStacks.init(slotIndex++, true, 62, 13);
		guiStacks.init(slotIndex++, true, 62, 32);
		
		// Initialize the output slots
		guiStacks.init(slotIndex++, false, 116, 21);
		
		// populate the slots
        guiStacks.set(ingredients);
	}

}
