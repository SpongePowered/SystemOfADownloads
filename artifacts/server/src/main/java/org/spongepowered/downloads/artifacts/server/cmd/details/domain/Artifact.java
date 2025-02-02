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
package org.spongepowered.downloads.artifacts.server.cmd.details.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import jakarta.persistence.Column;
import org.spongepowered.downloads.artifact.api.ArtifactCoordinates;
import org.spongepowered.downloads.artifacts.events.ArtifactEvent;
import org.spongepowered.downloads.artifacts.server.cmd.details.DetailsCommand;
import org.spongepowered.downloads.artifacts.server.cmd.group.domain.Group;
import org.spongepowered.downloads.events.DomainObject;
import org.spongepowered.downloads.events.Envelope;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@MappedEntity
public record Artifact(
    @Id
    @GeneratedValue
    Long id,

    @Column(nullable = false)
    String artifactId,

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    @Column(nullable = false,
        name = "group_id")
    Group group,

    @Column(nullable = false,
        name = "displayName")
    String name,

    String description,
    @Column(columnDefinition = "jsonb")
    List<String> gitRepo,
    String website
    ) implements DomainObject<DetailsCommand, ArtifactEvent, Artifact> {

    public ArtifactCoordinates coordinates() {
        return new ArtifactCoordinates(this.group.getGroupId(), this.artifactId);
    }

    @Override
    public Envelope<DetailsCommand, ArtifactEvent, Artifact> update(final DetailsCommand command) {
        return switch (command) {
            case DetailsCommand.RegisterArtifact ignore -> Envelope.empty();
            case DetailsCommand.UpdateDisplayName dn -> {
                if (this.name.equals(dn.displayName())) {
                    yield Envelope.empty();
                }

                yield this.withDisplayName(dn.displayName());
            }
            case DetailsCommand.UpdateIssues ignored -> Envelope.empty();
            case DetailsCommand.UpdateWebsite uw -> {
                if (Objects.equals(this.website, uw.website().toExternalForm())) {
                    yield Envelope.empty();
                }
                yield this.withWebsite(uw.website().toExternalForm());
            }
            case DetailsCommand.UpdateGitRepository gr -> {
                if (this.gitRepo.contains(gr.gitRemote())) {
                    yield Envelope.empty();
                }
                yield this.withGitRepo(gr.gitRemote());
            }
            case DetailsCommand.UpdateGitRepositories grs -> {
                final var newRepos = grs.gitRemote().stream().distinct().toList();
                final var newArtifact = new Artifact(
                    this.id,
                    this.artifactId,
                    this.group,
                    this.name,
                    this.description,
                    newRepos,
                    this.website
                );
                yield Envelope.of(newArtifact, new ArtifactEvent.GitReposReplaced(newArtifact.coordinates(), newRepos));
            }
        };
    }

    Envelope<DetailsCommand, ArtifactEvent, Artifact> withDisplayName(String displayName) {
        final var newArtifact = new Artifact(
            this.id,
            this.artifactId,
            this.group,
            displayName,
            this.description,
            this.gitRepo,
            this.website
        );

        return Envelope.of(newArtifact, new ArtifactEvent.DisplayNameUpdated(newArtifact.coordinates(), displayName));
    }

    Envelope<DetailsCommand, ArtifactEvent, Artifact> withWebsite(String website) {
        final var newArtifact = new Artifact(
            this.id,
            this.artifactId,
            this.group,
            this.name,
            this.description,
            this.gitRepo,
            website
        );
        return Envelope.of(newArtifact, new ArtifactEvent.WebsiteUpdated(newArtifact.coordinates(), website));
    }

    Envelope<DetailsCommand, ArtifactEvent, Artifact> withGitRepo(String gitRepo) {
        final var newRepoList = Stream.concat(this.gitRepo.stream(), Stream.of(gitRepo)).distinct().toList();
        final var newArtifact = new Artifact(
            this.id,
            this.artifactId,
            this.group,
            this.name,
            this.description,
            newRepoList,
            this.website
        );
        return Envelope.of(newArtifact, new ArtifactEvent.GitRepositoryAssociated(newArtifact.coordinates(), gitRepo));
    }


}
