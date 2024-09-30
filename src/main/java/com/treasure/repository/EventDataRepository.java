package com.treasure.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.treasure.model.EventData;

@Repository
public interface EventDataRepository extends CrudRepository<EventData, Integer> {

    @Query("SELECT u FROM EventData u WHERE u.status = '0'")
    ArrayList<EventData> findEventDatas();
	
	
}
