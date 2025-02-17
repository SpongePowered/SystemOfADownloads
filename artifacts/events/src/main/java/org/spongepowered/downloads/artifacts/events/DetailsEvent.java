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

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface DetailsEvent extends EventMarker {

    ArtifactCoordinates coordinates();

    default String partitionKey() {
        return this.coordinates().asMavenString();
    }

    default String topic() {
        return "ArtifactsArtifactDetailsUpserted";
    }

    @JsonDeserialize
    @JsonTypeName("registered")
    record ArtifactRegistered(
        ArtifactCoordinates coordinates
    ) implements DetailsEvent {
        @JsonCreator
        public ArtifactRegistered {
        }
    }

    @JsonDeserialize
    @JsonTypeName("details")
    record ArtifactDetailsUpdated(
        ArtifactCoordinates coordinates,
        String displayName
    ) implements DetailsEvent {

        @JsonCreator
        public ArtifactDetailsUpdated {
        }
    }

    @JsonDeserialize
    @JsonTypeName("issues")
    record ArtifactIssuesUpdated(
        ArtifactCoordinates coordinates,
        String url
    ) implements DetailsEvent {

        @JsonCreator
        public ArtifactIssuesUpdated {
        }
    }

    @JsonDeserialize
    @JsonTypeName("git-repo")
    record ArtifactGitRepositoryUpdated(
        ArtifactCoordinates coordinates,
        String gitRepo
    ) implements DetailsEvent {

        @JsonCreator
        public ArtifactGitRepositoryUpdated {
        }
    }

    @JsonDeserialize
    @JsonTypeName("website")
    record ArtifactWebsiteUpdated(
        ArtifactCoordinates coordinates,
        String url
    ) implements DetailsEvent {

        @JsonCreator
        public ArtifactWebsiteUpdated {
        }
    }


}
