package com.Twitter.org.Repository;

import com.Twitter.org.Models.Users.Blocks.Blocks;
import com.Twitter.org.Models.Users.Blocks.BlocksId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocksRepository extends CrudRepository<Blocks, BlocksId> {

}