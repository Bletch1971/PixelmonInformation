package bletch.pixelmoninformation.jei.enums;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.utils.TextUtils;

@ParametersAreNonnullByDefault
public enum EnumPokeChestTier {

	TIER1("Tier1", "gui.pokechesttier.tier1.name"),
	TIER2("Tier2", "gui.pokechesttier.tier2.name"),
	TIER3("Tier3", "gui.pokechesttier.tier3.name");
	
	protected final String name;	
	protected final String localiseKey;

	private EnumPokeChestTier(String name, String localiseKey) {
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
