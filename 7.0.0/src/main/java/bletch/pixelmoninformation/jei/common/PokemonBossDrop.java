package bletch.pixelmoninformation.jei.common;

import java.util.ArrayList;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.jei.enums.EnumPokemonBossType;
import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class PokemonBossDrop {

	private EnumPokemonBossType type;
	private ArrayList<ItemStack> drops;
	
	public PokemonBossDrop(EnumPokemonBossType type, ArrayList<ItemStack> drops) {
		this.type = type;
		this.drops = drops;
	}
	
	public EnumPokemonBossType getType() {
		return this.type;
	}
	
	public ArrayList<ItemStack> getDrops() {
		return this.drops;
	}
	
}
