package noki.bouyomichat;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;


public class BouyomiChatConf {

    private static Builder builder;
    private static ForgeConfigSpec configSpec;

    public static final String charsetName = "UTF-8";
    public static final String confLocale = "bouyomichat.config.";

    public static ConfigValue<String> host;
    public static final String hostName = "host";
    public static IntValue port;
    public static final String portName = "port";

    public static BooleanValue readName;
    public static final String readNameName = "readName";
    public static BooleanValue readPrefix;
    public static final String readPrefixName = "readPrefix";
    public static BooleanValue readBouyomi;
    public static final String readBouyomiName = "readBouyomi";
    public static BooleanValue readSwitch;
    public static final String readSwitchName = "readSwitch";

    public static boolean currentReadswitchFlag = false;
    public static boolean currentReadSwitch = true;

    public static IntValue speed;
    public static final String speedName = "speed";
    public static IntValue tone;
    public static final String toneName = "tone";
    public static IntValue volume;
    public static final String volumeName = "volume";
    public static IntValue voice;
    public static final String voiceName = "voice";

    public static ConfigValue<String> pathBouyomi;
    public static final String pathBouyomiName = "pathBouyomi";
    public static BooleanValue autoExecBouyomi;
    public static final String autoExecBouyomiName = "autoExecBouyomi";


    public static void loadConfig() {

        builder = new Builder();


        builder.push("Connection");
        host = builder
                .comment("Enter your host server name. [default: localhost]")
//                .translation(confLocale+hostName)
                .define(hostName, "localhost");
        port = builder.comment("Enter port id used for BouyomiChan communication. [default: 50001]")
//                .translation(confLocale+portName)
                .defineInRange(portName, 50001, 1024, 65535);
        builder.pop();


        builder.push("Enable or Disable Settings");
        readName = builder.comment("If true, read out a player name. [default: true]")
//                .translation(confLocale+readNameName)
                .define(readNameName, true);
        readPrefix = builder.comment("If true, read out a bracket part like [~~~]. [default: true]")
//                .translation(confLocale+readPrefixName)
                .define(readPrefixName, true);
        readBouyomi = builder.comment("If true, read out a message shown when you use BouyomiChat's commands. [default: false]")
//                .translation(confLocale+readBouyomiName)
                .define(readBouyomiName, false);
        readSwitch = builder.comment("You can set the initial state of whether reading out chats or not. [default: true]")
//                .translation(confLocale+readSwitchName)
                .define(readSwitchName, true);
        builder.pop();


        builder.push("BouyomiChan Parameter");
        speed = builder.comment("Speed of reading out. [default: -1]")
//                .translation(confLocale+speedName)
                .defineInRange(speedName, -1, -1, 300);
        tone = builder.comment("Tone of reading out. [default: -1]")
//                .translation(confLocale+toneName)
                .defineInRange(toneName, -1, -1, 200);
        volume = builder.comment("Volume of reading out. [default: -1]")
//                .translation(confLocale+volumeName)
                .defineInRange(volumeName, -1, -1, 100);
        voice = builder.comment("Type of voice. [default: 0]")
//                .translation(confLocale+voiceName)
                .defineInRange(voiceName, 0, 0, 8);
        builder.pop();


        builder.push("Auto-Execute BouyomiChan");
        pathBouyomi = builder.comment("Enter path to your BouyomiChan.exe. [default: ]")
//                .translation(confLocale+pathBouyomiName)
                .define(pathBouyomiName, "");
        //.replaceAll("\\\\", "\\\\\\\\");
        autoExecBouyomi = builder.comment("You can set the initial state of whether reading out chats or not. [default: true]")
//                .translation(confLocale+autoExecBouyomiName)
                .define(autoExecBouyomiName, true);
        builder.pop();


        configSpec = builder.build();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, configSpec);

    }



}