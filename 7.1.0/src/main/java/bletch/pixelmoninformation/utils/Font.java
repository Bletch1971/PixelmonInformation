package bletch.pixelmoninformation.utils;

import java.awt.Color;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

@ParametersAreNonnullByDefault
public class Font {
	
    public final static Font small = new Font(true);
    public final static Font normal = new Font(false);

    public FontRenderer fontRenderer;

    private Font(boolean small) {
        Minecraft mc = Minecraft.getMinecraft();
        fontRenderer = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.getTextureManager(), small);
        ((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(fontRenderer);
    }

    public int getStringWidth(String text) {
        return fontRenderer.getStringWidth(text);
    }

    public void printLeft(Object o, int x, int y) {
    	printLeft(o, x, y, Color.BLACK.getRGB(), false);
    }

    public void printLeft(Object o, int x, int y, int color) {
    	printLeft(o, x, y, color, false);
    }

    public void printLeft(Object o, int x, int y, int color, boolean shadow) {
        fontRenderer.drawString(String.valueOf(o), x, y, color, shadow);
    }
    
    public void printCentered(Object o, int x, int y) {
    	printCentered(o, x, y, Color.BLACK.getRGB(), false);
    }
    
    public void printCentered(Object o, int x, int y, int color) {
    	printCentered(o, x, y, color, false);
    }
    
    public void printCentered(Object o, int x, int y, int color, boolean shadow) {
    	int width = getStringWidth(String.valueOf(o));
    	int newX = x - (width / 2);
    	
    	fontRenderer.drawString(String.valueOf(o), newX, y, color, shadow);
    }
    
    public void printRight(Object o, int x, int y) {
    	printRight(o, x, y, Color.BLACK.getRGB(), false);
    }
    
    public void printRight(Object o, int x, int y, int color) {
    	printRight(o, x, y, color, false);
    }
    
    public void printRight(Object o, int x, int y, int color, boolean shadow) {
    	int width = getStringWidth(String.valueOf(o));
    	int newX = x - width;
    	
    	fontRenderer.drawString(String.valueOf(o), newX, y, color, shadow);
    }
    
    public String trimStringToWidth(String text, int width, boolean addElipse) {
    	if (fontRenderer.getStringWidth(text) > width) {
    		return fontRenderer.trimStringToWidth(text, width) + (addElipse ? "..." : "");
    	} else {
    		return text;
    	}
    }
    
}
