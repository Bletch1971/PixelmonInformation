package bletch.pixelmoninformation.jei.common;

import java.util.ArrayList;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.enums.EnumBossMode;

import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class PokemonBossDrop {

	private EnumBossMode bossMode;
	private ArrayList<ItemStack> drops;
	
	public PokemonBossDrop(EnumBossMode bossMode, ArrayList<ItemStack> drops) {
		this.bossMode = bossMode;
		this.drops = drops;
	}
	
	public EnumBossMode getBossMode() {
		return this.bossMode;
	}
	
	public ArrayList<ItemStack> getDrops() {
		return this.drops;
	}
	
}
