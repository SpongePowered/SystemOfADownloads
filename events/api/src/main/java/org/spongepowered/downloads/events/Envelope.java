/*
 * This file is part of SystemOfADownload, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://spongepowered.org/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.downloads.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Envelope<C, E extends EventMarker, D extends DomainObject<C, E, D>>(
    D updatedObject,
    List<? extends E> events
) {
    public static <C, E extends EventMarker, D extends DomainObject<C, E, D>> Envelope<C, E, D> empty() {
        return new Envelope<>(null, List.of());
    }

    public static <C, E extends EventMarker, D extends DomainObject<C, E, D>> Envelope<C, E, D> of(final D updatedObject, final E event) {
        return new Envelope<>(updatedObject, List.of(event));
    }

    public Envelope(D updatedObject, List<? extends E> events) {
        this.updatedObject = updatedObject;
        this.events = Objects.requireNonNull(events, "events");

    }

    public boolean isEmpty() {
        return this.events == null || this.events.isEmpty();
    }
}
