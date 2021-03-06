package subaraki.BMA.enchantment;

import com.google.common.base.Predicates;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import subaraki.BMA.item.BmaItems;

public class EnchantmentHandler {

	public static final EnchantmentWand wand_enchantment = new EnchantmentWand();
	public static final EnumEnchantmentType WAND = (EnumHelper.addEnchantmentType("WAND", Predicates.equalTo(BmaItems.wand_stick )));
	
	public EnchantmentHandler() {
		addEnchantings();
	}
	
	public static void addEnchantings(){
		ForgeRegistries.ENCHANTMENTS.register(wand_enchantment);
	}
	
	
}
