package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantDao extends JpaRepository<Participant, Long> {
}
