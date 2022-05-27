package bletch.pixelmoninformation.jei.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import bletch.pixelmoninformation.jei.enums.EnumItemDropType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemDrop implements Comparable<ItemDrop> {

	public ItemStack itemStack;
	public int minDrop;
    public int maxDrop;
    public float chance;
    public EnumItemDropType dropType;
    protected Set<Condition> conditions;
    protected float sortIndex;

    public ItemDrop(ItemStack itemStack, EnumItemDropType dropType) {
        this(itemStack, dropType, itemStack.getCount());
    }

    public ItemDrop(ItemStack itemStack, EnumItemDropType dropType, float chance) {
        this(itemStack, (int) Math.floor(chance), (int) Math.ceil(chance), dropType, chance);
    }

    public ItemDrop(ItemStack itemStack, int minDrop, int maxDrop, EnumItemDropType dropType) {
        this(itemStack, minDrop, maxDrop, dropType, 1F);
    }
    
    public ItemDrop(ItemStack itemStack, int minDrop, int maxDrop, EnumItemDropType dropType, Condition... conditions) {
        this(itemStack, minDrop, maxDrop, dropType, 1F, conditions);
    }

    public ItemDrop(ItemStack itemStack, int minDrop, int maxDrop, EnumItemDropType dropType, float chance, Condition... conditions) {
        this.itemStack = itemStack;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.dropType = dropType;
        this.chance = chance;
        this.sortIndex = Math.min(chance, 1F) * (float) (minDrop + maxDrop);
        this.conditions = new HashSet<>();
        
        Collections.addAll(this.conditions, conditions);
    }

    public ItemDrop(Item item, int minDrop, int maxDrop, EnumItemDropType dropType, Condition... conditions) {
        this(new ItemStack(item), minDrop, maxDrop, dropType, 1F, conditions);
    }

    public ItemDrop(Item item, int itemDamage, int minDrop, int maxDrop, EnumItemDropType dropType, Condition... conditions) {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, dropType, 1F, conditions);
    }

    public ItemDrop(Item item, int minDrop, int maxDrop, EnumItemDropType dropType, float chance, Condition... conditions) {
        this(new ItemStack(item), minDrop, maxDrop, dropType, chance, conditions);
    }

    public ItemDrop(Item item, int itemDamage, int minDrop, int maxDrop, EnumItemDropType dropType, float chance, Condition... conditions) {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, dropType, chance, conditions);
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new LinkedList<>();
        
        if (itemStack != null) {
        	list.add(itemStack);
        }
        
        return list;
    }

    public String toString() {
        if (minDrop == maxDrop) {
        	return (dropType == EnumItemDropType.MAIN ? "" : dropType.getColor() + dropType.getLocalisedName() + " ") + TextFormatting.RESET + minDrop + getDropChance();
        }
        
        return (dropType == EnumItemDropType.MAIN ? "" : dropType.getColor() + dropType.getLocalisedName() + " ") + TextFormatting.RESET + minDrop + "-" + maxDrop + getDropChance();
    }

    private String getDropChance() {
        return chance < 1F ? " (" + formatChance() + "%)" : "";
    }

    private String formatChance() {
        float chance = this.chance * 100;
        
        if (chance < 10) {
        	return String.format("%.1f", chance);
    	}
        
        return String.format("%2d", (int) chance);
    }

    public String chanceString() {
        if (chance >= 0.995f) {
        	return String.format("%.2G", chance);
        }
        
    	return String.format("%.2G%%", chance * 100f);
    }

    public List<String> getTooltipText() {
        return conditions.stream().map(Condition::toString).collect(Collectors.toList());
    }

    public void addConditional(Condition conditional) {
        this.conditions.add(conditional);
    }

    public void addConditionals(List<Condition> conditionals) {
        this.conditions.addAll(conditionals);
    }

    public float getSortIndex() {
        return sortIndex;
    }

    @Override
    public int compareTo(@Nonnull ItemDrop o) {
        if (ItemStack.areItemStacksEqual(itemStack, o.itemStack)) {
            return Float.compare(o.getSortIndex(), getSortIndex());
        }
        
        int cmp = Float.compare(o.getSortIndex(), getSortIndex());
        return cmp != 0 ? cmp : itemStack.getDisplayName().compareTo(o.itemStack.getDisplayName());
    }
    
}
