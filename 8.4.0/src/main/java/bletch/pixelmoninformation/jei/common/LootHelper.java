package bletch.pixelmoninformation.jei.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.entities.npcs.registry.PokemonDropInformation;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ServerNPCRegistry;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import bletch.pixelmoninformation.jei.enums.EnumPokeChestTier;
import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.conditions.RandomChanceWithLooting;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraft.world.storage.loot.functions.Smelt;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@ParametersAreNonnullByDefault
public class LootHelper {
	
	private static FakeWorld fakeWorld;
	
    public static void applyCondition(LootCondition condition, LootDrop lootDrop) {
    	
        if (condition instanceof RandomChance) {
            lootDrop.chance = ((RandomChance) condition).chance;
        } 
        else if (condition instanceof RandomChanceWithLooting) {
            lootDrop.chance = ((RandomChanceWithLooting) condition).chance;
            lootDrop.addConditional(Condition.affectedByLooting);
        } 
        
    }
    
    public static void applyFunction(LootFunction lootFunction, LootDrop lootDrop) {
    	
        if (lootFunction instanceof EnchantRandomly || lootFunction instanceof EnchantWithLevels) {
            lootDrop.enchanted = true;
        } 
        else if (lootFunction instanceof LootingEnchantBonus) {
            lootDrop.addConditional(Condition.affectedByLooting);
        } 
        else if (lootFunction instanceof SetCount) {
            lootDrop.minDrop = MathHelper.floor(((SetCount) lootFunction).countRange.getMin());
            if (lootDrop.minDrop < 0) {
            	lootDrop.minDrop = 0;
            }
            lootDrop.itemStack.setCount(lootDrop.minDrop < 1 ? 1 : lootDrop.minDrop);
            lootDrop.maxDrop = MathHelper.floor(((SetCount) lootFunction).countRange.getMax());
        } 
        else if (lootFunction instanceof SetMetadata) {
            lootDrop.itemStack.setItemDamage(MathHelper.floor(((SetMetadata) lootFunction).metaRange.getMin()));
        } 
        else if (lootFunction instanceof Smelt) {
            lootDrop.smeltedItem = lootFunction.apply(lootDrop.itemStack, null, null);
            if (ItemStack.areItemStacksEqual(lootDrop.itemStack, lootDrop.smeltedItem)) {
                lootDrop.smeltedItem = null;
            }
        } 
    	else {
            try {
                lootDrop.itemStack = lootFunction.apply(lootDrop.itemStack, null, null);
            } 
            catch (NullPointerException ignored) {
            }
        }
        
    }

    public static List<ResourceLocation> getAllChestLootTablesResourceLocations() {
        ArrayList<ResourceLocation> list = new ArrayList<>();

        list.add(LootTableList.CHESTS_SPAWN_BONUS_CHEST);
        list.add(LootTableList.CHESTS_VILLAGE_BLACKSMITH);
        list.add(LootTableList.CHESTS_SIMPLE_DUNGEON);
        list.add(LootTableList.CHESTS_ABANDONED_MINESHAFT);
        list.add(LootTableList.CHESTS_DESERT_PYRAMID);
        list.add(LootTableList.CHESTS_JUNGLE_TEMPLE);
        list.add(LootTableList.CHESTS_STRONGHOLD_CORRIDOR);
        list.add(LootTableList.CHESTS_STRONGHOLD_CROSSING);
        list.add(LootTableList.CHESTS_STRONGHOLD_LIBRARY);
        list.add(LootTableList.CHESTS_IGLOO_CHEST);
        list.add(LootTableList.CHESTS_WOODLAND_MANSION);
        list.add(LootTableList.CHESTS_NETHER_BRIDGE);
        list.add(LootTableList.CHESTS_END_CITY_TREASURE);

        return list;
    } 
    
    public static List<PokeChestDrop> getAllPixelmonPokeChestDrops() {
    	ArrayList<PokeChestDrop> list = new ArrayList<>();
    	
    	try {
    		list.add(new PokeChestDrop(EnumPokeChestTier.TIER1, DropItemRegistry.tier1));
    	}
    	catch (Exception ex) {
    	}
    	
    	try {
    		list.add(new PokeChestDrop(EnumPokeChestTier.TIER2, DropItemRegistry.tier2));
    	}
    	catch (Exception ex) {
    	}
    	
    	try {
    		list.add(new PokeChestDrop(EnumPokeChestTier.TIER3, DropItemRegistry.tier3));
    	}
    	catch (Exception ex) {
    	}
    	
    	try {
    		list.add(new PokeChestDrop(EnumPokeChestTier.TIER4, DropItemRegistry.ultraSpace));
    	}
    	catch (Exception ex) {
    	}

        return list;
    }

    public static List<ShopkeeperData> getAllPixelmonShopKeepers() {    	
    	try {
    		return ServerNPCRegistry.getEnglishShopkeepers();
    	}
    	catch (Exception ex) {
    		return new ArrayList<ShopkeeperData>();
    	}
    }    
    
    public static List<PokemonBossDrop> getAllPixelmonPokemonBossDrops() {
    	final ArrayList<PokemonBossDrop> drops = new ArrayList<>();

    	try {
        	HashMap<EnumBossMode, ArrayList<ItemStack>> bossDrops = DropItemRegistry.bossDrops;
			if (bossDrops == null) {
				ModDetails.MOD_LOGGER.error("An issue occured on fetching DropItemRegistry.bossDrops.");
				return new ArrayList<PokemonBossDrop>();
			}
			
        	bossDrops.entrySet().stream()
        		.filter(d -> d.getKey().isBossPokemon() && d.getValue() != null && d.getValue().size() > 0)
        		.sorted((d1, d2) -> d1.getKey().compareTo(d2.getKey()))
        		.map(d -> new PokemonBossDrop(d.getKey(), d.getValue()))
        		.forEach(drops::add);
        	
        	return drops;
    	}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Error processing bossDrops.\n" + ex.getMessage());
    		return new ArrayList<PokemonBossDrop>();
    	}
    }

	public static List<PokemonDrop> getAllPixelmonDrops() {
		Field pokemonDropsField = null;
		
		try {
			pokemonDropsField = DropItemRegistry.class.getDeclaredField("pokemonDrops");
			if (pokemonDropsField == null) {
				ModDetails.MOD_LOGGER.error("An issue occured on getDeclaredField(pokemonDrops) from DropItemRegistry.");
				return new ArrayList<PokemonDrop>();
			}
		}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Encountered an issue fetching field pokemonDrops from DropItemRegistry.\n" + ex.getMessage());
    		return new ArrayList<PokemonDrop>();
    	}  		
		
		Object pokemonDropsValue = null;
		
		try {
			if (!pokemonDropsField.isAccessible()) {
				pokemonDropsField.setAccessible(true);
			}
			
			pokemonDropsValue = pokemonDropsField.get(null);
			if (pokemonDropsValue == null) {
				ModDetails.MOD_LOGGER.error("Field value of pokemonDrops is null.");
				return new ArrayList<PokemonDrop>();
			}
			
			if (!(pokemonDropsValue instanceof Map)) {
				ModDetails.MOD_LOGGER.error("Field value of pokemonDrops is not a Map.");
				return new ArrayList<PokemonDrop>();
			}
		}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Encountered an issue fetching field value of pokemonDrops.\n" + ex.getMessage());
    		return new ArrayList<PokemonDrop>();
    	} 
		
    	Map<?, ?> pokemonDrops = null;
    	
    	try {
    		pokemonDrops = (Map<?, ?>)pokemonDropsValue;
    		if (pokemonDrops == null || pokemonDrops.size() == 0) {
    			return new ArrayList<PokemonDrop>();
    		}
		}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Encountered an issue assigning field value pokemonDrops to Map.\n" + ex.getMessage());
    		return new ArrayList<PokemonDrop>();
    	}    	
    	
    	final List<PokemonDrop> drops = new ArrayList<PokemonDrop>();
    	
    	try {
    		List<?> values = pokemonDrops.values().stream().collect(Collectors.toList());
    		Object value = values.get(0);
    		if (value instanceof PokemonDropInformation) {
    			ModDetails.MOD_LOGGER.info("pokemonDrops is a Map of PokemonDropInformation.");
  		
    			final int[] sequence = { 1 };
    			pokemonDrops.entrySet().stream()
    					.filter(d -> d.getKey() instanceof EnumSpecies)
    					.map(d -> new PokemonDrop((EnumSpecies)d.getKey(), sequence[0], (PokemonDropInformation)d.getValue()))
    					.forEach(drops::add);
    		}
    		else if (value instanceof Set<?>) {
    			ModDetails.MOD_LOGGER.info("pokemonDrops is a Map of Set<PokemonDropInformation>.");

    			final int[] sequence = { 1 };
    			pokemonDrops.entrySet().stream()
						.filter(d -> d.getKey() instanceof EnumSpecies)
						.forEach(d -> {
							sequence[0] = 1;
							((Set<?>)d.getValue()).stream()
									.filter(PokemonDropInformation.class::isInstance)
									.map(i -> new PokemonDrop((EnumSpecies)d.getKey(), sequence[0]++, (PokemonDropInformation)i))
									.forEach(drops::add);
						});
    		}
    		else if (value instanceof List<?>) {
    			ModDetails.MOD_LOGGER.info("pokemonDrops is a Map of List<PokemonDropInformation>.");

    			final int[] sequence = { 1 };
    			pokemonDrops.entrySet().stream()
						.filter(d -> d.getKey() instanceof EnumSpecies)
						.forEach(d -> { 
							sequence[0] = 1;
							((List<?>)d.getValue()).stream()
									.filter(PokemonDropInformation.class::isInstance)
									.map(i -> new PokemonDrop((EnumSpecies)d.getKey(), sequence[0]++, (PokemonDropInformation)i))
									.forEach(drops::add);
						});
    		}
    		else {
    			ModDetails.MOD_LOGGER.error("pokemonDrops is a Map of unknown type.");        		    		
    		}
    		
    		drops.sort((i1, i2) -> i1.compareTo(i2));
    		return drops;
    	}
    	catch (Exception ex) {
    		ModDetails.MOD_LOGGER.error("Error processing pokemonDrops.\n" + ex.getMessage());
    		return new ArrayList<PokemonDrop>();
    	}     	
    } 
    
    public static Item getItem(LootEntryItem lootEntry) {
        return ReflectionHelper.getPrivateValue(LootEntryItem.class, lootEntry, "item", "field_186368_a");
    }
    
    public static List<LootCondition> getLootConditions(LootPool pool) {
        return ReflectionHelper.getPrivateValue(LootPool.class, pool, "poolConditions", "field_186454_b");
    }
    
    public static List<LootEntry> getLootEntries(LootPool pool) {
        return ReflectionHelper.getPrivateValue(LootPool.class, pool, "lootEntries", "field_186453_a");
    }

    public static LootFunction[] getLootFunctions(LootEntryItem lootEntry) {
        return ReflectionHelper.getPrivateValue(LootEntryItem.class, lootEntry, "functions", "field_186369_b");
    }

    public static List<LootPool> getLootPools(LootTable table) {
        return ReflectionHelper.getPrivateValue(LootTable.class, table, "pools", "field_186466_c");
    }
    
    public static LootTableManager getLootTableManager() {
    	return getLootTableManager(getWorld());
    }

    public static LootTableManager getLootTableManager(@Nullable World world) {
    	if (world == null || world.getLootTableManager() == null) {
    		return getWorld().getLootTableManager();
    	}
        
        return world.getLootTableManager();
    }
    
    public static World getWorld() {
    	
        World world = Minecraft.getMinecraft().world;
        
        if (world == null) {
        	if (fakeWorld == null) {
        		fakeWorld = new FakeWorld();
        	}
        	world = fakeWorld;
        }
        
        return world;
    }   
    
    public static void postInitialise() {
    	if (fakeWorld != null) {
    		fakeWorld = null;
    	}
    }
}
