package bletch.pixelmoninformation.jei.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.npcs.registry.PokemonDropInformation;
import com.pixelmonmod.pixelmon.enums.EnumPokemon;
import bletch.pixelmoninformation.jei.enums.EnumItemDropType;
import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class PokemonDrop implements Comparable<PokemonDrop> {
	
	private static final String FIELD_POKEMON = "pokemon";
	private static final String FIELD_MAIN_DROP = "mainDrop";
	private static final String FIELD_RARE_DROP = "rareDrop";
	private static final String FIELD_OPT_DROP1 = "optDrop1";
	private static final String FIELD_OPT_DROP2 = "optDrop2";
	private static final String FIELD_MAIN_DROP_MIN = "mainDropMin";
	private static final String FIELD_MAIN_DROP_MAX = "mainDropMax";
	private static final String FIELD_RARE_DROP_MIN = "rareDropMin";
	private static final String FIELD_RARE_DROP_MAX = "rareDropMax";
	private static final String FIELD_OPT_DROP1_MIN = "optDrop1Min";
	private static final String FIELD_OPT_DROP1_MAX = "optDrop1Max";
	private static final String FIELD_OPT_DROP2_MIN = "optDrop2Min";
	private static final String FIELD_OPT_DROP2_MAX = "optDrop2Max";
	private static final String[] FIELD_NAMES = { 
			FIELD_POKEMON, FIELD_MAIN_DROP, FIELD_RARE_DROP, FIELD_OPT_DROP1, FIELD_OPT_DROP2, 
			FIELD_MAIN_DROP_MIN, FIELD_MAIN_DROP_MAX, FIELD_RARE_DROP_MIN, FIELD_RARE_DROP_MAX,
			FIELD_OPT_DROP1_MIN, FIELD_OPT_DROP1_MAX, FIELD_OPT_DROP2_MIN, FIELD_OPT_DROP2_MAX };
	
	private static List<Field> fields = null;
	
	private EnumPokemon species;
	private int speciesSequence;
	private ArrayList<ItemDrop> drops;
	
	public PokemonDrop(EnumPokemon species, int speciesSequence) {
		this(species, speciesSequence, new ArrayList<ItemDrop>());
	}
	
	public PokemonDrop(EnumPokemon species, int speciesSequence, ArrayList<ItemDrop> drops) {
		this.species = species;
		this.speciesSequence = speciesSequence;
		this.drops = drops;
	}
	
	public PokemonDrop(EnumPokemon species, int speciesSequence, PokemonDropInformation dropInfo) {
		this(species, speciesSequence, new ArrayList<ItemDrop>());
		
		processPokemonDropInformation(dropInfo);
	}
	
	public PokemonDrop(PokemonSpec pokemonSpec, int speciesSequence, ArrayList<ItemDrop> drops) {
		this.species = EnumPokemon.getFromNameAnyCase(pokemonSpec.name);
		this.speciesSequence = speciesSequence;
		this.drops = drops;
	}
	
	public PokemonDrop(PokemonSpec pokemonSpec, int speciesSequence, PokemonDropInformation dropInfo) {
		this(pokemonSpec, speciesSequence, new ArrayList<ItemDrop>());
		
		processPokemonDropInformation(dropInfo);
	}
	
	public EnumPokemon getSpecies() {
		return this.species;
	}
	
	public int getSpeciesSequence() {
		return this.speciesSequence;
	}
	
	public ArrayList<ItemDrop> getDrops() {
		return this.drops;
	}
	
	private void processPokemonDropInformation(PokemonDropInformation dropInfo) {
		if (fields == null) {
			openPokemonDropInformationFields();
		}
		
		HashMap<String, Object> fieldValues = new HashMap<String, Object>();
		
		fields.stream()
				.forEach(f -> {
					try {
						fieldValues.put(f.getName(), f.get(dropInfo));
					} catch (Exception ex) {
					}
				});
		
		if (fieldValues.containsKey(FIELD_POKEMON)) {
			Object pokemonValue = fieldValues.get(FIELD_POKEMON);
			if (pokemonValue instanceof EnumPokemon) {
				this.species = (EnumPokemon)pokemonValue;
			}
			
			if (pokemonValue instanceof PokemonSpec) {
				this.species = EnumPokemon.getFromNameAnyCase(((PokemonSpec)pokemonValue).name);
			}
		}
		
		if (fieldValues.containsKey(FIELD_MAIN_DROP) && fieldValues.get(FIELD_MAIN_DROP) != null && fieldValues.containsKey(FIELD_MAIN_DROP_MIN) && fieldValues.containsKey(FIELD_MAIN_DROP_MAX)) {
			this.drops.add(new ItemDrop((ItemStack)fieldValues.get(FIELD_MAIN_DROP), (int)fieldValues.get(FIELD_MAIN_DROP_MIN), (int)fieldValues.get(FIELD_MAIN_DROP_MAX), EnumItemDropType.MAIN));
		}
		
		if (fieldValues.containsKey(FIELD_OPT_DROP1) && fieldValues.get(FIELD_OPT_DROP1) != null && fieldValues.containsKey(FIELD_OPT_DROP1_MIN) && fieldValues.containsKey(FIELD_OPT_DROP1_MAX)) {
			this.drops.add(new ItemDrop((ItemStack)fieldValues.get(FIELD_OPT_DROP1), (int)fieldValues.get(FIELD_OPT_DROP1_MIN), (int)fieldValues.get(FIELD_OPT_DROP1_MAX), EnumItemDropType.OPTIONAL));
		}
		
		if (fieldValues.containsKey(FIELD_OPT_DROP2) && fieldValues.get(FIELD_OPT_DROP2) != null && fieldValues.containsKey(FIELD_OPT_DROP2_MIN) && fieldValues.containsKey(FIELD_OPT_DROP2_MAX)) {
			this.drops.add(new ItemDrop((ItemStack)fieldValues.get(FIELD_OPT_DROP2), (int)fieldValues.get(FIELD_OPT_DROP2_MIN), (int)fieldValues.get(FIELD_OPT_DROP2_MAX), EnumItemDropType.OPTIONAL));
		}
		
		if (fieldValues.containsKey(FIELD_RARE_DROP) && fieldValues.get(FIELD_RARE_DROP) != null && fieldValues.containsKey(FIELD_RARE_DROP_MIN) && fieldValues.containsKey(FIELD_RARE_DROP_MAX)) {
			this.drops.add(new ItemDrop((ItemStack)fieldValues.get(FIELD_RARE_DROP), (int)fieldValues.get(FIELD_RARE_DROP_MIN), (int)fieldValues.get(FIELD_RARE_DROP_MAX), EnumItemDropType.RARE));
		}
	}
	
	private static void openPokemonDropInformationFields() {
		
		List<String> fieldNames = Arrays.asList(FIELD_NAMES);
		
		try {
			fields = Arrays.asList(PokemonDropInformation.class.getDeclaredFields());
			fields.removeIf(f -> !fieldNames.contains(f.getName()));
			fields.stream()
					.filter(f -> !f.isAccessible())
					.forEach(f -> f.setAccessible(true));
		}
		catch (Exception ex) {
			fields = new ArrayList<Field>();
		}
	}

	@Override
	public int compareTo(@Nonnull PokemonDrop o) {
		return species.name.compareToIgnoreCase(o.species.name);
	}
	
}
