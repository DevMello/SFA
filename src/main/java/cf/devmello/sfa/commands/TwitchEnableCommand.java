package cf.devmello.sfa.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import cf.devmello.sfa.SFAClient;
import cf.devmello.sfa.config.ModConfig;
import cf.devmello.sfa.twitch_integration.Bot;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TwitchEnableCommand implements SubCommand {
  public ArgumentBuilder<FabricClientCommandSource, ?> getArgumentBuilder() {
    return ClientCommandManager.literal("enable")
        // The command to be executed if the command "twitch" is entered with the argument "enable"
        // It starts up the irc bot.
        .executes(ctx -> {
          ModConfig config = ModConfig.getConfig();

          if (SFAClient.bot != null && SFAClient.bot.isConnected()) {
            ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.enable.already_enabled"));
            return 1;
          }

          if (config.getUsername().equals("") || config.getOauthKey().equals("")) {
            ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.enable.set_config"));
            return -1;
          }

          if (config.getChannel().equals("")) {
            ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.enable.select_channel"));
          }

          SFAClient.bot = new Bot(config.getUsername(), config.getOauthKey(), config.getChannel());
          SFAClient.bot.start();
          ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.enable.connecting").formatted(Formatting.DARK_GRAY));
          // Return a result. -1 is failure, 0 is a pass and 1 is success.
          return 1;
        });
  }
}
