package subaraki.BMA.item.armor;

import lib.item.armor.ModeledArmor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import subaraki.BMA.handler.proxy.ClientProxy;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.mod.AddonBma;

public class ItemArcherArmor extends ModeledArmor {

	public ItemArcherArmor(ArmorMaterial enumArmorMaterial, EntityEquipmentSlot slot) {
		super(slot, enumArmorMaterial, "archer_"+slot.getName());
	}

	@Override
	public String armorClassName() {
		return BmaItems.archerClass;
	}

	@Override
	protected void get3DArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot armorSlot){
		if (stack != ItemStack.EMPTY) {
			if (stack.getItem() instanceof ItemArmor) {

				EntityEquipmentSlot type = ((ItemArmor) stack.getItem()).armorType;

				switch (type) {
				case HEAD:
				case LEGS:
					setArmorModel(AddonBma.proxy.getArmorModel(ClientProxy.archer_rest));
					break;
				case FEET:
				case CHEST:
					setArmorModel(AddonBma.proxy.getArmorModel(ClientProxy.archer_chest));
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public Item getLinkedShieldItem(){
		return BmaItems.shield_archer;
	}
}