package bletch.pixelmoninformation.tooltips;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.TextUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ParametersAreNonnullByDefault
public class PixelmonItemTooltip {

	public static final String KEY_SUFFIX = ".ptooltip";
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onItemTooltip(ItemTooltipEvent event) {
    	if (ModConfig.tooltips.restrictToAdvancedTooltips && !event.getFlags().isAdvanced()) {
    		return;
    	}
    	
    	List<String> tooltip = event.getToolTip();
    	if (tooltip == null) {
    		return;
    	}
    	
    	ItemStack itemStack = event.getItemStack();
    	if (itemStack == null) {
    		return;
    	}

    	Item item = itemStack.getItem();
    	if (item == null) {
    		return;
    	}
    	
    	ResourceLocation registryName = item.getRegistryName();
    	if (registryName == null) {
    		return;
    	}
    	
    	String resourceDomain = registryName.getNamespace();
    	if (resourceDomain == null || !(ModDetails.MOD_ID_PIXELMON.equalsIgnoreCase(resourceDomain))) {
    		return;
    	}

		String tooltipKey = registryName.getPath() + KEY_SUFFIX;
		
		List<String> value = TextUtils.translateMulti(tooltipKey);
		if (value != null && value.size() > 0) { 
			
			if (ModConfig.tooltips.useShiftKey) {
				if (!GuiScreen.isShiftKeyDown()) {
					value = null;
					if (ModConfig.tooltips.showShiftKeyInfo) {
						value = TextUtils.translateMulti(TextUtils.KEY_SHIFTINFO);
					}
				}
			}
	
			if (value != null && value.size() > 0) { 
				tooltip.addAll(value);	
			}
		}		
    }
    
}
