package tn.dkSoft.MyTicket.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto implements Serializable {


    private Long cartId;
    private List<CartItemDto> cartItemDtos = new ArrayList<> ();
    private Double totalPrice = 0.0;
}
