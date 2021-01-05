package ru.diasoft.task.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.checkerframework.checker.nullness.Opt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.entity.QReferenceEntity;
import ru.diasoft.task.entity.ReferenceEntity;
import ru.diasoft.task.exceptions.BusinessException;
import ru.diasoft.task.mappers.ReferenceMapper;
import ru.diasoft.task.repository.ReferenceRepository;

import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReferenceServiceTest {

    @Autowired
    private ReferenceService referenceService;

    @MockBean
    private ReferenceRepository referenceRepository;

    @MockBean
    private ValidationService validationService;

    private ReferenceEntity create;
    private ReferenceEntity update;

    private ReferenceEntity createTestReference() {
        ReferenceEntity referenceEntity = ReferenceEntity.builder()
                .id(1L)
                .name("uniq_name_1")
                .sysname("uniq_sysname_1")
                .description("uniq_description_1")
                .build();

        return referenceEntity;
    }

    @Before
    public void setUp() {

        create = createTestReference();

        update = createTestReference();
        update.setName("uniq_name_2");
        update.setSysname("uniq_sysname_1");
        update.setDescription("uniq_description_2");

    }

    @Test
    public void findAllTest() throws Exception {

        BooleanExpression predicate = QReferenceEntity.referenceEntity.sysname.containsIgnoreCase("uniq_sysname_1");
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReferenceEntity> page = new PageImpl<>(Arrays.asList(
                create
        ), pageable, 1L);

        Mockito.when(referenceRepository.findAll(predicate, pageable)).thenReturn(page);

        Iterable<ReferenceDTO> references = referenceService.findByParams(predicate, pageable);

        Mockito.verify(referenceRepository, Mockito.times(1)).findAll(predicate, pageable);
    }

    @Test
    public void findByIdTest() throws Exception {

        Long id = create.getId();

        Mockito.when(referenceRepository.findById(id)).thenReturn(Optional.ofNullable(create));

        ReferenceDTO found = referenceService.findById(id);

        Mockito.verify(referenceRepository, Mockito.times(1)).findById(id);

    }

    @Test
    public void createTest() throws Exception {

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(create);

        Mockito.doNothing().when(validationService).validateDTO(referenceDTO);
        Mockito.when(referenceRepository.findBySysnameEquals(create.getSysname())).thenReturn(Optional.empty());
        Mockito.when(referenceRepository.save(create)).thenReturn(create);

        ReferenceDTO createDTO = referenceService.create(referenceDTO);

        Mockito.verify(validationService, Mockito.times(1)).validateDTO(referenceDTO);
        Mockito.verify(referenceRepository, Mockito.times(1)).findBySysnameEquals(create.getSysname());
        Mockito.verify(referenceRepository, Mockito.times(1)).save(create);

    }

    @Test
    public void modifyChangeSysnameTest() throws Exception {

        Long id = update.getId();
        update.setSysname("uniq_sysname_2");
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(update);

        Mockito.when(referenceRepository.findById(id)).thenReturn(Optional.ofNullable(create));
        Mockito.doNothing().when(validationService).validateDTO(referenceDTO);
        Mockito.when(referenceRepository.findBySysnameEquals(update.getSysname())).thenReturn(Optional.empty());
        Mockito.when(referenceRepository.save(update)).thenReturn(update);

        referenceService.modify(id, referenceDTO);

        Mockito.verify(validationService, Mockito.times(1)).validateDTO(referenceDTO);
        Mockito.verify(referenceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(referenceRepository, Mockito.times(1)).findBySysnameEquals(update.getSysname());
        Mockito.verify(referenceRepository, Mockito.times(1)).save(update);

    }

    @Test
    public void modifyNotChangeSysnameTest() throws Exception {

        Long id = update.getId();
        update.setSysname("uniq_sysname_1");
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(update);

        Mockito.when(referenceRepository.findById(id)).thenReturn(Optional.ofNullable(create));
        Mockito.doNothing().when(validationService).validateDTO(referenceDTO);
        Mockito.when(referenceRepository.findBySysnameEquals(update.getSysname())).thenReturn(Optional.empty());
        Mockito.when(referenceRepository.save(update)).thenReturn(update);

        referenceService.modify(id, referenceDTO);

        Mockito.verify(validationService, Mockito.times(1)).validateDTO(referenceDTO);
        Mockito.verify(referenceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(referenceRepository, Mockito.times(0)).findBySysnameEquals(update.getSysname());
        Mockito.verify(referenceRepository, Mockito.times(1)).save(update);

    }

    @Test
    public void deleteTest() throws Exception {

        Long id = update.getId();

        Mockito.when(referenceRepository.findById(id)).thenReturn(Optional.ofNullable(create));
        Mockito.doNothing().when(referenceRepository).deleteById(id);

        referenceService.delete(id);

        Mockito.verify(referenceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(referenceRepository, Mockito.times(1)).deleteById(id);

    }

    @Test(expected = BusinessException.class)
    public void findByIdNotFoundErrorTest() throws Exception {

        Long id = 2L;

        Mockito.when(referenceRepository.findById(id)).thenReturn(Optional.empty());

        referenceService.findById(id);

    }

    @Test(expected = BusinessException.class)
    public void modifyIdNullErrorTest() throws Exception {

        Long id = update.getId();
        update.setSysname("uniq_sysname_1");
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(update);

        referenceService.modify(null, referenceDTO);

    }

    @Test(expected = BusinessException.class)
    public void deleteByIdNullErrorTest() throws Exception {

        referenceService.delete(null);

    }

    @Test(expected = BusinessException.class)
    public void deleteByIdNotFoundErrorTest() throws Exception {

        Long id = 2L;

        Mockito.when(referenceRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.doNothing().when(referenceRepository).deleteById(id);

        referenceService.delete(id);

    }

    @Test(expected = BusinessException.class)
    public void createDuplicateSysnameErrorTest() throws Exception {

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(create);

        Mockito.doNothing().when(validationService).validateDTO(referenceDTO);
        Mockito.when(referenceRepository.findBySysnameEquals(create.getSysname())).thenReturn(Optional.ofNullable(create));
        Mockito.when(referenceRepository.save(create)).thenReturn(null);

        ReferenceDTO createDTO = referenceService.create(referenceDTO);

    }

}
