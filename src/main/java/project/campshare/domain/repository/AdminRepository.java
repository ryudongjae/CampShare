package project.campshare.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.campshare.domain.model.admin.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {

}
