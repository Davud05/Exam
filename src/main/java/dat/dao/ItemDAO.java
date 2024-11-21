package dat.dao;

import dat.config.HibernateConfig;
import dat.daos.IDAO;
import dat.dto.ItemDTO;
import dat.entities.Item;
import dat.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemDAO implements IDAO<Item> {
    private static EntityManagerFactory emf;
    
    public ItemDAO() {
        emf = HibernateConfig.getEntityManagerFactory(false);
    }
    
    @Override
    public Item create(Item item) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
            return item;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Optional<Item> read(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Item item = em.find(Item.class, id);
            return Optional.ofNullable(item);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Item> readAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Item update(int id, Item item) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Item updated = em.merge(item);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }
    
    @Override
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Item item = em.find(Item.class, id);
            if (item != null) {
                em.remove(item);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public void addItemToStudent(int itemId, int studentId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Item item = em.find(Item.class, itemId);
            Student student = em.find(Student.class, studentId);
            if (item != null && student != null) {
                student.addItem(item);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public Set<ItemDTO> getItemsByStudent(int studentId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT i FROM Item i WHERE i.student.id = :studentId", 
                    Item.class)
                    .setParameter("studentId", studentId)
                    .getResultList()
                    .stream()
                    .map(ItemDTO::from)
                    .collect(Collectors.toSet());
        } finally {
            em.close();
        }
    }
} 