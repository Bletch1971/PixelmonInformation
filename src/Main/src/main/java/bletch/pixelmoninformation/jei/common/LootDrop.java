package bletch.pixelmoninformation.jei.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import bletch.pixelmoninformation.jei.enums.EnumItemDropType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class LootDrop extends ItemDrop {

    public ItemStack smeltedItem;
    public int fortuneLevel;
    public boolean enchanted;

    public LootDrop(ItemStack itemStack) {
        this(itemStack, itemStack.getCount());
    }

    public LootDrop(ItemStack itemStack, float chance) {
        this(itemStack, chance, 0);
    }

    public LootDrop(ItemStack itemStack, float chance, int fortuneLevel) {
        this(itemStack, (int) Math.floor(chance), (int) Math.ceil(chance), chance, fortuneLevel);
    }

    public LootDrop(ItemStack itemStack, int minDrop, int maxDrop, Condition... conditions) {
        this(itemStack, minDrop, maxDrop, 1F, 0, conditions);
    }

    public LootDrop(ItemStack itemStack, int minDrop, int maxDrop, float chance, int fortuneLevel, Condition... conditions) {
    	super(itemStack, minDrop, maxDrop, EnumItemDropType.MAIN, chance, conditions);

        this.smeltedItem = null;
        this.fortuneLevel = fortuneLevel;
    }

    public LootDrop(Item item, int minDrop, int maxDrop, Condition... conditions) {
        this(new ItemStack(item), minDrop, maxDrop, 1F, 0, conditions);
    }

    public LootDrop(Item item, int itemDamage, int minDrop, int maxDrop, Condition... conditions) {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, 1F, 0, conditions);
    }

    public LootDrop(Item item, int minDrop, int maxDrop, float chance, Condition... conditions) {
        this(new ItemStack(item), minDrop, maxDrop, chance, 0, conditions);
    }

    public LootDrop(Item item, int itemDamage, int minDrop, int maxDrop, float chance, Condition... conditions) {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, chance, 0, conditions);
    }

    public LootDrop(ItemStack itemStack, int minDrop, int maxDrop, float chance, Condition... conditions) {
        this(itemStack, minDrop, maxDrop, chance, 0, conditions);
    }

    public LootDrop(Item item, float chance, LootFunction... lootFunctions) {
        this(new ItemStack(item), chance);
        
        this.enchanted = false;
        addLootFunctions(lootFunctions);
    }

    public LootDrop(Item item, float chance, LootCondition[] lootConditions, LootFunction... lootFunctions) {
        this(item, chance, lootFunctions);
        
        addLootConditions(lootConditions);
    }

    public LootDrop addLootConditions(LootCondition[] lootFunctions) {
        return addLootConditions(Arrays.asList(lootFunctions));
    }

    public LootDrop addLootConditions(Collection<LootCondition> lootFunctions) {
        lootFunctions.forEach(this::addLootCondition);
        return this;
    }

    public LootDrop addLootCondition(LootCondition condition) {
        LootHelper.applyCondition(condition, this);
        return this;
    }

    public LootDrop addLootFunctions(LootFunction[] lootFunctions) {
        return addLootFunctions(Arrays.asList(lootFunctions));
    }

    public LootDrop addLootFunctions(Collection<LootFunction> lootFunctions) {
        lootFunctions.forEach(this::addLootFunction);
        return this;
    }

    public LootDrop addLootFunction(LootFunction lootFunction) {
        LootHelper.applyFunction(lootFunction, this);
        return this;
    }

    public boolean canBeCooked() {
        return smeltedItem != null;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> list = new LinkedList<>();
        
        if (itemStack != null) {
        	list.add(itemStack);
        }
        
        if (smeltedItem != null) {
        	list.add(smeltedItem);
        }
        
        return list;
    }

    @Override
    public List<String> getTooltipText() {
        return getTooltipText(false);
    }

    public List<String> getTooltipText(boolean smelted) {
        List<String> list = conditions.stream().map(Condition::toString).collect(Collectors.toList());
        
        if (smelted) {
            list.add(Condition.burning.toString());
        }
        
        return list;
    }

    public void addConditional(Condition condition) {
        this.conditions.add(condition);
    }

    public void addConditionals(List<Condition> conditions) {
        this.conditions.addAll(conditions);
    }

    public float getSortIndex() {
        return sortIndex;
    }

    public int compareTo(@Nonnull LootDrop o) {
        if (ItemStack.areItemStacksEqual(itemStack, o.itemStack)) {
            return Integer.compare(o.fortuneLevel, fortuneLevel);
        }
        
        int cmp = Float.compare(o.getSortIndex(), getSortIndex());
        return cmp != 0 ? cmp : itemStack.getDisplayName().compareTo(o.itemStack.getDisplayName());
    }
    
}
