package bletch.pixelmoninformation.jei.pokemonboss;

import java.util.Collection;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.common.LootHelper;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class PokemonBossRegistry {

	public static IForgeRegistry<PokemonBossEntry> REGISTRY;
	
	private PokemonBossRegistry() {}
	
	public static void addEntry(PokemonBossEntry entry) {
		REGISTRY.register(entry);
	}
	
	public static void setRegistry(IForgeRegistry<PokemonBossEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<PokemonBossEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<PokemonBossEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }

	public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
		LootHelper.getAllPixelmonPokemonBossDrops().stream()
				.map(d -> new PokemonBossEntry(d))
				.forEach(e -> addEntry(e));
	}
	
}
