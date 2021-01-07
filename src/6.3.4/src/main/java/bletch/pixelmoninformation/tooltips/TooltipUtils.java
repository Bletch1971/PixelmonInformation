package bletch.pixelmoninformation.tooltips;

import java.util.Arrays;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class TooltipUtils {
	public static final String SEPARATOR_COLON = " : ";
	public static final String SEPARATOR_DASH = " - ";
	public static final String SEPARATOR_FSLASH = "/";
	public static final String SEPARATOR_TIMES = "x ";
	public static final String INDENT = "  ";
	
	public static final String SYMBOL_GREENTICK = TextFormatting.GREEN+"\u2714";
	public static final String SYMBOL_REDCROSS = TextFormatting.RED+"\u2718";
	
	public static final String KEY_HELP = "gui.Help";
	public static final String KEY_INFO = "gui.Info";
	public static final String KEY_CONTROLHELP = "gui.ShowControlHelp";
	public static final String KEY_CONTROLINFO = "gui.ShowControlInfo";
	public static final String KEY_SHIFTHELP = "gui.ShowShiftHelp";
	public static final String KEY_SHIFTINFO = "gui.ShowShiftInfo";
		
	public static List<String> translateMulti(String translateKey) {
		if (translateKey == null || translateKey == "") {
			return null;
		}
		
		if (!I18n.canTranslate(translateKey)) {
			return null;
		}
		
		String value = I18n.translateToLocal(translateKey);
		if (value == "" || value.equalsIgnoreCase(translateKey)) {
			return null;
		}
		
		String[] values = value.split("\\\\n");
		return Arrays.asList(values);
	}
	
	public static String translate(String translateKey) {
		if (translateKey == null || translateKey == "") {
			return "";
		}
		
		if (!I18n.canTranslate(translateKey)) {
			return "";
		}
		
		String value = I18n.translateToLocal(translateKey);
		if (value == "" || value.equalsIgnoreCase(translateKey)) {
			return "";
		}
		
		return value.replace("\\n", "\n");
	}
}
