package utils;

import br.com.isaquebrb.votesession.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DateUtilsTest {

    @Test
    public void toDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2021, 3, 14, 19, 37);
        String formattedDate = DateUtils.toDateTime(dateTime);
        Assertions.assertEquals("14/03/2021 19:37:00", formattedDate);
    }
}
