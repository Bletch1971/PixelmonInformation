package bletch.pixelmoninformation.jei.mechanicalanvil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

import com.pixelmonmod.pixelmon.items.ItemPokeballDisc;
import com.pixelmonmod.pixelmon.items.ItemPokeballLid;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MechanicalAnvilRecipes {

	public static List<MechanicalAnvilEntry> RECIPES;
	
	private static void addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output) {
		RECIPES.add(new MechanicalAnvilEntry(input, output));
	}	
	
	public static MechanicalAnvilEntry[] getRecipes() {
		if (RECIPES == null) {
			initialize();
		}
		
		return RECIPES.toArray(new MechanicalAnvilEntry[0]);
	}
	
	private static void initialize() {
		RECIPES = new ArrayList<MechanicalAnvilEntry>();
		
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
						addRecipe(itemStack, new ItemStack(itemPokeballLid.get()));
					}
				}
				
				if (itemStack.getUnlocalizedName().equalsIgnoreCase("item.iron_disc") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getUnlocalizedName().equalsIgnoreCase("item.iron_base"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addRecipe(itemStack, itemBase.get());
					}
				}
				
				if (itemStack.getUnlocalizedName().equalsIgnoreCase("item.aluminum_disc") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getUnlocalizedName().equalsIgnoreCase("item.aluminum_base"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addRecipe(itemStack, itemBase.get());
					}
				}
			}
		}
		
		ModDetails.MOD_LOGGER.info("MechanicalAnvil recipes: " + RECIPES.size());
	}

}
