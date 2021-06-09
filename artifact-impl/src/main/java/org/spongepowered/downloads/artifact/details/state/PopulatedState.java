package org.spongepowered.downloads.artifact.details.state;

import org.spongepowered.downloads.artifact.api.ArtifactCoordinates;

public final class PopulatedState implements DetailsState {

    public final ArtifactCoordinates coordinates;
    public final String displayName;
    public final String website;
    public final String gitRepository;
    public final String issues;

    public PopulatedState(
        final ArtifactCoordinates coordinates, final String displayName, final String website,
        final String gitRepository,
        final String issues
    ) {
        this.coordinates = coordinates;
        this.displayName = displayName;
        this.website = website;
        this.gitRepository = gitRepository;
        this.issues = issues;
    }

    @Override
    public ArtifactCoordinates coordinates() {
        return this.coordinates;
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    @Override
    public String website() {
        return this.website;
    }

    @Override
    public String gitRepository() {
        return this.gitRepository;
    }

    @Override
    public String issues() {
        return this.issues;
    }

    public boolean isEmpty() {
        return this.coordinates.artifactId.isBlank() && this.coordinates.groupId.isBlank();
    }
}
