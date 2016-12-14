package hu.bme.instagram.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int likeId;
    private int likeCount=0;
    @ManyToMany
    @JoinColumn(name ="userLikes")
    private List<User> userLikes;

    public LikeEntity() {
        likeCount = 0;
        userLikes = new ArrayList<>();
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<User> userLikes) {
        this.userLikes = userLikes;
    }

    public void addOne(User user) {
        userLikes.add(user);
        likeCount += 1;
    }

    public boolean remove(User user) {
        likeCount -= 1;
        return userLikes.remove(user);
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }
}
