package bletch.pixelmoninformation.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.util.text.TextFormatting;

@ParametersAreNonnullByDefault
public class TextUtils {
	
	public static final String SYMBOL_FORMATTING = "\u00A7"; //§
	
	public static final String SYMBOL_FEMALE = TextFormatting.LIGHT_PURPLE + "\u2640";
	public static final String SYMBOL_MALE = TextFormatting.BLUE + "\u2642";
	
	public static final String SYMBOL_GREENTICK = TextFormatting.GREEN+"\u2714";
	public static final String SYMBOL_REDCROSS = TextFormatting.RED+"\u2718";
	public static final String SYMBOL_UNKNOWN = TextFormatting.GOLD+"\u003F";
	
}
