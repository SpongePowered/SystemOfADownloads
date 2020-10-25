package org.spongepowered.downloads.artifact.event;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import org.spongepowered.downloads.artifact.api.Artifact;

public interface ArtifactEvent extends Jsonable, AggregateEvent<ArtifactEvent> {

    AggregateEventShards<ArtifactEvent> INSTANCE = AggregateEventTag.sharded(ArtifactEvent.class, 10);

    @Override
    default AggregateEventTagger<ArtifactEvent> aggregateTag() {
        return INSTANCE;
    }

    final record ArtifactRegistered(Artifact artifact) implements ArtifactEvent {}
}
