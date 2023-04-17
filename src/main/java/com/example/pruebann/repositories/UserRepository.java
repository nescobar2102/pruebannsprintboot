package com.example.pruebann.repositories;
import com.example.pruebann.model.User;
//import com.odhiambopaul.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
     User findByEmail(String email);
}