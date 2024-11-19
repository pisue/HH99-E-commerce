package com.hh99.ecommerce.common.support;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
public abstract class IntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("ecommerce")
            .withUsername("ecommerce")
            .withPassword("ecommerce!@#")
            .withReuse(true) // 컨테이너 재사용으로 테스트 속도 향상
            .withUrlParam("characterEncoding", "UTF-8")
            .withUrlParam("serverTimezone", "Asia/Seoul");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        truncateAllTables();
    }

    private void truncateAllTables() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

        List<String> tableNames = jdbcTemplate.queryForList(
                "SHOW TABLES",
                String.class
        );

        for (String tableName : tableNames) {
            if (!tableName.equalsIgnoreCase("flyway_schema_history")) {
                jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
            }
        }

        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
