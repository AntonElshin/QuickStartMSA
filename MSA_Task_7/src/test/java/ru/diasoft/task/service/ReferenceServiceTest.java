package ru.diasoft.task.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ReferenceServiceTest {

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private ReferenceRepository referenceRepository;

    @MockBean
    private ValidationService validationService;

    private ReferenceEntity createTestReference() {
        ReferenceEntity referenceEntity = ReferenceEntity.builder()
                .name("uniq_name_1")
                .sysname("uniq_sysname_1")
                .description("uniq_description_1")
                .build();

        return referenceEntity;
    }

    @Test
    public void findAllTest() throws Exception {

        referenceRepository.deleteAll();

        ReferenceEntity create = referenceRepository.save(createTestReference());
        Long id = create.getId();

        BooleanExpression predicate = QReferenceEntity.referenceEntity.sysname.containsIgnoreCase("uniq_sysname_1");
        Pageable pageable = PageRequest.of(0, 10);

        Iterable<ReferenceDTO> references = referenceService.findByParams(predicate, pageable);

        Integer counter = 0;
        ReferenceDTO found = null;

        for(ReferenceDTO referenceDTO : references) {
            counter++;
            found = referenceDTO;
        }

        Assert.assertNotNull(found);
        Assert.assertTrue(counter == 1);
        Assert.assertEquals(found.getSysname(), create.getSysname());
        Assert.assertEquals(found.getName(), create.getName());
        Assert.assertEquals(found.getDescription(), create.getDescription());

    }

    @Test
    public void findByIdTest() throws Exception {

        referenceRepository.deleteAll();

        ReferenceEntity create = referenceRepository.save(createTestReference());
        Long id = create.getId();

        Assert.assertNotNull(id);

        ReferenceDTO found = referenceService.findById(id);

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getSysname(), create.getSysname());
        Assert.assertEquals(found.getName(), create.getName());
        Assert.assertEquals(found.getDescription(), create.getDescription());

    }

    @Test
    public void createTest() throws Exception {

        referenceRepository.deleteAll();

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(createTestReference());

        ReferenceDTO create = referenceService.create(referenceDTO);
        Long id = create.getId();

        Assert.assertNotNull(id);
        Mockito.verify(validationService, Mockito.times(1)).validateDTO(referenceDTO);

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertTrue(optional.isPresent());

        ReferenceEntity found = optional.get();

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getSysname(), create.getSysname());
        Assert.assertEquals(found.getName(), create.getName());
        Assert.assertEquals(found.getDescription(), create.getDescription());

    }

    @Test
    public void modifyTest() throws Exception {

        referenceRepository.deleteAll();

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(createTestReference());

        ReferenceDTO create = referenceService.create(referenceDTO);
        Long id = create.getId();

        Assert.assertNotNull(id);

        referenceDTO.setSysname("uniq_sysname_2");
        referenceDTO.setName("uniq_name_2");
        referenceDTO.setDescription("uniq_description_2");

        ReferenceDTO modify = referenceService.modify(id, referenceDTO);

        Mockito.verify(validationService, Mockito.times(2)).validateDTO(referenceDTO);

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertTrue(optional.isPresent());

        ReferenceEntity found = optional.get();

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getSysname(), modify.getSysname());
        Assert.assertEquals(found.getName(), modify.getName());
        Assert.assertEquals(found.getDescription(), modify.getDescription());

    }

    @Test
    public void deleteTest() throws Exception {

        referenceRepository.deleteAll();

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(createTestReference());

        ReferenceDTO create = referenceService.create(referenceDTO);
        Long id = create.getId();

        Assert.assertNotNull(id);

        referenceService.delete(id);

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertFalse(optional.isPresent());

    }

    @Test(expected = BusinessException.class)
    public void findByIdNotFoundErrorTest() throws Exception {

        referenceRepository.deleteAll();

        referenceService.findById(1L);

    }

    @Test(expected = BusinessException.class)
    public void createDuplicateSysnameErrorTest() throws Exception {

        referenceRepository.deleteAll();

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(createTestReference());

        ReferenceDTO create_1 = referenceService.create(referenceDTO);
        ReferenceDTO create_2 = referenceService.create(referenceDTO);

    }


}
