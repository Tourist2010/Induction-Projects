package com.gymmanagement.Interface;


import com.gymmanagement.beans.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface which enables MongoRepository functions
 */
@Transactional
public interface IRepository  extends MongoRepository<Member, Integer> {

}
