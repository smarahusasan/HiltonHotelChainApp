package smart.validator;

import smart.domain.Hotel;
import smart.utils.exceptions.ValidationException;

public class HotelValidator implements Validator<Hotel>{
    @Override
    public void validate(Hotel entity) throws ValidationException {
        if(entity.getName().isEmpty())
            throw new ValidationException("Hotel name is required");
        if(entity.getLocation().isEmpty())
            throw new ValidationException("Hotel location is required");
    }
}
