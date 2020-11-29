package mishamba.project.util.validator;

import com.mishamba.project.util.validator.EmailValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EmailValidatorTest {

    @Test
    public void testValidateValid() {
        EmailValidator emailValidator = new EmailValidator();
        String email = "misha.nenahov@gmail.com";
        assertTrue(emailValidator.validate(email));
    }

    @Test
    public void testValidateInvalid() {
        EmailValidator emailValidator = new EmailValidator();
        String invalidEmail = "invalid.email.gmail.com";
        assertFalse(emailValidator.validate(invalidEmail));
    }
}