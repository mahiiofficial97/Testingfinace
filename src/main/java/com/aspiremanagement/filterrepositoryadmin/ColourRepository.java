package com.aspiremanagement.filterrepositoryadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.filtermodeladmin.Colour;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Long>{

	@Query(value = "Select c from Colour c where c.categoryId=:categoryId")
	List<Colour> findAllColoursBycategoryId(String categoryId);

	@Query(value = "Select c from Colour c where c.colourName=:colourName AND c.categoryId=:categoryId")
	Colour fetchColour(String colourName, Long categoryId);

}
