package net.pinger.disguise.annotation;

import net.pinger.disguise.packet.PacketProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This type is an annotation used for marking a {@link PacketProvider} type which provides support
 * for at least one minecraft version.
 *
 * @author Pinger
 * @since 2.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {

    /**
     * This method returns the main version
     * this packet handles.
     *
     * @return the
     */

    String version();

    /**
     * Returns the versions compatible with this
     * PacketHandler version.
     *
     * @return the versions compatible
     */

    String[] compatibility() default "";

}
