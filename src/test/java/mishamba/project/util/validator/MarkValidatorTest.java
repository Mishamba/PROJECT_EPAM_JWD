package mishamba.project.util.validator;

import com.mishamba.project.util.validator.MarkValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MarkValidatorTest {

    @Test
    public void testIsCorrectValid() {
        MarkValidator markValidator = new MarkValidator();
        assertTrue(markValidator.isCorrect(8));
    }

    @Test
    public void testIsCorrectInvalid() {
        MarkValidator markValidator = new MarkValidator();
        assertFalse(markValidator.isCorrect(14));
    }
}