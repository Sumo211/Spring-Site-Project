package com.leon.user;

import com.leon.user.web.dto.UserCreatedDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static com.leon.utils.ConstraintViolationSetAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("TEST")
public class ValidUserValidatorTest {

    @Autowired
    private ValidatorFactory validatorFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @Test
    public void testInvalidIfAlreadyExistsUserWithGivenEmail() {
        String email = "test@mail.com";
        UserCreatedDto dto = new UserCreatedDto();
        dto.setEmail(email);
        dto.setPassword("secret-pwd");

        when(userService.findUserByEmail(email)).thenReturn(Optional.of(User.createUser(new UserId(1L), email, passwordEncoder.encode("test"))));

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserCreatedDto>> violations = validator.validate(dto);
        assertThat(violations).hasViolationSize(2).hasViolationOnPath("email");
    }

    @Test
    public void testValidIfNoUserWithGivenEmail() {
        String email = "test@mail.com";
        UserCreatedDto dto = new UserCreatedDto();
        dto.setEmail(email);
        dto.setPassword("secret-pwd");

        when(userService.findUserByEmail(email)).thenReturn(Optional.empty());

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserCreatedDto>> violations = validator.validate(dto);
        assertThat(violations).hasNoViolations();
    }

}
