package cf.devmello.sfa.commands;

import cf.devmello.sfa.SFAClient;
import cf.devmello.sfa.config.ModConfig;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

public class TwitchStreamCommand {
    String userDirectory = System.getProperty("user.dir");
    public ArgumentBuilder<FabricClientCommandSource, ?> getArgumentBuilder() {
        return ClientCommandManager.literal("stream")

                .executes(ctx -> {
                    ctx.getSource().sendFeedback(Text.of("Succesfully Started Streaming to Twitch"));
//                    byte[] imageBytes = new byte[0];
//                    try {
//                        imageBytes = IOUtils.toByteArray(new URL("file:///" + userDirectory + "/screenshot.png"));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    String base64 = Base64.getEncoder().encodeToString(imageBytes);
//                    System.out.println(base64);
                    return 1;

                });
    }


}
