package com.vi.multipleentity.Repo;

import com.vi.multipleentity.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepo extends JpaRepository<User,Integer>
{
}
