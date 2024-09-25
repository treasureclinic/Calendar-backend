package com.treasure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treasure.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	User findByUserId(String userId);
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	// 使用 @Query 注解定义使用 SUBSTRING 函数的查询
    @Query("SELECT u FROM User u WHERE SUBSTRING(u.userId, 1, 3) = :userIdSubstring ORDER BY u.userId")
    List<User> findUsersByUserIdSubstring(@Param("userIdSubstring") String userIdSubstring);
    
    @Query("SELECT u FROM User u WHERE u.status = '0' ORDER BY u.userId")
    ArrayList<User> findUsers();
}
