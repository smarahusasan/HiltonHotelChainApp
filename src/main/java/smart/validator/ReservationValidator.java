package smart.validator;

import smart.domain.Reservation;
import smart.utils.exceptions.ValidationException;

import java.sql.Date;
import java.time.LocalDate;

public class ReservationValidator implements Validator<Reservation>{
    @Override
    public void validate(Reservation entity) throws ValidationException {
        if(entity.getCheckInDate().before(entity.getReservationDate()) || entity.getCheckInDate().after(entity.getCheckOutDate())){
            throw new ValidationException("Reservation dates are invalid");
        }
    }
}
