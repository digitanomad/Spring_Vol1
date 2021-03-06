package springbook.user.dao;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
//@DirtiesContext
public class UserDaoTest {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;

	@Before
	public void setUp() {
//		ApplicationContext context = new GenericXmlApplicationContext(
//				"applicationContext.xml");
//		this.dao = this.context.getBean("userDao", UserDao.class);
		
		this.user1 = new User("gyumee", "박성철", "springno1");
		this.user2 = new User("leegw700", "이길원", "springno2");
		
//		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "", true);
//		dao.setDataSource(dataSource);
	}

	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		
		dao.add(user1);
		
		User user2 = dao.get(user1.getId());
		
		Assert.assertThat(user2.getName(), is(user1.getName()));
		Assert.assertThat(user2.getPassword(), is(user1.getPassword()));
	}
	
	@Test
	public void count() throws SQLException {
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		Assert.assertThat(dao.getCount(), is(2));

		User userget1 = dao.get(user1.getId());
		Assert.assertThat(user1.getName(), is(userget1.getName()));
		Assert.assertThat(user1.getPassword(), is(userget1.getPassword()));

		User userget2 = dao.get(user2.getId());
		Assert.assertThat(userget2.getName(), is(userget2.getName()));
		Assert.assertThat(user2.getPassword(), is(userget2.getPassword()));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		Assert.assertThat(dao.getCount(), is(2));

		User userget1 = dao.get("aaaa");
		Assert.assertThat(user1.getName(), is(userget1.getName()));
		Assert.assertThat(user1.getPassword(), is(userget1.getPassword()));

		User userget2 = dao.get(user2.getId());
		Assert.assertThat(userget2.getName(), is(userget2.getName()));
		Assert.assertThat(user2.getPassword(), is(userget2.getPassword()));
	}

}
