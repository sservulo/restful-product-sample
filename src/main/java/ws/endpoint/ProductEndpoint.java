package ws.endpoint;

import java.util.List;

import javax.ws.rs.core.Response;

import ws.persistence.model.Image;
import ws.persistence.model.Product;

/**
 * API Endpoint contract for the Product/Image service.
 * @author Samuel
 *
 */
public interface ProductEndpoint {
	/**
	 * <pre>
	 * Endpoint for Product creation in the service. Input is validated for non-null/non-empty fields and known parent reference before insertion.
	 * @param product marshaled Product object to be created
	 * @return JAX-RS HTTP Response containing the persisted object or error message in the body 
	 * 201 - Object successfully persisted
	 * 400 - Null or empty fields detected
	 * 404 - Unknown parent reference detected
	 * </pre>
	 */
    public Response createProduct(Product product);
	
	/**
	 * <pre>
	 * Endpoint for Product retrieval in the service. Defaults to returning the Product object with contained collections (children and images).
	 * This method is a non-redundant approach providing retrieval operation and requirements 2.c and 2.d. 
	 * @param id of the Product to be retrieved
	 * @param excludeChildren (optional) removes child collection from the returned object. Defaults to false.
	 * @param excludeImages (optional) removes image collection from the returned object. Defaults to false.
	 * @return JAX-RS HTTP Response containing the retrieved object or error message in the body
	 * 200 - Successful call
	 * 404 - Unknown id reference detected
	 * </pre>
	 */
    public Response getProduct(Long id, Boolean excludeChildren, Boolean excludeImages);
	
	/**
	 * <pre>
	 * Endpoint for Product update in the service. Input is validated for non-null/non-empty fields and known parent reference before insertion.
	 * @param id of the Product to be updated
	 * @param product marshaled Product object to be updated
	 * @return JAX-RS HTTP Response with empty body or error message
	 * 200 - Successful call
	 * 400 - Null or empty fields detected
	 * 404 - Unknown parent reference detected
	 * </pre>
	 */
    public Response updateProduct(Long id, Product product);
	
	/**
	 * <pre>
	 * Endpoint for Product deletion in the service.
	 * @param id of the Product to be deleted
	 * @return JAX-RS HTTP Response with empty body or error message
	 * 200 - Successful call
	 * 404 - Unknown id reference
	 * </pre>
	 */	
    public Response deleteProduct(Long id);
	
	/**
	 * <pre>
	 * Endpoint for Image creation in the service. Input is validated for non-null type and known product reference before insertion.
	 * @param image marshaled Image object to be created
	 * @return JAX-RS HTTP Response containing the persisted object or error message in the body 
	 * 201 - Object successfully persisted
	 * 400 - Null type field detected
	 * 404 - Unknown product reference detected
	 * </pre>
	 */
    public Response createImage(Image i);
	
	/**
	 * <pre>
	 * Endpoint for Image retrieval in the service.
	 * @param id of the Image to be retrieved
	 * @return JAX-RS HTTP Response containing the retrieved object or error message in the body
	 * 200 - Successful call
	 * 404 - Unknown id reference
	 * </pre>
	 */
    public Response getImage(Long id);
	
	/**
	 * <pre>
	 * Endpoint for Image update in the service. Input is validated for non-null type and known product reference before insertion.
	 * @param id of the Image to be updated
	 * @param image marshaled Image object to be updated
	 * @return JAX-RS HTTP Response with empty body or error message
	 * 200 - Successful call
	 * 400 - Null type field detected
	 * 404 - Unknown product reference
	 * </pre>
	 */
    public Response updateImage(Long id, Image i);
	
	/**
	 * <pre>
	 * Endpoint for Image deletion in the service.
	 * @param id of the Image to be deleted
	 * @return JAX-RS HTTP Response with empty body or error message
	 * 200 - Successful call
	 * 404 - Unknown id reference
	 * </pre>
	 */	
    public Response deleteImage(Long id);
	
	/**
	 * <pre>
	 * Endpoint for retrieval of ALL the Products in the service. Defaults to returning the Product object with contained collections (children and images).
	 * This method is a non-redundant approach providing retrieval operation for requirements 2.a and 2.b. 
	 * @param excludeChildren (optional) removes child collection from the returned object. Defaults to false.
	 * @param excludeImages (optional) removes image collection from the returned object. Defaults to false.
	 * @return List<Product> containing ALL Product objects
	 * </pre>
	 */
    public List<Product> getAllProducts(Boolean excludeChildren, Boolean excludeImages);
	
	/**
	 * <pre>
	 * Endpoint for retrieval of only the children from given Product in the service. 
	 * @param id of the Product from whom children will be retrieved
	 * @return JAX-RS HTTP Response containing a list of objects or error message in the body
	 * 200 - Successful call
	 * 404 - Unknown id reference
	 * </pre>
	 */
    public Response getProductChildren(Long id);
	
	/**
	 * <pre>
	 * Endpoint for retrieval of only the images from given Product in the service. 
	 * @param id of the Product from whom images will be retrieved
	 * @return JAX-RS HTTP Response containing a list of objects or error message in the body
	 * 200 - Successful call
	 * 404 - Unknown id reference
	 * </pre>
	 */
    public Response getProductImages(Long id);
}
