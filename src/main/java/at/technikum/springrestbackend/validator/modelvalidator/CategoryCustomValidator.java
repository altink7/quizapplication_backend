package at.technikum.springrestbackend.validator.modelvalidator;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.validator.CustomValidator;
import com.google.common.collect.Lists;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CategoryCustomValidator implements CustomValidator<Category> {

    @Override
    public void validate(Category category) {
        Set<ConstraintViolation<Integer>> violations = new HashSet<>();
        List<Category> allowedCategories = Lists.newArrayList(Category.SPORTS, Category.GEOGRAPHY, Category.HISTORY, Category.CULTURE, Category.SCIENCE, Category.NATURE, Category.UNDEFINED);

        if (!allowedCategories.contains(category)) {
            ConstraintViolation<Integer> violation = new CustomConstraintViolation<>(category + " is not a valid category!");
            violations.add(violation);
        }

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    static class CustomConstraintViolation<T> implements ConstraintViolation<T> {
        private final String message;

        CustomConstraintViolation(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getMessageTemplate() {
            return null;
        }

        @Override
        public T getRootBean() {
            return null;
        }

        @Override
        public Class<T> getRootBeanClass() {
            return null;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return null;
        }

        @Override
        public Object getInvalidValue() {
            return null;
        }

        @Override
        public ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public <U> U unwrap(Class<U> aClass) {
            return null;
        }
    }
}
