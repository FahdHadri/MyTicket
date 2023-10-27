package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dkSoft.MyTicket.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
