package com.tuckersoft.branchengine.controller;

import com.tuckersoft.branchengine.dto.CreateNodeRequest;
import com.tuckersoft.branchengine.dto.NodeResponse;
import com.tuckersoft.branchengine.service.NodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {

    private final NodeService service;

    public NodeController(NodeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public NodeResponse create(@Valid @RequestBody CreateNodeRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<NodeResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public NodeResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
