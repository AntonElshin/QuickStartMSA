package ru.diasoft.task.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.diasoft.task.repository.ReferenceRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/sql/reference-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/reference-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReferenceController_Find_Page_Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReferenceRepository referenceRepository;

    @Test
    public void findByParams_All() throws Exception {
        this.mockMvc.perform(get("/api/references"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalElements\":15")));
        ;
    }

    @Test
    public void findByParams_name() throws Exception {

        this.mockMvc.perform(get("/api/references?name=rEf nAme "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalElements\":15")));

        this.mockMvc.perform(get("/api/references??name=rEf nAme 6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ref name 6")))
                .andExpect(content().string(containsString("\"totalElements\":1")));
        ;
    }

    @Test
    public void findByParams_sysName() throws Exception {

        this.mockMvc.perform(get("/api/references?sysname=RefSysName"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalElements\":15")));

        this.mockMvc.perform(get("/api/references?sysname=RefSysName6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("refsysname6")))
                .andExpect(content().string(containsString("\"totalElements\":1")));
        ;
    }

    @Test
    public void findByParams_nameAndSysName() throws Exception {

        this.mockMvc.perform(get("/api/references?name=rEf nAme &sysname=RefSysName"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalElements\":15")));

        this.mockMvc.perform(get("/api/references?name=rEf nAme 6&sysname=RefSysName6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ref name 6")))
                .andExpect(content().string(containsString("refsysname6")))
                .andExpect(content().string(containsString("\"totalElements\":1")));
        ;
    }

    @Test
    public void findByParams_sortingAsc() throws Exception {

        this.mockMvc.perform(get("/api/references?sort=id,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content\":[{\"id\":1,")));
        ;

    }

    @Test
    public void findByParams_sortingDesc() throws Exception {

        this.mockMvc.perform(get("/api/references?sort=id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content\":[{\"id\":15")));
        ;

    }

    @Test
    public void findByParams_Page() throws Exception{

        this.mockMvc.perform(get("/api/references?sort=id,asc&size=10&page=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content\":[{\"id\":1,")))
                .andExpect(content().string(containsString("\"number\":0")))
                .andExpect(content().string(containsString("\"totalPages\":2")))
        ;

        this.mockMvc.perform(get("/api/references?sort=id,asc&size=10&page=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content\":[{\"id\":11,")))
                .andExpect(content().string(containsString("\"number\":1")))
                .andExpect(content().string(containsString("\"totalPages\":2")))
        ;

        this.mockMvc.perform(get("/api/references?sort=id,desc&size=10&page=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content\":[{\"id\":15,")))
                .andExpect(content().string(containsString("\"number\":0")))
                .andExpect(content().string(containsString("\"totalPages\":2")))
        ;

        this.mockMvc.perform(get("/api/references?sort=id,desc&size=10&page=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content\":[{\"id\":5,")))
                .andExpect(content().string(containsString("\"number\":1")))
                .andExpect(content().string(containsString("\"totalPages\":2")))
        ;

    }



}
