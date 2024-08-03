package net.pinger.disguise.skin;

import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import net.pinger.disguise.DisguiseAPI;
import net.pinger.disguise.context.PropertyContext;
import net.pinger.disguise.item.ItemBuilder;
import net.pinger.disguise.item.XMaterial;
import net.pinger.disguise.skull.SkullManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Base64;

/**
 * This class represents a type which can be used for changing
 * the player's properties through the GameObject type.
 * <p>
 * Each skin has a Base64 encoded value and signature, which is
 * used for the transformation.
 *
 * @since 1.0
 */

public class Skin {

    private final String value;
    private final String signature;
    private final SkinModel model;
    private final String profile;

    private transient String url;

    private final transient ItemStack skull;

    public Skin(String value, String signature, SkinModel model, String profile) {
        this.value = value;
        this.signature = signature;
        this.model = model;
        this.profile = profile;

        // Update the meta
        this.skull = new ItemBuilder(XMaterial.PLAYER_HEAD).build();
        SkullMeta meta = (SkullMeta) this.skull.getItemMeta();
        SkullManager.mutateItemMeta(meta, this);
        this.skull.setItemMeta(meta);
    }

    public Skin(String value, String signature) {
        this(value, signature, SkinModel.STEVE, null);
    }

    /**
     * This method returns a skin with specified value and signature.
     * <p>
     * Internally, this method also fetches the skin model, use the regular
     * constructor if you don't want to decode skin model and profile name.
     *
     * @param value the value
     * @param signature the signature
     * @return the
     */

    public static Skin of(String value, String signature) {
        String base64 = new String(Base64.getDecoder().decode(value));
        JsonObject base = DisguiseAPI.GSON.fromJson(base64, JsonObject.class);

        // First we will get the SkinModel
        JsonObject skin = base.getAsJsonObject("textures").getAsJsonObject("SKIN");
        SkinModel model = skin.has("metadata") ? SkinModel.ALEX : SkinModel.STEVE;

        // Now fetch the profile name
        String profileName = base.has("profileName") ? base.get("profileName").getAsString() : null;

        // Now we can return the created skin
        return new Skin(value, signature, model, profileName);
    }

    public String getUrl() {
        if (this.url == null) {
            this.url = fetchSkinUrl();
        }

        return this.url;
    }

    private String fetchSkinUrl() {
        String base64 = new String(Base64.getDecoder().decode(value));
        JsonObject base = DisguiseAPI.GSON.fromJson(base64, JsonObject.class);

        // First we will get the SkinModel
        JsonObject skin = base.getAsJsonObject("textures").getAsJsonObject("SKIN");
        return skin.get("url").getAsString();
    }

    /**
     * This method returns the encoded
     * value of this skin.
     *
     * @return the value
     */

    public String getValue() {
        return this.value;
    }

    public String getDecodedValue() {
        return new String(Base64.getDecoder().decode(this.value), StandardCharsets.UTF_8);
    }

    /**
     * This method returns the encoded
     * signature of this skin.
     *
     * @return the signature
     */

    public String getSignature() {
        return this.signature;
    }

    /**
     * This method returns the model of the specified skin.
     *
     * @return the model
     */

    public SkinModel getModel() {
        return this.model;
    }

    @Nullable
    public String getProfile() {
        return this.profile;
    }

    /**
     * Transforms this skin to a skull.
     * <p>
     * This instance is created once the skin has been initialized.
     *
     * @return the skull from this item
     */

    public ItemStack toSkull() {
        return this.skull;
    }

    /**
     * This method returns the property handle
     * for this skin.
     *
     * @return the handle
     */

    @Nonnull
    public Object getHandle() {
        return PropertyContext.createProperty(this);
    }

}
