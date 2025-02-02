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
package org.spongepowered.downloads.test.artifacts.server.cmd.group;

import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.spongepowered.downloads.artifacts.server.cmd.group.GroupCommand;
import org.spongepowered.downloads.artifacts.server.cmd.group.GroupService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(environments = "test",
    startApplication = false)
public class GroupServiceTest {
    @Inject
    Validator validator;

    @Inject
    GroupService service;

    @Test
    void testRegisterGroup() {
        final var request = new GroupCommand.RegisterGroup(
            "org.spongepowered", "SpongePowered", "https://spongepowered.org");
        final var response = this.service.registerGroup(request);
        assertNotNull(response);
        assertTrue(validator.validate(request).isEmpty());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void testRegisterGroupWithExistingId() {
        this.service.registerGroup(
            new GroupCommand.RegisterGroup("org.duplicate", "Duplicate", "https://duplicate.com"));
        final var response = this.service.registerGroup(
            new GroupCommand.RegisterGroup("org.duplicate", "Another Duplicate", "https://another-duplicate.com"));
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
    }

    @Test
    void testRegisterGroupWithArtifact() {
        final var request = new GroupCommand.RegisterGroup(
            "org.spongepowered", "SpongePowered", "https://spongepowered.org");
        final var response = this.service.registerGroup(request);
        assertNotNull(response);
        assertTrue(validator.validate(request).isEmpty());
        assertEquals(HttpStatus.OK, response.getStatus());
        final var artifactRegistration = new GroupCommand.RegisterArtifact("example", "Example Artifact");
        final var artifactResponse = this.service.registerArtifact(request.mavenCoordinates(), artifactRegistration);
        assertNotNull(artifactResponse);
        assertEquals(HttpStatus.OK, artifactResponse.getStatus());
    }

}
