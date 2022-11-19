package hu.ponte.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.ponte.hr.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
