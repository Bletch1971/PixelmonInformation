package bletch.pixelmoninformation.jei.pokemon;

import java.util.Collection;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.common.LootHelper;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class PokemonRegistry {

	public static IForgeRegistry<PokemonEntry> REGISTRY;
	
	private PokemonRegistry() {}
	
	public static void addEntry(PokemonEntry entry) {
		REGISTRY.register(entry);
	}
	
	public static void setRegistry(IForgeRegistry<PokemonEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<PokemonEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<PokemonEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }

	public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
		LootHelper.getAllPixelmonDrops().stream()
				.map(d -> new PokemonEntry(d))
				.forEach(e -> addEntry(e));
	}
	
}
