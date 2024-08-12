package club.someoneice.crystaldream.mixin;

import club.someoneice.crystaldream.init.ItemRendererHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ItemRenderer.class)
class ItemRendererMixin {
    @Shadow(remap = false) @Final
    private ItemModelShaper itemModelShaper;

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true, remap = false)
    public void getModelHook(ItemStack stack, Level level, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (stack.isEmpty()) return;
        var helper = ItemRendererHelper.ItemRenderList.stream().filter(it -> stack.is(it.item())).findAny();
        if (helper.isEmpty()) return;

        BakedModel bakedmodel = itemModelShaper.getModelManager().getModel(helper.get().itemInHand());
        ClientLevel clientlevel = level instanceof ClientLevel ? (ClientLevel)level : null;
        bakedmodel = bakedmodel.getOverrides().resolve(bakedmodel, stack, clientlevel, entity, seed);
        cir.setReturnValue(bakedmodel == null ? itemModelShaper.getModelManager().getMissingModel() : bakedmodel);
    }

    @Inject(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/neoforged/neoforge/client/ClientHooks;handleCameraTransforms(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemDisplayContext;Z)Lnet/minecraft/client/resources/model/BakedModel;"), remap = false)
    public void rendererHook(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci) {
        ci.cancel();
    }
}