package library_share_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_share_app.entity.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>{

}
