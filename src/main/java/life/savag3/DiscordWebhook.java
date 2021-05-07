package life.savag3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.*;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.constraints.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Builder
@AllArgsConstructor
public class DiscordWebhook {

    // Cosmetic Details
    @Getter @Setter @NotBlank private Author author;
    @Getter @Setter private String title;
    @Getter @Setter @Size(max = 2048) private String description;
    @Getter @Setter @Size(max = 2048) private String content;
    @Getter @Setter @PositiveOrZero private int color;
    @Getter @Setter @Size(max = 10) private Field[] fields;
    @Getter @Setter private Image thumbnail;
    @Getter @Setter private Image image;
    @Getter @Setter private Footer footer;

    // Sender details
    @Getter @NotBlank private String username;
    private String avatar_url;

    // Delivery Details
    @Getter @NotBlank private String sendTo;

    // Parse Color into rgb index
    public static int parseColor(Color color) {
        int rgb = color.getRed();
        rgb = (rgb << 8) + color.getGreen();
        rgb = (rgb << 8) + color.getBlue();
        return rgb;
    }

    @SneakyThrows
    public boolean send() {
        JsonObject message = new JsonObject();

        message.addProperty("username", this.username);
        message.addProperty("avatar_url", this.avatar_url);
        message.addProperty("content", this.content);

        JsonArray embeds = new JsonArray();
        JsonObject embed = new JsonObject();
        if (this.author != null)
            embed.add("author", this.author.get());

        embed.addProperty("title", this.title);
        embed.addProperty("description", this.description);
        embed.addProperty("color", this.color);

        JsonArray fields = new JsonArray();

        for (Field f : this.fields) {
            fields.add(f.get());
        }

        if (fields.size() != 0)
            embed.add("fields", fields);

        if (this.thumbnail != null)
            embed.add("thumbnail", this.thumbnail.get());
        if (this.image != null)
            embed.add("imagine", this.image.get());
        if (this.footer != null)
            embed.add("footer", this.footer.get());

        embeds.add(embed);
        message.add("embeds", embeds);

        URL url = new URL(this.sendTo);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Supreme-Webhook-Savag3life");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        @Cleanup OutputStream stream = connection.getOutputStream();
        stream.write(message.toString().getBytes(StandardCharsets.UTF_8));
        stream.flush();

        connection.getInputStream().close();
        connection.disconnect();
        return true;
    }

    @AllArgsConstructor
    public static class Field {

        @Size(max = 256) private String name;
        @Size(max = 1024) private String value;
        @Builder.Default private boolean inline = false;

        public JsonElement get() {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", this.name);
            obj.addProperty("value", this.value);
            obj.addProperty("inline", this.inline);

            return obj;
        }
    }

    @AllArgsConstructor
    public static class Image {

        private String url;

        public JsonObject get() {
            JsonObject obj = new JsonObject();
            obj.addProperty("url", this.url);
            return obj;
        }
    }

    @AllArgsConstructor
    public static class Footer {

        @Size(max = 2048) private String text;
        private String icon_url;

        public JsonElement get() {
            JsonObject obj = new JsonObject();
            obj.addProperty("text", this.text);
            obj.addProperty("icon_url", this.icon_url);

            return obj;
        }
    }

    @AllArgsConstructor
    public static class Author {

        @Size(max = 256) private String name;
        private String url;
        private String icon;

        public JsonElement get() {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", this.name);
            obj.addProperty("url", this.url);
            obj.addProperty("icon_url", this.icon);
            return obj;
        }
    }
}
