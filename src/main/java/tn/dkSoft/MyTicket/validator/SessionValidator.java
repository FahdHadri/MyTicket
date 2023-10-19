package tn.dkSoft.MyTicket.validator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tn.dkSoft.MyTicket.dto.SessionDto;

@Component
public class SessionValidator {

    public static List<String> validateSession(SessionDto sessionDto) {
        List<String> errors = new ArrayList<>();
        if (sessionDto == null) {
            errors.add("Please fill the session name");
            errors.add("Please fill the starter day");
            errors.add("Please fill the finish day");
            return errors;
        }
        if (StringUtils.isEmpty(sessionDto.getSessionName())) {
            errors.add("Please fill the session name");
        }
        if (StringUtils.isEmpty(sessionDto.getStartDate())) {
            errors.add("Please fill the starter day");
        }
        if (StringUtils.isEmpty(sessionDto.getFinishDate())) {
            errors.add("Please fill the finish day");
        }
        return errors;
    }
}
