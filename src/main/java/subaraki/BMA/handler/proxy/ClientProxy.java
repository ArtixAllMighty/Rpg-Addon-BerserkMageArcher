package subaraki.BMA.handler.proxy;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import subaraki.BMA.entity.EntityAugolustra;
import subaraki.BMA.entity.EntityBombarda;
import subaraki.BMA.entity.EntityDart;
import subaraki.BMA.entity.EntityExpelliarmus;
import subaraki.BMA.entity.EntityFlyingCarpet;
import subaraki.BMA.entity.EntityFreezeSpell;
import subaraki.BMA.entity.EntityHammerSmash;
import subaraki.BMA.entity.EntityHellArrow;
import subaraki.BMA.entity.EntityScintilla;
import subaraki.BMA.entity.renders.RenderAugolustra;
import subaraki.BMA.entity.renders.RenderBombarda;
import subaraki.BMA.entity.renders.RenderDart;
import subaraki.BMA.entity.renders.RenderExpelliarmus;
import subaraki.BMA.entity.renders.RenderFlyingCarpet;
import subaraki.BMA.entity.renders.RenderFreezeSpell;
import subaraki.BMA.entity.renders.RenderHammerSmash;
import subaraki.BMA.entity.renders.RenderHellArrow;
import subaraki.BMA.entity.renders.RenderScintilla;
import subaraki.BMA.handler.event.ClientEventsHandler;
import subaraki.BMA.item.BmaItems;
import subaraki.BMA.item.armor.model.ModelArcherArmor;
import subaraki.BMA.item.armor.model.ModelBerserkerArmor;
import subaraki.BMA.item.armor.model.ModelMageArmor;
import subaraki.BMA.render.LayerColdEffect;
import subaraki.BMA.render.LayerMageProtection;

public class ClientProxy extends ServerProxy {

	private static final ModelBerserkerArmor armorBerserkChest = new ModelBerserkerArmor(1.0f);
	private static final ModelBerserkerArmor armorBerserk = new ModelBerserkerArmor(0.5f);
	public static final String berserker_chest = "berserker_chest";
	public static final String berserker_rest = "berserker_rest";

	private static final ModelMageArmor armorMageChest = new ModelMageArmor(1.0f);
	private static final ModelMageArmor armorMage = new ModelMageArmor(0.5f);
	public static final String mage_chest = "mage_chest";
	public static final String mage_rest= "mage_rest";

	private static final ModelArcherArmor armorArcherChest = new ModelArcherArmor(1.0f);
	private static final ModelArcherArmor armorarcher = new ModelArcherArmor(0.5f);
	public static final String archer_chest = "archer_chest";
	public static final String archer_rest= "archer_rest";

	@Override
	public ModelBiped getArmorModel(String id) {

		switch (id) {
		case berserker_rest:
			return armorBerserk;
		case berserker_chest:
			return armorBerserkChest;

		case mage_rest:
			return armorMage;
		case mage_chest:
			return armorMageChest;

		case archer_rest:
			return armorarcher;
		case archer_chest:
			return armorArcherChest;
		}
		return super.getArmorModel(id);
	}

	public void registerRenders(){
		BmaItems.registerRenderers();

		RenderingRegistry.registerEntityRenderingHandler(EntityAugolustra.class, RenderAugolustra::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityExpelliarmus.class, RenderExpelliarmus::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityHammerSmash.class, RenderHammerSmash::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityHellArrow.class, RenderHellArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, RenderDart::new);

		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingCarpet.class, RenderFlyingCarpet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityFreezeSpell.class, RenderFreezeSpell::new);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBombarda.class, RenderBombarda::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityScintilla.class, RenderScintilla::new);

	}

	public void registerClientEvents(){
		new ClientEventsHandler();
	}

	@Override
	public void registerColors(){
		ItemColors ic = Minecraft.getMinecraft().getItemColors();

		ic.registerItemColorHandler(

				new IItemColor() {
					@Override
					public int colorMultiplier(ItemStack stack, int tintIndex) {
						if(stack != ItemStack.EMPTY){
							switch(stack.getMetadata())
							{
							case 0 : 
								return 0xa24203;
							case 1 : 
								return 0x3333FF;
							case 2 : 
								return 0x71544f;
							}
						}

						return 0xffffff;
					}
				}, 
				BmaItems.craftLeather
				);

		ic.registerItemColorHandler(

				new IItemColor() {
					@Override
					public int colorMultiplier(ItemStack stack, int tintIndex) {
						return 0x654f2d;
					}
				}, 
				BmaItems.dart
				);
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public Minecraft getClientMinecraft(){
		return Minecraft.getMinecraft();
	}


	public static int outsideSphereID;

	@Override
	public void registerRenderInformation() {

		Sphere sphere = new Sphere();
		// GLU_POINT will render it as dots.
		// GLU_LINE will render as wireframe
		// GLU_SILHOUETTE will render as ?shadowed? wireframe
		// GLU_FILL as a solid.
		sphere.setDrawStyle(GLU.GLU_FILL);
		// GLU_SMOOTH will try to smoothly apply lighting
		// GLU_FLAT will have a solid brightness per face, and will not shade.
		// GLU_NONE will be completely solid, and probably will have no depth to
		// it's appearance.
		sphere.setNormals(GLU.GLU_SMOOTH);
		// GLU_INSIDE will render as if you are inside the sphere, making it
		// appear inside out.(Similar to how ender portals are rendered)
		sphere.setOrientation(GLU.GLU_OUTSIDE);

		sphere.setTextureFlag(true);
		// Simple 1x1 red texture to serve as the spheres skin, the only pixel
		// in this image is red.
		// sphereID is returned from our sphereID() method
		outsideSphereID = GL11.glGenLists(1);
		// Create a new list to hold our sphere data.
		GL11.glNewList(outsideSphereID, GL11.GL_COMPILE);
		// Offset the sphere by it's radius so it will be centered
		GL11.glTranslatef(0.50F, 0.50F, 0.50F);

		sphere.draw(0.5F, 12, 24);
		// Drawing done, unbind our texture
		// Tell LWJGL that we are done creating our list.
		GL11.glEndList();
	}

	@Override
	public void addRenderLayers(){

		String types[] = new String[]{"default","slim"};

		for(String type : types){
			RenderPlayer renderer = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get(type));
			renderer.addLayer(new LayerMageProtection(renderer));

			if(renderer instanceof RenderLivingBase)
				((RenderLivingBase) renderer).addLayer(new LayerColdEffect((RenderLivingBase) renderer));

		}

		for(Render<? extends Entity> renderer : Minecraft.getMinecraft().getRenderManager().entityRenderMap.values())
		{
			if(renderer instanceof RenderLivingBase)
			{
				((RenderLivingBase) renderer).addLayer(new LayerColdEffect((RenderLivingBase) renderer));
			}
		}
	}
}