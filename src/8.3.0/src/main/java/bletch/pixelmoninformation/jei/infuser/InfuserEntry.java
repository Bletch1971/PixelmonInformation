package bletch.pixelmoninformation.jei.infuser;

import java.util.Arrays;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class InfuserEntry extends IForgeRegistryEntry.Impl<InfuserEntry> {

    private ItemStack input1 = ItemStack.EMPTY;
    private ItemStack input2 = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    
    public InfuserEntry(Block input1, Block input2, ItemStack outputStack) { 
    	this(new ItemStack(input1), new ItemStack(input2), outputStack); 
	}
    
    public InfuserEntry(Block input1, Item input2, ItemStack outputStack) { 
    	this(new ItemStack(input1), new ItemStack(input2), outputStack); 
	}
    
    public InfuserEntry(Item input1, Block input2, ItemStack outputStack) { 
    	this(new ItemStack(input1), new ItemStack(input2), outputStack); 
	}
    
    public InfuserEntry(Item input1, Item input2, ItemStack outputStack) { 
    	this(new ItemStack(input1), new ItemStack(input2), outputStack); 
	}
    
    public InfuserEntry(ItemStack inputStack1, ItemStack inputStack2, ItemStack outputStack) {
    	this.input1 = inputStack1;
    	this.input2 = inputStack2;
    	this.output = outputStack;
        
    	String resourcePath = ModDetails.MOD_ID + "/" + this.output.getItem().getRegistryName().getResourcePath();
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
    }
    
    public ItemStack getInput1() { 
    	return this.input1.copy(); 
	}
    
    public ItemStack getInput2() { 
    	return this.input2.copy(); 
	}
    
    public List<ItemStack> getInputs() {
    	return Arrays.asList(this.input1.copy(), this.input2.copy());
	}
    
    public ItemStack getOutput() { 
    	return this.output.copy(); 
	}
    
}
