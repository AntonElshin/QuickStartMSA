package ru.diasoft.task.controller;

import org.springframework.web.bind.annotation.*;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/references")
public class ReferenceController {

    private List<ReferenceDTO> references = new ArrayList<ReferenceDTO>();

    @GetMapping
    public Iterable<ReferenceDTO> getReferences() {
        return references;
    }

    @GetMapping("{id}")
    public ReferenceDTO getReferenceById(@PathVariable(name = "id") Long id) {
        return findReference(id);
    }

    @PostMapping
    public ReferenceDTO createReference(@RequestBody ReferenceDTO referenceDTO) {
        referenceDTO.setId();
        references.add(referenceDTO);
        return referenceDTO;
    }

    @PutMapping("{id}")
    public ReferenceDTO updateReference(@PathVariable(name = "id") Long id, @RequestBody ReferenceDTO referenceDTO) {
        ReferenceDTO foundReference = findReference(id);
        foundReference.setSysname(referenceDTO.getSysname());
        foundReference.setName(referenceDTO.getName());
        foundReference.setDescription(referenceDTO.getDescription());
        return foundReference;
    }

    @DeleteMapping("{id}")
    public void removeReference(@PathVariable(name = "id") Long id) {
        ReferenceDTO foundReference = findReference(id);
        references.remove(foundReference);
    }

    private ReferenceDTO findReference(Long id) {
        return references.stream()
                .filter(reference -> reference.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

}
