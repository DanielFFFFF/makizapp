package fr.makizart.common.storageservice.integrationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource("/application.yml")
@AutoConfigureMockMvc
class RestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;


    String baseUrl = "http://localhost:";

    @BeforeEach
    void setup() {
        baseUrl = baseUrl + port;
        jdbcTemplate.execute("insert into Student(name, grade) values('Jay', 7)");
    }
    @AfterEach
    void emptyData(){
        var totalRecords = jdbcTemplate.queryForObject("Select count(*) from Student", Integer.class);
        jdbcTemplate.execute("DELETE FROM Student");
    }

    @Test
    void name() {
    }
}