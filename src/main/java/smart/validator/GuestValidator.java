package smart.validator;

import smart.domain.Guest;
import smart.utils.exceptions.ValidationException;

public class GuestValidator implements Validator<Guest>{
    @Override
    public void validate(Guest entity) throws ValidationException {
        if(entity.getName().isEmpty())
            throw new ValidationException("Guest name is required");
        if(entity.getEmail().isEmpty() && !entity.getEmail().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$"))
            throw new ValidationException("Guest email is invalid");
        if(entity.getPhone().isEmpty() || !entity.getPhone().matches("^[0-9]+$"))
            throw new ValidationException("Guest phone number is invalid");
    }
}
