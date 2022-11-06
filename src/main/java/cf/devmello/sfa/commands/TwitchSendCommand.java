package cf.devmello.sfa.commands;

import cf.devmello.sfa.SFAClient;
import cf.devmello.sfa.config.ModConfig;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class TwitchSendCommand extends TwitchBaseCommand {
    public ArgumentBuilder<FabricClientCommandSource, ?> getArgumentBuilder() {
        return ClientCommandManager.literal("send")
                // The command to be executed if the command "twitch" is entered with the argument "watch"
                // It requires channel_name as an argument.
                // It will switch channels in the config to the channel name provided and
                // if the bot is connected to some channel, it will switch channels on the fly.
                .then(ClientCommandManager.argument("message", StringArgumentType.string())
                        .suggests(new TwitchWatchSuggestionProvider())
                        .executes(ctx -> {
                            String message = StringArgumentType.getString(ctx, "message");

                            //ModConfig.getConfig().setChannel(channelName);
                            // Also switch channels if the bot has been initialized
                            if (SFAClient.bot != null) {
                                ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.send.sent", message));
                                //SFAClient.bot.joinChannel(channelName);
                                SFAClient.bot.sendMessage(message);
                            } else {
                                ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.send.failed", message));
                            }
                            //ModConfig.getConfig().save();
                            return 1;
                        }));
    }
}
