
package com.orderservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class QueryUtils {
    public static boolean isTrue(Object input) {
        if (input == null) return false;

        if (input instanceof Number) {
            return !input.equals(0);
        }

        if (input instanceof String) {
            return !StringUtils.isEmpty(input);
        }

        if (input instanceof Collection) {
            return !((Collection<?>) input).isEmpty();
        }
        return true;
    }
    public static <T, P> Predicate filter(Root<T> root, CriteriaBuilder cb, P value, String... fields) {
        if (!isTrue(value)) {
            return cb.and();
        }
        List<Predicate> predicateList = Arrays.stream(fields).map(field -> cb.and(cb.equal(root.get(field), value))).collect(Collectors.toList());
        return cb.and(predicateList.toArray(new Predicate[]{}));
    }
}