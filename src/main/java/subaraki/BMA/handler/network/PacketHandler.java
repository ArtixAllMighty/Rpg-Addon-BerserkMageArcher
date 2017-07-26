package subaraki.BMA.handler.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import subaraki.BMA.handler.network.CSyncMageIndexPacket.PacketSyncMageIndexHandler;
import subaraki.BMA.handler.network.CSyncShieldPacket.CSyncShieldPacketHandler;
import subaraki.BMA.handler.network.CSyncSpellListPacket.PacketSyncSpellListHandler;
import subaraki.BMA.handler.network.SPacketExpelliarmus.PacketExpelliarmusHandler;

public class PacketHandler {

	private static final String CHANNEL = "B_M_A_Channel";
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(CHANNEL);
	
	public PacketHandler() {
		NETWORK.registerMessage(PacketSyncMageIndexHandler.class, CSyncMageIndexPacket.class, 0, Side.CLIENT);
		NETWORK.registerMessage(PacketExpelliarmusHandler.class, SPacketExpelliarmus.class, 1, Side.SERVER);
		NETWORK.registerMessage(PacketSyncSpellListHandler.class, CSyncSpellListPacket.class, 2, Side.CLIENT);
		NETWORK.registerMessage(CSyncShieldPacketHandler.class, CSyncShieldPacket.class, 3, Side.CLIENT);

	}
}