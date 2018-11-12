package com.leon.user;

import com.leon.infrastructure.jpa.JpaConfiguration;
import com.leon.infrastructure.jpa.SnowFlakeGenerator;
import com.leon.infrastructure.jpa.UniqueIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(JpaConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = "INTEGRATION-TEST")
public class UserRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testStoreUser_OK() {
        User user = new User(userRepository.getNextId(), "test-email", "secret-pwd", UserRole.USER);

        User storedUser = userRepository.save(user);

        assertThat(storedUser).isNotNull();
        assertThat(userRepository.count()).isEqualTo(6L);

        entityManager.flush();
        assertThat(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM xxx_user", Long.class)).isEqualTo(6L);
        assertThat(jdbcTemplate.queryForObject("SELECT role FROM xxx_user GROUP BY role", String.class)).isEqualTo("USER");
    }

    @Test
    public void testFindByEmail_OK() {
        User user = Users.newRandomUser();
        userRepository.save(user);

        Optional<User> storedUser = userRepository.findByEmailIgnoreCase(user.getEmail());

        assertThat(storedUser).isNotEmpty().contains(user);
    }

    @Test
    public void testFindByEmail_NotFound() {
        User user = Users.newRandomUser();
        userRepository.save(user);

        Optional<User> storedUser = userRepository.findByEmailIgnoreCase("unknown@mail.com");

        assertThat(storedUser).isEmpty();
    }

    @Test
    public void testFindByEmail_IgnoringCase() {
        User user = Users.newRandomUser();
        userRepository.save(user);

        Optional<User> storedUser = userRepository.findByEmailIgnoreCase(user.getEmail().toUpperCase(Locale.ENGLISH));

        assertThat(storedUser).isNotEmpty().contains(user);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean("snowFlakeGenerator")
        public UniqueIdGenerator<Long> uniqueIdGenerator() {
            return new SnowFlakeGenerator();
        }

    }

}
