package bletch.pixelmoninformation.jei.anvil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.items.ItemPokeballDisc;
import com.pixelmonmod.pixelmon.items.ItemPokeballLid;

import bletch.pixelmoninformation.utils.PixelmonUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistry;

@ParametersAreNonnullByDefault
public class AnvilRegistry {

	public static IForgeRegistry<AnvilEntry> REGISTRY;
	
	private AnvilRegistry() {}
	
	public static void addEntry(ItemStack input, ItemStack output) {
		REGISTRY.register(new AnvilEntry(input, output));
	}
	
	public static void setRegistry(IForgeRegistry<AnvilEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<AnvilEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<AnvilEntry> getValidEntries() {
    	return REGISTRY.getValuesCollection();
    }
    
    public static void initialize() {
		if (REGISTRY == null) {
			return;
		}
		
		ArrayList<ItemStack> pixelmonItemStacks = new ArrayList<ItemStack>();
		pixelmonItemStacks.addAll(PixelmonUtils.getPixelmonItemStacks());
		
		for (ItemStack pixelmonItemStack : pixelmonItemStacks) {
			
			NonNullList<ItemStack> itemStackList = NonNullList.create();

			if (pixelmonItemStack.getHasSubtypes()) {
				Item item = pixelmonItemStack.getItem();
				item.getSubItems(item.getCreativeTab(), itemStackList);
        	} else {
        		itemStackList.add(pixelmonItemStack);
        	}
        	
			for (ItemStack itemStack : itemStackList) {
				
				if (itemStack.getItem() instanceof ItemPokeballDisc) {
					ItemPokeballDisc itemPokeballDisc = (ItemPokeballDisc)itemStack.getItem();
					
					Optional<ItemPokeballLid> itemPokeballLid = pixelmonItemStacks.stream()
							.filter(s -> s.getItem() instanceof ItemPokeballLid)
							.map(s -> (ItemPokeballLid)s.getItem())  
							.filter(i -> i.pokeball.equals(itemPokeballDisc.pokeball))
							.findFirst();
					
					if (itemPokeballLid.isPresent()) {
						addEntry(itemStack, new ItemStack(itemPokeballLid.get()));
					}
				}
				
				if (itemStack.getTranslationKey().equalsIgnoreCase("item.iron_disc") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getTranslationKey().equalsIgnoreCase("item.iron_base"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addEntry(itemStack, itemBase.get());
					}
				}
				
				if (itemStack.getTranslationKey().equalsIgnoreCase("item.aluminum_disc") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getTranslationKey().equalsIgnoreCase("item.aluminum_base"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addEntry(itemStack, itemBase.get());
					}
				}
				
				if (itemStack.getTranslationKey().equalsIgnoreCase("item.aluminium_ingot") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getTranslationKey().equalsIgnoreCase("item.aluminium_plate"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addEntry(itemStack, itemBase.get());
					}
				}
			}
		}
	}
    
}
