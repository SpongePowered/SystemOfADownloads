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
package org.spongepowered.downloads.test.artifacts.server.lib.git;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.extensions.junit5.annotation.TestResourcesScope;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.spongepowered.downloads.artifact.api.query.ArtifactDetails;
import org.spongepowered.downloads.artifacts.server.lib.git.GitResolver;

@MicronautTest(startApplication = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GitResolverTest {

    @Inject GitResolver resolver;

    @Test
    void testResolveInvalidURL() {
        final var either = resolver.validateRepository("not://a/valid/url");
        final var join = either.join();
        Assertions.assertInstanceOf(ArtifactDetails.Response.Error.class, join);
    }

    @Test
    void testResolveValidURL() {
        final var either = resolver.validateRepository("https://github.com/SpongePowered/SpongeAPI.git");
        final var join = either.join();
        Assertions.assertInstanceOf(ArtifactDetails.Response.ValidRepo.class, join);
    }

    @Test
    void testResolveUnsupportedTransport() {
        final var either = resolver.validateRepository("git://github.com/SpongePowered/SpongeAPI.git");
        final var join = either.join();
        Assertions.assertInstanceOf(ArtifactDetails.Response.Error.class, join);
    }
}
