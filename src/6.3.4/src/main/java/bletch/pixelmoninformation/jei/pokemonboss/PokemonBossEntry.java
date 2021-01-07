package bletch.pixelmoninformation.jei.pokemonboss;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.common.PokemonBossDrop;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class PokemonBossEntry extends IForgeRegistryEntry.Impl<PokemonBossEntry> {

	private PokemonBossDrop drop;
    
    public PokemonBossEntry(PokemonBossDrop drop) {
		this.drop = drop;
		
		String resourcePath = ModDetails.MOD_ID + "/" + this.drop.getType().getName();
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
	}
    
    public PokemonBossDrop getPokemonBossDrop() { 
    	return this.drop; 
	} 
 
    public List<ItemStack> getItemStacks(@Nullable IFocus<ItemStack> focus) {
        return this.drop.getDrops().stream()
        		.filter(itemStack -> focus == null || ItemStack.areItemStacksEqual(ItemHandlerHelper.copyStackWithSize(itemStack, focus.getValue().getCount()), focus.getValue()))
        		.collect(Collectors.toList());
    }
    
    public String getLocalisedName() {
    	return this.drop.getType().getLocalisedName();
    }
    
}
