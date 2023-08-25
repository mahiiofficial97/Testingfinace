package com.aspiremanagement.repositoryadmin;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.aspiremanagement.modeladmin.Logs;


public interface LogsRepository extends JpaRepository<Logs,Integer> {

	@Modifying
	@Transactional
	@Query(value ="insert into logs (by_id, msg,starttime,endtime,status) VALUES (?1, ?2 ,?3, ?4, ?5)", nativeQuery = true)
	int saveData(Object object, Object object2, Object object3, Object object4, Object object5);

}
