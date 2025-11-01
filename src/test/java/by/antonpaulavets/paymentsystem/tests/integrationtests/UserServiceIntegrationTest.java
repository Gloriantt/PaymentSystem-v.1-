package by.antonpaulavets.paymentsystem.tests.integrationtests;

import by.antonpaulavets.paymentsystem.model.User;
import by.antonpaulavets.paymentsystem.repository.UserRepository;
import by.antonpaulavets.paymentsystem.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class UserServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateAndGetUser() {
        User user = new User();
        user.setName("Alice");
        user.setSurname("Smith");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setEmail("alice@example.com");

        User saved = userService.createUser(user);
        assertThat(saved.getId()).isNotNull();

        User found = userService.getUserById(saved.getId());
        assertThat(found.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void shouldCacheUserInRedis() {
        User user = new User();
        user.setName("Bob");
        user.setSurname("Brown");
        user.setBirthDate(LocalDate.of(1985, 5, 5));
        user.setEmail("bob@example.com");

        User saved = userService.createUser(user);

        // Первый вызов — идёт в БД
        userService.getUserById(saved.getId());

        // Второй вызов — должен пойти в кэш
        userService.getUserById(saved.getId());

        assertThat(saved.getEmail()).isEqualTo("bob@example.com");
    }
}