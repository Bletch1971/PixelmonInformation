package bletch.pixelmoninformation.jei.infuser;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.api.recipe.InfuserRecipes;
import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class InfuserRegistry {

	public static IForgeRegistry<InfuserEntry> REGISTRY;
	
	private InfuserRegistry() {}
	
	public static void addEntry(ItemStack input1, ItemStack input2, ItemStack output) {
		REGISTRY.register(new InfuserEntry(input1, input2, output));
	}
	
	public static void setRegistry(IForgeRegistry<InfuserEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<InfuserEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<InfuserEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }
    
    public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
		Map<Tuple<ItemStack, ItemStack>, Tuple<ItemStack, Integer>> infuserRecipes = (Map<Tuple<ItemStack, ItemStack>, Tuple<ItemStack, Integer>>)getInfuserRecipeList(InfuserRecipes.instance());
		if (infuserRecipes == null || infuserRecipes.isEmpty())
			return;
		
		for (Entry<Tuple<ItemStack, ItemStack>, Tuple<ItemStack, Integer>> recipe : infuserRecipes.entrySet()) {
			addEntry(recipe.getKey().getFirst(), recipe.getKey().getSecond(), recipe.getValue().getFirst());
		}
	}
    
    private static Map<?, ?> getInfuserRecipeList(InfuserRecipes infuserRecipes) {
		Field resultsListField = null;
		
		try {
			resultsListField = InfuserRecipes.class.getDeclaredField("resultsList");
			if (resultsListField == null) {
				ModDetails.MOD_LOGGER.error("An issue occured on getDeclaredField(resultsList) from InfuserRecipes.");
				return null;
			}
		}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Encountered an issue fetching field pokemonDrops from DropItemRegistry.\n" + ex.getMessage());
    		return null;
    	}   		
		
		Object resultsListValue = null;
		
		try {
			if (!resultsListField.isAccessible()) {
				resultsListField.setAccessible(true);
			}
			
			resultsListValue = resultsListField.get(infuserRecipes);
			if (resultsListValue == null) {
				ModDetails.MOD_LOGGER.error("Field value of resultsList is null.");
				return null;
			}
			
			if (!(resultsListValue instanceof Map)) {
				ModDetails.MOD_LOGGER.error("Field value of resultsList is not a Map.");
				return null;
			}
		}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Encountered an issue fetching field value of resultsList.\n" + ex.getMessage());
    		return null;
    	} 
		
    	Map<?, ?> resultsList = null;
    	
    	try {
    		resultsList = (Map<?, ?>)resultsListValue;
    		if (resultsList == null || resultsList.size() == 0) {
    			return null;
    		}
    		
    		return resultsList;
		}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Encountered an issue assigning field value resultsList to Map.\n" + ex.getMessage());
    		return null;
    	} 
    }

}
