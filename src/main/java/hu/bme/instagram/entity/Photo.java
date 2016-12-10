package hu.bme.instagram.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@NamedQuery(
        name = "Photo.searchForTitle",
        query = "select p from Photo p where p.title like :tag"
)
public class Photo {

    @Id
    private String public_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Size(min = 1)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user")
    @NotNull
    private User user;

    @OneToOne
    private LikeEntity likeEntity;

    public LikeEntity getLikeEntity() {
        return likeEntity;
    }

    public void setLikeEntity(LikeEntity likeEntity) {
        this.likeEntity = likeEntity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
