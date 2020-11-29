package mishamba.project.util.validator;

import com.mishamba.project.util.validator.DateValidator;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.testng.Assert.*;

public class DateValidatorTest {

    @Test
    public void testCheckForFutureFuture() {
        DateValidator dateValidator = new DateValidator();
        Calendar date = new GregorianCalendar();
        date.set(2021, Calendar.AUGUST, 4);
        assertTrue(dateValidator.checkForFuture(date.getTime()));
    }

    @Test
    public void testCheckForFuturePast() {
        DateValidator dateValidator = new DateValidator();
        Calendar date = new GregorianCalendar();
        date.set(2019, Calendar.AUGUST, 4);
        assertFalse(dateValidator.checkForFuture(date.getTime()));
    }
}