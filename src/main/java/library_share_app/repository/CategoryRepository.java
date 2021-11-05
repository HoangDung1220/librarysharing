package library_share_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_share_app.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

}
