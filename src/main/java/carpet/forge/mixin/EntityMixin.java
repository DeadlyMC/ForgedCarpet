package carpet.forge.mixin;

import carpet.forge.helper.BlockRotator;
import carpet.forge.interfaces.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity
{
    @Shadow public float rotationYaw;
    
    @Shadow private int fire;
    
    @Override
    public int getFire()
    {
        return this.fire;
    }
    
    @Override
    public String cm_name()
    {
        return "Other Entity";
    }

    @Inject(method = "getHorizontalFacing", at = @At("HEAD"), cancellable = true)
    private void onGetHorizontalFacing(CallbackInfoReturnable<EnumFacing> cir)
    {
        if (BlockRotator.flippinEligibility((Entity)(Object)this))
        {
            cir.setReturnValue(EnumFacing.byHorizontalIndex(MathHelper.floor((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite());
        }
    }
}