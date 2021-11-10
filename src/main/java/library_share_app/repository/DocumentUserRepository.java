package library_share_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_share_app.entity.DocumentUserEntity;

public interface DocumentUserRepository extends JpaRepository<DocumentUserEntity, Long> {

}
