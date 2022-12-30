package bletch.pixelmoninformation.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.client.storage.ClientStorageManager;
import com.pixelmonmod.pixelmon.pokedex.Pokedex;

import bletch.pixelmoninformation.PixelmonInformation;
import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@ParametersAreNonnullByDefault
public class PixelmonUtils {
	
	public static List<Block> getPixelmonBlocks() {

		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(b -> b.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
				.distinct()
				.sorted((b1, b2) -> b1.getClass().getTypeName().compareTo(b2.getClass().getTypeName()))
				.collect(Collectors.toList());
    }
	
	public static List<ItemStack> getPixelmonBlockStacks() {

		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(b -> b.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
				.distinct()
				.map(b -> new ItemStack(b))
				.sorted((s1, s2) -> s1.getClass().getTypeName().compareTo(s2.getClass().getTypeName()))
				.collect(Collectors.toList());
    }
	
	public static List<Class<?>> getPixelmonBlockClasses() {

		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(b -> b.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
				.map(b -> b.getClass())
				.filter(c -> !c.getTypeName().equalsIgnoreCase(Block.class.getTypeName()))
				.distinct()
				.sorted((c1, c2) -> c1.getTypeName().compareTo(c2.getTypeName()))
				.collect(Collectors.toList());
    }
	
	public static List<Item> getPixelmonItems() {

		return StreamSupport.stream(ForgeRegistries.ITEMS.spliterator(), false)
        		.filter(i -> i.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
        		.distinct()
        		.sorted((i1, i2) -> i1.getClass().getTypeName().compareTo(i2.getClass().getTypeName()))
        		.collect(Collectors.toList());
    }
	
	public static List<ItemStack> getPixelmonItemStacks() {

		return StreamSupport.stream(ForgeRegistries.ITEMS.spliterator(), false)
        		.filter(i -> i.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
        		.distinct()
        		.map(i -> new ItemStack(i))
        		.sorted((s1, s2) -> s1.getClass().getTypeName().compareTo(s2.getClass().getTypeName()))
        		.collect(Collectors.toList());
    }
	
	public static List<Class<?>> getPixelmonItemClasses() {

		return StreamSupport.stream(ForgeRegistries.ITEMS.spliterator(), false)
        		.filter(i -> i.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
        		.distinct()
        		.map(i -> i.getClass())
        		.sorted((c1, c2) -> c1.getTypeName().compareTo(c2.getTypeName()))
        		.collect(Collectors.toList());
    }
	
	public static Pokedex getClientPokedex() {
		
		if (!PixelmonInformation.proxy.isRemote()) {
			return null;
		}

		try {
			Field pokedexField = ClientStorageManager.class.getDeclaredField("pokedex");
			if (pokedexField != null) {
				pokedexField.setAccessible(true);
				
				Object pokedexValue = pokedexField.get(null);
				if (pokedexValue != null && pokedexValue instanceof Pokedex) {
					return (Pokedex)pokedexValue;
				}
			}
		}
		catch (Exception ex) {
		}
		
		return null;
	}
}
