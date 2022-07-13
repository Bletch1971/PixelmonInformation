package bletch.pixelmoninformation.top;

import java.nio.charset.StandardCharsets;
import java.util.List;

import bletch.pixelmoninformation.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IElementFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class WrappedTextElement implements IElement {

	public static int ELEMENT_ID = -1;
	private String text;
	private int maxLines;
	
	private List<String> textLines;
	private int width;
	private int height;
	
	public WrappedTextElement(String text) {
		PopulateServerDetails(text,  4);
	}
	
	public WrappedTextElement(String text, int maxLines) {
		PopulateServerDetails(text,  maxLines);
	}
	
	protected WrappedTextElement(String text, int maxLines, boolean isClient) { 		
		if (isClient) {
			PopulateClientDetails(text, maxLines);
		} else {
			PopulateServerDetails(text, maxLines);
		}
	}	
	
	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getID() {
		return ELEMENT_ID;
	}

	@Override
	public int getWidth() {		
		return this.width;
	}

	@Override
	public void render(int x, int y) {
		Minecraft minecraft = Minecraft.getMinecraft();
		int lineY = y;
		
		for (String line : this.textLines) {
			renderText(minecraft, x, lineY, line);
			lineY += minecraft.fontRenderer.FONT_HEIGHT;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(text.length());
		buf.writeBytes(text.getBytes(StandardCharsets.UTF_8));
		buf.writeInt(this.maxLines);
	}
	
	protected void PopulateClientDetails(String text, int maxLines) {
		Minecraft minecraft = Minecraft.getMinecraft();
		
		this.text = text; 
		this.maxLines = maxLines;
		
		this.textLines = StringUtils.split(text, minecraft, maxLines);
		this.width = 0; 
		this.height = Math.max(0, minecraft.fontRenderer.FONT_HEIGHT * this.textLines.size());		
		
		for (String line : this.textLines) {
			int lineWidth = minecraft.fontRenderer.getStringWidth(line);
			this.width = Math.max(this.width, lineWidth);
		}
	}
	
	protected void PopulateServerDetails(String text, int maxLines) {
		this.text = text;  
		this.maxLines = maxLines;
		
		this.width = 0; 
		this.height = 0;
		this.textLines = null;
	}    
	
	public static int renderText(Minecraft mc, int x, int y, String txt) {        
		GlStateManager.color(1.0F, 1.0F, 1.0F);

	    GlStateManager.pushMatrix();
	    GlStateManager.translate(0.0F, 0.0F, 32.0F);
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    GlStateManager.enableRescaleNormal();
	    GlStateManager.enableLighting();
	    net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
	
	    GlStateManager.disableLighting();
	    GlStateManager.disableDepth();
	    GlStateManager.disableBlend();
	    int width = mc.fontRenderer.getStringWidth(txt);
	    mc.fontRenderer.drawStringWithShadow(txt, x, y, 16777215);
	    GlStateManager.enableLighting();
	    GlStateManager.enableDepth();
	    // Fixes opaque cooldown overlay a bit lower
	    GlStateManager.enableBlend();
	
	
	    GlStateManager.popMatrix();
	    GlStateManager.disableRescaleNormal();
	    GlStateManager.disableLighting();
	
	    return width;
    }

    public static class Factory implements IElementFactory {

		@Override
		public IElement createElement(ByteBuf buf) {
			int textLength = buf.readInt();
			byte[] idBuf = new byte[textLength];
			buf.readBytes(idBuf);
			int maxLines = buf.readInt();
			String text = new String(idBuf, StandardCharsets.UTF_8);
			
			return new WrappedTextElement(text, maxLines, true);
		}

    }
}
