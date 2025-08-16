package smart.validator;

import smart.utils.exceptions.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}