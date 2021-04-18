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
package org.spongepowered.downloads.artifact.collection;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.spongepowered.downloads.artifact.api.ArtifactCollection;
import org.spongepowered.downloads.artifact.api.MavenCoordinates;

import java.util.Objects;
import java.util.StringJoiner;

public class ACState {

    public final MavenCoordinates coordinates;
    public final Map<String, ArtifactCollection> collection;

    public static ACState empty() {
        return new ACState(MavenCoordinates.parse("com.example:example:0"), HashMap.empty());
    }

    public ACState(final MavenCoordinates coordinates, final Map<String, ArtifactCollection> collection) {
        this.coordinates = coordinates;
        this.collection = collection;
    }

    public boolean isRegistered() {
        return !"com.example".equals(this.coordinates.groupId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ACState acState = (ACState) o;
        return Objects.equals(coordinates, acState.coordinates) && Objects.equals(
            collection, acState.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, collection);
    }

    @Override
    public String toString() {
        return new StringJoiner(
            ", ", ACState.class.getSimpleName() + "[", "]")
            .add("coordinates=" + coordinates)
            .add("collection=" + collection)
            .toString();
    }
}
