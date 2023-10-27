package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.dkSoft.MyTicket.model.Cart;
import tn.dkSoft.MyTicket.model.CartItem;

public interface CartRepository extends JpaRepository<Cart,Long> {
   // @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.id = ?2")
    //Cart findCartByEmailAndCartId(String emailId, Long cartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.tickets.id = :ticketId")
    CartItem findCartItemByTicketIdAndCartId(@Param ("cartId") Long cartId, @Param("ticketId") Long ticketId);
}
