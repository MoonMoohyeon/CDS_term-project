package cds.team20.whiteboard.repository;

import cds.team20.whiteboard.entity.UserEntity;
import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository
//        extends CrudRepository<UserEntity, Long> {
{
    Optional<UserEntity> findByUserIndex(String userIndex);
}
