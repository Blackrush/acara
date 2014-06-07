package com.github.blackrush.acara.dispatch;

import com.github.blackrush.acara.ListenerMetadata;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * {@link com.github.blackrush.acara.dispatch.DispatcherLookup} may give, or not give, a {@link com.github.blackrush.acara.dispatch.Dispatcher}
 * who can take care of a given {@link com.github.blackrush.acara.ListenerMetadata}
 */
@FunctionalInterface
public interface DispatcherLookup {
    /**
     * Lookup for a {@link com.github.blackrush.acara.dispatch.Dispatcher} for a given {@link com.github.blackrush.acara.ListenerMetadata}
     * @param metadata a non-null metadata
     * @return a non-null option
     */
    Optional<Dispatcher> lookup(ListenerMetadata metadata);

    /**
     * Fold two {@link com.github.blackrush.acara.dispatch.DispatcherLookup} into one.
     * @param other a non-null other
     * @return a non-null new {@link com.github.blackrush.acara.dispatch.DispatcherLookup}
     */
    default DispatcherLookup withFallback(DispatcherLookup other) {
        requireNonNull(other, "other");

        return metadata -> {
            Optional<Dispatcher> res = this.lookup(metadata);

            if (res.isPresent()) {
                return res;
            }

            return other.lookup(metadata);
        };
    }
}