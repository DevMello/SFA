package cf.devmello.sfa.mixin;

import cf.devmello.sfa.SFAClient;
import cf.devmello.sfa.config.ModConfig;
import cf.devmello.sfa.twitch_integration.CalculateMinecraftColor;
import java.util.Date;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatMixin {
	@Inject(at = @At("HEAD"), method = "sendMessage(Ljava/lang/String;Z)Z", cancellable = true)
	private void sendMessage(String text, boolean addToHistory, CallbackInfoReturnable<Boolean> info) {
      ModConfig config = ModConfig.getConfig();


      String prefix = config.getPrefix();

      // Allow users to write /twitch commands (such as disabling and enabling the mod) when their prefix is "".
      if (prefix.equals("") && text.startsWith("/twitch")) {
        return; // Don't cancel the message, return execution to the real method
      }

      // If the message is a twitch message
      if (text.startsWith(prefix)) {
        if (SFAClient.bot != null && SFAClient.bot.isConnected()) {
          String textWithoutPrefix = text.substring(text.indexOf(prefix) + prefix.length());
          SFAClient.bot.sendMessage(textWithoutPrefix); // Send the message to the Twitch IRC Chat

          Date currentTime = new Date();
          String formattedTime = SFAClient.formatDateTwitch(currentTime);

          String username = SFAClient.bot.getUsername();
          Formatting userColor;
          if (SFAClient.bot.isFormattingColorCached(username)) {
            userColor = SFAClient.bot.getFormattingColor(username);
          } else {
            userColor = CalculateMinecraftColor.getDefaultUserColor(username);
            SFAClient.bot.putFormattingColor(username, userColor);
          }

          boolean isMeMessage = textWithoutPrefix.startsWith("/me");

          // Add the message to the Minecraft Chat
          SFAClient.addTwitchMessage(formattedTime, username, isMeMessage ? textWithoutPrefix.substring(4) : textWithoutPrefix, userColor, isMeMessage);
          MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(text);
          info.setReturnValue(true);
        } else {
          SFAClient.addNotification(Text.translatable("text.twitchchat.chat.integration_disabled"));
        }
      }
	}
}
