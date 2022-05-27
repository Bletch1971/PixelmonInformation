package bletch.pixelmoninformation.jei;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.jei.anvil.AnvilEntry;
import bletch.pixelmoninformation.jei.anvil.AnvilRegistry;
import bletch.pixelmoninformation.jei.brewing.BrewingEntry;
import bletch.pixelmoninformation.jei.brewing.BrewingRegistry;
import bletch.pixelmoninformation.jei.dungeon.DungeonEntry;
import bletch.pixelmoninformation.jei.dungeon.DungeonRegistry;
import bletch.pixelmoninformation.jei.infuser.InfuserEntry;
import bletch.pixelmoninformation.jei.infuser.InfuserRegistry;
import bletch.pixelmoninformation.jei.mechanicalanvil.MechanicalAnvilEntry;
import bletch.pixelmoninformation.jei.mechanicalanvil.MechanicalAnvilRegistry;
import bletch.pixelmoninformation.jei.pokechest.PokeChestEntry;
import bletch.pixelmoninformation.jei.pokechest.PokeChestRegistry;
import bletch.pixelmoninformation.jei.pokemon.PokemonEntry;
import bletch.pixelmoninformation.jei.pokemon.PokemonRegistry;
import bletch.pixelmoninformation.jei.pokemonboss.PokemonBossEntry;
import bletch.pixelmoninformation.jei.pokemonboss.PokemonBossRegistry;
import bletch.pixelmoninformation.jei.shopkeeper.ShopKeeperEntry;
import bletch.pixelmoninformation.jei.shopkeeper.ShopKeeperRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
@ParametersAreNonnullByDefault
public class RegistrationHandler {

	@SubscribeEvent
	public static void addRegistry(RegistryEvent.NewRegistry event) {
		if (ModConfig.jei.showJeiRecipeTabs) {
			AnvilRegistry.setRegistry(
					new RegistryBuilder<AnvilEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_anvil_recipes"))
						.setType(AnvilEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
	
			MechanicalAnvilRegistry.setRegistry(
					new RegistryBuilder<MechanicalAnvilEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_mechanical_anvil_recipes"))
						.setType(MechanicalAnvilEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
	
			InfuserRegistry.setRegistry(
					new RegistryBuilder<InfuserEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_infuser_recipes"))
						.setType(InfuserEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
	
			BrewingRegistry.setRegistry(
					new RegistryBuilder<BrewingEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_brewing_recipes"))
						.setType(BrewingEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
		}

		if (ModConfig.jei.showJeiDungeonChestTab && 
				(ModConfig.jei.removeJeiDungeonChestTabModCheck || !Loader.isModLoaded(ModDetails.MOD_ID_JER))) {
			DungeonRegistry.setRegistry(
					new RegistryBuilder<DungeonEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_dungeon_chest_loot_drops"))
						.setType(DungeonEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());			
		}
		
		if (ModConfig.jei.showJeiPokeChestTab) {
			PokeChestRegistry.setRegistry(
					new RegistryBuilder<PokeChestEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_poke_chest_loot_drops"))
						.setType(PokeChestEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
		}
		
		if (ModConfig.jei.showJeiShopKeeperTab) {
			ShopKeeperRegistry.setRegistry(
					new RegistryBuilder<ShopKeeperEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_shop_keeper_items"))
						.setType(ShopKeeperEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
		}
		
		if (ModConfig.jei.showJeiPokemonBossTab) {
			PokemonBossRegistry.setRegistry(
					new RegistryBuilder<PokemonBossEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_pokemon_boss_items"))
						.setType(PokemonBossEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
		}
		
		if (ModConfig.jei.showJeiPokemonTab) {
			PokemonRegistry.setRegistry(
					new RegistryBuilder<PokemonEntry>()
						.setName(new ResourceLocation(ModDetails.MOD_ID, "pi_pokemon_items"))
						.setType(PokemonEntry.class)
		                .disableSaving()
		                .allowModification()
		                .create());
		}
	}
	
	@SubscribeEvent
	public static void registerAnvilEntries(RegistryEvent.Register<AnvilEntry> event) {
	}
	
	@SubscribeEvent
	public static void registerMechanicalAnvilEntries(RegistryEvent.Register<MechanicalAnvilEntry> event) {
	}
	
	@SubscribeEvent
	public static void registerInfuserEntries(RegistryEvent.Register<InfuserEntry> event) {
	}
	
	@SubscribeEvent
	public static void registerBrewingEntries(RegistryEvent.Register<BrewingEntry> event) {
	}
	
	@SubscribeEvent
	public static void registerDungeonEntries(RegistryEvent.Register<DungeonEntry> event) {
	}	
		
	@SubscribeEvent
	public static void registerPokeChestEntries(RegistryEvent.Register<PokeChestEntry> event) {
	}		
	
	@SubscribeEvent
	public static void registerShopKeeperEntries(RegistryEvent.Register<ShopKeeperEntry> event) {
	}
	
	@SubscribeEvent
	public static void registerPokemonBossEntries(RegistryEvent.Register<PokemonBossEntry> event) {
	}
	
	@SubscribeEvent
	public static void registerPokemonEntries(RegistryEvent.Register<PokemonEntry> event) {
	}
}
