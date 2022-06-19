package bletch.pixelmoninformation.top;

import com.google.common.base.Function;

import bletch.pixelmoninformation.core.ModCommonConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class PixelmonBlockTop {

	public static class getTheOneProbe implements Function<ITheOneProbe, Void> {

		public static ITheOneProbe probe;
		
		@Override
		public Void apply(ITheOneProbe theOneProbe) {
			probe = theOneProbe;
			
			if (WrappedTextElement.ELEMENT_ID == -1) {
				WrappedTextElement.ELEMENT_ID = probe.registerElementFactory(new WrappedTextElement.Factory());
			}
			
			probe.registerProvider(new IProbeInfoProvider() {

				@Override
				public String getID() {
					return ModDetails.MOD_ID + ":default";
				}

				@Override
				public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
					Block block = state.getBlock();
					
					if (block.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
						PixelmonBlockTop.getTheOneProbe.addProbeInfo(mode, probeInfo, player, world, state, data);
					}	
				}

			});
			
			probe.registerBlockDisplayOverride((mode, probeInfo, player, world, state, data) -> {
				Block block = state.getBlock();
				
				if (block.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
					return PixelmonBlockTop.getTheOneProbe.overrideStandardInfo(mode, probeInfo, player, world, state, data);
				}
				
				return false;
			});
			
			return null;
		}
		
		private static boolean addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
			if (ModCommonConfig.instance.topUseCrouchKey() && mode == ProbeMode.NORMAL) {
				return false;
			}
			
			Block block = state.getBlock();

			ResourceLocation registryName = block.getRegistryName();
			if (registryName == null) {
				return false;
			}
			
			if (ModCommonConfig.instance.wailaBlocksShowTooltip()) {
				String translateKey = registryName.getPath() + PixelmonUtils.TOOLTIP_SUFFIX;			
				String output = new TranslationTextComponent(translateKey).getString();
		        
		        if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
		        	probeInfo.element(new WrappedTextElement(output));
		        }
			}
			
			if (ModCommonConfig.instance.wailaBlocksShowInformation()) {
				String translateKey = registryName.getPath() + PixelmonUtils.INFORMATION_SUFFIX;			
				String output = new TranslationTextComponent(translateKey).getString();
		        
		        if (I18n.exists(translateKey) && !StringUtils.isNullOrWhitespace(output)) {
	        		probeInfo.element(new WrappedTextElement(output));
		        }
			}
			
//			if (block instanceof ApricornLeavesBlock && ModCommonConfig.instance.topBlocksShowGrowthStages()) {
//				ApricornLeavesBlock leavesBlock = ((ApricornLeavesBlock)block);
//				
//			}
			
//			if (block instanceof BerryLeavesBlock && ModCommonConfig.instance.topBlocksShowGrowthStages()) {
//				BerryLeavesBlock leavesBlock = ((BerryLeavesBlock)block);
//				
//			}
			
			return true;
		}
		
		private static boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
			return false;
		}
	}
}
