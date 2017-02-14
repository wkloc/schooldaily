package com.pgs.repository;

import com.pgs.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mmalek on 2/14/2017.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
