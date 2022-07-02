package net.pinger.disguise;

import com.google.gson.JsonObject;
import net.pinger.disguise.context.PropertyContext;
import net.pinger.disguise.item.ItemBuilder;
import net.pinger.disguise.item.XMaterial;
import net.pinger.disguise.skull.SkullManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;

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

    private final ItemStack skull = new ItemBuilder(XMaterial.PLAYER_HEAD).build();

    public Skin(String value, String signature) {
        this.value = value;
        this.signature = signature;

        // Update the meta
        SkullMeta meta = (SkullMeta) this.skull.getItemMeta();
        SkullManager.mutateItemMeta(meta, this);
        this.skull.setItemMeta(meta);
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

    /**
     * Returns a json representation of this object.
     *
     * @return the json representation
     */

    public JsonObject toJsonObject() {
        // Create a new json object
        JsonObject object = new JsonObject();


        // Fill in the values
        JsonObject properties = new JsonObject();
        properties.addProperty("value", this.getValue());
        properties.addProperty("signature", this.getSignature());

        // Add the given properties
        object.add("properties", properties);

        // Return the object
        return object;
    }

}
