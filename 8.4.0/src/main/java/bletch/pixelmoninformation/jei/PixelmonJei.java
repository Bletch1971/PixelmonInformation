package bletch.pixelmoninformation.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.blocks.machines.BlockAnvil;
import com.pixelmonmod.pixelmon.blocks.machines.BlockInfuser;
import com.pixelmonmod.pixelmon.blocks.machines.BlockMechanicalAnvil;
import com.pixelmonmod.pixelmon.client.gui.machines.infuser.GuiInfuser;
import com.pixelmonmod.pixelmon.client.gui.machines.mechanicalanvil.GuiMechanicalAnvil;

import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.anvil.AnvilCategory;
import bletch.pixelmoninformation.jei.anvil.AnvilEntry;
import bletch.pixelmoninformation.jei.anvil.AnvilRegistry;
import bletch.pixelmoninformation.jei.anvil.AnvilWrapper;
import bletch.pixelmoninformation.jei.brewing.BrewingCategory;
import bletch.pixelmoninformation.jei.brewing.BrewingEntry;
import bletch.pixelmoninformation.jei.brewing.BrewingRegistry;
import bletch.pixelmoninformation.jei.brewing.BrewingWrapper;
import bletch.pixelmoninformation.jei.dungeon.DungeonCategory;
import bletch.pixelmoninformation.jei.dungeon.DungeonEntry;
import bletch.pixelmoninformation.jei.dungeon.DungeonRegistry;
import bletch.pixelmoninformation.jei.dungeon.DungeonWrapper;
import bletch.pixelmoninformation.jei.infuser.InfuserCategory;
import bletch.pixelmoninformation.jei.infuser.InfuserEntry;
import bletch.pixelmoninformation.jei.infuser.InfuserRegistry;
import bletch.pixelmoninformation.jei.infuser.InfuserWrapper;
import bletch.pixelmoninformation.jei.mechanicalanvil.MechanicalAnvilCategory;
import bletch.pixelmoninformation.jei.mechanicalanvil.MechanicalAnvilEntry;
import bletch.pixelmoninformation.jei.mechanicalanvil.MechanicalAnvilRegistry;
import bletch.pixelmoninformation.jei.mechanicalanvil.MechanicalAnvilWrapper;
import bletch.pixelmoninformation.jei.pokechest.PokeChestCategory;
import bletch.pixelmoninformation.jei.pokechest.PokeChestEntry;
import bletch.pixelmoninformation.jei.pokechest.PokeChestRegistry;
import bletch.pixelmoninformation.jei.pokechest.PokeChestWrapper;
import bletch.pixelmoninformation.jei.pokemon.PokemonCategory;
import bletch.pixelmoninformation.jei.pokemon.PokemonEntry;
import bletch.pixelmoninformation.jei.pokemon.PokemonRegistry;
import bletch.pixelmoninformation.jei.pokemon.PokemonWrapper;
import bletch.pixelmoninformation.jei.pokemonboss.PokemonBossCategory;
import bletch.pixelmoninformation.jei.pokemonboss.PokemonBossEntry;
import bletch.pixelmoninformation.jei.pokemonboss.PokemonBossRegistry;
import bletch.pixelmoninformation.jei.pokemonboss.PokemonBossWrapper;
import bletch.pixelmoninformation.jei.shopkeeper.ShopKeeperCategory;
import bletch.pixelmoninformation.jei.shopkeeper.ShopKeeperEntry;
import bletch.pixelmoninformation.jei.shopkeeper.ShopKeeperRegistry;
import bletch.pixelmoninformation.jei.shopkeeper.ShopKeeperWrapper;
import bletch.pixelmoninformation.utils.DebugUtils;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;

@JEIPlugin
@ParametersAreNonnullByDefault
public class PixelmonJei implements IModPlugin {
	
	private static final String KEY_SUFFIX = ".pinformation";

    private static IJeiHelpers jeiHelpers;
    private static IJeiRuntime jeiRuntime;

    private static List<BlankRecipeCategory<?>> categories = new ArrayList<BlankRecipeCategory<?>>();
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
    	PixelmonJei.jeiRuntime = jeiRuntime;
    }
    
    @Override
    public void register(IModRegistry registry) {
		if (!ModConfig.jei.enableJeiIntegration) {
			return;
		}
    	
    	if (ModConfig.jei.showJeiInformationTab) {
    		try {
    			registerPixelmonIngredientInfo(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}    		
    	}
    	
    	if (ModConfig.jei.showJeiRecipeTabs) {    		
    		try {
    			registerPixelmonRecipes(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		} 
		}
    	
    	if (ModConfig.jei.showJeiDungeonChestTab && 
				(ModConfig.jei.removeJeiDungeonChestTabModCheck || !Loader.isModLoaded(ModDetails.MOD_ID_JER))) {    		
    		try {
    			registerDungeonLoot(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		} 
		}
    	
    	if (ModConfig.jei.showJeiPokeChestTab) {
    		try {
    			registerPokeChestLoot(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		} 
    	}
    	
    	if (ModConfig.jei.showJeiShopKeeperTab) {
    		try {
    			registerShopKeepers(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		} 
    	}
    	
    	if (ModConfig.jei.showJeiPokemonBossTab) {
    		try {
    			registerPokemonBossDrops(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		} 
    	}
    	
    	if (ModConfig.jei.showJeiPokemonTab) {
    		try {
    			registerPokemonDrops(registry);
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		} 
    	}
	}
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
    	PixelmonJei.jeiHelpers = registry.getJeiHelpers();
    	
		if (!ModConfig.jei.enableJeiIntegration) {
			return;
		}
		
		if (ModConfig.jei.showJeiRecipeTabs) {
    		try {
    			categories.add(new AnvilCategory());
        		categories.add(new MechanicalAnvilCategory());
        		categories.add(new InfuserCategory());
        		categories.add(new BrewingCategory());
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}
    	
    	if (ModConfig.jei.showJeiDungeonChestTab && 
				(ModConfig.jei.removeJeiDungeonChestTabModCheck || !Loader.isModLoaded(ModDetails.MOD_ID_JER))) {
    		try {
    			categories.add(new DungeonCategory());
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}    
    	
    	if (ModConfig.jei.showJeiPokeChestTab) {
    		try {
    			categories.add(new PokeChestCategory());
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}
    	
    	if (ModConfig.jei.showJeiShopKeeperTab) {
    		try {
    			categories.add(new ShopKeeperCategory());
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}    	
    	
    	if (ModConfig.jei.showJeiPokemonBossTab) {
    		try {
    			categories.add(new PokemonBossCategory());
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}     	
    	
    	if (ModConfig.jei.showJeiPokemonTab) {
    		try {
    			categories.add(new PokemonCategory());
    		}
    		catch (Exception ex) {
    			ModDetails.MOD_LOGGER.error(ex.getMessage());
    		}
    	}
    	
    	categories.forEach(c -> registry.addRecipeCategories(c));
	}    

    public static BlankRecipeCategory<?> getCategory(Class<?> categoryClass) {
    	return categories.stream()
    			.filter(categoryClass::isInstance)
    			.findFirst().orElse(null);
    }
    
    public static IGuiHelper getGuiHelper() {
        return PixelmonJei.jeiHelpers.getGuiHelper();
    }
    
    public static IJeiHelpers getJeiHelper() {
        return PixelmonJei.jeiHelpers;
    }

    public static IJeiRuntime getJeiRuntime() {
        return PixelmonJei.jeiRuntime;
    }

	public static String getJeiUid(String category) {
		return ModDetails.MOD_ID + ":" + category;
	}
	
	private static void registerPixelmonIngredientInfo(IModRegistry registry) {
		ArrayList<String> processed = new ArrayList<String>();
		
		ModDetails.MOD_LOGGER.info("Registering item/block information with JEI");
		
		ArrayList<ItemStack> pixelmonItemStacks = new ArrayList<ItemStack>();
		pixelmonItemStacks.addAll(PixelmonUtils.getPixelmonBlockStacks());
		pixelmonItemStacks.addAll(PixelmonUtils.getPixelmonItemStacks());
		
		ArrayList<String> missingInformation = new ArrayList<String>();
		ArrayList<String> registeredInformation = new ArrayList<String>();		
		int count = 0;
		
    	for (ItemStack pixelmonItemStack : pixelmonItemStacks) {
        
			NonNullList<ItemStack> itemStackList = NonNullList.create();

			if (pixelmonItemStack.getHasSubtypes()) {
				Item item = pixelmonItemStack.getItem();
				item.getSubItems(item.getCreativeTab(), itemStackList);
    		} else {
    			itemStackList.add(pixelmonItemStack);
    		}

			for (ItemStack itemStack : itemStackList) {
				String key = itemStack.getUnlocalizedName() + KEY_SUFFIX;
				
	        	if (processed.contains(key)) {
	        		continue;
	        	}
	        	processed.add(key);
	        	
	        	// check if the item belongs to the Pixelmon domain
	        	if (!itemStack.getItem().getRegistryName().getResourceDomain().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
	        		continue;
	        	}
	        	
				if (TextUtils.canTranslate(key) || ModConfig.debug.enableDebug && ModConfig.debug.registerJeiMissingInformation) {
					
					try {
						registry.addIngredientInfo(itemStack, VanillaTypes.ITEM, key);
						count++;
						
						if (ModConfig.debug.enableDebug && ModConfig.debug.showJeiRegisteredInformation) {
							registeredInformation.add("Registered JEI information for " + itemStack.getDisplayName() + "; key: " + key);
						} 
					}
		    		catch (Exception e) {
		    			if (ModConfig.debug.enableDebug) {
		    				DebugUtils.writeLine("Error registering JEI information for " + itemStack.getDisplayName() + "; key: " + key, true);
		    			}
		    		}
					
				} else {

					if (ModConfig.debug.enableDebug && ModConfig.debug.showJeiMissingInformation) {
						missingInformation.add("Missing JEI information for " + itemStack.getDisplayName() + "; key: " + key);
        			}
					
				}
			}
		}
    	
    	if (ModConfig.debug.enableDebug && registeredInformation != null && registeredInformation.size() > 0) {
    		registeredInformation.sort((i1, i2) -> i1.compareTo(i2));
    		DebugUtils.writeLines(registeredInformation, true);
    	}
    	
    	if (ModConfig.debug.enableDebug && missingInformation != null && missingInformation.size() > 0) {
    		missingInformation.sort((i1, i2) -> i1.compareTo(i2));
    		DebugUtils.writeLines(missingInformation, true);
    	}
    	
    	ModDetails.MOD_LOGGER.info("Registered item/block information with JEI - count: " + count);
	}

	private static void registerPixelmonRecipes(IModRegistry registry) {
		
		try {
			Optional<BlockAnvil> blockAnvil = StreamSupport.stream(Block.REGISTRY.spliterator(), false)
					.filter(BlockAnvil.class::isInstance)
					.map(BlockAnvil.class::cast)
					.findFirst();
			
			if (blockAnvil.isPresent()) {
				ModDetails.MOD_LOGGER.info("Registering anvil recipes with JEI");
				
				AnvilRegistry.initialize();

				registry.handleRecipes(AnvilEntry.class, AnvilWrapper::new, PixelmonJei.getJeiUid(AnvilCategory.CATEGORY));
				registry.addRecipes(AnvilRegistry.getValidEntries(), PixelmonJei.getJeiUid(AnvilCategory.CATEGORY));
				registry.addRecipeCatalyst(new ItemStack(blockAnvil.get()), PixelmonJei.getJeiUid(AnvilCategory.CATEGORY));

				ModDetails.MOD_LOGGER.info("Registered anvil recipes with JEI - count: " + AnvilRegistry.getValidEntries().size());
			}
		}
		catch (Exception e) {
			ModDetails.MOD_LOGGER.error("Error registering anvil recipes with JEI");
		}
		
		try {
			Optional<BlockMechanicalAnvil> blockMechanicalAnvil = StreamSupport.stream(Block.REGISTRY.spliterator(), false)
					.filter(BlockMechanicalAnvil.class::isInstance)
					.map(BlockMechanicalAnvil.class::cast)
					.findFirst();
			
			if (blockMechanicalAnvil.isPresent()) {			
				ModDetails.MOD_LOGGER.info("Registering mechanical anvil recipes with JEI");
				
				MechanicalAnvilRegistry.initialize();

				registry.handleRecipes(MechanicalAnvilEntry.class, MechanicalAnvilWrapper::new, PixelmonJei.getJeiUid(MechanicalAnvilCategory.CATEGORY));
				registry.addRecipes(MechanicalAnvilRegistry.getValidEntries(), PixelmonJei.getJeiUid(MechanicalAnvilCategory.CATEGORY));
				registry.addRecipeCatalyst(new ItemStack(blockMechanicalAnvil.get()), PixelmonJei.getJeiUid(MechanicalAnvilCategory.CATEGORY));
				registry.addRecipeClickArea(GuiMechanicalAnvil.class, 80, 35, 22, 15, PixelmonJei.getJeiUid(MechanicalAnvilCategory.CATEGORY));	
				
				ModDetails.MOD_LOGGER.info("Registered mechanical anvil recipes with JEI - count: " + MechanicalAnvilRegistry.getValidEntries().size());			
			}
		}
		catch (Exception e) {
			ModDetails.MOD_LOGGER.error("Error registering mechanical anvil recipes with JEI");
		}
		
		try {
			Optional<BlockInfuser> blockInfuser = StreamSupport.stream(Block.REGISTRY.spliterator(), false)
					.filter(BlockInfuser.class::isInstance)
					.map(BlockInfuser.class::cast)
					.findFirst();
			
			if (blockInfuser.isPresent()) {			
				ModDetails.MOD_LOGGER.info("Registering infuser recipes with JEI");
				
				InfuserRegistry.initialize();

				registry.handleRecipes(InfuserEntry.class, InfuserWrapper::new, PixelmonJei.getJeiUid(InfuserCategory.CATEGORY));
				registry.addRecipes(InfuserRegistry.getValidEntries(), PixelmonJei.getJeiUid(InfuserCategory.CATEGORY));
				registry.addRecipeCatalyst(new ItemStack(blockInfuser.get()), PixelmonJei.getJeiUid(InfuserCategory.CATEGORY));
				registry.addRecipeClickArea(GuiInfuser.class, 107, 32, 14, 12, PixelmonJei.getJeiUid(InfuserCategory.CATEGORY));	
				
				ModDetails.MOD_LOGGER.info("Registered infuser recipes with JEI - count: " + InfuserRegistry.getValidEntries().size());					
			}
		}
		catch (Exception e) {
			ModDetails.MOD_LOGGER.error("Error registering infuser recipes with JEI");
		}

		try {
			ModDetails.MOD_LOGGER.info("Registering brewing recipes with JEI");
			
			BrewingRegistry.initialize();

			registry.handleRecipes(BrewingEntry.class, BrewingWrapper::new, PixelmonJei.getJeiUid(BrewingCategory.CATEGORY));
			registry.addRecipes(BrewingRegistry.getValidEntries(), PixelmonJei.getJeiUid(BrewingCategory.CATEGORY));
			registry.addRecipeCatalyst(new ItemStack(Items.BREWING_STAND), PixelmonJei.getJeiUid(BrewingCategory.CATEGORY));
			registry.addRecipeClickArea(GuiBrewingStand.class, 63, 15, 12, 27, PixelmonJei.getJeiUid(BrewingCategory.CATEGORY));	
			
			ModDetails.MOD_LOGGER.info("Registered brewing recipes with JEI - count: " + BrewingRegistry.getValidEntries().size());	
		}
		catch (Exception e) {
			ModDetails.MOD_LOGGER.error("Error registering brewing recipes with JEI");
		}

	}
	
	private static void registerDungeonLoot(IModRegistry registry) {
		
		ModDetails.MOD_LOGGER.info("Registering dungeon loot with JEI");
		
		DungeonRegistry.initialize();

		registry.handleRecipes(DungeonEntry.class, DungeonWrapper::new, PixelmonJei.getJeiUid(DungeonCategory.CATEGORY));
		registry.addRecipes(DungeonRegistry.getValidEntries(), PixelmonJei.getJeiUid(DungeonCategory.CATEGORY));

		ModDetails.MOD_LOGGER.info("Registered dungeon loot with JEI - count: " + DungeonRegistry.getValidEntries().size());
	}
	
	private static void registerPokeChestLoot(IModRegistry registry) {
		
		ModDetails.MOD_LOGGER.info("Registering poké chest loot with JEI");
		
		PokeChestRegistry.initialize();

		registry.handleRecipes(PokeChestEntry.class, PokeChestWrapper::new, PixelmonJei.getJeiUid(PokeChestCategory.CATEGORY));
		registry.addRecipes(PokeChestRegistry.getValidEntries(), PixelmonJei.getJeiUid(PokeChestCategory.CATEGORY));

		ModDetails.MOD_LOGGER.info("Registered poké chest loot with JEI - count: " + PokeChestRegistry.getValidEntries().size());
	}
	
	private static void registerShopKeepers(IModRegistry registry) {
		
		ModDetails.MOD_LOGGER.info("Registering shop keepers with JEI");
		
		ShopKeeperRegistry.initialize();

		registry.handleRecipes(ShopKeeperEntry.class, ShopKeeperWrapper::new, PixelmonJei.getJeiUid(ShopKeeperCategory.CATEGORY));
		registry.addRecipes(ShopKeeperRegistry.getValidEntries(), PixelmonJei.getJeiUid(ShopKeeperCategory.CATEGORY));

		ModDetails.MOD_LOGGER.info("Registered shop keepers with JEI - count: " + ShopKeeperRegistry.getValidEntries().size());
	}	
	
	private static void registerPokemonBossDrops(IModRegistry registry) {
		
		ModDetails.MOD_LOGGER.info("Registering pokémon boss drops with JEI");
		
		PokemonBossRegistry.initialize();

		registry.handleRecipes(PokemonBossEntry.class, PokemonBossWrapper::new, PixelmonJei.getJeiUid(PokemonBossCategory.CATEGORY));
		registry.addRecipes(PokemonBossRegistry.getValidEntries(), PixelmonJei.getJeiUid(PokemonBossCategory.CATEGORY));

		ModDetails.MOD_LOGGER.info("Registered pokémon boss drops with JEI - count: " + PokemonBossRegistry.getValidEntries().size());
	}
	
	private static void registerPokemonDrops(IModRegistry registry) {
		
		ModDetails.MOD_LOGGER.info("Registering pokémon drops with JEI");
		
		PokemonRegistry.initialize();

		registry.handleRecipes(PokemonEntry.class, PokemonWrapper::new, PixelmonJei.getJeiUid(PokemonCategory.CATEGORY));
		registry.addRecipes(PokemonRegistry.getValidEntries(), PixelmonJei.getJeiUid(PokemonCategory.CATEGORY));

		ModDetails.MOD_LOGGER.info("Registered pokémon drops with JEI - count: " + PokemonRegistry.getValidEntries().size());
	}
}
