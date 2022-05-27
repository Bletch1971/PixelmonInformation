package bletch.pixelmoninformation.jei.pokemon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.lwjgl.opengl.GL11;

import com.pixelmonmod.pixelmon.enums.EnumType;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.PixelmonJei;
import bletch.pixelmoninformation.utils.Font;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.GuiHelper;
import mezz.jei.gui.elements.GuiIconButtonSmall;
import mezz.jei.gui.recipes.RecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;

@ParametersAreNonnullByDefault
public class PokemonWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

	private static final int buttonWidth = 13;
	private static final int buttonHeight = 13;
	
	private static ResourceLocation[] pokemonGenerationTextures = null;
	
	private final PokemonEntry entry;
	private IFocus<ItemStack> focus;
    private GuiButton buttonPrev;
    private GuiButton buttonNext;
    private int pageIndex;
    
    private GuiButton buttonPrevBiome;
    private GuiButton buttonNextBiome;
    private int pageIndexBiome;    
    
    public PokemonWrapper(PokemonEntry entry) {
        this.entry = entry;
        this.focus = null;
        this.pageIndex = 1;
        this.pageIndexBiome = 1;
        
        if (pokemonGenerationTextures == null) {
        	populatePokemonGenerationTextures();
        }
        
		GuiHelper guiHelper = (GuiHelper) PixelmonJei.getGuiHelper();
		if (guiHelper != null) {
			IDrawableStatic arrowPrevious = guiHelper.getArrowPrevious();
			IDrawableStatic arrowNext = guiHelper.getArrowNext();
			
			if (arrowPrevious != null && arrowNext != null) {
				this.buttonPrev = new GuiIconButtonSmall(0, 3, 97, buttonWidth, buttonHeight, arrowPrevious);
	        	this.buttonNext = new GuiIconButtonSmall(1, 45, 97, buttonWidth, buttonHeight, arrowNext);
	        	updateButtonsState();
			}

			IDrawableStatic arrowPreviousBiome = guiHelper.getArrowPrevious();
			IDrawableStatic arrowNextBiome = guiHelper.getArrowNext();
			
			if (arrowPreviousBiome != null && arrowNextBiome != null) {
				this.buttonPrevBiome = new GuiIconButtonSmall(2, 62, 97, buttonWidth, buttonHeight, arrowPreviousBiome);
	        	this.buttonNextBiome = new GuiIconButtonSmall(3, 150, 97, buttonWidth, buttonHeight, arrowNextBiome);
	        	updateButtonsStateBiomes();
			}
		}
    }

	public PokemonEntry getEntry() {
		return this.entry;
	}

    public IFocus<ItemStack> getFocus() {
    	return this.focus;
    }
    
    public int getPageIndex() {
    	return this.pageIndex;
    }
    
    public int getPageIndexBiome() {
    	return this.pageIndexBiome;
    }
    
    public IFocus<ItemStack> setFocus(IFocus<ItemStack> focus) {
    	// check if the focus is changing
    	if (!(this.focus == null && focus == null || this.focus != null && focus != null && this.focus.equals(focus))) {
    		// focus has changed, reset the page index
    		this.pageIndex = 1;
    	}

    	return this.focus = focus;
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.ITEM, this.entry.getItemStacks(null));
    }

    public int amountOfItems() {
    	return amountOfItems(this.focus);
    }
    
    public int amountOfItems(@Nullable IFocus<ItemStack> focus) {
    	return getItems(focus).size();
    }

    public List<ItemStack> getItems() {
        return getItems(this.focus);
    }
    
    public List<ItemStack> getItems(@Nullable IFocus<ItemStack> focus) {
        List<ItemStack> stacks = this.entry.getItemStacks(focus);
        stacks.removeIf(Objects::isNull);
        return stacks;
    }
    
    public List<ItemStack> getItems(int startIndex, int count) {
    	return getItems(this.focus, startIndex, count);
    }
    
    public List<ItemStack> getItems(@Nullable IFocus<ItemStack> focus, int startIndex, int count) {
        List<ItemStack> stacks = getItems(focus);
        
        if (stacks.size() <= startIndex) {
        	return new ArrayList<ItemStack>(0);
        }
        if (stacks.size() <= startIndex + count) {
        	count = stacks.size() - startIndex;
        }

    	return stacks.subList(startIndex, startIndex + count);
    }

    public int amountOfBiomes() {
    	return getBiomes().size();
    }

    public List<Biome> getBiomes() {
        List<Biome> biomes = this.entry.getBiomeList();
        biomes.removeIf(Objects::isNull);
        return biomes;
    }
    
    public List<Biome> getBiomes(int startIndex, int count) {
        List<Biome> biomes = getBiomes();
        
        if (biomes.size() <= startIndex) {
        	return new ArrayList<Biome>(0);
        }
        if (biomes.size() <= startIndex + count) {
        	count = biomes.size() - startIndex;
        }

    	return biomes.subList(startIndex, startIndex + count);
    }
    
    private void populatePokemonGenerationTextures() {
    	pokemonGenerationTextures = new ResourceLocation[9];
    	
    	pokemonGenerationTextures[0] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen1.png");
    	pokemonGenerationTextures[1] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen2.png");
    	pokemonGenerationTextures[2] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen3.png");
    	pokemonGenerationTextures[3] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen4.png");
    	pokemonGenerationTextures[4] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen5.png");
    	pokemonGenerationTextures[5] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen6.png");
    	pokemonGenerationTextures[6] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen7.png");
    	pokemonGenerationTextures[7] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen8.png");
    	pokemonGenerationTextures[8] = new ResourceLocation(ModDetails.MOD_ID, "textures/pokemon/generations/gen9.png");
    }
    
    public void updateButtonsState() {
    	double temp = (double)amountOfItems() / (double)PokemonCategory.ITEMS_PER_PAGE;
    	int pages = (int)Math.ceil(temp);
    	
    	if (pages > 1) {
    		this.buttonPrev.visible = true;
    		this.buttonNext.visible = true;
    	} else {
    		this.buttonPrev.visible = false;
    		this.buttonNext.visible = false;
    	}
    }
    
    public void updateButtonsStateBiomes() {
    	double temp = (double)amountOfBiomes() / (double)PokemonCategory.BIOMES_PER_PAGE;
    	int pages = (int)Math.ceil(temp);
    	
    	if (pages > 1) {
    		this.buttonPrevBiome.visible = true;
    		this.buttonNextBiome.visible = true;
    	} else {
    		this.buttonPrevBiome.visible = false;
    		this.buttonNextBiome.visible = false;
    	}
    }
    
    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    	// output the name of the pokemon
    	Font.normal.printLeft(this.entry.getLocalisedName(), 2, 2);      

        int x = 0;
        int y = 0;
    	
        // output the pokemon types
        x = 2;
        y = 10;
        String delimiter = " ";
        String output = TextUtils.translate("gui.pokemon.type");
    	Font.small.printLeft(output, x, y);
    	x += Font.small.getStringWidth(output);
    	
        for (EnumType type : this.entry.getTypeList()) {
        	Font.small.printLeft(delimiter, x, y);
        	x += Font.small.getStringWidth(delimiter);

        	output = type.getLocalizedName();
        	Font.small.printLeft(output, x, y, type.getColor());
        	x += Font.small.getStringWidth(output);
        	
        	delimiter = ", ";
        }

        // output the national pokedex number
        Font.small.printLeft(String.format(TextUtils.translate("gui.pokemon.dexnum"), this.entry.getSpecies().getNationalPokedexNumber()), 2, 18);

        // output the drops
        Font.small.printLeft(String.format(TextUtils.translate("gui.pokemon.drops"), ""), 2, 28);
        
    	double temp = (double)amountOfItems() / (double)PokemonCategory.ITEMS_PER_PAGE;
    	int pages = (int)Math.ceil(temp);
    	
    	if (pages > 1) {
    		// if there is 2 or more pages, then draw the darker background with the page details
    		Gui.drawRect(3, 97, 58, 110, 0x40FFFFFF);
    		Font.normal.printCentered(this.pageIndex + "/" + pages, 30, 100, Color.WHITE.getRGB(), true);
    	}
    	
        this.buttonPrev.drawButton(minecraft, mouseX, mouseY, 1f);
        this.buttonNext.drawButton(minecraft, mouseX, mouseY, 1f);

        // output the biomes
        int startIndex = (this.pageIndexBiome - 1) * PokemonCategory.BIOMES_PER_PAGE;
        List<Biome> biomes = getBiomes(startIndex, PokemonCategory.BIOMES_PER_PAGE);
        x = PokemonCategory.BIOMELEFT;
        y = PokemonCategory.BIOMETOP;
        
    	temp = (double)amountOfBiomes() / (double)PokemonCategory.BIOMES_PER_PAGE;
    	pages = (int)Math.ceil(temp);
	
    	if (pages > 1) {
    		// if there is 2 or more pages, then draw the darker background with the page details
    		Gui.drawRect(62, 97, 163, 110, 0x40FFFFFF);
    		Font.normal.printCentered(this.pageIndexBiome + "/" + pages, 112, 100, Color.WHITE.getRGB(), true);
    	}
    	
    	Font.small.printLeft(String.format(TextUtils.translate("gui.pokemon.biomes"), ""), 63, 28);
		for (int biomeIndex = 0; biomeIndex < biomes.size(); biomeIndex++) {
			Font.small.printLeft(Font.small.trimStringToWidth(biomes.get(biomeIndex).getBiomeName(), 99, true), x, y, Color.WHITE.getRGB(), true);
			y += Font.small.fontRenderer.FONT_HEIGHT;
		}
    	
        this.buttonPrevBiome.drawButton(minecraft, mouseX, mouseY, 1f);
        this.buttonNextBiome.drawButton(minecraft, mouseX, mouseY, 1f);
        
    	// output the generation image of the pokemon (done last to avoid any drawing problems)
        int pokemonGeneration = this.entry.getGeneration();
        if (pokemonGeneration > 0 && pokemonGeneration < pokemonGenerationTextures.length) {
            int u = 0;
            int v = 0;
            int width = 24;
            int height = 11;
            x = recipeWidth - width;
            y = 0;
            
            GL11.glEnable(GL11.GL_BLEND);
        	GL11.glColor4f(1f, 1f, 1f, 1f);            
        	
        	// bind the texture
        	minecraft.getTextureManager().bindTexture(pokemonGenerationTextures[pokemonGeneration - 1]);
        	// draw the texture
        	Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, width, height);
        } 
    }
    
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
    	List<String> tooltip = new LinkedList<>();
    	
    	// check if the mouse is over the name of the pokemon
    	int height = Font.normal.fontRenderer.FONT_HEIGHT;
    	if (mouseY >= 2 && mouseY <= height + 2) {
    		
        	String name = this.entry.getLocalisedName();
        	int width = Font.normal.getStringWidth(name);
        	
        	if (mouseX >= 2 && mouseX <= width + 2) {
        		String output = this.entry.getLocalizedDescription();
        		if (!StringUtils.isNullOrWhitespace(output)) {
            		tooltip.add(TextFormatting.DARK_AQUA + name);
            		tooltip.add(output);
        		}
        	}
    	}

    	
    	// check if the mouse is over the type of the pokemon
        height = Font.small.fontRenderer.FONT_HEIGHT;
        
        if (mouseY >= 10 && mouseY <= height + 10) {
            String delimiter = " ";
            int x = 2;
        	
        	String output = TextUtils.translate("gui.pokemon.type");
	    	x += Font.small.getStringWidth(output);
	    	
	        for (EnumType type : this.entry.getTypeList()) {
	        	x += Font.small.getStringWidth(delimiter);
	
	        	String typeValue = type.name().toLowerCase();
	        	
	        	output = type.getLocalizedName();
	        	int width = Font.small.getStringWidth(output);
	        	
	        	if (mouseX >= x && mouseX <= width + x) {
	        		tooltip.add(TextFormatting.DARK_AQUA + output);
	        		
	        		output = TextUtils.translate("pokemontype." + typeValue + ".strong");
	        		if (!StringUtils.isNullOrWhitespace(output)) {
	        			tooltip.add(output);
	        		}
	        		output = TextUtils.translate("pokemontype." + typeValue + ".weak");
	        		if (!StringUtils.isNullOrWhitespace(output)) {
	        			tooltip.add(output);
	        		}
	        		output = TextUtils.translate("pokemontype." + typeValue + ".bad");
	        		if (!StringUtils.isNullOrWhitespace(output)) {
	        			tooltip.add(output);
	        		}
	        		break;
	        	}

	        	x += width;
	        	delimiter = ", ";
	        }
        }

        
    	return tooltip;
    }
    
    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
    	
    	double temp = (double)amountOfItems() / (double)PokemonCategory.ITEMS_PER_PAGE;
    	int pages = (int)Math.ceil(temp);
    	
    	if (this.buttonPrev.mousePressed(minecraft, mouseX, mouseY)) {
    		this.buttonPrev.playPressSound(minecraft.getSoundHandler());
    		
    		try {
	    		RecipesGui recipesGui = (RecipesGui)PixelmonJei.getJeiRuntime().getRecipesGui();
	    		if (recipesGui != null) {
	        		this.pageIndex--;
	        		if (this.pageIndex <= 0) {
	        			this.pageIndex = pages;
	        		}
	        		
	        		recipesGui.onStateChange();
	        		updateButtonsState();
	        		
	        		return true;
	    		}
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}	    		
    	}

    	if (this.buttonNext.mousePressed(minecraft, mouseX, mouseY)) {
    		this.buttonNext.playPressSound(minecraft.getSoundHandler());

    		try {
        		RecipesGui recipesGui = (RecipesGui)PixelmonJei.getJeiRuntime().getRecipesGui();
        		if (recipesGui != null) {
            		this.pageIndex++;
            		if (this.pageIndex > pages) {
            			this.pageIndex = 1;
            		}
            		
            		recipesGui.onStateChange();
            		updateButtonsState();
            		
            		return true;
        		}    		
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}

    	temp = (double)amountOfBiomes() / (double)PokemonCategory.BIOMES_PER_PAGE;
    	pages = (int)Math.ceil(temp);
    	
    	if (this.buttonPrevBiome.mousePressed(minecraft, mouseX, mouseY)) {
    		this.buttonPrevBiome.playPressSound(minecraft.getSoundHandler());
    		
    		try {
        		this.pageIndexBiome--;
        		if (this.pageIndexBiome <= 0) {
        			this.pageIndexBiome = pages;
        		}

        		updateButtonsStateBiomes();
        		
        		return true;
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}	    		
    	}

    	if (this.buttonNextBiome.mousePressed(minecraft, mouseX, mouseY)) {
    		this.buttonNextBiome.playPressSound(minecraft.getSoundHandler());

    		try {
        		this.pageIndexBiome++;
        		if (this.pageIndexBiome > pages) {
        			this.pageIndexBiome = 1;
        		}

        		updateButtonsStateBiomes();
        		
        		return true; 		
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}
    	
        return false;
    }
    
    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
    	tooltip.add(this.entry.getItemDrop(ingredient).toString());
    }
    
}
