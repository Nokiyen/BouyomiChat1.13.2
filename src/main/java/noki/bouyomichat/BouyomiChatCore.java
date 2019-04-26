package noki.bouyomichat;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import noki.bouyomichat.command.CommandBouyomi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import noki.bouyomichat.bc.BouyomiEvent;


/**********
 * @Mod BouyomiChat
 *
 * @author Nokiyen
 * 
 * @description 棒読みちゃんにチャットを読んでもらうModです。
 * 元祖棒読みちゃんMod作者のIwatoRockyさんのコードを参考にしています(しかし、内容はけっこう違いﾏｽ)。
 */
@Mod("bouyomichat")
public class BouyomiChatCore {
	
	//******************************//
	// define member variables.
	//******************************//
	public static Logger logger = LogManager.getLogger();

	
	//******************************//
	// define member methods.
	//******************************//
	public BouyomiChatCore() {

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
		BouyomiChatConf.loadConfig();
		MinecraftForge.EVENT_BUS.register(new BouyomiEvent());
		MinecraftForge.EVENT_BUS.register(this);

	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {

		CommandBouyomi.register(event.getCommandDispatcher());

	}

	//----------
	//Core Event Methods.
	//----------
	private void postInit(final FMLLoadCompleteEvent event) {

		if(BouyomiChatConf.autoExecBouyomi.get()) {
			executeBouyomiChan();
		}

	}
	
	
	//----------
	//Static Method.
	//----------
	public static void log(String message, Object... data) {
		
		logger.info("[BouyomiChat:LOG] "+message, data);
		
	}
	
	public static boolean executeBouyomiChan() {
		
		File file = new File(BouyomiChatConf.pathBouyomi.get());
		boolean flag = true;
		if(file.exists()) {
			try {
				Runtime rt = Runtime.getRuntime();
				rt.exec(BouyomiChatConf.pathBouyomi.get());
			} catch (IOException ex) {
				BouyomiChatCore.log("Can't execute Bouyomi-Chan.");
				flag = false;
			}
		}
		else {
			flag = false;
		}
		
		return flag;
		
	}
	
}
