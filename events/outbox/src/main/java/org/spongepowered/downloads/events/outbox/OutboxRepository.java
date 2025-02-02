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
package org.spongepowered.downloads.events.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsPageableRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.spongepowered.downloads.events.EventMarker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Singleton
@R2dbcRepository(dialect = Dialect.POSTGRES)
public abstract class OutboxRepository implements ReactiveStreamsPageableRepository<OutboxEvent, Long> {
    @Inject
    private ObjectMapper om;

    @Override
    public abstract <S extends OutboxEvent> Publisher<S> saveAll(Iterable<S> entities);

    public Mono<Void> saveAll(final List<? extends EventMarker> event) {
        return Flux.fromStream(() -> {
                final var outboxEvents = new ArrayList<OutboxEvent>();
                try {
                    for (final var e : event) {
                        outboxEvents.add(new OutboxEvent(e.topic(), e.partitionKey(), om.writeValueAsString(e)));
                    }
                } catch (final JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return outboxEvents.stream();
            })
            .collectList()
            .flatMap(f -> Flux.from(this.saveAll(f)).then());
    }

}
