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
package org.spongepowered.downloads.artifacts.server.lib.git;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.spongepowered.downloads.artifact.api.query.ArtifactDetails;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Singleton
public class GitResolver {

    @Inject
    @Named("git-resolver")
    private ExecutorService executorService;

    public CompletableFuture<ArtifactDetails.Response> validateRepository(
        final String repoURL
    ) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        final var refs = Git.lsRemoteRepository()
                            .setRemote(repoURL)
                            .setTags(false)
                            .setHeads(false)
                            .setTimeout(20)
                            .call();
                        if (refs.isEmpty()) {
                            return this.noReferences(repoURL);
                        }
                        return new ArtifactDetails.Response.ValidRepo(repoURL);
                    } catch (GitAPIException e) {
                        throw new RuntimeException(e);
                    }
                }, this.executorService
            )
            .exceptionally(t -> switch (t) {
                case InvalidRemoteException ignore -> this.invalidRemote(repoURL);
                case GitAPIException e -> this.genericRemoteProblem(e);
                default -> this.badRequest(repoURL, t);
            });
    }

    private ArtifactDetails.Response noReferences(String repoUrl) {
        return new ArtifactDetails.Response.Error(String.format("Invalid remote: %s. No references found", repoUrl));
    }

    private ArtifactDetails.Response badRequest(String repoURL, Throwable t) {
        return new ArtifactDetails.Response.Error(String.format("Invalid remote: %s. got error %s", repoURL, t));
    }

    private ArtifactDetails.Response invalidRemote(String repoURL) {
        return new ArtifactDetails.Response.Error(String.format("Invalid remote: %s", repoURL));
    }

    private ArtifactDetails.Response genericRemoteProblem(GitAPIException t) {
        return new ArtifactDetails.Response.Error(String.format("Error resolving repository: %s", t));
    }

}


