package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tn.dkSoft.MyTicket.model.CartItem;
import tn.dkSoft.MyTicket.model.Tickets;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci.tickets FROM CartItem ci WHERE ci.tickets.id = ?1")
    Tickets findTicketsById(Long ticketId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.tickets.id = ?2")
    CartItem findCartItemByTicketIdAndCartId(Long cartId, Long ticketId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.tickets.id = ?2")
    void deleteCartItemByTicketIdAndCartId(Long cartId, Long ticketId);

}
