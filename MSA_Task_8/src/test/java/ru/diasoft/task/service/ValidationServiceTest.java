package ru.diasoft.task.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.entity.ReferenceEntity;
import ru.diasoft.task.exceptions.BusinessException;
import ru.diasoft.task.mappers.ReferenceMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ValidationServiceTest {

    @Autowired
    private ValidationService validateService;

    private ReferenceEntity createTestReference() {
        ReferenceEntity referenceEntity = ReferenceEntity.builder()
                .name("uniq_name_1")
                .sysname("uniq_sysname_1")
                .description("uniq_description_1")
                .build();

        return referenceEntity;
    }

    @Test
    public void validateReferenceDTO_valid_default() throws Exception {
        ReferenceEntity referenceEntity = createTestReference();
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

        validateService.validateDTO(referenceDTO);
    }

    @Test
    public void validateReferenceDTO_sysname_onlyDigit() throws Exception {
        ReferenceEntity referenceEntity = createTestReference();
        referenceEntity.setSysname("1");
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

        validateService.validateDTO(referenceDTO);
    }

    @Test
    public void validateReferenceDTO_sysname_otherAllowedChars() throws Exception {
        ReferenceEntity referenceEntity = createTestReference();
        referenceEntity.setSysname("f _");
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

        validateService.validateDTO(referenceDTO);
    }

    @Test
    public void validateReferenceDTO_sysname_null() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setSysname(null);
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

    @Test
    public void validateReferenceDTO_sysname_blank() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setSysname("");
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

    @Test
    public void validateReferenceDTO_sysname_startsInvalidChars() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setSysname("Яuniq_sysname_1");
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

    @Test
    public void validateReferenceDTO_sysname_containsInvalidChars() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setSysname("uniq?_sysname,_1");
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

    @Test
    public void validateReferenceDTO_sysname_invalidChars() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setSysname("Тест");
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

    @Test
    public void validateReferenceDTO_name_null() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setName(null);
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

    @Test
    public void validateReferenceDTO_name_blank() throws Exception {

        Assertions.assertThrows(BusinessException.class, () -> {

            ReferenceEntity referenceEntity = createTestReference();
            referenceEntity.setName("");
            ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

            validateService.validateDTO(referenceDTO);
        });
    }

}
