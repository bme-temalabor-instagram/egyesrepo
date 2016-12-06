package hu.bme.instagram.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Like {

    private int likeCount = 0;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<User> likes;

    public Like() {
        likeCount = 0;
        likes = new ArrayList<>();
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public void addOne(User user) {
        likes.add(user);
        likeCount += 1;
    }

    public void remove(User user) {
        likes.remove(user);
        likeCount -= 1;
    }
}
