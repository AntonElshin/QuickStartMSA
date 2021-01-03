package ru.diasoft.task.controller;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.task.entity.ReferenceEntity;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.exceptions.BusinessException;
import ru.diasoft.task.service.ReferenceService;

import java.util.List;

@RestController
public class ReferenceController {

    @Autowired
    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @ApiOperation(value = "Получение списка справочников по параметам")
    @GetMapping(value = "/api/references", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ReferenceDTO> getReferences(
            @QuerydslPredicate(root = ReferenceEntity.class) Predicate predicate,
            @PageableDefault Pageable pageable
    ) {
        return referenceService.findByParams(predicate, pageable);
    }

    @ApiOperation(value = "Получение справочника по идентификатору")
    @GetMapping(value = "/api/references/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReferenceDTO getReference(
            @PathVariable(name = "id") Long id
    ) throws BusinessException {
        return referenceService.findById(id);
    }

    @ApiOperation(value = "Создание справочника")
    @PostMapping(value = "/api/references", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReferenceDTO referenceCreate(
            @RequestBody
            @ApiParam(name = "Reference") ReferenceDTO referenceDTO
    ) throws BusinessException {
        return referenceService.create(referenceDTO);
    }

    @ApiOperation(value = "Изменение справочников по идентификатору")
    @PutMapping(path = "/api/references/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReferenceDTO referenceModify(
            @PathVariable(name = "id") Long id,
            @RequestBody
            @ApiParam(name = "Reference") ReferenceDTO referenceDTO
    ) throws BusinessException {
        return referenceService.modify(id, referenceDTO);
    }

    @ApiOperation(value = "Удаление справочника по идентификатору")
    @DeleteMapping(value = "/api/references/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteReference(
            @PathVariable(name = "id") Long id
    ) throws BusinessException {
        referenceService.delete(id);
    }

}
