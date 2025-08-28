package net.fabricmc.example.mixin;

import btw.util.status.StatusCategory;
import btw.util.status.StatusEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase {




    @Shadow public abstract World getEntityWorld();


    @Shadow public abstract float getAbsorptionAmount();

    @Shadow public abstract void setDead();

    @Unique
    private float cachedHealth = 0f;

    public EntityPlayerMixin(World par1World) {
        super(par1World);
    }

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onUpdate(CallbackInfo ci) {
        if ((Object) this instanceof EntityPlayer) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1.0f);
            if (this.getHealth() > 1.0f){
                this.setHealth(1.0f);
            }
        }


    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void onLivingUpdate(CallbackInfo ci) {
        if (this.worldObj.difficultySetting == 0 && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 * 10 == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
            this.heal(0.0f);

        }
    }

    @Inject(method = "clonePlayer", at = @At("RETURN"))
    private void clonePlayer(EntityPlayer otherPlayer, boolean playerLeavingTheEnd, CallbackInfo ci) {
            if (playerLeavingTheEnd) {
                this.setHealth(otherPlayer.getHealth());
            } else {
                this.setHealth(1.0f);
            }

    }


    @Inject(method = "getStatusForCategory", at = @At("HEAD"))
    private void getAllActiveStatusEffects(StatusCategory category, CallbackInfoReturnable<Optional<StatusEffect>> cir) {
        this.cachedHealth = this.getHealth();
        this.getDataWatcher().updateObject(6, getNormalizedHealth());
    }

    @Inject(method = "getStatusForCategory", at = @At("RETURN"))
    private void getAllActiveStatusEffectsReturn(CallbackInfoReturnable<ArrayList<StatusEffect>> cir) {
        this.getDataWatcher().updateObject(6, this.cachedHealth);
    }


    @Inject(method = "preparePlayerToSpawn", at = @At("HEAD"))
    public void preparePlayerToSpawn(CallbackInfo ci) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1.0f);;
            this.setHealth(1.0f);
        }

    @Inject(method = "damageEntity", at = @At("HEAD"))
    public void damageEntity(CallbackInfo ci)  {
        ChatMessageComponent chatMessageComponent = new ChatMessageComponent();
        chatMessageComponent.setColor(EnumChatFormatting.DARK_RED);
        chatMessageComponent.addText("<Better Than Wolves> Too bad, huh? It was all your fault.");
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(chatMessageComponent);
        World world = this.getEntityWorld();
        world.createExplosion(this,this.posX,this.posY,this.posZ,0.0f,false);
        EntityLightningBolt lightningBolt = new EntityLightningBolt(world,this.posX,this.posY,this.posZ-50);
        world.spawnEntityInWorld(lightningBolt);
        this.setDead();

    }

    private float getNormalizedHealth() {
        return (20F / Math.min(getMaxHealth(), 20F)) * getHealth();
    }

}

