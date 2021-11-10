package library_share_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import library_share_app.entity.CategoryEntity;
import library_share_app.entity.DocumentEntity;
import library_share_app.entity.UserEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>{
	
	List<DocumentEntity> findByCategory (CategoryEntity category);
	DocumentEntity findOneByIdAndUserNot(Long id,UserEntity user);
}
