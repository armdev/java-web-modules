package backend.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author armen
 */
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String email;
    private String passwd;
    private String firstname;
    private String lastname;
    private Integer userRole;
    private Date registeredDate;
    private String imageId;
    private Integer status;
    private Date lastLoginDate;
    private String languageCode;
    private Integer moderatorValue;
    private boolean moderator = false;
    private String profileImageURL;
    private String username;
    private boolean accept = false;
    private boolean acceptSubscr = false;
    private String personalWebPage;

    public String getPersonalWebPage() {
        return personalWebPage;
    }

    public void setPersonalWebPage(String personalWebPage) {
        this.personalWebPage = personalWebPage;
    }    
    
    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean isAcceptSubscr() {
        return acceptSubscr;
    }

    public void setAcceptSubscr(boolean acceptSubscr) {
        this.acceptSubscr = acceptSubscr;
    }   
    

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Integer getModeratorValue() {
        return moderatorValue;
    }

    public void setModeratorValue(Integer moderatorValue) {
        this.moderatorValue = moderatorValue;
    }

    public boolean isModerator() {
        return moderator;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
 
    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEntity other = (UserEntity) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + '}';
    }
}
