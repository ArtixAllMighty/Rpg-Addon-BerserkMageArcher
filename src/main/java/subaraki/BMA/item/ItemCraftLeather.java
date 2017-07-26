package subaraki.BMA.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import subaraki.BMA.mod.AddonBma;

public class ItemCraftLeather extends Item{

	public ItemCraftLeather(){
		super();
		setHasSubtypes(true);
		setUnlocalizedName(AddonBma.MODID+"."+"craftLeather");
		setRegistryName("craftLeather");
		setMaxStackSize(64);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		return super.getUnlocalizedName() + "_" + meta ;
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < 3; ++i)
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
}