package lk.ijse.gdse.springboot_practice.repository;

import jakarta.transaction.Transactional;
import lk.ijse.gdse.springboot_practice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    @Modifying
    @Transactional
    @Query(value = "update Event set event_status = 'Completed' where id = ?",nativeQuery = true)
    void changeEventStatus(String id);

    List<Event> findEventByEventNameContainingIgnoreCase(String keyword);
}
