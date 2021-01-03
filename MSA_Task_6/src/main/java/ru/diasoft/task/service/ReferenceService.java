package ru.diasoft.task.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.exceptions.BusinessException;

import java.util.List;

public interface ReferenceService {

    Iterable<ReferenceDTO> findByParams(Predicate predicate, Pageable pageable);

    ReferenceDTO findById(Long id) throws BusinessException;

    ReferenceDTO create(ReferenceDTO reference) throws BusinessException;

    ReferenceDTO modify(Long id, ReferenceDTO reference) throws BusinessException;

    void delete(Long id) throws BusinessException;

}
