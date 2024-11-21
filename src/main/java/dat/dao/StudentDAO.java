package dat.dao;

import dat.config.HibernateConfig;
import dat.daos.IDAO;
import dat.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class StudentDAO implements IDAO<Student> {  // Fjernet Integer type parameter
    private static EntityManagerFactory emf;

    public StudentDAO() {
        emf = HibernateConfig.getEntityManagerFactory(false);
    }

    @Override
    public Student create(Student student) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
            return student;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Student> read(int id) {  // Ændret fra Integer til int
        EntityManager em = emf.createEntityManager();
        try {
            Student student = em.find(Student.class, id);
            return Optional.ofNullable(student);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> readAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Student update(int id, Student student) {  // Tilføjet id parameter
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student updated = em.merge(student);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {  // Ændret fra Integer til int
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}