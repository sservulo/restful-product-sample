package ws.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Product {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "parent_product_id")
	private Product parent;
	
	//assuming that once a parent is removed, so should be the children to retain consistency
	@OneToMany(mappedBy="parent", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Product> children = new ArrayList<Product>();
	
	@OneToMany(mappedBy="product", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Image> images = new ArrayList<Image>();
	
	public Product(){}
	
	public Product(String name, String description){
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getParent() {
		return parent;
	}

	public void setParent(Product parent) {
		this.parent = parent;
	}

	public List<Product> getChildren() {
		return children;
	}

	public List<Image> getImages() {
		return images;
	}

}
