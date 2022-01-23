package app.component;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name="user") // Use this line just to be safe and avoid "doesn't exist"
public class User{
	@Id @GeneratedValue(generator="idGen")
	@GenericGenerator(name="idGen",
		parameters = @Parameter(name = "length", value = "6"), // parameter is length of id we want generated
		strategy= "app.custom.CustomIdGenerator")
	private String id;
	
	@Column(unique = true)
	private String handle;

	private String password;
	private byte[] salt;
	
	private String name;
	
	//@Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")// for h2 only(?)
	private Date created;
	
	// ***** Constructors *****
	public User() {
	}
	public User(String id, String handle) {
		this.id = id;
		this.handle = handle;
	}
	public User(String id, String handle, String name) {
		this.id = id;
		this.handle = handle;
		this.name = name;
	}
	public User(String id, String handle, String name, Date created) {
		this.id = id;
		this.handle = handle;
		this.name = name;
		this.created = created;
	}
	// ***** Getters & Setters *****
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
		public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	// ***** Interfaces *****
	interface IdHandleOnly{
		String getId();
		String getHandle();
	}
	
	// ***** Methods *****
	public void checkName() {
		// Default name is set to handle
		// Or maybe set to ""
		if(this.getName() == null) {
			System.out.println(":: Name is NULL, setting to default");
			this.setName(this.handle);
		}else if(this.getName().isEmpty()) {
			System.out.println(":: Name is EMPTY, setting to default");
			// Maybe set a minimum requirement check ?
			// at least a letter, number, special character ?
			this.setName(this.handle);
		}
	}// checkName()
	
	public void hideInfo() {
		this.setPassword(null);
		this.setSalt(null);
	}// hideInfo()
}// - User





