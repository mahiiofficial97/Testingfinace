package com.aspiremanagement.repositoryadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aspiremanagement.modeladmin.Items;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long>{

	@Query(value = "Select i From Items i where i.name=:itemName AND i.shortDescription=:shortDescription")
	Items getItemByName(String itemName, String shortDescription);

}
