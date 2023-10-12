package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = Util.getInstance();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("""
                    create table if not exists User
                    (
                    id int primary key auto_increment not null,
                    name varchar(50),
                    lastName varchar(50),
                    age int
                    )
                    """).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignore) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User;").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignore) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        try {
            List<User> list;
            session = util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            return list;
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
