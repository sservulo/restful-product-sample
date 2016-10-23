package ws.endpoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ws.persistence.enumerate.ImageType;
import ws.persistence.model.Image;
import ws.persistence.model.Product;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestProductEndpointTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testCreateProduct_validProduct() {
		Product parent = new Product("Camaro", "Vintage muscle car");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Product> request = new HttpEntity<Product>(parent, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void testCreateProduct_validProductWithParent() {
		Product parent = new Product("Camaro", "Vintage muscle car");
		parent = insertProduct(parent);
		
		Product child = new Product("Wheel", "Vintage muscle wheel");
		child.setParent(parent);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Product> request = new HttpEntity<Product>(child, headers);
		ResponseEntity<Product> response = restTemplate.postForEntity("/product", request, Product.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void testCreateProduct_nullField1() {
		Product product = new Product(null, "Vintage muscle car");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testCreateProduct_nullField2() {
		Product product = new Product("Camaro", null);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testCreateProduct_emptyField() {
		Product product = new Product("Camaro", "");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testCreateProduct_invalidParent() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product.setParent(new Product());
		product.getParent().setId(-1L);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetProduct_validProduct() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);

		ResponseEntity<Product> response = restTemplate.getForEntity("/product/"+ String.valueOf(product.getId()), Product.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testDeleteProduct_validProduct() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		restTemplate.delete("/product/"+ String.valueOf(product.getId()));
	}
	
	@Test
	public void testGet_uknownProduct() {
		ResponseEntity<String> response = restTemplate.getForEntity("/product/"+ String.valueOf(-1L), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testCreateImage_validImage() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		Image image = new Image(ImageType.PNG);
		image.setProduct(product);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Image> request = new HttpEntity<Image>(image, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product/image", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	public void testCreateImage_nullTypeImage() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		Image image = new Image();
		image.setProduct(product);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Image> request = new HttpEntity<Image>(image, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product/image", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testCreateImage_invalidProduct() {
		Product product = new Product("Camaro", "Vintage muscle car");
		Image image = new Image(ImageType.PNG);
		image.setProduct(product);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Image> request = new HttpEntity<Image>(image, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("/product/image", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetImage_validImage() {
		Product product = insertProduct(new Product("Camaro", "Vintage muscle car"));
		Image image = insertImage(product);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/product/image/" + String.valueOf(image.getId()), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testGetImage_invalidImage() {
		ResponseEntity<String> response = restTemplate.getForEntity("/product/image/" + String.valueOf(-1), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testGetAllProducts() {
		  ResponseEntity<String> response = restTemplate.getForEntity("/product/all", String.class);
		  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testGetAllProducts_excludeChildren() {
		  ResponseEntity<String> response = restTemplate.getForEntity("/product/all?excludeChildren=true", String.class);
		  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testGetAllProducts_excludeImages() {
		  ResponseEntity<String> response = restTemplate.getForEntity("/product/all?excludeImages=true", String.class);
		  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testGetAllProducts_excludeBoth() {
		  ResponseEntity<String> response = restTemplate.getForEntity("/product/all?excludeChildren=true&excludeImages=true", String.class);
		  assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testGetProduct_excludeChildren() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		for(int i = 0; i < 10; i++){
			Product child = new Product("Camaro", "Vintage muscle car");
			child.setParent(product);
			insertProduct(child);
		}
		ResponseEntity<Product> response = restTemplate.getForEntity("/product/"+ String.valueOf(product.getId())+"?excludeChildren=true", Product.class);
		assertTrue(response.getBody().getChildren().isEmpty());
	}
	
	@Test
	public void testGetProduct_excludeImages() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		for(int i = 0; i < 10; i++){
			Product child = new Product("Camaro", "Vintage muscle car");
			child.setParent(product);
			insertProduct(child);
			
			Image image = new Image(ImageType.PNG);
			image.setProduct(product);
		}
		ResponseEntity<Product> response = restTemplate.getForEntity("/product/"+ String.valueOf(product.getId())+"?excludeImages=true", Product.class);
		assertTrue(response.getBody().getImages().isEmpty());
	}
	
	@Test
	public void testGetProduct_excludeBoth() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		for(int i = 0; i < 10; i++){
			Image image = new Image(ImageType.PNG);
			image.setProduct(product);
		}
		ResponseEntity<Product> response = restTemplate.getForEntity("/product/"+ String.valueOf(product.getId())+"?excludeChildren=true&excludeImages=true", Product.class);
		assertTrue(response.getBody().getChildren().isEmpty() && response.getBody().getImages().isEmpty());
	}
	
	@Test
	public void testGetProductChildren() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		for(int i = 0; i < 10; i++){
			Product child = new Product("Camaro", "Vintage muscle car");
			child.setParent(product);
			insertProduct(child);
		}
		ResponseEntity<String> response = restTemplate.getForEntity("/product/"+ String.valueOf(product.getId())+"/children", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void  testGetProductImages() {
		Product product = new Product("Camaro", "Vintage muscle car");
		product = insertProduct(product);
		for(int i = 0; i < 10; i++){
			Image image = new Image(ImageType.PNG);
			image.setProduct(product);
		}
		ResponseEntity<String> response = restTemplate.getForEntity("/product/"+ String.valueOf(product.getId())+"/image", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	private Product insertProduct(Product product) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Product> request = new HttpEntity<Product>(product, headers);
		ResponseEntity<Product> response = restTemplate.postForEntity("/product", request, Product.class);
		return response.getBody();
	}
	
	private Image insertImage(Product product) {
		Image image = new Image(ImageType.PNG);
		image.setProduct(product);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Image> request = new HttpEntity<Image>(image, headers);
		ResponseEntity<Image> response = restTemplate.postForEntity("/product/image", request, Image.class);
		return response.getBody();
	}
}