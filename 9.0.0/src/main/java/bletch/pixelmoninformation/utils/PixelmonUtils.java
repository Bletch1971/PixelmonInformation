package bletch.pixelmoninformation.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.client.storage.ClientPlayerPokedex;
import com.pixelmonmod.pixelmon.client.storage.ClientStorageManager;
import com.pixelmonmod.pixelmon.entities.npcs.NPCEntity;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;

import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

@ParametersAreNonnullByDefault
public class PixelmonUtils {	

	public static final String INFORMATION_SUFFIX = ".pinformation";
	public static final String TOOLTIP_SUFFIX = ".ptooltip";

	public static final int APRICORNTREE_MAX_STAGE = 6; 
	public static final int BERRYTREE_MAX_STAGE = 5; 

	public static List<Class<?>> getPixelmonBlockClasses() {	

		return StreamSupport.stream(ForgeRegistries.BLOCKS.spliterator(), false)
				.filter(b -> b.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))
				.map(b -> b.getClass())
				.filter(c -> !c.getTypeName().equalsIgnoreCase(Block.class.getTypeName()))
				.distinct()
				.sorted((c1, c2) -> c1.getTypeName().compareTo(c2.getTypeName()))
				.collect(Collectors.toList());		
	}

	public static List<Class<?>> getPixelmonEntityClasses() {	
		List<Class<?>> list = new ArrayList<Class<?>>();

		list.add(PixelmonEntity.class);
		list.add(NPCEntity.class);

		list.sort((c1, c2) -> c1.getTypeName().compareTo(c2.getTypeName()));

		return list;
	}

	public static ClientPlayerPokedex getClientPokedex() {

		try {
			Field pokedexField = ClientStorageManager.class.getDeclaredField("pokedex");
			if (pokedexField != null) {
				pokedexField.setAccessible(true);

				Object pokedexValue = pokedexField.get(null);
				if (pokedexValue != null && pokedexValue instanceof ClientPlayerPokedex) {
					return (ClientPlayerPokedex)pokedexValue;
				}
			}
		}
		catch (Exception ex) {
			//ModDetails.MOD_LOGGER.info("Issue occurred reflecting ClientStorageManager.pokedex.");
		}

		return null;
	}

}
