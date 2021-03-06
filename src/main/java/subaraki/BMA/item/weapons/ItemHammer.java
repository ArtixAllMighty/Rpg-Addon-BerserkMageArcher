package subaraki.BMA.item.weapons;

import java.util.UUID;

import lib.playerclass.capability.PlayerClass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.item.BmaItems;

public class ItemHammer extends ItemSword{

	public ItemHammer(ToolMaterial material) {
		super(material);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack itemStack = player.getHeldItem(hand);
		
		if(!PlayerClass.get(player).isPlayerClass(BmaItems.berserkerClass))
			return super.onItemRightClick(world, player, hand);

		Vec3d vec = player.getLook(1);

		EntityHammerSmash ehs = new EntityHammerSmash(world);
		ehs.setLocationAndAngles(player.posX + vec.x*1.5f, player.posY, player.posZ + vec.z*1.5f, player.rotationYaw, player.rotationPitch);
		ehs.setItemStackToSlot(null, itemStack.copy());
		UUID uuid = player.getUniqueID();
		ehs.setOwnerId(uuid);

		player.setHeldItem(hand, ItemStack.EMPTY);

		if(!world.isRemote)
			world.spawnEntity(ehs);

		player.getCooldownTracker().setCooldown(this, 500);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
		
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem().equals(Items.IRON_INGOT) ? true : super.getIsRepairable(toRepair, repair);
	}
}
