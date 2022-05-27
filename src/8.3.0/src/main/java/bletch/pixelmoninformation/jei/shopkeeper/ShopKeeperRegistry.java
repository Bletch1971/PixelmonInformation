package bletch.pixelmoninformation.jei.shopkeeper;

import java.util.Collection;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;

import bletch.pixelmoninformation.jei.common.LootHelper;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class ShopKeeperRegistry {

	public static IForgeRegistry<ShopKeeperEntry> REGISTRY;
	
	private ShopKeeperRegistry() {}
	
	public static void addEntry(ShopKeeperEntry entry) {
		REGISTRY.register(entry);
	}
	
	public static void setRegistry(IForgeRegistry<ShopKeeperEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<ShopKeeperEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<ShopKeeperEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }

	public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
		List<ShopkeeperData> shopKeeperData = LootHelper.getAllPixelmonShopKeepers();
		shopKeeperData.stream()
				.map(d -> new ShopKeeperEntry(d))
				.forEach(e -> addEntry(e));
	}
	
}
