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
package org.spongepowered.downloads.artifacts.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.spongepowered.downloads.artifact.api.ArtifactCoordinates;
import org.spongepowered.downloads.events.EventMarker;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public sealed interface ArtifactEvent extends EventMarker {

    ArtifactCoordinates coordinates();

    default String partitionKey() {
        return this.coordinates().asMavenString();
    }

    default String topic() {
        return "ArtifactsArtifactUpserted";
    }

    @JsonTypeName("registered")
    @JsonDeserialize
    record ArtifactRegistered(
        ArtifactCoordinates coordinates
    ) implements ArtifactEvent {

        @JsonCreator
        public ArtifactRegistered {
        }
    }

    @JsonTypeName("git-repository")
    @JsonDeserialize
    record GitRepositoryAssociated(
        ArtifactCoordinates coordinates,
        String repository
    ) implements ArtifactEvent {

        @JsonCreator
        public GitRepositoryAssociated {
        }
    }

    @JsonTypeName("git-repositories-replacement")
    @JsonDeserialize
    record GitReposReplaced(
        ArtifactCoordinates coordinates,
        List<String> repository
    ) implements ArtifactEvent {

        @JsonCreator
        public GitReposReplaced {
        }

    }

    @JsonTypeName("website")
    @JsonDeserialize
    record WebsiteUpdated(
        ArtifactCoordinates coordinates,
        String url
    ) implements ArtifactEvent {

        @JsonCreator
        public WebsiteUpdated {
        }
    }

    @JsonTypeName("issues")
    @JsonDeserialize
    record IssuesUpdated(
        ArtifactCoordinates coordinates,
        String url
    ) implements ArtifactEvent {

        @JsonCreator
        public IssuesUpdated {
        }
    }

    @JsonTypeName("displayName")
    @JsonDeserialize
    record DisplayNameUpdated(
        ArtifactCoordinates coordinates,
        String displayName
    ) implements ArtifactEvent {

        @JsonCreator
        public DisplayNameUpdated {
        }
    }
}
