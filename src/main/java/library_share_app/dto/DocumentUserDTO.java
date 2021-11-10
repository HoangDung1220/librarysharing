package library_share_app.dto;


public class DocumentUserDTO {
	
	private Long id;
	private Long id_document;
	private DocumentDTO document;
	private Long id_user;
	private UserDTO user;
	private boolean status;
	
	
	
	public DocumentUserDTO() {
		super();
	}

	public DocumentUserDTO(Long id, Long id_document, DocumentDTO document, Long id_user, UserDTO user,
			boolean status) {
		super();
		this.id = id;
		this.id_document = id_document;
		this.document = document;
		this.id_user = id_user;
		this.user = user;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_document() {
		return id_document;
	}

	public void setId_document(Long id_document) {
		this.id_document = id_document;
	}

	public DocumentDTO getDocument() {
		return document;
	}

	public void setDocument(DocumentDTO document) {
		this.document = document;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

}
