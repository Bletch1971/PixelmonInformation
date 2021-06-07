package bletch.pixelmoninformation.jei.brewing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.config.PixelmonItems;

import bletch.pixelmoninformation.utils.PixelmonUtils;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class BrewingRegistry {

	public static IForgeRegistry<BrewingEntry> REGISTRY;
	
	private BrewingRegistry() {}
	
	public static void addEntry(ItemStack input1, ItemStack input2, ItemStack output) {
		REGISTRY.register(new BrewingEntry(input1, input2, output));
	}
	
	public static void setRegistry(IForgeRegistry<BrewingEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<BrewingEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<BrewingEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }
    
	public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
    	ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
    	inputs.add(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER)); // minecraft water bottle
    	inputs.addAll(PixelmonItems.getPotionElixirList().stream()
    			.map(i -> new ItemStack(i))
				.collect(Collectors.toList()));
    	
    	List<ItemStack> pixelmonStacks = PixelmonUtils.getPixelmonItemStacks();
    	
    	for (ItemStack pixelmonStack: pixelmonStacks) {
    		for (ItemStack input: inputs) {
    			ItemStack output = BrewingRecipeRegistry.getOutput(input, pixelmonStack);
    			if (output != ItemStack.EMPTY) {
    				addEntry(pixelmonStack, input, output);
    			}
    		}
    	}
	}
}
