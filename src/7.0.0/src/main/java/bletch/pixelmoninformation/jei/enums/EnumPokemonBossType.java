package bletch.pixelmoninformation.jei.enums;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.utils.TextUtils;

@ParametersAreNonnullByDefault
public enum EnumPokemonBossType {
	
	BOSS("Boss", "gui.pokemonbosstype.boss.name"),
	MEGABOSS("MegaBoss", "gui.pokemonbosstype.megaboss.name"),
	SHINEYMEGABOSS("ShinyMegaBoss", "gui.pokemonbosstype.shinymegaboss.name");
	
	protected final String name;	
	protected final String localiseKey;

	private EnumPokemonBossType(String name, String localiseKey) {
		this.name = name;
		this.localiseKey = localiseKey;
	}
	
	public String getName() {
		return this.name;
	}	
	
	public String getUnlocalisedName() {
		return this.localiseKey;
	}
	
	public String getLocalisedName() {
		return TextUtils.translate(this.localiseKey);
	}
	
	@Override
	public String toString() {
		return getLocalisedName();
	}
	
}
