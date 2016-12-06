package hu.bme.instagram.entity;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.Set;

@Scope("session")
@Entity
public class User {

    @Id
    private String userId;
    private String name;
    private String googlePictureUrl;
    @OneToMany(mappedBy = "user")
    private Set<Photo> photos;

    public String getGooglePictureUrl() {
        return googlePictureUrl;
    }

    public void setGooglePictureUrl(String googlePictureUrl) {
        this.googlePictureUrl = googlePictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!userId.equals(user.userId)) return false;
        if (!name.equals(user.name)) return false;
        if (!googlePictureUrl.equals(user.googlePictureUrl)) return false;
        return photos != null ? photos.equals(user.photos) : user.photos == null;

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + googlePictureUrl.hashCode();
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }
}
