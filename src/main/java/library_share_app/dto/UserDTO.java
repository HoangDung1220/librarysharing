package library_share_app.dto;

public class UserDTO {
	
	private Long id;
	private String fullname;
	private String gmail;
	private String nameRoom;
	private String username;
	private String password;
	private boolean status;
	
	
	
	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String fullname, String gmail, String nameRoom, String username, String password,
			boolean status) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.gmail = gmail;
		this.nameRoom = nameRoom;
		this.username = username;
		this.password = password;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getNameRoom() {
		return nameRoom;
	}

	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

}
