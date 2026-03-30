package bottledpepsi.reverseaging;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;


@Mod(value = ReverseAging.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ReverseAging.MODID, value = Dist.CLIENT)
public class ReverseAgingClient {

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ReverseAging.LOGGER.info("ReverseAging Initialized");
    }
}
