package bletch.pixelmoninformation.jei.mechanicalanvil;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class MechanicalAnvilEntry extends IForgeRegistryEntry.Impl<MechanicalAnvilEntry> {

	private ItemStack input = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    
    public MechanicalAnvilEntry(Block input, ItemStack outputStack) { 
    	this(new ItemStack(input), outputStack); 
	}
    
    public MechanicalAnvilEntry(Item input, ItemStack outputStack) { 
    	this(new ItemStack(input), outputStack); 
	}
    
    public MechanicalAnvilEntry(ItemStack inputStack, ItemStack outputStack) {
        this.input = inputStack;
        this.output = outputStack;
        
        String resourcePath = ModDetails.MOD_ID + "/" + this.output.getItem().getRegistryName().getNamespace();
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
    }
    
    public ItemStack getInput() { 
    	return this.input.copy(); 
	}
    
    public ItemStack getOutput() { 
    	return this.output.copy(); 
	}
    
}
