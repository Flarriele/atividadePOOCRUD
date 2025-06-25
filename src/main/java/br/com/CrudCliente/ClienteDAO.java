package br.com.CrudCliente;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query; // Importe Query para HQL/JPQL

import java.util.List;

public class ClienteDAO {

    /**
     * Salva um novo cliente no banco de dados.
     */
    public void salvar(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction(); // Inicia uma transação
            session.save(cliente); // Persiste o objeto Cliente
            transaction.commit(); // Confirma a transação
            System.out.println("Cliente salvo com sucesso: " + cliente.getNome());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Em caso de erro, desfaz a transação
            }
            System.err.println("Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca um cliente pelo seu ID.
     */
    public Cliente buscarPorId(Long id) {
        try (Session session = HibernateUtil.openSession()) {
            // Usa session.get() para buscar um objeto pelo ID
            return session.get(Cliente.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Atualiza um cliente existente no banco de dados.
     */
    public void atualizar(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            session.update(cliente); // Atualiza o objeto Cliente
            transaction.commit();
            System.out.println("Cliente atualizado com sucesso: " + cliente.getNome());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exclui um cliente do banco de dados.
     */
    public void excluir(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.openSession()) {
            transaction = session.beginTransaction();
            session.delete(cliente); // Exclui o objeto Cliente
            transaction.commit();
            System.out.println("Cliente excluído com sucesso: " + cliente.getNome());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lista todos os clientes no banco de dados.
     */
    public List<Cliente> listarTodos() {
        try (Session session = HibernateUtil.openSession()) {
            Query<Cliente> query = session.createQuery("from Cliente", Cliente.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os clientes: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}