package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dkSoft.MyTicket.model.Order;

public interface OrderRepository extends JpaRepository <Order,Long> {
}
