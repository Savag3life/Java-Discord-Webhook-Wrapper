package life.savag3;

import java.awt.*;

public class Example {


    public Example() {
        DiscordWebhook webhook = DiscordWebhook.builder()
                .color(DiscordWebhook.parseColor(Color.RED)) // Set the color
                .author(new DiscordWebhook.Author("Savag3life Bot", "", "")) // Set the "Author" line on the embed message
                .footer(new DiscordWebhook.Footer("SupremeDevelopment | Created By Savag3life", "")) // Set the embed footer
                .content("Example Bot message.....") // This is the message above the embed, its like a normal discord message
                .description("This is in the embed") // This is the main body text of the embed message
                .title("This is an example title") // This is the title of the embedded message
                .thumbnail(new DiscordWebhook.Image("https://cdn.discordapp.com/emojis/685282855768686616.gif?v=1")) // This is the picture on the right side of the embed message
                .fields(new DiscordWebhook.Field[] { // Embedded fields
                        new DiscordWebhook.Field("Example #1", "This is the content of a field, Its also not in-line", false),
                        new DiscordWebhook.Field("Example #1", "This is the first of 2 inline fields", true),
                        new DiscordWebhook.Field("Example #1", "This is the second of 2 inline fields", true)
                })
                .build(); // Called to convert the builder into a sendable DiscordWebhook object.

        webhook.send();
    }

}
