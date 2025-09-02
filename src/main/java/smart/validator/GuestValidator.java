package smart.validator;

import smart.domain.Guest;
import smart.utils.exceptions.ValidationException;

public class GuestValidator implements Validator<Guest>{
    @Override
    public void validate(Guest entity) throws ValidationException {
        if(entity.name().isEmpty() || !entity.name().matches("^[a-zA-Z ]+$"))
            throw new ValidationException("Guest name is invalid");
        if(entity.email().isEmpty() || !entity.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new ValidationException("Guest email is invalid");
        if(entity.phone().isEmpty() || !entity.phone().matches("^[0-9]+$"))
            throw new ValidationException("Guest phone number is invalid");
    }
}
