package bletch.pixelmoninformation.waila;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityApricornTree;
import com.pixelmonmod.pixelmon.blocks.tileEntities.TileEntityBerryTree;

import bletch.pixelmoninformation.PixelmonInformation;
import bletch.pixelmoninformation.core.ModConfig;
import bletch.pixelmoninformation.utils.DebugUtils;
import bletch.pixelmoninformation.utils.PixelmonUtils;
import bletch.pixelmoninformation.utils.StringUtils;
import bletch.pixelmoninformation.utils.TextUtils;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@ParametersAreNonnullByDefault
public class PixelmonBlockWaila implements IWailaDataProvider {

	public static final String KEY_SUFFIX_INFORMATION = ".pinformation";
	public static final String KEY_SUFFIX_TOOLTIP = ".ptooltip";
	
	public static final int APRICORNTREE_MAX_STAGE = 6; 
	public static final int BERRYTREE_MAX_STAGE = 5; 
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currentTip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		Minecraft minecraft = null;
		if (PixelmonInformation.proxy.isRemote()) {
			minecraft = Minecraft.getMinecraft();
		}
		
		if (ModConfig.waila.useSneaking && !accessor.getPlayer().isSneaking()) {
			return currentTip;
		}
		
		TileEntity tileEntity = accessor.getTileEntity();
		
		if (ModConfig.waila.blocks.showBlockTooltip) {
			String translateKey_tooltip = itemStack.getUnlocalizedName() + KEY_SUFFIX_TOOLTIP;
			
			if (ModConfig.debug.enableDebug && ModConfig.debug.showWailaBlockTranslationKey) {
				currentTip.add(TextUtils.translate("gui.translationkey") + " " + translateKey_tooltip);
			}
			
			String output = TextUtils.translate(translateKey_tooltip);
			List<String> results = StringUtils.split(output, minecraft, 4);
			
			if (results != null && !results.isEmpty()) {
				currentTip.addAll(results);
			}
		}
		
		if (ModConfig.waila.blocks.showBlockInformation) {
			String translateKey_information = itemStack.getUnlocalizedName() + KEY_SUFFIX_INFORMATION;
			
			if (ModConfig.debug.enableDebug && ModConfig.debug.showWailaBlockTranslationKey) {
				currentTip.add(TextUtils.translate("gui.translationkey") + " " + translateKey_information);
			}
			
			String output = TextUtils.translate(translateKey_information);
			List<String> results = StringUtils.split(output, minecraft, 4);
			
			if (results != null && !results.isEmpty()) {
				currentTip.addAll(results);
			}
		}
		
		if (tileEntity instanceof TileEntityApricornTree) {
			if (ModConfig.waila.blocks.showGrowthStages) {
				int stage = ((TileEntityApricornTree)tileEntity).getStage() + 1;
				if (stage < APRICORNTREE_MAX_STAGE)
					currentTip.add(TextUtils.translate("gui.growthstage") + " " + stage + " / " + APRICORNTREE_MAX_STAGE);
				else
					currentTip.add(TextUtils.translate("gui.growthstage") + " " + TextUtils.translate("gui.growthcomplete"));
			}
		}
		
		if (tileEntity instanceof TileEntityBerryTree) {
			if (ModConfig.waila.blocks.showGrowthStages) {
				int stage = ((TileEntityBerryTree)tileEntity).getStage();
				if (stage < BERRYTREE_MAX_STAGE)
					currentTip.add(TextUtils.translate("gui.growthstage") + " " + stage + " / " + BERRYTREE_MAX_STAGE);
				else
					currentTip.add(TextUtils.translate("gui.growthstage") + " " + TextUtils.translate("gui.growthcomplete"));
			}
		}
    	
		return currentTip;
	}
	
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currentTip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tag, World world, BlockPos position) {
		if (tileEntity != null) {
			tileEntity.writeToNBT(tag);
		}

		return tag;
	}

	public static void callbackRegister(IWailaRegistrar registrar) {
		PixelmonBlockWaila dataProvider = new PixelmonBlockWaila();
		ArrayList<String> processed = new ArrayList<String>();
		
		ArrayList<Class<?>> pixelmonClassess = new ArrayList<Class<?>>();
		pixelmonClassess.addAll(PixelmonUtils.getPixelmonBlockClasses());
		
		// remove any blocks that are inherited from other pixelmon mod blocks
		for (int i = pixelmonClassess.size() - 1; i >= 0; i--) {
			if (pixelmonClassess.contains(pixelmonClassess.get(i).getSuperclass())) {
				pixelmonClassess.remove(i);
			}
		}
		
		for (Class<?> pixelmonClass : pixelmonClassess) {
        	String key = pixelmonClass.getTypeName();

        	if (processed.contains(key)) {
        		continue;
        	}
        	processed.add(key);
        	
        	registrar.registerNBTProvider(dataProvider, pixelmonClass);
			registrar.registerBodyProvider(dataProvider, pixelmonClass);
			
			if (ModConfig.debug.enableDebug && ModConfig.debug.showWailaBlocksRegistered) {
				DebugUtils.writeLine("Registered WAILA information for block " + key, true);
			} 
		}	
	}

}
