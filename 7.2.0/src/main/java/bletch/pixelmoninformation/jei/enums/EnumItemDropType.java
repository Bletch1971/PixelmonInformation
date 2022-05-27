package bletch.pixelmoninformation.jei.enums;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.utils.TextUtils;
import net.minecraft.util.text.TextFormatting;

@ParametersAreNonnullByDefault
public enum EnumItemDropType {
	
	MAIN("main", "gui.itemdroptype.main.name"),
	OPTIONAL("optional", "gui.itemdroptype.optional.name"),
	RARE("rare", "gui.itemdroptype.rare.name");
	
	protected final String name;	
	protected final String localiseKey;

	private EnumItemDropType(String name, String localiseKey) {
		this.name = name;
		this.localiseKey = localiseKey;
	}
	
	public TextFormatting getColor() {
		return getColor(this);
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
	
	public static TextFormatting getColor(EnumItemDropType type) {
		switch (type) {
			case OPTIONAL:
				return TextFormatting.GREEN;			
			case RARE:
				return TextFormatting.GOLD;
			default:
				return TextFormatting.WHITE;
		}
	}
	
}
