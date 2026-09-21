package com.tuckersoft.branchengine.service;

import com.tuckersoft.branchengine.dto.CreateNodeRequest;
import com.tuckersoft.branchengine.dto.NodeResponse;
import com.tuckersoft.branchengine.entity.StoryNode;
import com.tuckersoft.branchengine.repository.StoryNodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class NodeService {

    private final StoryNodeRepository repository;

    public NodeService(StoryNodeRepository repository) {
        this.repository = repository;
    }

    public NodeResponse create(CreateNodeRequest request) {
        if (repository.existsByNodeCode(request.nodeCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "nodeCode already exists"
            );
        }

        StoryNode node = new StoryNode();
        node.setNodeCode(request.nodeCode());
        node.setTitle(request.title());
        node.setSceneText(request.sceneText());
        node.setBranchCapacity(request.branchCapacity());
        node.setCurrentBranches(0);
        node.setPrimaryBranchCode(request.primaryBranchCode());
        node.setGlitchBranchCode(request.glitchBranchCode());
        node.setCreatedAt(Instant.now());

        return toResponse(repository.save(node));
    }

    public List<NodeResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NodeResponse findById(Long id) {
        StoryNode node = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "StoryNode not found"
                ));

        return toResponse(node);
    }

    private NodeResponse toResponse(StoryNode node) {
        return new NodeResponse(
                node.getId(),
                node.getNodeCode(),
                node.getTitle(),
                node.getSceneText(),
                node.getBranchCapacity(),
                node.getCurrentBranches(),
                node.getPrimaryBranchCode(),
                node.getGlitchBranchCode(),
                node.getCreatedAt()
        );
    }
}
