package com.leon.film;

import com.leon.film.web.dto.FilmCreatedDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.OffsetDateTime;
import java.util.Set;

import static com.leon.utils.ConstraintViolationSetAssert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "TEST")
public class FilmCodeValidatorTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenEmptyString_NotValid() {
        FilmCreatedDto dto = new FilmCreatedDto("", "XXX", OffsetDateTime.now(), "desc", null);
        Set<ConstraintViolation<FilmCreatedDto>> violations = validator.validate(dto);
        assertThat(violations).hasViolationOnPath("code");
    }

    @Test
    public void givenWordPresent_OK() {
        FilmCreatedDto dto = new FilmCreatedDto("ARM", "XXX", OffsetDateTime.now(), "desc", null);
        Set<ConstraintViolation<FilmCreatedDto>> violations = validator.validate(dto);
        assertThat(violations).hasNoViolations();
    }

}
