package site.quasiphainomenon.organizer3.repositories;

import org.springdoc.core.converters.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.quasiphainomenon.organizer3.domains.Resident;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {

    List<Resident> findByOrderByLastnameAsc();
}
