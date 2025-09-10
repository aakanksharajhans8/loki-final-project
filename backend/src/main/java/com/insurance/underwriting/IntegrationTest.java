package com.insurance.underwriting;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IntegrationTest {

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        mysql.start();
        r.add("spring.datasource.url", mysql::getJdbcUrl);
        r.add("spring.datasource.username", mysql::getUsername);
        r.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    DataSource ds;

    @Test
    void contextLoadsAndFlywayRuns() throws Exception {
        assertThat(ds.getConnection()).isNotNull();
    }
}
