package bletch.pixelmoninformation.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class StringUtils {

	public static final String EMPTY = "";
	
	public static Boolean isNullOrWhitespace(@Nullable String value) {
		return (value == null || value.trim().length() == 0);
	}
	
	public static List<String> split(String text, int displayWidth, FontRenderer fontRenderer, int maxLines) {
		if (isNullOrWhitespace(text) || fontRenderer == null) {
			return new ArrayList<String>();
		}
		
		List<String> result = new ArrayList<String>();
		
		String[] textLines = text.split("\\\\n");
		
		for (String line : textLines) {
			int textWidth = fontRenderer.getStringWidth(line);
			
			// check if the text is longer than the display width
			if (textWidth > displayWidth) {
				// break the text into parts using the whitespaces 
				String[] textParts = line.split(" ");
				String delimiter = StringUtils.EMPTY;
				String formatting = StringUtils.EMPTY;
				
				// clear the output
				String output = StringUtils.EMPTY;
				
				// cycle through each part
				for (String part : textParts) {
					textWidth = fontRenderer.getStringWidth(output);
					
					// check if the current output length is longer than the display width
					if (textWidth >= displayWidth) {
						result.add(output);
						
						// reset the output text and delimiter
						output = formatting;
						delimiter = StringUtils.EMPTY;
					}
					
					// add the part to the output
					output += delimiter + part;
					delimiter = " ";
					
					if (part.startsWith(TextUtils.SYMBOL_FORMATTING)) {
						formatting = part.substring(0, 2);
					}
				}       			
				
				// check if the output is empty
				if (!StringUtils.isNullOrWhitespace(output)) {
					// output is not empty, add to the tooltip
					result.add(output);
				}
			} else {
				result.add(line);
			}
		}
		
		return result;
	}
	
	public static List<String> split(String text, Minecraft minecraft, int maxLines) {
		if (isNullOrWhitespace(text) || minecraft == null) {
			return new ArrayList<String>();
		}
		
		ScaledResolution resolution = new ScaledResolution(minecraft);
		int displayWidth = resolution.getScaledWidth() / 3;

		return split(text, displayWidth, minecraft.fontRenderer, maxLines);
	}
}
