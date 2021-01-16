package ru.diasoft.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.diasoft.task.dto.ReferenceDTO;
import ru.diasoft.task.entity.ReferenceEntity;
import ru.diasoft.task.mappers.ReferenceMapper;
import ru.diasoft.task.repository.ReferenceRepository;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class ReferenceController_CRUD_Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReferenceRepository referenceRepository;

    private ReferenceEntity createTestReference() {
        ReferenceEntity referenceEntity = ReferenceEntity.builder()
                .name("uniq_name_1")
                .sysname("uniq_sysname_1")
                .description("uniq_description_1")
                .build();

        return referenceEntity;
    }

    @Test
    public void read() throws Exception {

        // создаём запись
        ReferenceEntity referenceEntity = new ReferenceEntity("uniq_name_1", "uniq_sysname_1", "uniq_description_1");
        referenceRepository.save(referenceEntity);
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);

        // получаем по идентификатору
        this.mockMvc.perform(get("/api/references/" + referenceDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("uniq_name_1")))
                .andExpect(content().string(containsString("uniq_sysname_1")))
                .andExpect(content().string(containsString("uniq_description_1")))
        ;

        referenceRepository.deleteById(referenceDTO.getId());

    }

    @Test
    public void create() throws Exception {

        //подготовка таблиц для теста
        referenceRepository.deleteAll();

        Optional<ReferenceEntity> foundReference;

        ReferenceEntity referenceEntity = createTestReference();
        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);
        String json = new ObjectMapper().writeValueAsString(referenceDTO);

        mockMvc.perform(post("/api/references").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        foundReference = referenceRepository.findBySysnameEquals(referenceDTO.getSysname());

        Long id = null;
        if(foundReference.isPresent()) {
            id = foundReference.get().getId();
        }

        // получаем по идентификатору
        this.mockMvc.perform(get("/api/references/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("uniq_name_1")))
                .andExpect(content().string(containsString("uniq_sysname_1")))
                .andExpect(content().string(containsString("uniq_description_1")));

    }

    @Test
    public void update() throws Exception {

        //подготовка таблиц для теста
        referenceRepository.deleteAll();

        // создаём запись
        ReferenceEntity referenceEntity = createTestReference();
        referenceRepository.save(referenceEntity);

        ReferenceDTO referenceDTO = ReferenceMapper.INSTANCE.toDto(referenceEntity);
        Long id = referenceDTO.getId();
        String json = new ObjectMapper().writeValueAsString(referenceDTO);
        json = json.replaceAll("uniq_name_1", "uniq_name_2");
        json = json.replaceAll("uniq_sysname_1", "uniq_sysname_2");
        json = json.replaceAll("uniq_description_1", "uniq_description_2");

        //обновляем запись
        this.mockMvc.perform(put("/api/references/" + id).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(referenceDTO.getId()))
                .andExpect(content().string(containsString("uniq_name_2")))
                .andExpect(content().string(containsString("uniq_sysname_2")))
                .andExpect(content().string(containsString("uniq_description_2")));

        referenceRepository.deleteById(referenceDTO.getId());

    }

    @Test
    public void delete() throws Exception {

        //подготовка таблиц для теста
        referenceRepository.deleteAll();

        // создаём запись справочника
        ReferenceEntity referenceEntity = createTestReference();
        referenceRepository.save(referenceEntity);

        //удаляем справочник
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/references/" + referenceEntity.getId()))
                .andDo(print());

        //проверяем удаление справочника
        this.mockMvc.perform(get("/api/references"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalElements\":0")));

    }

}
