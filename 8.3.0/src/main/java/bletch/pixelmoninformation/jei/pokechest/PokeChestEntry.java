package bletch.pixelmoninformation.jei.pokechest;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.common.PokeChestDrop;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class PokeChestEntry extends IForgeRegistryEntry.Impl<PokeChestEntry> {

    private PokeChestDrop drop;
    
    public PokeChestEntry(PokeChestDrop drop) {
		this.drop = drop;
		
		String resourcePath = ModDetails.MOD_ID + "/" + this.drop.getBlock().getRegistryName().getResourcePath();
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
	}
    
    public PokeChestDrop getPokeChestDrop() { 
    	return this.drop; 
	} 
    
    public ItemStack getInput() { 
    	return new ItemStack(this.drop.getBlock()); 
	}  
    
    public List<ItemStack> getItemStacks(@Nullable IFocus<ItemStack> focus) {
        return this.drop.getDrops().stream()
        		.filter(itemStack -> focus == null || ItemStack.areItemStacksEqual(ItemHandlerHelper.copyStackWithSize(itemStack, focus.getValue().getCount()), focus.getValue()))
        		.collect(Collectors.toList());
    }
    
    public String getLocalisedName() {
    	return this.drop.getTier().getLocalisedName();
    }
    
}
