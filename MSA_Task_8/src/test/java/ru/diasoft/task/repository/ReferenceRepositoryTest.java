package ru.diasoft.task.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.task.entity.ReferenceEntity;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ReferenceRepositoryTest {

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private TestEntityManager entityManager;

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

        ReferenceEntity create = createTestReference();
        Long id = (Long) entityManager.persistAndGetId(create);
        
        Iterable<ReferenceEntity> references = referenceRepository.findAll();

        Integer counter = 0;
        ReferenceEntity found = null;
        
        for(ReferenceEntity reference : references) {
            counter++;
            found = reference;
        }

        Assert.assertNotNull(found);
        Assert.assertTrue(counter == 1);
        Assert.assertEquals(found.getSysname(), create.getSysname());
        Assert.assertEquals(found.getName(), create.getName());
        Assert.assertEquals(found.getDescription(), create.getDescription());

        entityManager.remove(found);

    }

    @Test
    public void findById() throws Exception {

        ReferenceEntity create = createTestReference();
        Long id = (Long) entityManager.persistAndGetId(create);

        Assert.assertNotNull(id);

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertTrue(optional.isPresent());

        ReferenceEntity found = optional.get();

        Assert.assertEquals(found.getId(), id);
        Assert.assertEquals(found.getSysname(), create.getSysname());
        Assert.assertEquals(found.getName(), create.getName());
        Assert.assertEquals(found.getDescription(), create.getDescription());

        entityManager.remove(found);

    }

    @Test
    public void findBySysnameEqualsTest() throws Exception {

        ReferenceEntity create = createTestReference();
        Long id = (Long) entityManager.persistAndGetId(create);

        Optional<ReferenceEntity> optional = referenceRepository.findBySysnameEquals("uniq_sysname_1");
        Assert.assertTrue(optional.isPresent());

        ReferenceEntity found = optional.get();

        Assert.assertEquals(found.getId(), id);
        Assert.assertEquals(found.getSysname(), create.getSysname());
        Assert.assertEquals(found.getName(), create.getName());
        Assert.assertEquals(found.getDescription(), create.getDescription());

        entityManager.remove(found);

    }

    @Test
    public void saveCreateTest() throws Exception {

        ReferenceEntity create = createTestReference();
        ReferenceEntity created = referenceRepository.save(create);

        Assert.assertNotNull(created.getId());
        Assert.assertEquals(created.getSysname(), create.getSysname());
        Assert.assertEquals(created.getName(), create.getName());
        Assert.assertEquals(created.getDescription(), create.getDescription());

        entityManager.remove(created);

    }

    @Test
    public void saveUpdateTest() throws Exception {

        ReferenceEntity update = referenceRepository.save(createTestReference());
        Long id = (Long) entityManager.getId(update);

        update.setSysname("uniq_sysname_2");
        update.setName("uniq_name_2");
        update.setDescription("uniq_description_2");

        referenceRepository.save(update);

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertTrue(optional.isPresent());

        ReferenceEntity found = optional.get();

        Assert.assertEquals(found.getSysname(), "uniq_sysname_2");
        Assert.assertEquals(found.getName(), "uniq_name_2");
        Assert.assertEquals(found.getDescription(), "uniq_description_2");

        entityManager.remove(found);

    }

    @Test
    public void deleteByIdTest() throws Exception {

        ReferenceEntity create = referenceRepository.save(createTestReference());
        Long id = (Long) entityManager.getId(create);

        referenceRepository.deleteById(id);

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertFalse(optional.isPresent());

    }

    @Test
    public void deleteAllTest() throws Exception {

        ReferenceEntity create = referenceRepository.save(createTestReference());
        Long id = (Long) entityManager.getId(create);

        referenceRepository.deleteAll();

        Optional<ReferenceEntity> optional = referenceRepository.findById(id);

        Assert.assertFalse(optional.isPresent());

    }

}
