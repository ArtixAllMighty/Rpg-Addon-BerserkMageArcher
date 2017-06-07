package subaraki.BMA.mod;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import subaraki.BMA.capability.MageDataCapability;
import subaraki.BMA.enchantment.EnchantmentHandler;
import subaraki.BMA.handler.event.BmaEventHandler;
import subaraki.BMA.handler.event.ClientEventsHandler;
import subaraki.BMA.handler.event.SpellHandler;
import subaraki.BMA.handler.network.PacketHandler;
import subaraki.BMA.handler.proxy.ServerProxy;
import subaraki.BMA.item.BmaItems;

@Mod(modid = AddonBma.MODID, name = AddonBma.NAME, version = AddonBma.VERSION, dependencies = "required-after:subcommonlib@[1.2,)")
public class AddonBma {

	public static final String MODID = "bma_addon";
	public static final String NAME = "Berserker, Mage, Archer Addon for Rpg Inventory";
	public static final String VERSION = "1.11.2 4.0.2.0";

	@SidedProxy(clientSide = "subaraki.BMA.handler.proxy.ClientProxy", serverSide = "subaraki.BMA.handler.proxy.ServerProxy")
	public static ServerProxy proxy;

	public static SpellHandler spells;

	public static Logger log = LogManager.getLogger(MODID);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){

		ModMetadata modMeta = event.getModMetadata();
		modMeta.authorList = Arrays.asList(new String[] { "Subaraki" });
		modMeta.autogenerated = false;
		modMeta.credits = "ZetaHunter and Richard Digits (retired team members)";
		modMeta.description = "Class Armor for Mage, Berserker and Archer";
		modMeta.url = "https://github.com/ArtixAllMighty/Rpg-Inventory-2016/wiki";

		BmaItems.loadItems();
		
		proxy.registerRenders();
		proxy.registerClientEvents();
		proxy.registerEntities();
		
		new PacketHandler();
		new EnchantmentHandler();
		new BmaEventHandler();
		
		
		new MageDataCapability().register();
		
		spells = new SpellHandler();

	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		proxy.registerColors();
		proxy.addRenderLayers();
		proxy.registerRenderInformation();
	}
}
