package com.leon.utils;

import org.assertj.core.api.AbstractAssert;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class ConstraintViolationSetAssert extends AbstractAssert<ConstraintViolationSetAssert, Set<? extends ConstraintViolation>> {

    private ConstraintViolationSetAssert(Set<? extends ConstraintViolation> actual) {
        super(actual, ConstraintViolationSetAssert.class);
    }

    public static ConstraintViolationSetAssert assertThat(Set<? extends ConstraintViolation> actual) {
        return new ConstraintViolationSetAssert(actual);
    }

    public ConstraintViolationSetAssert hasViolationOnPath(String path) {
        isNotNull();
        if (!containsViolationWithPath(actual, path)) {
            failWithMessage("There was no violation with path <%s>. Violation paths: <%s>", path,
                    actual.stream().map(violation -> violation.getPropertyPath().toString()).collect(toList()));
        }

        return this;
    }

    public ConstraintViolationSetAssert hasNoViolations() {
        isNotNull();
        if (!actual.isEmpty()) {
            failWithMessage("Expecting no violations, but there are %s violations", actual.size());
        }

        return this;
    }

    private boolean containsViolationWithPath(Set<? extends ConstraintViolation> violations, String path) {
        boolean result = false;
        for (ConstraintViolation violation : violations) {
            if (violation.getPropertyPath().toString().equals(path)) {
                result = true;
                break;
            }
        }

        return result;
    }

}
