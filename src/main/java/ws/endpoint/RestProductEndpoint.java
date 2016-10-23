package ws.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ws.constants.ErrorMessage;
import ws.persistence.model.Image;
import ws.persistence.model.Product;
import ws.persistence.repository.ImageRepository;
import ws.persistence.repository.ProductRepository;
import ws.validator.ImageValidator;
import ws.validator.ProductValidator;

/**
 * REST implementation of API Endpoint Contract specified on ProductEndpoint. 
 * @author Samuel
 * @see ProductEndpoint
 */
@Component
@Path("/product")
public class RestProductEndpoint implements ProductEndpoint {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	ImageRepository imageRepo;
	
	@Autowired
	ProductValidator productValidator;
	
	@Autowired
	ImageValidator imageValidator;
	
	@Override
	@POST
	@Consumes("application/json")
	@Produces("application/json")
    public Response createProduct(Product p) {
		if(!productValidator.hasValidFields(p)){
			return Response.status(400).entity(ErrorMessage.EMPTY_FIELDS).build();
		}
		if(!productValidator.hasValidParent(p)){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PARENT_PRODUCT).build();
		}
		productRepo.save(p);
		p = productRepo.findOne(p.getId());
		return Response.status(201).entity(p).build();
    }
	

	@Override
	@GET
	@Path("/{id}")
	@Produces("application/json")
    public Response getProduct(
    		@PathParam("id") Long id,
    		@DefaultValue("false") @QueryParam("excludeChildren") Boolean excludeChildren,
    		@DefaultValue("false") @QueryParam("excludeImages") Boolean excludeImages) {
		Product p = productRepo.findOne(id);
		if(p == null){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PRODUCT).build();
		}
		p = excludeProductRelations(p, excludeChildren, excludeImages);
		return Response.status(200).entity(p).build();
    }
	
	@Override
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
    public Response updateProduct(@PathParam("id") Long id, Product p) {
		if(!productValidator.hasValidFields(p)){
			return Response.status(400).entity(ErrorMessage.EMPTY_FIELDS).build();
		}
		if(!productValidator.hasValidParent(p)){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PARENT_PRODUCT).build();
		}
		p.setId(id);
		productRepo.save(p);
		return Response.status(200).build();
    }
	
	@Override
	@DELETE
	@Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
		if(productRepo.findOne(id) == null){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PRODUCT).build();
		}
        productRepo.delete(id);
        return Response.status(200).build();
    }	
	
	@Override
	@POST
	@Path("/image")
	@Consumes("application/json")
	@Produces("application/json")
    public Response createImage(Image i) {
		if(!imageValidator.hasValidFields(i)){
			return Response.status(400).entity(ErrorMessage.EMPTY_FIELDS).build();
		}
		if(!imageValidator.hasValidProduct(i)){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PRODUCT).build();
		}
		imageRepo.save(i);
		i = imageRepo.findOne(i.getId());
		return Response.status(201).entity(i).build();
    }
	
	@Override
	@GET
	@Path("/image/{id}")
	@Produces("application/json")
    public Response getImage(@PathParam("id") Long id) {
		if(imageRepo.findOne(id) == null){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_IMAGE).build();
		}
		Image i = imageRepo.findOne(id);
		return Response.status(200).entity(i).build();
    }
	
	@Override
	@PUT
	@Path("/image/{id}")
	@Consumes("application/json")
    public Response updateImage(@PathParam("id") Long id, Image i) {
		if(!imageValidator.hasValidFields(i)){
			return Response.status(400).entity(ErrorMessage.EMPTY_FIELDS).build();
		}
		if(!imageValidator.hasValidProduct(i)){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PRODUCT).build();
		}
		i.setId(id);
		imageRepo.save(i);
		return Response.status(200).build();
    }
	
	@Override
	@DELETE
	@Path("/image/{id}")
    public Response deleteImage(@PathParam("id") Long id) {
		if(imageRepo.findOne(id) == null){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_IMAGE).build();
		}
        imageRepo.delete(id);
        return Response.status(200).build();
    }
	
	@Override
	@GET
	@Path("/all")
	@Produces("application/json")
    public List<Product> getAllProducts(
    		@DefaultValue("false") @QueryParam("excludeChildren") Boolean excludeChildren,
    		@DefaultValue("false") @QueryParam("excludeImages") Boolean excludeImages) {
		Iterable<Product> products = productRepo.findAll();
		return excludeRelations(products, excludeChildren, excludeImages);
    }
	
	@Override
	@GET
	@Path("/{id}/children")
	@Produces("application/json")
    public Response getProductChildren(@PathParam("id") Long id) {
		Product p = productRepo.findOne(id);
		if(p == null){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PRODUCT).build();
		}
		return Response.status(200).entity(p.getChildren()).build();
    }
	
	@Override
	@GET
	@Path("/{id}/image")
	@Produces("application/json")
    public Response getProductImages(@PathParam("id") Long id) {
		Product p = productRepo.findOne(id);
		if(productRepo.findOne(id) == null){
			return Response.status(404).entity(ErrorMessage.UNKNOWN_PRODUCT).build();
		}
		return Response.status(200).entity(p.getImages()).build();
    }
	
	/**
	 * <pre>
	 * Internal method to remove child and image collections from Product object for output purpose.
	 * @param product Product object to be striped from collections 
	 * @param excludeChildren (optional) removes child collection from the returned object. Defaults to false.
	 * @param excludeImages (optional) removes image collection from the returned object. Defaults to false.
	 * @return product Product object striped from specified collections
	 * </pre> 
	 */
	private Product excludeProductRelations(Product p, boolean excludeChildren, boolean excludeImages){
		if(excludeChildren){ p.getChildren().clear(); }			
		if(excludeImages){ p.getImages().clear(); }
		return p;
	}
	
	/**
	 * <pre>
	 * Internal method to remove child and image collections from Product objects contained in an Iterable.
	 * @param products Iterable from whom objects will be striped from collections 
	 * @param excludeChildren (optional) removes child collection from the objects.
	 * @param excludeImages (optional) removes image collection from the objects.
	 * @return List<Product> List of Product objects striped from specified collections
	 * </pre> 
	 */
	private List<Product> excludeRelations(Iterable<Product> products, boolean excludeChildren, boolean excludeImages){
		List<Product> output = new ArrayList<Product>();
		for(Product p : products){
			p = excludeProductRelations(p, excludeChildren, excludeImages);
		}
		return output;
	}

}
