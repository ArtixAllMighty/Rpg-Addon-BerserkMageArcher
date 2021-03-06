package subaraki.BMA.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import subaraki.BMA.capability.FreezeData;
import subaraki.BMA.capability.FreezeDataCapability;

public class EntityFreezeSpell extends EntityThrowable{

	public EntityFreezeSpell(World worldIn){
		super(worldIn);
	}
	public EntityFreezeSpell(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	public EntityFreezeSpell(World worldIn, EntityLivingBase livingBaseIn){
		super(worldIn, livingBaseIn);
		this.shoot(livingBaseIn.getLookVec().x, livingBaseIn.getLookVec().y, livingBaseIn.getLookVec().z, 1.5f, 0.0f);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (result.entityHit != null)
		{
			if(result.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase toFreeze = (EntityLivingBase)result.entityHit;

				if(toFreeze.hasCapability(FreezeDataCapability.CAPABILITY, null))
				{
					FreezeData cap = FreezeData.get(toFreeze); 
					cap.initFreezeData();
					
					this.setDead();
				}
			}
		}
	}

	@Override
	protected float getGravityVelocity(){
		return 0F;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(ticksExisted > 60)
			this.setDead();

		if(world.isRemote){
			for(int i = 0 ; i < 5; i++)
				world.spawnParticle(EnumParticleTypes.REDSTONE, posX - 0.5f + world.rand.nextFloat()/2f, posY, posZ - 0.5f + world.rand.nextFloat()/2f, 1.0D, 1.0D, 1.0D, new int[0]);
		}
	}
}
