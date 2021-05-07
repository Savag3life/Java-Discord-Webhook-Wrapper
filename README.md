## Discord Webhook Wrapper
This is an every quick and dirty wrapper & builder for sending Discord webhooks. It heavily utilizes Lombok to create a simple builder to build & send the webhook at runtime.

## Requirements:
- [A valid discord webhook URL](https://help.dashe.io/en/articles/2521940-how-to-create-a-discord-webhook-url)
- Java 8+

## Usage
```java
        DiscordWebhook.builder()
                .username("SupremeStrikes") // Required
                .avatar_url("https://cdn.discordapp.com/emojis/706764727165911052.png?v=1")
                .author(new DiscordWebhook.Author("A Faction Has Been Striked!", "https://google.com/", "https://cdn.discordapp.com/emojis/685282855768686616.gif?v=1"))
                .footer(new DiscordWebhook.Footer("SupremeDevelopment", ""))
                .color(DiscordWebhook.parseColor(Color.RED))
                .fields(new DiscordWebhook.Field[] {
                        new DiscordWebhook.Field("Details:", "Faction\nStriker\nTime", true),
                        new DiscordWebhook.Field("Details:", "Example Faction\nSavag3life\n" + Time.simpleTimestamp(), true),
                })
                .sendTo("some-valid-webhook-url") // Required
                .build()
                .send();
```
Send something like this to the `some-valid-webhook-url` URL

![imagine](https://github.com/Savag3life/Java-Discord-Webhook-Wrapper/blob/main/example.png)

