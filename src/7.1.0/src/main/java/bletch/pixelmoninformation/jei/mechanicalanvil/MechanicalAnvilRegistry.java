package bletch.pixelmoninformation.jei.mechanicalanvil;

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
public class MechanicalAnvilRegistry {

	public static IForgeRegistry<MechanicalAnvilEntry> REGISTRY;
	
	private MechanicalAnvilRegistry() {}
	
	public static void addEntry(ItemStack input, ItemStack output) {
		REGISTRY.register(new MechanicalAnvilEntry(input, output));
	}
	
	public static void setRegistry(IForgeRegistry<MechanicalAnvilEntry> registry) {
		REGISTRY = registry;
	}
    
	public static Collection<MechanicalAnvilEntry> getEntries() {
		return REGISTRY.getValuesCollection();
	}
	
    /**
     * Returns the list of all valid recipes which do not have any missing ingredients
     * This is used by the JEI plugin
     */
    public static Collection<MechanicalAnvilEntry> getValidEntries() {
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
				
				if (itemStack.getUnlocalizedName().equalsIgnoreCase("item.iron_disc") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getUnlocalizedName().equalsIgnoreCase("item.iron_base"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addEntry(itemStack, itemBase.get());
					}
				}
				
				if (itemStack.getUnlocalizedName().equalsIgnoreCase("item.aluminum_disc") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getUnlocalizedName().equalsIgnoreCase("item.aluminum_base"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addEntry(itemStack, itemBase.get());
					}
				}
			}
		}
	}
	
}
