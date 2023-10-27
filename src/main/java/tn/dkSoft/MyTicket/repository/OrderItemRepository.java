package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dkSoft.MyTicket.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
