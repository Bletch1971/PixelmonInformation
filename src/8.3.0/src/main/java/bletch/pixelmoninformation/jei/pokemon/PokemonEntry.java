package bletch.pixelmoninformation.jei.pokemon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.conditions.SpawnCondition;
import com.pixelmonmod.pixelmon.entities.pixelmon.Entity1Base;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.common.ItemDrop;
import bletch.pixelmoninformation.jei.common.PokemonDrop;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class PokemonEntry extends IForgeRegistryEntry.Impl<PokemonEntry> {

	private PokemonDrop drop;
	
	private List<EnumType> typeList;
	private List<Biome> biomeList;
    
    public PokemonEntry(PokemonDrop drop) {
    	this.drop = drop;
    	this.typeList = null;
    	this.biomeList = null;
		
    	String resourcePath = ModDetails.MOD_ID + "/pokemon_drops_" + this.drop.getSpecies().name() + "_" + this.drop.getSpeciesSequence();
    	setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
	}
    
    public PokemonDrop getPokemonDrop() { 
    	return this.drop; 
	} 
    
    public ItemDrop getItemDrop(ItemStack ingredient) {
        return this.drop.getDrops().stream()
        		.filter(drop -> ItemStack.areItemsEqual(drop.itemStack, ingredient))
        		.findFirst()
        		.orElse(null);
    }
    
    public List<ItemStack> getItemStacks(@Nullable IFocus<ItemStack> focus) {
        return this.drop.getDrops().stream()
        		.map(drop -> drop.itemStack)
        		.filter(itemStack -> focus == null || ItemStack.areItemStacksEqual(ItemHandlerHelper.copyStackWithSize(itemStack, focus.getValue().getCount()), focus.getValue()))
        		.collect(Collectors.toList());
    }
    
    public int getGeneration() {
    	return this.drop.getSpecies().getGeneration();
    } 
    
    public String getLocalisedName() {
    	return Entity1Base.getLocalizedName(this.drop.getSpecies());
    }
    
    public String getLocalizedDescription() {
    	return Entity1Base.getLocalizedDescription(this.drop.getSpecies().name);
    }
    
    public EnumSpecies getSpecies() {
    	return this.drop.getSpecies();
    }
    
    public List<EnumType> getTypeList() {
    	if (typeList == null) {
    		BaseStats baseStats = this.drop.getSpecies().getBaseStats();
    		this.typeList = baseStats != null ? baseStats.getTypeList() : new ArrayList<EnumType>();
    	}
    	
    	return this.typeList;
    }
    
    public List<Biome> getBiomeList() {
    	if (biomeList == null) {
    		this.biomeList = new ArrayList<Biome>(); 

            SpawnSet spawnSet = PixelmonSpawning.standard.stream()
            	.filter(s -> s.id.equalsIgnoreCase(this.drop.getSpecies().name))
        		.findFirst().orElse(null);
            
            if (spawnSet != null) {
            	List<SpawnCondition> spawnConditions = spawnSet.spawnInfos.stream()
            		.map(s -> s.condition)
        			.filter(Objects::nonNull)
        			.collect(Collectors.toList());
            	
            	if (spawnConditions != null && !spawnConditions.isEmpty())
            	{
            		spawnConditions.forEach(spawnCondition -> {
            			
            			// get the biomes field from the SpawnCondition class
	            		Field biomesField = null;
	            		
	            		try {
	            			biomesField = SpawnCondition.class.getDeclaredField("biomes");
	            			if (biomesField == null) {
	            				ModDetails.MOD_LOGGER.error("An issue occured on getDeclaredField(biomes) from SpawnCondition.");
	            				return; 
	            			}
	            		}
	                	catch (Exception ex) {
	                		ModDetails.MOD_LOGGER.error("Encountered an issue fetching field biomes from SpawnCondition.\n" + ex.getMessage());
	                		return; 
	                	}
	            		
	            		
	            		// get the biomes value from the SpawnCondition instance
	            		Object biomesValue = null;
	            		
	            		try {
	            			if (!biomesField.isAccessible()) {
	            				biomesField.setAccessible(true);
	            			}
	            			
	            			biomesValue = biomesField.get(spawnCondition);
	            			if (biomesValue == null) {
	            				ModDetails.MOD_LOGGER.error("Field value of biomes is null.");
	            				return; 
	            			}
	            			
	            			if (!(biomesValue instanceof List) && !(biomesValue instanceof Set)) {
	            				ModDetails.MOD_LOGGER.error("Field value of biomes is not a List or Set.");
	            				return; 
	            			}
	            		}
	                	catch (Exception ex) {
	                		ModDetails.MOD_LOGGER.error("Encountered an issue fetching field value of biomes.\n" + ex.getMessage());
	                		return; 
	                	} 
	            		
	            		
	            		if (biomesValue instanceof Set<?>) {
	            			Set<Biome> biomesSet = (Set<Biome>)biomesValue;
	            			
	            			biomesSet.stream()
		            			.filter(Objects::nonNull)
		            			.collect(Collectors.toList())
		            			.forEach(this.biomeList::add);
	            		}
	            		else if (biomesValue instanceof List<?>) {
	            			List<Biome> biomesList = (List<Biome>)biomesValue;
	            			
	            			biomesList.stream()
		            			.filter(Objects::nonNull)
		            			.collect(Collectors.toList())
		            			.forEach(this.biomeList::add);
	            		}
            		});
            	}
                
                this.biomeList.sort((b1, b2) -> b1.getBiomeName().compareTo(b2.getBiomeName()));
            }
    	}
    	
    	return this.biomeList;
    }
}
