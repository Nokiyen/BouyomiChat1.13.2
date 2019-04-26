package noki.bouyomichat.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import noki.bouyomichat.BouyomiChatConf;
import noki.bouyomichat.BouyomiChatCore;
import noki.bouyomichat.bc.BouyomiGate;
import static noki.bouyomichat.bc.EBouyomiPostType.*;


public class CommandBouyomi {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("bouyomi").requires(commander -> {
//                    BouyomiChatCore.log("on bouyomi command {}.", Environment.get().getDist().toString());
                    try {
                        EntityPlayerMP player = commander.asPlayer();
                    }
                    catch (CommandSyntaxException e) {
                        return false;
                    }
                    return true;
                })
//                Commands.literal("bouyomi")
                        .then(Commands.literal(PAUSE.getName()).executes(context ->  {
                            BouyomiGate.instance.stackQueue(PAUSE);
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+PAUSE.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(RESUME.getName()).executes(context ->  {
                            BouyomiGate.instance.stackQueue(RESUME);
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+RESUME.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(SKIP.getName()).executes(context ->  {
                            BouyomiGate.instance.stackQueue(SKIP);
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+SKIP.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(RELOAD.getName()).executes(context ->  {
                            BouyomiGate.instance.stackQueue(RELOAD);
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+RELOAD.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(CLEAR.getName()).executes(context ->  {
                            BouyomiGate.instance.stackQueue(CLEAR);
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+CLEAR.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(EDU.getName())
                                .then(Commands.literal("from")
                                        .then(Commands.literal("to").executes(context -> {
                                            BouyomiGate.instance.stackQueue(EDU,
                                                    "教育("+context.getArgument("from", String.class)+"="+context.getArgument("to", String.class)+")");
                                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+EDU.getName()));
                                            return 0;
                                        }))
                                )
                        )
                        .then(Commands.literal(FORGET.getName())
                                .then(Commands.literal("target").executes(context -> {
                                    BouyomiGate.instance.stackQueue(FORGET, "忘却("+context.getArgument("target", String.class)+")");
                                    context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+FORGET.getName()));
                                    return 0;
                                }))
                        )
                        .then(Commands.literal(ON.getName()).executes(context ->  {
                            BouyomiChatConf.currentReadswitchFlag = true;
                            BouyomiChatConf.currentReadSwitch = true;
                            BouyomiGate.instance.stackQueue(CLEAR);
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+ON.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(OFF.getName()).executes(context ->  {
                            BouyomiChatConf.currentReadswitchFlag = true;
                            BouyomiChatConf.currentReadSwitch = false;
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+OFF.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(HELP.getName()).executes(context ->  {
                            context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command."+HELP.getName()));
                            return 0;
                        }))
                        .then(Commands.literal(EXEC.getName()).executes(context ->  {
                            boolean res = BouyomiChatCore.executeBouyomiChan();
                            if(res == true) {
                                context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command.exec"));
                            }
                            else {
                                context.getSource().asPlayer().sendMessage(new TextComponentTranslation("bouyomichat.command.execfail"));
                            }
                            return 0;
                        }))
        );
    }

}
