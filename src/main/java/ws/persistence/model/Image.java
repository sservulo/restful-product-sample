package ws.persistence.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import ws.persistence.enumerate.ImageType;

@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Image {
	
    /**
     * Hibernate managed unique identifier.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;	
	
	@Enumerated(EnumType.STRING)
	ImageType type;
	
	
	/**
	 * Relation with associated product.
	 */
	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
	
	public Image() {}
	
	public Image(ImageType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ImageType getType() {
		return type;
	}

	public void setType(ImageType type) {
		this.type = type;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
