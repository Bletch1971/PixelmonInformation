package bletch.pixelmoninformation.jei.anvil;

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

public class AnvilRecipes {

	public static List<AnvilEntry> RECIPES;
	
	private static void addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output) {
		RECIPES.add(new AnvilEntry(input, output));
	}
	
	public static AnvilEntry[] getRecipes() {
		if (RECIPES == null) {
			initialize();
		}
		
		return RECIPES.toArray(new AnvilEntry[0]);
	}
	
	private static void initialize() {
		RECIPES = new ArrayList<>();
		
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
				
				if (itemStack.getUnlocalizedName().equalsIgnoreCase("item.aluminium_ingot") ) {
					Optional<ItemStack> itemBase = pixelmonItemStacks.stream()
							.filter(s -> s.getUnlocalizedName().equalsIgnoreCase("item.aluminium_plate"))
							.findFirst();					
					
					if (itemBase.isPresent()) {
						addRecipe(itemStack, itemBase.get());
					}
				}
			}
		}
		
		ModDetails.MOD_LOGGER.info("Anvil recipes: " + RECIPES.size());
	}
	
}
