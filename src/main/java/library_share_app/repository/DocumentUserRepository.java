package library_share_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import library_share_app.entity.DocumentUserEntity;
import library_share_app.entity.UserEntity;

public interface DocumentUserRepository extends JpaRepository<DocumentUserEntity, Long> {
	
	List<DocumentUserEntity> findByUser(UserEntity user);

}
