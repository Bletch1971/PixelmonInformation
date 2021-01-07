package bletch.pixelmoninformation.jei.pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.entities.pixelmon.Entity1Base;
import com.pixelmonmod.pixelmon.entities.pixelmon.Entity3HasStats;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;
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
    	int nationalDex = this.drop.getSpecies().getNationalPokedexInteger();
    	return getGeneration(nationalDex);
    }  
    
    public static int getGeneration(int nationalDex) {
    	// taken from the pixelmon 7.0.0 code
        return nationalDex < 152 ? 1 : (nationalDex < 252 ? 2 : (nationalDex < 387 ? 3 : (nationalDex < 495 ? 4 : (nationalDex < 650 ? 5 : (nationalDex < 722 ? 6 : 7)))));
    }    
    
    public String getLocalisedName() {
    	return Entity1Base.getLocalizedName(this.drop.getSpecies());
    }
    
    public String getLocalizedDescription() {
    	return Entity1Base.getLocalizedDescription(this.drop.getSpecies().name);
    }
    
    public EnumPokemon getSpecies() {
    	return this.drop.getSpecies();
    }

    public List<EnumType> getTypeList() {
    	if (typeList == null) {
    		BaseStats baseStats = Entity3HasStats.getBaseStats(this.drop.getSpecies()).orElse(null);
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
        		spawnSet.spawnInfos.stream()
        				.map(s -> s.condition)
        				.filter(Objects::nonNull)
        				.map(c -> c.biomes)
        				.filter(Objects::nonNull)
        				.collect(Collectors.toList())
        				.forEach(a -> a.forEach(this.biomeList::add));
            }
            
            this.biomeList.sort((b1, b2) -> b1.getBiomeName().compareTo(b2.getBiomeName()));
    	}
    	
    	return this.biomeList;
    }
    
}
