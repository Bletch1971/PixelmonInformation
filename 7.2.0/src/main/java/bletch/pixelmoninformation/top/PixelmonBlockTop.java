package bletch.pixelmoninformation.top;

import java.util.List;

import javax.annotation.Nullable;

import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityApricornTree;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityBerryTree;

import bletch.pixelmoninformation.PixelmonInformation;
import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.core.ModDetails;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PixelmonBlockTop {
	
	public static class getTheOneProbe implements com.google.common.base.Function<ITheOneProbe, Void> {

		public static final String KEY_SUFFIX_INFORMATION = ".pinformation";
		public static final String KEY_SUFFIX_TOOLTIP = ".ptooltip";
		
		public static final int APRICORNTREE_MAX_STAGE = 6; 
		public static final int BERRYTREE_MAX_STAGE = 5; 
		
		public static ITheOneProbe probe;

		@Nullable
		@Override
		public Void apply(ITheOneProbe theOneProbe) {
			probe = theOneProbe;
			
			probe.registerProvider(new IProbeInfoProvider() {

				@Override
				public String getID() {
					return ModDetails.MOD_ID + ":default";
				}

				@Override
				public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
					Block block = state.getBlock();
					
					if (block.getRegistryName().getResourceDomain().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
						PixelmonBlockTop.getTheOneProbe.addProbeInfo(mode, probeInfo, player, world, state, data);
					}	
				}

			});
			
			probe.registerBlockDisplayOverride((mode, probeInfo, player, world, state, data) -> {
				Block block = state.getBlock();
				
				if (block.getRegistryName().getResourceDomain().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
					return PixelmonBlockTop.getTheOneProbe.overrideStandardInfo(mode, probeInfo, player, world, state, data);
				}
				
				return false;
			});
			
			return null;
		}
		
		private static boolean addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {

			Minecraft minecraft = null;
			if (PixelmonInformation.proxy.isRemote()) {
				minecraft = Minecraft.getMinecraft();
			}
			
			if (ModConfig.top.useSneaking && mode == ProbeMode.NORMAL) {
				return false;
			}
			
			TileEntity tileEntity = world.getTileEntity(data.getPos());	
			ItemStack itemStack = data.getPickBlock();
			if (!itemStack.getItem().getRegistryName().getResourceDomain().equalsIgnoreCase(ModDetails.MOD_ID_PIXELMON)) {
				if (tileEntity != null) {
					itemStack = new ItemStack(state.getBlock(), 1, tileEntity.getBlockMetadata());
				} else {
					itemStack = new ItemStack(state.getBlock());
				}
			}

			if (ModConfig.top.blocks.showBlockTooltip) {
				String translateKey_tooltip = itemStack.getUnlocalizedName() + KEY_SUFFIX_TOOLTIP;
				
				if (ModConfig.debug.enableDebug && ModConfig.debug.showTopBlockTranslationKey) {
					probeInfo.text(TextUtils.translate("gui.translationkey") + " " + translateKey_tooltip);
				}

				String output = TextUtils.translate(translateKey_tooltip);
				List<String> results = StringUtils.split(output, minecraft, 4, true);
				
				if (results != null && !results.isEmpty()) {
					results.forEach(r -> probeInfo.text(r));
				}
			}

			if (ModConfig.top.blocks.showBlockInformation) {
				String translateKey_information = itemStack.getUnlocalizedName() + KEY_SUFFIX_INFORMATION;
				
				if (ModConfig.debug.enableDebug && ModConfig.debug.showTopBlockTranslationKey) {
					probeInfo.text(TextUtils.translate("gui.translationkey") + " " + translateKey_information);
				}
				
				String output = TextUtils.translate(translateKey_information);
				List<String> results = StringUtils.split(output, minecraft, 4);
				
				if (results != null && !results.isEmpty()) {
					results.forEach(r -> probeInfo.text(r));
				}
			}
			
			if (tileEntity instanceof TileEntityApricornTree) {
				if (ModConfig.top.blocks.showGrowthStages) {
					int stage = ((TileEntityApricornTree)tileEntity).getStage() + 1;
					if (stage < APRICORNTREE_MAX_STAGE)
						probeInfo.text(TextUtils.translate("gui.growthstage") + " " + stage + " / " + APRICORNTREE_MAX_STAGE);
					else
						probeInfo.text(TextUtils.translate("gui.growthstage") + " " + TextUtils.translate("gui.growthcomplete"));
				}
			}
			
			if (tileEntity instanceof TileEntityBerryTree) {
				if (ModConfig.top.blocks.showGrowthStages) {
					int stage = ((TileEntityBerryTree)tileEntity).getStage();
					if (stage < BERRYTREE_MAX_STAGE)
						probeInfo.text(TextUtils.translate("gui.growthstage") + " " + stage + " / " + BERRYTREE_MAX_STAGE);
					else
						probeInfo.text(TextUtils.translate("gui.growthstage") + " " + TextUtils.translate("gui.growthcomplete"));
				}
			}
			
			return true;
		}
		
		private static boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState state, IProbeHitData data) {
			return false;
		}
		
	}
	
}
