package noki.bouyomichat.bc;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import noki.bouyomichat.BouyomiChatConf;
import noki.bouyomichat.ModInfo;


/**********
 * @class BouyomiEvent
 *
 * @description クライアントでチャットを受けた時のイベントです。並びにアップデート通知のイベントも。
 */
public class BouyomiEvent {

	//******************************//
	// define member variables.
	//******************************//
	private boolean notified = false;
	
	
	//******************************//
	// define member methods.
	//******************************//
	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent event) {
		
		//**コンフィグによる読み上げ可否。
		if((!BouyomiChatConf.currentReadswitchFlag && !BouyomiChatConf.currentReadSwitch)
				|| (BouyomiChatConf.currentReadswitchFlag && !BouyomiChatConf.currentReadSwitch)) {
			return;
		}
		
		if(event.getMessage() == null) {
			return;
		}

		String message = event.getMessage().getUnformattedComponentText();
		
		//**configによるチャットメッセージへの内部操作。
		//**原則生文送信で棒読みちゃんの辞書で対応してもらうことを想定し、
		//**内部での文字列操作はごくシンプルに留める。
		
		//コンフィグにより、modからのチャットを読ませない。
		if(BouyomiChatConf.readBouyomi.get() == false) {
			Pattern pattern = Pattern.compile("^\\[BouyomiChat\\]");
			Matcher matcher = pattern.matcher(message);
			if(matcher.find()) {
				return;
			}
		}
		
		//コンフィグにより、名前を読ませない。
		if(BouyomiChatConf.readName.get() ==false) {
			Pattern pattern = Pattern.compile("<[a-zA-Z0-9_]{2,16}>\\s|\\s<[a-zA-Z0-9_]{2,16}>$");
			Matcher matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
			pattern = Pattern.compile("<[a-zA-Z0-9_]{2,16}>");
			matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
		}
		
		//コンフィグにより、前置詞を読ませない。
		if(BouyomiChatConf.readPrefix.get() == false) {
			Pattern pattern = Pattern.compile("\\[\\S+\\]\\s|\\s\\[\\S+\\]$");
			Matcher matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
			pattern = Pattern.compile("\\[\\S+\\]");
			matcher = pattern.matcher(message);
			if(matcher.find()) {
				message = matcher.replaceFirst("");
			}
		}
		
//		message = message.replaceAll("\\u00a7.", "");//必要なさそう。様子見。
		
		BouyomiGate.instance.stackQueue(EBouyomiPostType.TALK, message);

	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		
		if(this.notified) {
			return;
		}
		
		if(!event.getWorld().isRemote) {
			return;
		}
		
		if(event.getEntity() == null) {
			return;
		}
		
		if(!(event.getEntity() instanceof EntityPlayer)) {
			return;
		}
		
		UUID targetID = ((EntityPlayer)event.getEntity()).getGameProfile().getId();
		UUID playerID = Minecraft.getInstance().player.getGameProfile().getId();
		if(!targetID.equals(playerID)) {
			return;
		}
		
		VersionChecker.CheckResult result = VersionChecker.getResult(ModList.get().getModFileById(ModInfo.ID).getMods().get(0));
		if(result == null) {
			return;
		}
		
		if(result.status == VersionChecker.Status.UP_TO_DATE) {
			return;
		}
		
		if(result.target == null) {
			return;
		}
		
		event.getEntity().sendMessage(
				new TextComponentTranslation(ModInfo.ID.toLowerCase()+".version.notify", result.target.toString()));
		this.notified = true;
					
	}
	
}
