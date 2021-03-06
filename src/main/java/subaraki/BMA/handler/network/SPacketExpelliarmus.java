package subaraki.BMA.handler.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketExpelliarmus implements IMessage {

	public int entity_ID = -1;

	public SPacketExpelliarmus() {}

	public SPacketExpelliarmus(int id) {
		entity_ID = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entity_ID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entity_ID);
	}

	public static class PacketExpelliarmusHandler implements IMessageHandler<SPacketExpelliarmus, IMessage>{

		@Override
		public IMessage onMessage(SPacketExpelliarmus message, MessageContext ctx) {
			((WorldServer)ctx.getServerHandler().player.world).addScheduledTask(() -> {
				World world = ctx.getServerHandler().player.world;
				Entity entity = world.getEntityByID(message.entity_ID);

				if(entity instanceof EntityLivingBase){
					EntityLivingBase elb = (EntityLivingBase)entity;
					ItemStack held = elb.getHeldItemMainhand().copy();
					EntityItem ei = new EntityItem(elb.world, elb.posX, elb.posY, elb.posZ, held);
					ei.motionX *= 2;
					ei.motionZ *= 2;
					elb.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
					elb.world.spawnEntity(ei);
				}
			});
			return null;
		}
	}

}
