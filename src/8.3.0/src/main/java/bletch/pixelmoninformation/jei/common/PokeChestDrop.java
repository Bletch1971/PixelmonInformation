package bletch.pixelmoninformation.jei.common;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.annotation.ParametersAreNonnullByDefault;

import com.pixelmonmod.pixelmon.blocks.BlockBeastChest;
import com.pixelmonmod.pixelmon.blocks.BlockMasterChest;
import com.pixelmonmod.pixelmon.blocks.BlockPokeChest;
import com.pixelmonmod.pixelmon.blocks.BlockUltraChest;

import bletch.pixelmoninformation.jei.enums.EnumPokeChestTier;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

@ParametersAreNonnullByDefault
public class PokeChestDrop {

	private EnumPokeChestTier tier;
	private ArrayList<ItemStack> drops;
	private Block block;
	
	public PokeChestDrop(EnumPokeChestTier tier, ArrayList<ItemStack> drops) {
		this.tier = tier;
		this.drops = drops;
	}
	
	public EnumPokeChestTier getTier() {
		return this.tier;
	}
	
	public ArrayList<ItemStack> getDrops() {
		return this.drops;
	}
	
	public Block getBlock() {
		if (this.block == null) {
			setBlock();
		}
		
		return this.block;
	}
	
	public void setBlock() {

		this.block = null;
		
		Class<?> blockClass = null;
		
		switch (this.tier) {
			case TIER1:
				blockClass = BlockPokeChest.class;
				break;
			case TIER2:
				blockClass = BlockUltraChest.class;
				break;
			case TIER3:
				blockClass = BlockMasterChest.class;
				break;
			case TIER4:
				blockClass = BlockBeastChest.class;
				break;
			default:
				return;
		}

		Optional<?> block = StreamSupport.stream(Block.REGISTRY.spliterator(), false)
				.filter(blockClass::isInstance)
				.map(blockClass::cast)
				.findFirst();
		if (block.isPresent()) {
			this.block = (Block)block.get();
		}
	}
	
}
