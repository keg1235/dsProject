package com.sara.project.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    int countById(Long id);

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM USER", nativeQuery = true)
    Long findMaxValue();
//    @Query(value = "SELECT * FROM USER WHERE EMAIL = :email AND U:idList", nativeQuery = true)
    User findByEmailAndPassword(String email, String userPwd);

    @Modifying
    @Query(value = "DELETE FROM USER WHERE ID IN :idList", nativeQuery = true)
    void deleteByIdIn(List<Long> idList);


    User findByEmail(String subId);


    @Query(value = "SELECT count(*) from USER where EMAIL = ?1 and PASSWORD =  ?2", nativeQuery = true)
    long countByEmailAndPassword(String email, String password);

    List<User> findByIdUseYn(String yes);
}
