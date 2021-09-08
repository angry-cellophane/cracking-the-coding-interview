package com.github.ka.cci.graphs;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
p 110, 6th edition
4.7
Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of
projects, where the second project is dependent on the first project). All of a project's dependencies
must be built before the project is. Find a build order that will allow the projects to be built. If there
is no valid build order, return an error.
EXAMPLE
Input:
projects: a, b, c, d, e, f
dependencies: (a, d), (f, b), (b, d), (f, a), (d, c)
Output: f, e, a, b, d, c
 */
public class BuildOrder {

    /*
        c -> d -> a -> f
               -> b ->
                       e

        take node with zero incoming edges
        put in results
        update number of incoming edge for adjacent nodes
        take node out
        repeat
     */
    static List<String> buildOrder(List<String> projects, List<List<String>> dependencies) {
        if (projects == null || projects.isEmpty() || dependencies == null || dependencies.isEmpty()) return projects;

        var graph = new HashMap<String, Set<String>>(); // project name -> list of other project it depends on
        var incomingEdges = new HashMap<String, Integer>();
        for (var dependency: dependencies) {
            var from = dependency.get(1);
            var to = dependency.get(0);

            graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            incomingEdges.computeIfAbsent(from, k -> 0);
            incomingEdges.put(to, incomingEdges.getOrDefault(to, 0) + 1);
        }

        var noIncomingEdges = new LinkedList<String>();
        projects.forEach(p -> {
            if (incomingEdges.getOrDefault(p, 0) == 0) {
                noIncomingEdges.add(p);
            }
        });

        var result = new LinkedList<String>();
        var seen = new HashSet<String>();
        if (noIncomingEdges.isEmpty()) {
            throw new RuntimeException("found cyclic dependency");
        }

        while (!noIncomingEdges.isEmpty()) {
            var project = noIncomingEdges.poll();
            result.addFirst(project);
            seen.add(project);
            for (var n: graph.getOrDefault(project, Collections.emptySet())) {
                if (seen.contains(n)) {
                    throw new RuntimeException("found cyclic dependency");
                }

                var incoming = incomingEdges.get(n) - 1;
                incomingEdges.put(n, incoming);
                if (incoming == 0) {
                    noIncomingEdges.add(n);
                }
            }
            graph.remove(project);
        }

        return result;
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void test(List<String> projects, List<List<String>> dependencies, List<String> expected) {
        var actual = buildOrder(projects, dependencies);

        System.out.println("expected = " + expected + ", actual = " + actual);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.arguments(List.of("a", "b", "c", "d", "e", "f"),
                        List.of(List.of("a", "d"), List.of("f", "b"), List.of("b", "d"), List.of("f", "a"), List.of("d", "c")),
                        List.of("f", "b", "a", "d", "e", "c"))
        );
    }
}
