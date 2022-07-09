package bletch.pixelmoninformation.top;

import com.google.common.base.Function;

import bletch.pixelmoninformation.core.ModCommonConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class PixelmonBlockTop {

	public static class getTheOneProbe implements Function<ITheOneProbe, Void> {

		public static ITheOneProbe PROBE;
		public static IProbeConfig CONFIG;

		@Override
		public Void apply(ITheOneProbe theOneProbe) {
			PROBE = theOneProbe;
			CONFIG = PROBE.createProbeConfig();

			if (WrappedTextElement.ELEMENT_ID == -1) {
				WrappedTextElement.ELEMENT_ID = PROBE.registerElementFactory(new WrappedTextElement.Factory());
			}

			PROBE.registerProvider(new IProbeInfoProvider() {

				@Override
				public String getID() {
					return ModDetails.MOD_ID + ":default";
				}

				@Override
				public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
					Block block = state.getBlock();

					if (block.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
						PixelmonBlockTop.getTheOneProbe.addPixelmonBlockInfo(mode, probeInfo, player, world, state, data);
					}	
				}

			});

			PROBE.registerBlockDisplayOverride((mode, probeInfo, player, world, state, data) -> {
				Block block = state.getBlock();

				if (block.getRegistryName().getNamespace().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
					return PixelmonBlockTop.getTheOneProbe.overridePixelmonBlockInfo(mode, probeInfo, player, world, state, data);
				}

				return false;
			});

			return null;
		}

		private static boolean addPixelmonBlockInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
			if (!ModCommonConfig.INSTANCE.topShowBlockInformation() 
					|| ModCommonConfig.INSTANCE.topUseCrouchKey() && !player.isCrouching()) {
				return false;
			}

			Block block = state.getBlock();

			ResourceLocation registryName = block.getRegistryName();
			if (registryName == null) {
				return false;
			}

			if (ModCommonConfig.INSTANCE.wailaBlocksShowTooltip()) {
				String translateKey = registryName.getPath() + PixelmonUtils.TOOLTIP_SUFFIX;			
				String output = new TranslationTextComponent(translateKey).getString();

				if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					probeInfo.element(new WrappedTextElement(output));
				}
			}

			if (ModCommonConfig.INSTANCE.wailaBlocksShowInformation()) {
				String translateKey = registryName.getPath() + PixelmonUtils.INFORMATION_SUFFIX;			
				String output = new TranslationTextComponent(translateKey).getString();

				if (!StringUtils.isNullOrWhitespace(output) && !output.equalsIgnoreCase(translateKey)) {
					probeInfo.element(new WrappedTextElement(output));
				}
			}

//			if (block instanceof ApricornLeavesBlock && ModCommonConfig.INSTANCE.topBlocksShowGrowthStages()) {
//				ApricornLeavesBlock leavesBlock = ((ApricornLeavesBlock)block);
//				
//			}

//			if (block instanceof BerryLeavesBlock && ModCommonConfig.INSTANCE.topBlocksShowGrowthStages()) {
//				BerryLeavesBlock leavesBlock = ((BerryLeavesBlock)block);
//				
//			}

			return true;
		}

		private static boolean overridePixelmonBlockInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
			return false;
		}
	}
}
