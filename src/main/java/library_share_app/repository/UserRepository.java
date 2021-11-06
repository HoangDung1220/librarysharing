package library_share_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import library_share_app.entity.UserEntity;

public interface UserRepository extends  JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findOneByUsernameAndPassword(String username,String password);
}
