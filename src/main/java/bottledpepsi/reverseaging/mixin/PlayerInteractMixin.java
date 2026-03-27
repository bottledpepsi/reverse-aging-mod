package bottledpepsi.reverseaging.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.animal.equine.Mule;
import net.minecraft.world.entity.animal.equine.TraderLlama;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;


@Mixin(Player.class)
public abstract class PlayerInteractMixin {

    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void goldenDandelionUse(Entity entity, InteractionHand hand, Vec3 hitPos, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = (Player)(Object)this;

        if (player.level().isClientSide()) return;

        ItemStack stack = player.getItemInHand(hand);

        if (!stack.is(Items.GOLDEN_DANDELION)) return;

        if (!(entity instanceof AgeableMob mob)) return;

        if (mob.isBaby()) return;

        if (mob instanceof Villager || mob instanceof WanderingTrader) return;

        if (
                (mob instanceof AbstractHorse horse && (horse.isWearingBodyArmor())) ||
                        (mob instanceof Llama llama && (llama.hasChest())) ||
                        (mob instanceof Mule mule && (mule.hasChest())) ||
                        (mob instanceof TraderLlama traderLlama && (traderLlama.hasChest())) ||
                        mob.hasPassenger(e -> true) ||
                        mob.isSaddled()
        ) {
            return;
        }

        mob.setAge(-24000);

        player.swing(hand);

        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    mob.getX(),
                    mob.getY() + mob.getBbHeight() / 2,
                    mob.getZ(),
                    10,
                    0.3, 0.3, 0.3,
                    0.0
            );
        }

        player.level().playSound(
                null,
                mob.blockPosition(),
                SoundEvents.CHICKEN_EGG,
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}