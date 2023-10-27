package tn.dkSoft.MyTicket.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@RequiredArgsConstructor
@Data
public class UserDto  implements Serializable {

    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String password;
    //private Set<Role> roles = new HashSet<> ();
    @JsonIgnore
    private CartDto cart;


}
