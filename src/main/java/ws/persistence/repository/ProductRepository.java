package ws.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import ws.persistence.model.Product;

/**
 * JPA Repository interface for Image persistence. Automatically provides methods to save, delete and retrieve objects.
 * @author Samuel
 *
 */
public interface ProductRepository extends CrudRepository<Product,Long>{
}
