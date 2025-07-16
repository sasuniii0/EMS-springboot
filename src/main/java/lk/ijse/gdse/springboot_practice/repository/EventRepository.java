package lk.ijse.gdse.springboot_practice.repository;

import lk.ijse.gdse.springboot_practice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
}
