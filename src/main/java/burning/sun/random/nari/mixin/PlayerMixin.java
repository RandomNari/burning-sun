package burning.sun.random.nari.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {


    @Inject(method = "aiStep", at = @At("TAIL"))
    public void burnUndead(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (isSunBurnTick()) {
            player.igniteForSeconds(8.0F);
        }
    }

    public boolean isSunBurnTick() {
        Player player = (Player) (Object) this;
        if (!player.level().isClientSide() && (Boolean) player.level().environmentAttributes().getValue(EnvironmentAttributes.MONSTERS_BURN, player.position())) {
            float br = player.getLightLevelDependentMagicValue();
            BlockPos roundedPos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
            boolean isInNonBurnableBlock = player.isInWaterOrRain() || player.isInPowderSnow || player.wasInPowderSnow;
            if (br > 0.5F && player.getRandom().nextFloat() * 30.0F < (br - 0.4F) * 2.0F && !isInNonBurnableBlock && player.level().canSeeSky(roundedPos)) {
                return true;
            }
        }

        return false;
    }
}
