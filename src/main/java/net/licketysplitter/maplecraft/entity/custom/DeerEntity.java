package net.licketysplitter.maplecraft.entity.custom;

import net.licketysplitter.maplecraft.datagen.loot.ModBuiltInLootTables;
import net.licketysplitter.maplecraft.entity.ModEntities;
import net.licketysplitter.maplecraft.entity.client.DeerVariant;
import net.licketysplitter.maplecraft.entity.client.animation.DeerAntlers;
import net.licketysplitter.maplecraft.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class DeerEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> POINTS =
            SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;

    private static final EntityDataAccessor<Boolean> RUBBING =
            SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState rubAnimationState = new AnimationState();
    public int rubAnimationTimeout = 0;

    private static final EntityDataAccessor<Boolean> GRAZING =
            SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState grazeAnimationState = new AnimationState();
    public int grazeAnimationTimeout = 0;
    public final int GRAZE_INTERVAL = 2500;
    public int grazeCooldown = GRAZE_INTERVAL;

    public final int ANTLER_GROWTH_TIME = 24000;
    public int antlerGrowthCooldown = ANTLER_GROWTH_TIME;

    public static final double SPRINT_SPEED_MULTIPLIER = 1.75;
    public static final double APPROACH_SPEED_MULTIPLIER = 0.5;
    public static final float PLAYER_APPROACH_DISTANCE = 32F;
    public static final float MOVEMENT_SPEED = 0.2F;

    private static final EntityDataAccessor<Boolean> DATA_TRUSTING =
            SynchedEntityData.defineId(DeerEntity.class, EntityDataSerializers.BOOLEAN);
    @Nullable
    private DeerEntity.DeerAvoidEntityGoal<Player> deerAvoidPlayersGoal;
    @Nullable
    private DeerEntity.DeerTemptGoal temptGoal;

    public DeerEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.reassessTrustingGoals();
    }


    protected void registerGoals(){
        this.temptGoal = new DeerEntity.DeerTemptGoal(this, 0.6, (p_335521_) ->
                p_335521_.is(ModItems.SUGAR_GLASS_SHARD.get()), true);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new DeerPanicGoal(this));
        this.goalSelector.addGoal(2, new DeerBreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, this.temptGoal);
        /*Avoid Player Goal Priority 4*/
        this.goalSelector.addGoal(4, new DeerFollowParentGoal(this, SPRINT_SPEED_MULTIPLIER));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new DeerRubGoal(this, 1.0D, 12, 1));
        this.goalSelector.addGoal(1, new DeerGrazeGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.JUMP_STRENGTH, 2)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER, 0.5F);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.SUGAR_GLASS_SHARD.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.DEER.get().create(serverLevel);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isRubbing()){
            if(rubAnimationTimeout <= 0) {
                rubAnimationTimeout = 80;
                rubAnimationState.start(this.tickCount);
            }else {
                --this.rubAnimationTimeout;
            }
        }
        if(!this.isRubbing()) {
            if(rubAnimationTimeout <= 0) {
                rubAnimationState.stop();
            }
            else {
                --this.rubAnimationTimeout;
            }
        }

        if(this.isGrazing()){
            if(grazeAnimationTimeout <= 0){
                grazeAnimationTimeout = 160;
                grazeAnimationState.start(this.tickCount);
            } else {
                --this.grazeAnimationTimeout;
            }
        }
        if(!this.isGrazing()){
            if(grazeAnimationTimeout <= 0){
                grazeAnimationState.stop();
            } else {
                --this.grazeAnimationTimeout;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
            this.growAntlers();
        }
    }

    /* VARIANTS */

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(VARIANT, 0);
        pBuilder.define(POINTS, 0);
        pBuilder.define(RUBBING, false);
        pBuilder.define(GRAZING, false);
        pBuilder.define(DATA_TRUSTING, false);
    }

    private int getTypeVariant(){
        return this.entityData.get(VARIANT);
    }

    private int getTypePoints(){
        return this.entityData.get(POINTS);
    }

    public DeerVariant getVariant() {
        return DeerVariant.byID(this.getTypeVariant() & 255);
    }

    public DeerAntlers getPoints(){
        return DeerAntlers.byID(this.getTypePoints() & 255);
    }

    private void setVariant(DeerVariant variant){
        this.entityData.set(VARIANT, variant.getID() & 255);
    }

    public void setPoints(DeerAntlers points){
        this.entityData.set(POINTS, points.getID() & 255);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(VARIANT, pCompound.getInt("Variant"));
        this.entityData.set(POINTS, pCompound.getInt("Points"));
        this.setTrusting(pCompound.getBoolean("Trusting"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
        pCompound.putInt("Points", this.getTypePoints());
        pCompound.putBoolean("Trusting", this.isTrusting());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType,
                                        @Nullable SpawnGroupData pSpawnGroupData) {

        DeerVariant variant = Util.getRandom(DeerVariant.values(), this.random);
        this.setVariant(variant);
        if (variant == DeerVariant.BUCK){
            DeerAntlers points = Util.getRandom(DeerAntlers.values(), this.random);
            this.setPoints(points);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    /* BUCK RUB */

    public void setRubbing(boolean rubbing){
        this.entityData.set(RUBBING, rubbing);
    }

    public boolean isRubbing(){
        return this.entityData.get(RUBBING);
    }

    public void growAntlers(){
        if(this.getVariant() == DeerVariant.BUCK && this.getPoints() != DeerAntlers.EIGHT && !this.isBaby()) {
            if (antlerGrowthCooldown == 0) {
                if ((int) (Math.random() * 12000) == 1) {
                    this.setPoints(DeerAntlers.byID(this.getPoints().getID() + 1));
                    antlerGrowthCooldown = ANTLER_GROWTH_TIME;
                }
            }
            else {
                antlerGrowthCooldown--;
            }
        }
    }

    public void resetAntlersToZero(){
        this.setPoints(DeerAntlers.ZERO);
        antlerGrowthCooldown = ANTLER_GROWTH_TIME;
    }

    public boolean canRub() {
        return (this.getVariant() == DeerVariant.BUCK && this.getPoints() != DeerAntlers.ZERO);
    }

    public ResourceKey<LootTable> getDefaultLootTable() {
        ResourceKey resourceKey;
        if(this.getVariant() == DeerVariant.DOE) {
            resourceKey = ModBuiltInLootTables.DOE;
        } else {
            switch (this.getPoints()) {
                case ZERO -> resourceKey = ModBuiltInLootTables.BUCK;
                case TWO -> resourceKey = ModBuiltInLootTables.BUCK2;
                case FOUR -> resourceKey = ModBuiltInLootTables.BUCK4;
                case SIX -> resourceKey = ModBuiltInLootTables.BUCK6;
                case EIGHT -> resourceKey = ModBuiltInLootTables.BUCK8;
                default -> throw new MatchException((String) null, (Throwable) null);
            }
        }
        return resourceKey;
    }

    boolean isTrusting() {
        return (Boolean)this.entityData.get(DATA_TRUSTING);
    }

    private void setTrusting(boolean pTrusting) {
        this.entityData.set(DATA_TRUSTING, pTrusting);
        this.reassessTrustingGoals();
    }

    protected void reassessTrustingGoals() {
        if (this.deerAvoidPlayersGoal == null) {
            this.deerAvoidPlayersGoal = new DeerEntity.DeerAvoidEntityGoal(this, Player.class);
        }

        this.goalSelector.removeGoal(this.deerAvoidPlayersGoal);
        if (!this.isTrusting()) {
            this.goalSelector.addGoal(4, this.deerAvoidPlayersGoal);
        }
    }

    /* GRAZING */

    public void setGrazing(boolean grazing){
        this.entityData.set(GRAZING, grazing);
    }

    public boolean isGrazing(){
        return this.entityData.get(GRAZING);
    }

    /* GOALS */


    static class DeerTemptGoal extends TemptGoal{
        private static DeerEntity deer;

        public DeerTemptGoal(DeerEntity deerEntity, double pSpeedModifier, Predicate<ItemStack> pItems, boolean pCanScare) {
            super(deerEntity, pSpeedModifier, pItems, pCanScare);
            deer = deerEntity;
        }

        protected boolean canScare() {
            return super.canScare() && !deer.isTrusting();
        }
    }

    static class DeerAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final DeerEntity deer;

        public DeerAvoidEntityGoal(DeerEntity deerEntity, Class<T> pEntityClassToAvoid) {
            super(deerEntity, pEntityClassToAvoid, PLAYER_APPROACH_DISTANCE, APPROACH_SPEED_MULTIPLIER, SPRINT_SPEED_MULTIPLIER, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
            this.deer = deerEntity;
        }

        public boolean canUse() {
            return !this.deer.isTrusting() && super.canUse();
        }

        public boolean canContinueToUse() {
            return !this.deer.isTrusting() && super.canContinueToUse();
        }

        @Override
        public void tick() {
            if (this.mob.distanceToSqr(this.toAvoid) < 49.0) {
                this.mob.getNavigation().setSpeedModifier(SPRINT_SPEED_MULTIPLIER);
                deer.setSprinting(true);
            } else {
                this.mob.getNavigation().setSpeedModifier(APPROACH_SPEED_MULTIPLIER);
                deer.setSprinting(false);
            }
        }
    }

    static class DeerBreedGoal extends BreedGoal{
        private static DeerEntity deer;

        public DeerBreedGoal(DeerEntity deerEntity, double pSpeedModifier) {
            super(deerEntity, pSpeedModifier);
            deer = deerEntity;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (((DeerEntity)animal).getVariant() != ((DeerEntity)partner).getVariant());
        }
    }

    static class DeerRubGoal extends MoveToBlockGoal {
        private final DeerEntity entity;
        private static int dropDelay = 70;

        public DeerRubGoal(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            entity = ((DeerEntity) pMob);
        }

        @Override
        protected @NotNull BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        public double acceptedDistance() {
            return 2.0;
        }

        @Override
        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            BlockState blockState = levelReader.getBlockState(blockPos);
            return blockState.is(BlockTags.LOGS_THAT_BURN);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && entity.canRub() && !entity.isGrazing();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && entity.canRub() && !entity.isGrazing();
        }

        @Override
        public boolean isInterruptable() {
            return true;
        }

        public void tick() {
            if (this.isReachedTarget()) {
                entity.getLookControl().setLookAt(blockPos.getCenter());
                entity.setRubbing(true);
                if(dropDelay == 0 ){
                    rub();
                }
                --dropDelay;
            } else {
                entity.setRubbing(false);
                dropDelay = 70;
            }

            super.tick();
        }

        private void rub() {
            ItemLike antlerDrop;
            switch (entity.getPoints()) {
                case TWO -> antlerDrop = ModItems.TWO_POINT_ANTLER.get();
                case FOUR -> antlerDrop = ModItems.FOUR_POINT_ANTLER.get();
                case SIX -> antlerDrop = ModItems.SIX_POINT_ANTLER.get();
                case EIGHT -> antlerDrop = ModItems.EIGHT_POINT_ANTLER.get();
                default -> throw new MatchException((String) null, (Throwable) null);
            }
            Block.popResource(entity.level(), this.blockPos, new ItemStack(antlerDrop, 1));
            entity.resetAntlersToZero();
        }

        @Override
        public void start() {
            if(entity.canRub()) {
                dropDelay = 70;
                super.start();
            }
        }

        @Override
        public void stop() {
            entity.setRubbing(false);
            super.stop();
        }
    }

    static class DeerPanicGoal extends PanicGoal{
        private static DeerEntity deer;

        public DeerPanicGoal(DeerEntity deerEntity) {
            super(deerEntity, SPRINT_SPEED_MULTIPLIER);
            deer = deerEntity;
        }

        @Override
        public void start() {
            deer.setSprinting(true);
            super.start();
        }

        @Override
        public void stop() {
            deer.setSprinting(false);
            super.stop();
        }
    }

    static class DeerFollowParentGoal extends FollowParentGoal{
        private static DeerEntity deer;

        public DeerFollowParentGoal(Animal pAnimal, double pSpeedModifier) {
            super(pAnimal, pSpeedModifier);
            deer = (DeerEntity) pAnimal;
        }

        @Override
        public void start() {
            deer.setSprinting(true);
            super.start();
        }

        @Override
        public void stop() {
            deer.setSprinting(false);
            super.stop();
        }
    }

    static class DeerGrazeGoal extends Goal{
        private static DeerEntity deer;

        public DeerGrazeGoal(DeerEntity deerEntity){
            deer = deerEntity;
        }

        @Override
        public boolean canUse() {
            if(deer.grazeCooldown == 0) {
                deer.grazeCooldown = deer.GRAZE_INTERVAL;
                deer.setGrazing(!deer.isRubbing());
                return !deer.isRubbing();
            }
            deer.setGrazing(false);
            --deer.grazeCooldown;
            return false;

        }
    }

}
