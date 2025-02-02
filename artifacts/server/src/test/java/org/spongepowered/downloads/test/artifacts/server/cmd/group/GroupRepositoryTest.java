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

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.spongepowered.downloads.artifacts.server.cmd.group.GroupRepository;
import org.spongepowered.downloads.artifacts.server.cmd.group.domain.Group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
public class GroupRepositoryTest {

    @Inject
    GroupRepository groupRepository;

    @Test
    void testRegisterGroup() {
        final var group = new Group();
        group.setGroupId("org.spongepowered");
        group.setName("SpongePowered");
        group.setWebsite("https://spongepowered.org");
        groupRepository.save(group);
        final var groupOpt = groupRepository.findByGroupId("org.spongepowered");
        assertNotNull(groupOpt);
        assertTrue(groupOpt.isPresent());
        final var groupFound = groupOpt.get();
        assertEquals("org.spongepowered", groupFound.getGroupId());
        assertEquals("SpongePowered", groupFound.getName());
        assertEquals("https://spongepowered.org", groupFound.getWebsite());
    }


    @Test
    void testFindByGroupIdNonExistent() {
        final var groupOpt = groupRepository.findByGroupId("non.existent.group");
        assertNotNull(groupOpt);
        assertTrue(groupOpt.isEmpty());
    }

    @Test
    void testSaveMultipleGroups() {
        final var group1 = new Group();
        group1.setGroupId("org.example1");
        group1.setName("Example 1");
        group1.setWebsite("https://example1.org");
        groupRepository.save(group1);

        final var group2 = new Group();
        group2.setGroupId("org.example2");
        group2.setName("Example 2");
        group2.setWebsite("https://example2.org");
        groupRepository.save(group2);

        final var groupOpt1 = groupRepository.findByGroupId("org.example1");
        final var groupOpt2 = groupRepository.findByGroupId("org.example2");

        assertTrue(groupOpt1.isPresent());
        assertTrue(groupOpt2.isPresent());

        assertEquals("Example 1", groupOpt1.get().getName());
        assertEquals("Example 2", groupOpt2.get().getName());
    }

    @Test
    void testUpdateExistingGroup() {
        final var group = new Group();
        group.setGroupId("org.updateme");
        group.setName("Update Me");
        group.setWebsite("https://updateme.org");
        groupRepository.save(group);

        final var savedGroupOpt = groupRepository.findByGroupId("org.updateme");
        assertTrue(savedGroupOpt.isPresent());
        assertEquals("Update Me", savedGroupOpt.get().getName());
        assertEquals("https://updateme.org", savedGroupOpt.get().getWebsite());
        final var toUpdate = savedGroupOpt.get();

        toUpdate.setName("Updated Group");
        toUpdate.setWebsite("https://updated.org");
        groupRepository.update(toUpdate);

        final var groupOpt = groupRepository.findByGroupId("org.updateme");
        assertTrue(groupOpt.isPresent());
        assertEquals("Updated Group", groupOpt.get().getName());
        assertEquals("https://updated.org", groupOpt.get().getWebsite());
    }

    @Test
    void testFindByGroupIdCaseSensitivity() {
        final var group = new Group();
        group.setGroupId("org.CaseSensitive");
        group.setName("Case Sensitive");
        group.setWebsite("https://casesensitive.org");
        groupRepository.save(group);

        final var groupOptLower = groupRepository.findByGroupId("org.casesensitive");
        final var groupOptUpper = groupRepository.findByGroupId("org.CASESENSITIVE");
        final var groupOptCorrect = groupRepository.findByGroupId("org.CaseSensitive");

        assertTrue(groupOptLower.isEmpty());
        assertTrue(groupOptUpper.isEmpty());
        assertTrue(groupOptCorrect.isPresent());
        assertEquals("Case Sensitive", groupOptCorrect.get().getName());
    }
}
