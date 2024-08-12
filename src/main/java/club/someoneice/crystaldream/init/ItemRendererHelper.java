package club.someoneice.crystaldream.init;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public record ItemRendererHelper(
        Item item,
        ModelResourceLocation itemInInventory,
        ModelResourceLocation itemInHand
) {
    public static List<ItemRendererHelper> ItemRenderList = Lists.newArrayList();

    public static ItemRendererHelper createItemRendererHelper(Item item, ResourceLocation itemInInventory, ResourceLocation itemInHand) {
        return new ItemRendererHelper(item, ModelResourceLocation.inventory(itemInInventory), ModelResourceLocation.inventory(itemInHand));
    }
}
