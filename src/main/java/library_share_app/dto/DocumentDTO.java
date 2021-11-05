package library_share_app.dto;

import java.util.Date;

public class DocumentDTO {
	
	private Long id;
	private String displayFileName;
	private String fileName;
	private String description;
	private boolean status;
	private String sizeFile;
	private Date SharedDate;
	private Long id_Category;
	private CategoryDTO category;
	private Long id_user;
	private UserDTO user;
	
	
	
	
	public DocumentDTO() {
	}


	public DocumentDTO(Long id, String displayFileName, String fileName, String description, boolean status,
			String sizeFile, Date sharedDate, Long id_Category, CategoryDTO category, Long id_user, UserDTO user) {
		super();
		this.id = id;
		this.displayFileName = displayFileName;
		this.fileName = fileName;
		this.description = description;
		this.status = status;
		this.sizeFile = sizeFile;
		SharedDate = sharedDate;
		this.id_Category = id_Category;
		this.category = category;
		this.id_user = id_user;
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
		return SharedDate;
	}


	public void setSharedDate(Date sharedDate) {
		SharedDate = sharedDate;
	}


	public Long getId_Category() {
		return id_Category;
	}


	public void setId_Category(Long id_Category) {
		this.id_Category = id_Category;
	}


	public CategoryDTO getCategory() {
		return category;
	}


	public void setCategory(CategoryDTO category) {
		this.category = category;
	}


	public Long getId_user() {
		return id_user;
	}


	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}


	public UserDTO getUser() {
		return user;
	}


	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	


}
