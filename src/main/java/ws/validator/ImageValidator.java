package ws.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ws.persistence.model.Image;
import ws.persistence.repository.ProductRepository;

/**
 * Image validator for user input verification.
 * @author Samuel
 *
 */
@Component
public class ImageValidator {
	@Autowired
	ProductRepository productRepo;

	/**
	 * Verifies if provided Image has a valid non-null type field (meaning it already is one of the supported type extensions).
	 * @param image Image from whom type will be verified
	 * @return boolean if valid
	 * @see ImageType
	 */
	public boolean hasValidFields(Image image){
		return image.getType() != null;
	}
	
	/**
	 * Verifies if provided Image has a non-null and valid product reference (in database).
	 * @param image Image from whom product will be verified
	 * @return boolean if valid
	 */
	public boolean hasValidProduct(Image image){
		return image.getProduct() != null && image.getProduct().getId() != null && productRepo.findOne(image.getProduct().getId()) != null;
	}
}
