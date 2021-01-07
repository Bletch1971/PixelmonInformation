package bletch.pixelmoninformation.jei.dungeon;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.common.LootDrop;
import bletch.pixelmoninformation.jei.common.LootHelper;
import bletch.pixelmoninformation.utils.TextUtils;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class DungeonEntry extends IForgeRegistryEntry.Impl<DungeonEntry> {
	
	private static Map<String, String> nameToLocalKeyMap = null;
	
    private String name;
	private Set<LootDrop> drops;

    public DungeonEntry(String name, LootTable lootTable) {
        this.name = name;
        this.drops = new TreeSet<>();

        processLootTable(lootTable, LootHelper.getLootTableManager());
        
        String resourcePath = ModDetails.MOD_ID + "/" + name;
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
    }

    public LootDrop getChestDrop(ItemStack ingredient) {
        return this.drops.stream()
        		.filter(drop -> ItemStack.areItemsEqual(drop.itemStack, ingredient))
        		.findFirst()
        		.orElse(null);
    }

    public List<ItemStack> getItemStacks(@Nullable IFocus<ItemStack> focus) {
        return this.drops.stream()
        		.map(drop -> drop.itemStack)
        		.filter(itemStack -> focus == null || ItemStack.areItemStacksEqual(ItemHandlerHelper.copyStackWithSize(itemStack, focus.getValue().getCount()), focus.getValue()))
        		.collect(Collectors.toList());
    }
    
    public String getLocalisedName() {
    	if (nameToLocalKeyMap == null) {
    		initializeDungeons();
    	}
    	
        String name = nameToLocalKeyMap.get(this.name);
        return TextUtils.translate(name == null ? this.name : name);
    }

    private void processLootTable(LootTable lootTable, LootTableManager manager) {
    	
    	List<LootPool> lootPools = LootHelper.getLootPools(lootTable);
    	if (lootPools != null) {
    		
    		lootPools.forEach(pool -> {
                
                List<LootEntry> lootEntries = LootHelper.getLootEntries(pool);
                if (lootEntries != null) {
                	
                    final float totalWeight = lootEntries.stream()
                    		.mapToInt(entry -> entry.getEffectiveWeight(0))
                    		.sum();
                    
                    lootEntries.stream()
            				.filter(LootEntryItem.class::isInstance)
            				.map(LootEntryItem.class::cast)
                    		.map(entry -> new LootDrop(LootHelper.getItem(entry), entry.getEffectiveWeight(0) / totalWeight, LootHelper.getLootFunctions(entry)))
                    		.forEach(drops::add);

                    lootEntries.stream()
                    		.filter(LootEntryTable.class::isInstance)
                    		.map(LootEntryTable.class::cast)
                    		.map(entry -> manager.getLootTableFromLocation(entry.table))
                    		.forEach(table -> processLootTable(table, manager));
                }
            });
        }
    }
    
    private static void initializeDungeons() {
    	nameToLocalKeyMap = new LinkedHashMap<>();
		
    	nameToLocalKeyMap.put("chests/spawn_bonus_chest", "jei.dungeonchest.spawn_bonus_chest.name");
    	nameToLocalKeyMap.put("chests/village_blacksmith", "jei.dungeonchest.village_blacksmith.name");
    	nameToLocalKeyMap.put("chests/simple_dungeon", "jei.dungeonchest.simple_dungeon.name");
    	nameToLocalKeyMap.put("chests/abandoned_mineshaft", "jei.dungeonchest.abandoned_mineshaft.name");
    	nameToLocalKeyMap.put("chests/desert_pyramid", "jei.dungeonchest.desert_pyramid.name");
    	nameToLocalKeyMap.put("chests/jungle_temple", "jei.dungeonchest.jungle_temple.name");
    	nameToLocalKeyMap.put("chests/stronghold_corridor", "jei.dungeonchest.stronghold_corridor.name");
    	nameToLocalKeyMap.put("chests/stronghold_crossing", "jei.dungeonchest.stronghold_crossing.name");
    	nameToLocalKeyMap.put("chests/stronghold_library", "jei.dungeonchest.stronghold_library.name");
    	nameToLocalKeyMap.put("chests/igloo_chest", "jei.dungeonchest.igloo_chest.name");
    	nameToLocalKeyMap.put("chests/woodland_mansion", "jei.dungeonchest.woodland_mansion.name");
    	nameToLocalKeyMap.put("chests/nether_bridge", "jei.dungeonchest.nether_bridge.name");
    	nameToLocalKeyMap.put("chests/end_city_treasure", "jei.dungeonchest.end_city_treasure.name");
		
	}
}
