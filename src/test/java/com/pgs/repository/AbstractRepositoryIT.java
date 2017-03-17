package com.pgs.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wkloc on 2017-02-24.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractRepositoryIT {

    @Autowired
    protected TestEntityManager entityManager;

}
