package bletch.pixelmoninformation.waila;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import bletch.pixelmoninformation.core.ModCommonConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

@ParametersAreNonnullByDefault
public class PixelmonBlockWaila implements IComponentProvider, IServerDataProvider<TileEntity> {
	
	public static final PixelmonBlockWaila INSTANCE = new PixelmonBlockWaila();
	
	private static final String NBT_TAG_TILEENTITY = "tileentity";
	
	@Override
	public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
		Minecraft minecraft = Minecraft.getInstance();

		if (ModCommonConfig.instance.wailaUseCrouchKey() && !accessor.getPlayer().isCrouching()) {
			return;
		}
		
		Block block = accessor.getBlock();

		ResourceLocation registryName = block.getRegistryName();
		if (registryName == null) {
			return;
		}

		String resourceDomain = registryName.getNamespace();
		if (resourceDomain == null || !(resourceDomain.equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON))) {
			return;
		}
		
		if (ModCommonConfig.instance.wailaBlocksShowTooltip()) {
			String translateKey = registryName.getPath() + PixelmonUtils.TOOLTIP_SUFFIX;			
			String output = new TranslationTextComponent(translateKey).getString();
	        
	        if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
	        	List<String> outputLines = StringUtils.split(output, minecraft, 4);
				if (outputLines != null && !outputLines.isEmpty()) {
					outputLines.forEach(l -> tooltip.add(new StringTextComponent(l)));
				}
	        }
		}
		
		if (ModCommonConfig.instance.wailaBlocksShowInformation()) {
			String translateKey = registryName.getPath() + PixelmonUtils.INFORMATION_SUFFIX;			
			String output = new TranslationTextComponent(translateKey).getString();
	        
	        if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
	        	List<String> outputLines = StringUtils.split(output, minecraft, 4);
				if (outputLines != null && !outputLines.isEmpty()) {
					outputLines.forEach(l -> tooltip.add(new StringTextComponent(l)));
				}
	        }
		}
		
//		if (block instanceof ApricornLeavesBlock && ModCommonConfig.instance.wailaBlocksShowGrowthStages()) {
//			ApricornLeavesBlock leavesBlock = ((ApricornLeavesBlock)block);
//			
//		}
		
//		if (block instanceof BerryLeavesBlock && ModCommonConfig.instance.wailaBlocksShowGrowthStages()) {
//			BerryLeavesBlock leavesBlock = ((BerryLeavesBlock)block);
//			
//		}
	}
	
	@Override
    public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity tileEntity) {
		if (tileEntity != null) {
			data.put(NBT_TAG_TILEENTITY, tileEntity.getTileData());
		}
	}

	public void register(IRegistrar registrar) {
		if (!ModCommonConfig.instance.enableWailaIntegration() || !ModCommonConfig.instance.wailaShowBlockInformation())
			return;
		
		ArrayList<String> processed = new ArrayList<String>();
		
		ArrayList<Class<?>> blockClasses = new ArrayList<Class<?>>();
		blockClasses.addAll(PixelmonUtils.getPixelmonBlockClasses());
		
		// remove any blocks that are inherited from other pixelmon mod blocks
		for (int i = blockClasses.size() - 1; i >= 0; i--) {
			if (blockClasses.contains(blockClasses.get(i).getSuperclass())) {
				blockClasses.remove(i);
			}
		}
		
		for (Class<?> blockClass : blockClasses) {
        	String key = blockClass.getTypeName();

        	if (processed.contains(key)) {
        		continue;
        	}
        	processed.add(key);
        	
			registrar.registerComponentProvider(this, TooltipPosition.BODY, blockClass);
			registrar.registerBlockDataProvider(this, blockClass);
		}	
	}
	
}
