package ws.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ws.persistence.model.Product;
import ws.persistence.repository.ProductRepository;

@Component
public class ProductValidator {
	@Autowired
	ProductRepository productRepo;
	
	/**
	 * Verifies if provided Product has valid non-null/non-empty fields.
	 * @param product Product from whom fields will be verified
	 * @return boolean if valid
	 */
	public boolean hasValidFields(Product product){
		//verify if fields are non empty
		return product.getName() != null && !product.getName().isEmpty() && 
				product.getDescription() != null && !product.getDescription().isEmpty();
	}
	
	/**
	 * Verifies if provided Product has a non-null and valid parent reference (in database).
	 * @param product Product from whom parent will be verified
	 * @return boolean if valid
	 */
	public boolean hasValidParent(Product product){
		//has no parent or provided parent exists in database
		return product.getParent() == null || 
				(product.getParent() != null && product.getParent().getId() != null && productRepo.findOne(product.getParent().getId()) != null);
	}
}
