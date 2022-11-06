package cf.devmello.sfa.commands;

import cf.devmello.sfa.SFAClient;
import com.mojang.brigadier.builder.ArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TwitchDisableCommand implements SubCommand {
  public ArgumentBuilder<FabricClientCommandSource, ?> getArgumentBuilder() {
    return ClientCommandManager.literal("disable")
        // The command to be executed if the command "twitch" is entered with the argument "disable"
        // It shuts down the irc bot.
        .executes(ctx -> {
          if (SFAClient.bot == null || !SFAClient.bot.isConnected()) {
            ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.disable.already_disabled"));
            return 1;
          }

          SFAClient.bot.stop();
          ctx.getSource().sendFeedback(Text.translatable("text.twitchchat.command.disable.disabled").formatted(
              Formatting.DARK_GRAY));

          // Return a result. -1 is failure, 0 is a pass and 1 is success.
          return 1;
        });
  }
}
