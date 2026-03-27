package bottledpepsi.reverseaging;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReverseAging implements ModInitializer {
	public static final String MOD_ID = "reverseaging";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("ReverseAging Initialized");
	}
}