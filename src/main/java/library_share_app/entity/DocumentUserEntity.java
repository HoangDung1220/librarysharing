package library_share_app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="document_user")
public class DocumentUserEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="id_document")
	private DocumentEntity document;
	
	@OneToOne
	@JoinColumn(name="id_user")
	private UserEntity user;
	
	@Column
	private boolean status;
	
	

	public DocumentUserEntity() {
		super();
	}

	public DocumentUserEntity(Long id, DocumentEntity document, UserEntity user, boolean status) {
		super();
		this.id = id;
		this.document = document;
		this.user = user;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DocumentEntity getDocument() {
		return document;
	}

	public void setDocument(DocumentEntity document) {
		this.document = document;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
