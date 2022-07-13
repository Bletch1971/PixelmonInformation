package bletch.pixelmoninformation.jei.shopkeeper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopItem;
import com.pixelmonmod.pixelmon.entities.npcs.registry.ShopkeeperData;

import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.TextUtils;
import mezz.jei.api.recipe.IFocus;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ParametersAreNonnullByDefault
public class ShopKeeperEntry extends IForgeRegistryEntry.Impl<ShopKeeperEntry> {
	
	private static Map<String, String> nameToLocalKeyMap = null;
	
	private ShopkeeperData shopkeeperData;
	private ArrayList<ShopItem> items;
    
    public ShopKeeperEntry(ShopkeeperData shopkeeperData) {
		this.shopkeeperData = shopkeeperData;
		this.items = new ArrayList<ShopItem>();
		
		processShopItems(shopkeeperData);
		
		String resourcePath = ModDetails.MOD_ID + "/shopkeeper_" + this.shopkeeperData.id;
        setRegistryName(new ResourceLocation(ModDetails.MOD_ID_JEI, resourcePath));
	}
    
    public ShopkeeperData getShopkeeperData() { 
    	return this.shopkeeperData; 
	}

    public ShopItem getShopItem(ItemStack ingredient) {
        return this.items.stream()
        		.filter(shopItem -> ItemStack.areItemsEqual(shopItem.getItemStack(), ingredient))
        		.findFirst()
        		.orElse(null);
    }  

    public List<ItemStack> getItemStacks(@Nullable IFocus<ItemStack> focus) {
        return this.items.stream()
        		.map(shopItem -> shopItem.getItemStack())
        		.filter(itemStack -> focus == null || ItemStack.areItemStacksEqual(ItemHandlerHelper.copyStackWithSize(itemStack, focus.getValue().getCount()), focus.getValue()))
        		.collect(Collectors.toList());
    }
    
    public String getLocalisedName() {
    	if (nameToLocalKeyMap == null) {
    		initializeShopKeepers();
    	}
    	
        String name = nameToLocalKeyMap.get(this.shopkeeperData.id);
        return TextUtils.translate(name == null ? this.shopkeeperData.id : name);
    }
    
    @SuppressWarnings("unchecked")
	private void processShopItems(ShopkeeperData shopkeeperData) {
    	
		try {
			Field itemsField = ShopkeeperData.class.getDeclaredField("items");
			if (!itemsField.isAccessible()) {
				itemsField.setAccessible(true);
			}
			
			this.items = (ArrayList<ShopItem>)itemsField.get(shopkeeperData);			
		}
		catch (Exception ex) {
			ModDetails.MOD_LOGGER.error(ex.getMessage());
		}
    }
    
    private static void initializeShopKeepers() {
    	nameToLocalKeyMap = new LinkedHashMap<>();
		
    	nameToLocalKeyMap.put("pokemartmain1", "jei.shopkeeper.pokemartmain1.name");
    	nameToLocalKeyMap.put("pokemartmain2", "jei.shopkeeper.pokemartmain2.name");
    	nameToLocalKeyMap.put("pokemartmain3", "jei.shopkeeper.pokemartmain3.name");
    	nameToLocalKeyMap.put("pokemartsecond1", "jei.shopkeeper.pokemartsecond1.name");
    	nameToLocalKeyMap.put("pokemartsecond2", "jei.shopkeeper.pokemartsecond2.name");
    	nameToLocalKeyMap.put("pokemartsecond3", "jei.shopkeeper.pokemartsecond3.name");
    	nameToLocalKeyMap.put("spawn1", "jei.shopkeeper.spawn1.name");
		
	}
    
}
