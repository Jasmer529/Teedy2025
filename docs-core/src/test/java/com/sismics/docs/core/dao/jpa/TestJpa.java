package com.sismics.docs.core.dao.jpa;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.TransactionUtil;
import com.sismics.docs.core.util.authentication.InternalAuthenticationHandler;
import org.junit.Assert;
import org.junit.Test;

import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.dao.criteria.UserCriteria;
import com.sismics.docs.core.util.jpa.SortCriteria;
import com.sismics.docs.core.model.jpa.User;

import com.sismics.docs.core.dao.dto.UserDto;

import java.util.List;


/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
public class TestJpa extends BaseTransactionalTest {
    @Test
    public void testJpa() throws Exception {
        // Create a user
        UserDao userDao = new UserDao();
        User user = createUser("testJpa");

        TransactionUtil.commit();

        // Search a user by his ID
        user = userDao.getById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals("toto@docs.com", user.getEmail());

        // Authenticate using the database
        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("testJpa", "12345678"));

        // Delete the created user
        userDao.delete("testJpa", user.getId());
        TransactionUtil.commit();
    }

    @Test
    public void testFindByCriteria_searchOnly() throws Exception {
        UserDao userDao = new UserDao();

        User user = createUser("findByCriteriaUser");
        TransactionUtil.commit();

        UserCriteria criteria = new UserCriteria();
        criteria.setSearch("findByCriteriaUser");
        //criteria.setGroupId()
        SortCriteria sortCriteria = new SortCriteria(0,true);

        List<UserDto> users = userDao.findByCriteria(criteria, sortCriteria);

        Assert.assertFalse(users.isEmpty());
        UserDto userDto = users.get(0);
        Assert.assertEquals("findByCriteriaUser", userDto.getUsername());
        Assert.assertEquals("toto@docs.com", userDto.getEmail());
    }

}
