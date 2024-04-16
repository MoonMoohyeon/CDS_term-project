package cds.team20.whiteboard.repository;

import cds.team20.whiteboard.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail); //email로 user정보 가져옴
}
