package library_share_app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="document")

public class DocumentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String displayFileName;
	
	@Column
	private String fileName;
	
	@Column
	private String description;
	
	@Column
	private boolean status;
	
	@Column
	private String sizeFile;
	
	@Column
	private Date sharedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	
	
	

	public DocumentEntity() {
		super();
	}

	public DocumentEntity(Long id, String displayFileName, String fileName, String description, boolean status,
			String sizeFile, Date sharedDate, CategoryEntity category, UserEntity user) {
		super();
		this.id = id;
		this.displayFileName = displayFileName;
		this.fileName = fileName;
		this.description = description;
		this.status = status;
		this.sizeFile = sizeFile;
		this.sharedDate = sharedDate;
		this.category = category;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayFileName() {
		return displayFileName;
	}

	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(String sizeFile) {
		this.sizeFile = sizeFile;
	}

	public Date getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(Date sharedDate) {
		this.sharedDate = sharedDate;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	

}
