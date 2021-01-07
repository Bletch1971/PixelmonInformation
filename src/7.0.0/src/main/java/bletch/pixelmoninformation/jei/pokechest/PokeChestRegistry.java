package bletch.pixelmoninformation.jei.pokechest;

import java.util.Collection;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.common.LootHelper;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class PokeChestRegistry {

	public static IForgeRegistry<PokeChestEntry> REGISTRY;
	
	private PokeChestRegistry() {}
	
	public static void addEntry(PokeChestEntry entry) {
		REGISTRY.register(entry);
	}
	
	public static void setRegistry(IForgeRegistry<PokeChestEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<PokeChestEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<PokeChestEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }

	public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
		LootHelper.getAllPixelmonPokeChestDrops().stream()
				.filter(d -> d.getBlock() != null && d.getDrops() != null)
				.map(d -> new PokeChestEntry(d))
				.forEach(e -> addEntry(e));
	}

}
