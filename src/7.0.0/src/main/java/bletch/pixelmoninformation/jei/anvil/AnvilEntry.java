package bletch.pixelmoninformation.jei.anvil;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class AnvilEntry extends IForgeRegistryEntry.Impl<AnvilEntry> {
	
    private ItemStack input = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    
    public AnvilEntry(Block input, ItemStack outputStack) { 
    	this(new ItemStack(input), outputStack); 
	}
    
    public AnvilEntry(Item input, ItemStack outputStack) { 
    	this(new ItemStack(input), outputStack); 
	}
    
    public AnvilEntry(ItemStack inputStack, ItemStack outputStack) {
    	this.input = inputStack;
    	this.output = outputStack;
        
    	String resourcePath = ModDetails.MOD_ID + "/" + this.output.getItem().getRegistryName().getResourcePath();
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
    }
    
    public ItemStack getInput() { 
    	return this.input.copy(); 
	}
    
    public ItemStack getOutput() { 
    	return this.output.copy(); 
	}
    
}
