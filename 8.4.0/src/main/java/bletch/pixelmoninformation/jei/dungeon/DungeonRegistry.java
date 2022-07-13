package bletch.pixelmoninformation.jei.dungeon;

import java.util.Collection;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.common.LootHelper;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class DungeonRegistry {

	public static IForgeRegistry<DungeonEntry> REGISTRY;
	
	private DungeonRegistry() {}
	
	public static void addEntry(DungeonEntry entry) {
		REGISTRY.register(entry);
	}
	
	public static void setRegistry(IForgeRegistry<DungeonEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<DungeonEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<DungeonEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }

	public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
        LootTableManager manager = LootHelper.getLootTableManager();
        if (manager != null) {
	        LootHelper.getAllChestLootTablesResourceLocations().stream()
	            .map(resourceLocation -> new DungeonEntry(resourceLocation.getResourcePath(), manager.getLootTableFromLocation(resourceLocation)))
	            .forEach(e -> addEntry(e));
        }
	}
	
}
