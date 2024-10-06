package moflop.mods.negorerouse.entity;

import com.google.common.base.Predicate;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorDestructable;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Furia on 14/05/08.
 */
public class EntityPhantomSwordExBase extends Entity implements IProjectile,IThrowableEntity {

    /**
     * ★撃った人
     */
    protected Entity thrower;

    protected ItemStack blade = ItemStack.EMPTY;

    /**
     * ★多段Hit防止用List
     */
    protected List<Entity> alreadyHitEntity = new ArrayList<Entity>();

    protected float AttackLevel = 0.0f;

    /**
     * ■コンストラクタ
     * @param par1World
     */
    public EntityPhantomSwordExBase(World par1World)
    {
        super(par1World);

        this.noClip = true;

        //■生存タイマーリセット
        ticksExisted = 0;

        //■サイズ変更
        setSize(0.5F, 0.5F);
    }

    public EntityPhantomSwordExBase(World par1World, EntityLivingBase entityLiving, float AttackLevel, float roll){
        this(par1World,entityLiving,AttackLevel);
        this.setRoll(roll);
    }

    public EntityPhantomSwordExBase(World par1World, EntityLivingBase entityLiving, float AttackLevel)
    {
        this(par1World);

        this.AttackLevel = AttackLevel;

        //■撃った人
        setThrower(entityLiving);

        blade = entityLiving.getHeldItem(EnumHand.MAIN_HAND);
        if(!blade.isEmpty() && !(blade.getItem() instanceof ItemSlashBlade)){
            blade = ItemStack.EMPTY;
        }

        //■撃った人と、撃った人が（に）乗ってるEntityも除外
        alreadyHitEntity.clear();
        alreadyHitEntity.add(thrower);
        alreadyHitEntity.add(thrower.getRidingEntity());
        alreadyHitEntity.addAll(thrower.getPassengers());


        {
            float dist = 2.0f;

            double ran = (rand.nextFloat() - 0.5) * 2.0;

            double yaw =  Math.toRadians(-thrower.rotationYaw + 90);

            double x = ran * Math.sin(yaw);
            double y = 1.0 - Math.abs(ran);
            double z = ran * Math.cos(yaw);

            x*=dist;
            y*=dist;
            z*=dist;

            //■初期位置・初期角度等の設定
            setLocationAndAngles(thrower.posX + x,
                    thrower.posY + y,
                    thrower.posZ + z,
                    thrower.rotationYaw,
                    thrower.rotationPitch);

            iniYaw = thrower.rotationYaw;
            iniPitch = thrower.rotationPitch;

            setDriveVector(1.75f);
        }
    }

    private static final DataParameter<Integer> THROWER_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityPhantomSwordExBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LIFETIME = EntityDataManager.<Integer>createKey(EntityPhantomSwordExBase.class, DataSerializers.VARINT);
    private static final DataParameter<Float> ROLL = EntityDataManager.<Float>createKey(EntityPhantomSwordExBase.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> TARGET_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityPhantomSwordExBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> INTERVAL = EntityDataManager.<Integer>createKey(EntityPhantomSwordExBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityPhantomSwordExBase.class, DataSerializers.VARINT);
    private static final DataParameter<Float> SCALE = EntityDataManager.<Float>createKey(EntityPhantomSwordExBase.class, DataSerializers.FLOAT);

    /**
     * ■イニシャライズ
     */
    @Override
    protected void entityInit() {

        //EntityId
        this.getDataManager().register(THROWER_ENTITY_ID, 0);

        //lifetime
        this.getDataManager().register(LIFETIME, 20);

        //Roll
        this.getDataManager().register(ROLL, 0.0f);

        //EntityId
        this.getDataManager().register(TARGET_ENTITY_ID, 0);

        //interval
        this.getDataManager().register(INTERVAL, 7);

        //color
        this.getDataManager().register(COLOR, 0x3333FF);

        this.getDataManager().register(SCALE,1.0f);
    }

    public int getThrowerEntityId(){
        return this.getDataManager().get(THROWER_ENTITY_ID);
    }
    public void setThrowerEntityId(int entityid){
        this.getDataManager().set(THROWER_ENTITY_ID, entityid);
    }

    public int getTargetEntityId(){
        return this.getDataManager().get(TARGET_ENTITY_ID);
    }
    public void setTargetEntityId(int entityid){
        this.getDataManager().set(TARGET_ENTITY_ID, entityid);
    }

    public float getRoll(){
        return this.getDataManager().get(ROLL);
    }
    public void setRoll(float roll){
        this.getDataManager().set(ROLL,roll);
    }

    public int getLifeTime(){
        return this.getDataManager().get(LIFETIME);
    }
    public void setLifeTime(int lifetime){
        this.getDataManager().set(LIFETIME,lifetime);
    }

    public int getInterval(){
        return this.getDataManager().get(INTERVAL);
    }
    public void setInterval(int value){
        this.getDataManager().set(INTERVAL,value);
    }

    public int getColor(){
        return this.getDataManager().get(COLOR);
    }
    public void setColor(int value){
        this.getDataManager().set(COLOR,value);
    }

    public final float getScale(){
        return getDataManager().get(SCALE);
    }
    public final void setScale(float scale){
        this.getDataManager().set(SCALE,scale);
    }

    float speed = 0.0f;
    float iniYaw = Float.NaN;
    float iniPitch = Float.NaN;

    public boolean doTargeting(){

        if(this.ticksExisted > getInterval()) return false;

        int targetid = this.getTargetEntityId();

        Entity owner = this.thrower;
        if(this.thrower == null)
            owner = this;

        if(targetid == 0){

            Entity rayEntity = getRayTrace(owner, 30.0f); //最長３０
            if(rayEntity != null){
                targetid = rayEntity.getEntityId();
                this.setTargetEntityId( rayEntity.getEntityId());
            }
        }

        //視線中に無かった場合近傍Entityに拡張検索
        if(targetid == 0){
            Entity rayEntity = getRayTrace(owner, 30.0f,5.0f,5.0f); //最長３０、視線外10幅まで探索拡張
            if(rayEntity != null){
                targetid = rayEntity.getEntityId();
                this.setTargetEntityId( rayEntity.getEntityId());
            }
        }

        if(targetid != 0){
            Entity target = world.getEntityByID(targetid);

            if(target != null){

                if(Float.isNaN(iniPitch) && thrower != null){
                    iniYaw = thrower.rotationYaw;
                    iniPitch = thrower.rotationPitch;
                }
                faceEntity(this,target,ticksExisted * 1.0f,ticksExisted * 1.0f);
                setDriveVector(1.75F, false);
            }
        }

        return true;
    }

    public Entity getRayTrace(Entity owner, double reachMax){
        return this.getRayTrace(owner, reachMax,1.0f,0.0f);
    }

    public Entity getRayTrace(Entity owner, double reachMax, float expandFactor, float expandBorder) {
        Entity pointedEntity;
        float par1 = 1.0f;

        RayTraceResult objectMouseOver = rayTrace(owner, reachMax, par1);
        double reachMin = reachMax;
        Vec3d entityPos = getPosition(owner);

        if (objectMouseOver != null) {
            reachMin = objectMouseOver.hitVec.distanceTo(entityPos);
        }

        Vec3d lookVec = getLook(owner, par1);
        Vec3d reachVec = entityPos.addVector(lookVec.x * reachMax, lookVec.y * reachMax, lookVec.z * reachMax);
        pointedEntity = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this
                , this.getEntityBoundingBox()
                        .offset(lookVec.x * reachMax, lookVec.y * reachMax, lookVec.z * reachMax)
                        .grow((double) expandFactor + reachMax, (double) expandFactor + reachMax, (double) expandFactor + reachMax));
        list.removeAll(alreadyHitEntity);

        double tmpDistance = reachMin;

        EntityLivingBase viewer = (owner instanceof EntityLivingBase) ? (EntityLivingBase) owner : null;

        for (Entity entity : list) {
            if (entity == null || !entity.canBeCollidedWith()) continue;

            if (!EntitySelectorAttackable.getInstance().apply(entity))
                continue;

            if(viewer != null && !viewer.canEntityBeSeen(entity))
                continue;

            float borderSize = entity.getCollisionBorderSize() + expandBorder; //視線外10幅まで判定拡張
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow((double) borderSize, (double) borderSize, (double) borderSize);
            RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(entityPos, reachVec);
            double counter = reachMax;
            /*
            while(0 < counter || 4 < reachVec.lengthSquared() || movingobjectposition != null){
                counter--;
                movingobjectposition = axisalignedbb.calculateIntercept(entityPos, reachVec);
                reachVec = reachVec.subtract(lookVec);
            }
            */

            if (axisalignedbb.contains(entityPos)) {
                if (0.0D < tmpDistance || tmpDistance == 0.0D) {
                    pointedEntity = entity;
                    tmpDistance = 0.0D;
                }
            } else if (movingobjectposition != null) {
                double d3 = entityPos.distanceTo(movingobjectposition.hitVec);

                if (d3 < tmpDistance || tmpDistance == 0.0D) {
                    if (entity == this.getRidingEntity() && !entity.canRiderInteract()) {
                        if (tmpDistance == 0.0D) {
                            pointedEntity = entity;
                        }
                    } else {
                        pointedEntity = entity;
                        tmpDistance = d3;
                    }
                }
            }
        }

        return pointedEntity;
    }

    public static RayTraceResult rayTrace(Entity owner, double par1, float par3)
    {
        Vec3d Vec3d = getPosition(owner);
        Vec3d Vec3d1 = getLook(owner, par3);
        Vec3d Vec3d2 = Vec3d.addVector(Vec3d1.x * par1, Vec3d1.y * par1, Vec3d1.z * par1);
        return owner.world.rayTraceBlocks(Vec3d, Vec3d2, false, false, true);
    }
    public static Vec3d getPosition(Entity owner)
    {
        return new Vec3d(owner.posX, owner.posY + owner.getEyeHeight(), owner.posZ);
    }
    public static Vec3d getLook(Entity owner, float rotMax)
    {
        float f1;
        float f2;
        float f3;
        float f4;

        if (rotMax == 1.0F)
        {
            f1 =  MathHelper.cos(-owner.rotationYaw   * 0.017453292F - (float)Math.PI);
            f2 =  MathHelper.sin(-owner.rotationYaw   * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.cos(-owner.rotationPitch * 0.017453292F);
            f4 =  MathHelper.sin(-owner.rotationPitch * 0.017453292F);
            return new Vec3d((double)(f2 * f3), (double)f4, (double)(f1 * f3));
        }
        else
        {
            f1 = owner.prevRotationPitch + (owner.rotationPitch - owner.prevRotationPitch) * rotMax;
            f2 = owner.prevRotationYaw + (owner.rotationYaw - owner.prevRotationYaw) * rotMax;
            f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
            f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
            float f5 = -MathHelper.cos(-f1 * 0.017453292F);
            float f6 = MathHelper.sin(-f1 * 0.017453292F);
            return new Vec3d((double)(f4 * f5), (double)f6, (double)(f3 * f5));
        }
    }

    public void faceEntity(Entity viewer, Entity target, float yawStep, float pitchStep)
    {
        double d0 = target.posX - viewer.posX;
        double d1 = target.posZ - viewer.posZ;
        double d2;

        if (target instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)target;
            d2 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (viewer.posY + (double)viewer.getEyeHeight());
        }
        else
        {
            AxisAlignedBB boundingBox = target.getEntityBoundingBox();
            d2 = (boundingBox.minY + boundingBox.maxY) / 2.0D - (viewer.posY + (double)viewer.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f2 = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d2, d3) * 180.0D / Math.PI));


        iniPitch = this.updateRotation(iniPitch, f3, pitchStep);
        iniYaw = this.updateRotation(iniYaw, f2, yawStep);



        /**/

    }

    private float updateRotation(float par1, float par2, float par3)
    {
        float f3 = MathHelper.wrapDegrees(par2 - par1);

        if (f3 > par3)
        {
            f3 = par3;
        }

        if (f3 < -par3)
        {
            f3 = -par3;
        }

        return par1 + f3;
    }

    public void setDriveVector(float fYVecOfset){
        setDriveVector(fYVecOfset,true);
    }

    /**
     * ■初期ベクトルとかを決めてる。
     * ■移動速度設定
     * @param fYVecOfst
     */
    public void setDriveVector(float fYVecOfst,boolean init)
    {
        //■角度 -> ラジアン 変換
        float fYawDtoR = (  iniYaw / 180F) * (float)Math.PI;
        float fPitDtoR = (iniPitch / 180F) * (float)Math.PI;

        //■単位ベクトル
        motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst;
        motionY = -MathHelper.sin(fPitDtoR) * fYVecOfst;
        motionZ =  MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst;

        float f3 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
        rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / Math.PI);
        if(init){
            speed = fYVecOfst;
            prevRotationYaw = rotationYaw;
            prevRotationPitch = rotationPitch;
        }
    }

    public void setInitialPosition(double x, double y, double z,
                                   float yaw, float pitch, float roll)
    {
        this.prevPosX = this.lastTickPosX = x;
        this.prevPosY = this.lastTickPosY = y;
        this.prevPosZ = this.lastTickPosZ = z;

        this.prevRotationYaw   = this.rotationYaw   = MathHelper.wrapDegrees(-yaw);
        this.prevRotationPitch = this.rotationPitch = MathHelper.wrapDegrees(-pitch);
        setRoll(roll);

        setPosition(x, y, z);
    }

    @Override
    public void updateRidden() {

        Entity ridingEntity = this.ridingEntity2;

        if(ridingEntity.isDead){
            this.setDead();
            return;
        }

        posX = ridingEntity.posX + (this.hitX * Math.cos(Math.toRadians(ridingEntity.rotationYaw)) - this.hitZ * Math.sin(Math.toRadians(ridingEntity.rotationYaw)));
        posY = ridingEntity.posY + this.hitY;
        posZ = ridingEntity.posZ + (this.hitX * Math.sin(Math.toRadians(ridingEntity.rotationYaw)) + this.hitZ * Math.cos(Math.toRadians(ridingEntity.rotationYaw)));

        this.prevRotationPitch = rotationPitch;
        this.prevRotationYaw = rotationYaw;
        rotationPitch = ridingEntity.rotationPitch + this.hitPitch;
        rotationYaw = ridingEntity.rotationYaw + this.hitYaw;

        setPosition(posX, posY, posZ);

        setRotation(rotationYaw,rotationPitch);

        //■死亡チェック
        if(ticksExisted >= 200/*getLifeTime()*/) {

            if(!ridingEntity.isDead){
                if(!world.isRemote){
                    float magicDamage = Math.max(1.0f, AttackLevel / 2);
                    ridingEntity.hurtResistantTime = 0;
                    DamageSource ds = new EntityDamageSource("directMagic",this.getThrower()).setDamageBypassesArmor().setMagicDamage();
                    ridingEntity.attackEntityFrom(ds, magicDamage);
                    if(!blade.isEmpty() && ridingEntity instanceof EntityLivingBase){
                        if(thrower != null){
                            StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.BreakPhantomSword);
                            ((ItemSlashBlade)blade.getItem()).hitEntity(blade,(EntityLivingBase)ridingEntity,(EntityLivingBase)thrower);
                        }

                        ReflectionAccessHelper.setVelocity(ridingEntity, 0, 0, 0);
                        ridingEntity.addVelocity(0.0, 0.1D, 0.0);

                        ((EntityLivingBase) ridingEntity).hurtTime = 1;
                    }
                }
            }

            setDead();
        }
    }

    /**
     * 向き初期化
     */
    protected void initRotation(){

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }
    }

    /**
     *
     * @return hitInfo : IEntitySelector (Destructable / Attackable)
     */
    protected RayTraceResult getRayTraceResult(){
        Vec3d Vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d Vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(Vec3d, Vec3d1);
        Vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            IBlockState state = null;
            BlockPos pos = movingobjectposition.getBlockPos();
            if(pos != null)
                state = world.getBlockState(pos);
            if(state != null && state.getCollisionBoundingBox(world, pos) == null)
                movingobjectposition = null;
            else
                Vec3d1 = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y, movingobjectposition.hitVec.z);
        }

        Entity entity = null;

        AxisAlignedBB bb = this.getEntityBoundingBox().offset(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D);
        AxisAlignedBB bb2 = this.getEntityBoundingBox().grow(1.0D, 1.0D, 1.0D);

        Predicate<Entity>[] selectors = new Predicate[]{EntitySelectorDestructable.getInstance(), EntitySelectorAttackable.getInstance()};
        for(Predicate<Entity> selector : selectors){
            List list = this.world.getEntitiesInAABBexcluding(this, bb, selector);
            list.removeAll(alreadyHitEntity);

            if(selector.equals(EntitySelectorAttackable.getInstance()) && getTargetEntityId() != 0){
                Entity target = world.getEntityByID(getTargetEntityId());
                if(target != null){
                    if(target.getEntityBoundingBox().intersects(bb) || target.getEntityBoundingBox().intersects(bb2) )
                        list.add(target);
                }
            }

            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if(entity1 instanceof EntityPhantomSwordExBase)
                    if(((EntityPhantomSwordExBase) entity1).getThrower() == this.getThrower())
                        continue;

                if (entity1.canBeCollidedWith())
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().grow((double) f1, (double) f1, (double) f1);
                    RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(Vec3d1, Vec3d);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = Vec3d1.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new RayTraceResult(entity);
                movingobjectposition.hitInfo = selector;
                break;
            }
        }


        if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

            if (entityplayer.capabilities.disableDamage || (this.getThrower() != null && this.getThrower() instanceof EntityPlayer && !((EntityPlayer)this.getThrower()).canAttackPlayer(entityplayer)))
            {
                movingobjectposition = null;
            }
        }

        return movingobjectposition;
    }

    public void doRotation(){

        if(doTargeting()) return;

        float f2;
        f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }
    }

    public void normalizeRotation(){

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
    }

    protected void destructEntity(Entity target){

        if(this.thrower == null) return;

        StylishRankManager.setNextAttackType(this.thrower, StylishRankManager.AttackTypes.DestructObject);

        boolean isDestruction = true;

        if(target instanceof EntityFireball){
            if((((EntityFireball)target).shootingEntity != null && ((EntityFireball)target).shootingEntity.getEntityId() == this.thrower.getEntityId())){
                isDestruction = false;
            }else if(this.thrower instanceof  EntityLivingBase){
                isDestruction = !target.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.thrower), this.AttackLevel);
            }
        }else if(target instanceof EntityArrow){
            if((((EntityArrow)target).shootingEntity != null && ((EntityArrow)target).shootingEntity.getEntityId() == this.thrower.getEntityId())){
                isDestruction = false;
            }
        }else if(target instanceof EntityThrowable){
            if((((EntityThrowable)target).getThrower() != null && ((EntityThrowable)target).getThrower().getEntityId() == this.thrower.getEntityId())){
                isDestruction = false;
            }
        }

        if(isDestruction && target instanceof IThrowableEntity){
            if((((IThrowableEntity)target).getThrower() != null && ((IThrowableEntity)target).getThrower().getEntityId() == this.thrower.getEntityId())){
                isDestruction = false;
            }
        }

        if(isDestruction){
            ReflectionAccessHelper.setVelocity(target, 0, 0, 0);
            target.setDead();

            for (int var1 = 0; var1 < 10; ++var1)
            {
                Random rand = this.getRand();
                double var2 = rand.nextGaussian() * 0.02D;
                double var4 = rand.nextGaussian() * 0.02D;
                double var6 = rand.nextGaussian() * 0.02D;
                double var8 = 10.0D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL
                        , target.posX + (double)(rand.nextFloat() * target.width * 2.0F) - (double)target.width - var2 * var8
                        , target.posY + (double)(rand.nextFloat() * target.height) - var4 * var8
                        , target.posZ + (double)(rand.nextFloat() * target.width * 2.0F) - (double)target.width - var6 * var8
                        , var2, var4, var6);
            }
        }

        StylishRankManager.doAttack(this.thrower);

        this.setDead();
    }

    protected void attackEntity(Entity target){

        if(this.thrower != null)
            this.thrower.getEntityData().setInteger("LastHitSummonedSwords",this.getEntityId());

        mountEntity(target);

        if(!this.world.isRemote){
            float magicDamage = Math.max(1.0f, AttackLevel);
            target.hurtResistantTime = 0;
            DamageSource ds = new EntityDamageSource("directMagic",this.getThrower()).setDamageBypassesArmor().setMagicDamage();
            target.attackEntityFrom(ds, magicDamage);

            if(!blade.isEmpty() && target instanceof EntityLivingBase && thrower != null && thrower instanceof EntityLivingBase){
                StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.PhantomSword);
                ((ItemSlashBlade)blade.getItem()).hitEntity(blade,(EntityLivingBase)target,(EntityLivingBase)thrower);

                ReflectionAccessHelper.setVelocity(target, 0, 0, 0);
                target.addVelocity(0.0, 0.1D, 0.0);

                ((EntityLivingBase) target).hurtTime = 1;

                ((ItemSlashBlade)blade.getItem()).setDaunting(((EntityLivingBase) target));
            }
        }
    }

    protected void blastAttackEntity(Entity target){
        if(!this.world.isRemote){
            float magicDamage = 1;
            target.hurtResistantTime = 0;
            DamageSource ds = new EntityDamageSource("directMagic",this.getThrower()).setDamageBypassesArmor().setMagicDamage();
            target.attackEntityFrom(ds, magicDamage);

            if(!blade.isEmpty() && target instanceof EntityLivingBase && thrower != null && thrower instanceof EntityLivingBase){
                StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.PhantomSword);
                ((ItemSlashBlade)blade.getItem()).hitEntity(blade,(EntityLivingBase)target,(EntityLivingBase)thrower);

                ReflectionAccessHelper.setVelocity(target, 0, 0, 0);
                target.addVelocity(0.0, 0.1D, 0.0);

                ((EntityLivingBase) target).hurtTime = 1;

                ((ItemSlashBlade)blade.getItem()).setDaunting(((EntityLivingBase) target));
            }
        }
    }

    protected boolean onImpact(RayTraceResult mop)
    {

        boolean result = true;

        if (mop.entityHit != null){
            Entity target = mop.entityHit;

            if(mop.hitInfo.equals(EntitySelectorAttackable.getInstance())){

                attackEntity(target);

            }else{ //(mop.hitInfo.equals(ItemSlashBlade.getInstance)){

                destructEntity(target);
            }
        }else{


            if(!world.getCollisionBoxes(this,this.getEntityBoundingBox()).isEmpty())
            {
                if(this.getThrower() != null && this.getThrower() instanceof EntityPlayer)
                    ((EntityPlayer)this.getThrower()).onCriticalHit(this);
                //this.setDead();
                result = false;
            }
        }

        return result;
    }

    public void spawnParticle(){
        if (this.isInWater())
        {
            float trailLength;
            for (int l = 0; l < 4; ++l)
            {
                trailLength = 0.25F;
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE
                        , this.posX - this.motionX * (double)trailLength
                        , this.posY - this.motionY * (double)trailLength
                        , this.posZ - this.motionZ * (double)trailLength
                        , this.motionX, this.motionY, this.motionZ);
            }
        }
    }

    public void calculateSpeed(){
        float speedReductionFactor = 1.10F;

        if (this.isInWater())
            speedReductionFactor = 1.0F;

        this.motionX *= (double)speedReductionFactor;
        this.motionY *= (double)speedReductionFactor;
        this.motionZ *= (double)speedReductionFactor;
        //this.motionY -= (double)fallingFactor;

    }

    //■毎回呼ばれる。移動処理とか当り判定とかもろもろ。
    @Override
    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();

        if(this.ridingEntity2 != null){
            updateRidden();
        }else{

            if (this.ticksExisted >= getLifeTime())
            {
                this.setDead();
            }

            initRotation();

            RayTraceResult movingobjectposition = getRayTraceResult();

            if (movingobjectposition != null)
            {
                if(onImpact(movingobjectposition))
                    return;
            }

            calculateSpeed();

            doRotation();

            if(getInterval() < this.ticksExisted)
                move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            normalizeRotation();

            spawnParticle();

        }
    }

    @Override
    public void setDead() {
        if(this.thrower != null && this.thrower instanceof EntityPlayer)
            ((EntityPlayer)thrower).onCriticalHit(this);
        /*
        if(!this.world.isRemote)
            System.out.println("dead" + this.ticksExisted);
            */

        this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 0.25F, 1.6F);

        AxisAlignedBB bb = this.getEntityBoundingBox().grow(1.0D, 1.0D, 1.0D);
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, bb, EntitySelectorAttackable.getInstance());
        list.removeAll(alreadyHitEntity);
        for(Entity target : list){
            if(blade.isEmpty()) break;
            if(target == null) continue;
            blastAttackEntity(target);
        }

        super.setDead();
    }

    /**
     * ■Random
     * @return
     */
    public Random getRand()
    {
        return this.rand;
    }

    /**
     * ■Checks if the offset position from the entity's current position is inside of liquid. Args: x, y, z
     * Liquid = 流体
     */
    @Override
    public boolean isOffsetPositionInLiquid(double par1, double par3, double par5)
    {
        //AxisAlignedBB axisalignedbb = this.boundingBox.getOffsetBoundingBox(par1, par3, par5);
        //List list = this.world.getCollidingBoundingBoxes(this, axisalignedbb);
        //return !list.isEmpty() ? false : !this.world.isAnyLiquid(axisalignedbb);
        return false;
    }

    /**
     * ■Tries to moves the entity by the passed in displacement. Args: x, y, z
     */
    @Override
    public void move(MoverType moverType, double x, double y, double z) {
        super.move(moverType, x,y,z);
    }


    /**
     * ■Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    @Override
    protected void dealFireDamage(int par1) {}

    /**
     * ■Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    @Override
    public boolean handleWaterMovement()
    {
        return false;
    }

    /**
     * ■Checks if the current block the entity is within of the specified material type
     */
    @Override
    public boolean isInsideOfMaterial(Material par1Material)
    {
        return false;
    }

    /**
     * ■Whether or not the current entity is in lava
     */
    @Override
    public boolean isInLava() {
        return false;
    }

    /**
     * ■環境光による暗さの描画（？）
     *    EntityXPOrbのぱくり
     */
    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender()
    {
        float f1 = 0.5F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * ■Gets how bright this entity is.
     *    EntityPortalFXのぱくり
     */
    @Override
    public float getBrightness()
    {
        float f1 = super.getBrightness();
        float f2 = 0.9F;
        f2 = f2 * f2 * f2 * f2;
        return f1 * (1.0F - f2) + f2;
        //return super.getBrightness();
    }

    /**
     * ■NBTの読込
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

    /**
     * ■NBTの書出
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

    double hitX;
    double hitY;
    double hitZ;
    float hitYaw;
    float hitPitch;


    public Entity ridingEntity2 = null;

    public Entity getRidingEntity(){
        return this.ridingEntity2;
    }

    /**
     * ■Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
     */
    public void mountEntity(Entity par1Entity) {
        if(par1Entity != null){
            this.hitYaw = this.rotationYaw - par1Entity.rotationYaw;
            this.hitPitch = this.rotationPitch - par1Entity.rotationPitch;
            this.hitX = this.lastTickPosX - par1Entity.posX;
            this.hitY = this.lastTickPosY - par1Entity.posY;
            this.hitZ = this.lastTickPosZ - par1Entity.posZ;
            this.ridingEntity2 = par1Entity;

            this.ticksExisted = 0;
        }
    }

    /**
     * ■Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {}

    @Override
    public void setPortal(BlockPos p_181015_1_) {
    }

    /**
     * ■Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    @Override
    public boolean isBurning()
    {
        return false;
    }

    @Override
    public boolean shouldRenderInPass(int pass)
    {
        return pass == 1;
    }

    /**
     * ■Sets the Entity inside a web block.
     */
    @Override
    public void setInWeb() {}


    //IProjectile
    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {

    }

    //IThrowableEntity
    @Override
    public Entity getThrower() {
        if(this.thrower == null){
            int id = getThrowerEntityId();
            if(id != 0){
                this.thrower = this.getEntityWorld().getEntityByID(id);
            }
        }

        return this.thrower;
    }

    @Override
    public void setThrower(Entity entity) {
        if(entity != null)
            setThrowerEntityId(entity.getEntityId());
        this.thrower = entity;
    }
}