package ru.diasoft.task.service;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.task.entity.ReferenceEntity;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.exceptions.BusinessException;
import ru.diasoft.task.exceptions.Errors;
import ru.diasoft.task.mappers.ReferenceMapper;
import ru.diasoft.task.repository.ReferenceRepository;

import java.util.Optional;

@Service
public class ReferenceServiceImpl implements ReferenceService {

    private ReferenceRepository referenceRepository;
    private ValidationService validationService;

    @Autowired
    public ReferenceServiceImpl(
            ReferenceRepository referenceRepository,
            ValidationService validationService
    ) {
        this.referenceRepository = referenceRepository;
        this.validationService = validationService;
    }

    @Override
    public Iterable<ReferenceDTO> findByParams(Predicate predicate, Pageable pageable) {
        return referenceRepository.findAll(predicate, pageable).map(ReferenceMapper.INSTANCE::toDto);
    }

    @Override
    public ReferenceDTO findById(Long id) throws BusinessException {
        Optional<ReferenceEntity> foundReference = referenceRepository.findById(id);
        if(!foundReference.isPresent()) {
            throw new BusinessException(Errors.REFERENCE_NOT_FOUND_BY_ID, id);
        }
        ReferenceEntity referenceEntity = foundReference.get();
        return ReferenceMapper.INSTANCE.toDto(referenceEntity);
    }

    @Override
    @Transactional
    public ReferenceDTO create(ReferenceDTO referenceDTO) throws BusinessException {

        checkReferenceForCreate(referenceDTO);

        ReferenceEntity entity = referenceRepository.save(ReferenceMapper.INSTANCE.fromDto(referenceDTO));
        return ReferenceMapper.INSTANCE.toDto(entity);
    }

    @Override
    @Transactional
    public ReferenceDTO modify(Long id, ReferenceDTO referenceDTO) throws BusinessException {

        if(id == null) {
            throw new BusinessException(Errors.IDENTIFIER_IS_NULL, id);
        }

        ReferenceEntity referenceEntity = checkReferenceForModify(id, referenceDTO);
        referenceDTO.setId(id);

        ReferenceEntity entity = referenceRepository.save(ReferenceMapper.INSTANCE.fromDto(referenceDTO));
        return ReferenceMapper.INSTANCE.toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws BusinessException {

        if(id == null) {
            throw new BusinessException(Errors.IDENTIFIER_IS_NULL, id);
        }

        Optional<ReferenceEntity> foundReference = referenceRepository.findById(id);
        if(!foundReference.isPresent()) {
            throw new BusinessException(Errors.REFERENCE_NOT_FOUND_BY_ID, id);
        }
        ReferenceEntity referenceEntity = foundReference.get();

        //удаляем справочник
        referenceRepository.deleteById(id);
    }


    public void checkReferenceForCreate(ReferenceDTO referenceDTO) throws BusinessException {

        Optional<ReferenceEntity> foundReference;

        //проверка обязательных параметров
        validationService.validateDTO(referenceDTO);

        //проверка на уникальность сиснейма
        foundReference = referenceRepository.findBySysnameEquals(referenceDTO.getSysname());
        if(foundReference.isPresent()) {
            throw new BusinessException(Errors.NOT_UNIQUE_REFERENCE_SYSNAME, referenceDTO.getSysname());
        }
    }

    public ReferenceEntity checkReferenceForModify(Long id, ReferenceDTO referenceDTO) throws BusinessException {

        //проверка обязательных параметров
        validationService.validateDTO(referenceDTO);

        // поиск элемента справочника по идентификатору
        Optional<ReferenceEntity> foundReference = referenceRepository.findById(id);
        if(!foundReference.isPresent()) {
            throw new BusinessException(Errors.REFERENCE_NOT_FOUND_BY_ID, id);
        }
        ReferenceEntity referenceEntity = foundReference.get();

        //проверка на уникальность сиснейма, если он менялся
        if(!referenceDTO.getSysname().equalsIgnoreCase(referenceEntity.getSysname())) {
            foundReference = referenceRepository.findBySysnameEquals(referenceDTO.getSysname());
            if(foundReference.isPresent()) {
                throw new BusinessException(Errors.NOT_UNIQUE_REFERENCE_SYSNAME, referenceDTO.getSysname());
            }
        }

        return referenceEntity;
    }

}
