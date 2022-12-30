package bletch.pixelmoninformation.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModUtils {
	
	private final static Map<String, String> modNamesForIds = new HashMap<>();

    private static void init() {
        Map<String, ModContainer> modMap = Loader.instance().getIndexedModList();
        for (Map.Entry<String, ModContainer> modEntry : modMap.entrySet()) {
            String lowercaseId = modEntry.getKey().toLowerCase(Locale.ENGLISH);
            String modName = modEntry.getValue().getName();
            modNamesForIds.put(lowercaseId, modName);
        }
    }

    public static String getModName(Block block) {
        if (modNamesForIds.isEmpty()) {
            init();
        }
        
        ResourceLocation itemResourceLocation = block.getRegistryName();
        String modId = itemResourceLocation.getNamespace();
        String lowercaseModId = modId.toLowerCase(Locale.ENGLISH);
        String modName = modNamesForIds.get(lowercaseModId);
        
        if (modName == null) {
            modName = modId.toUpperCase(Locale.ENGLISH);
            modNamesForIds.put(lowercaseModId, modName);
        }
        
        return modName;
    }

    public static String getModName(Entity entity) {
        if (modNamesForIds.isEmpty()) {
            init();
        }
        
        EntityRegistry.EntityRegistration modSpawn = EntityRegistry.instance().lookupModSpawn(entity.getClass(), true);
        if (modSpawn == null) {
            return "Minecraft";
        }
        
        ModContainer container = modSpawn.getContainer();
        if (container == null) {
            return "Minecraft";
        }
        
        String modId = container.getModId();
        String lowercaseModId = modId.toLowerCase(Locale.ENGLISH);
        String modName = modNamesForIds.get(lowercaseModId);
        
        if (modName == null) {
            modName = modId.toUpperCase(Locale.ENGLISH);
            modNamesForIds.put(lowercaseModId, modName);
        }
        
        return modName;
    }

}
