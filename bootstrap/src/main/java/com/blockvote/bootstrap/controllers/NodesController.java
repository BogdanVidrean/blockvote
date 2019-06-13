package com.blockvote.bootstrap.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.synchronizedSet;

@RestController
@RequestMapping("/nodes")
public class NodesController {

    private static final Set<String> nodes = synchronizedSet(new HashSet<>());

    @GetMapping
    public ResponseEntity<Set<String>> getNodes() {
        return new ResponseEntity<>(nodes, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> registerNode(@RequestParam String enode) {
        nodes.add(enode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
