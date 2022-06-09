package bletch.pixelmoninformation.tooltips;

import java.util.List;

import bletch.pixelmoninformation.core.ModClientConfig;
import bletch.pixelmoninformation.core.ModDetails;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PixelmonItemTooltip {

	public static final String KEY_SNEAK = "gui.tooltip.collapsed";
	public static final String TOOLTIP_SUFFIX = ".ptooltip";	

	private static final GameSettings settings = Minecraft.getInstance().options;

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onItemTooltip(ItemTooltipEvent event) {
		if (ModClientConfig.instance.restrictToAdvancedTooltips() && !event.getFlags().isAdvanced()) {
			return;
		}

		List<ITextComponent> tooltip = event.getToolTip();
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
		if (resourceDomain == null || !(resourceDomain.equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))) {
			return;
		}

		String tooltipKey = registryName.getPath() + TOOLTIP_SUFFIX;

		if (I18n.exists(tooltipKey)) {    	
			boolean sneaking = InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), settings.keyShift.getKey().getValue());

			if (ModClientConfig.instance.useSneakKey() && !sneaking) {
				tooltipKey = KEY_SNEAK;

				if (ModClientConfig.instance.showSneakKeyInfo() && I18n.exists(tooltipKey)) {
					tooltip.add(new TranslationTextComponent(tooltipKey));
				} 

			} else {
				tooltip.add(new TranslationTextComponent(tooltipKey));
			}		
		}
	}
}
