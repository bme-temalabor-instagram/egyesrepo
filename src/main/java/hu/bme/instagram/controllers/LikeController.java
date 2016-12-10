package hu.bme.instagram.controllers;

import hu.bme.instagram.dal.LikeRepository;
import hu.bme.instagram.dal.PhotoRepository;
import hu.bme.instagram.entity.LikeEntity;
import hu.bme.instagram.entity.Photo;
import hu.bme.instagram.entity.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Scope("session")
public class LikeController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private LikeRepository likeRepository;

    @PostMapping(value = "/load_likes", produces = "application/json")
    public @ResponseBody String loadLikes(@RequestParam(value = "photo_id", required = true) String photoId,
                     HttpServletRequest request) {
        System.out.println("Load likes POST received");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "";

        Photo photo = photoRepository.findOne(photoId);
        if (photo == null)
            return "";

        LikeEntity likeEntity = photo.getLikeEntity();
        List<User> likers = likeEntity.getUserLikes();
        String usernames = "";
        for (int i = 0; i < likers.size(); i++) {
            usernames += likers.get(i).getName();
            if (i != likers.size() - 1) {
                usernames += ", ";
            }
        }
        if (likers.size() == 0) {
            System.out.println("no likers");
        }
        return usernames;
    }

    @PostMapping(value = "/like")
    public @ResponseBody String like(@RequestParam(value = "photo_id", required = true) String photoId,
                       HttpServletRequest request) {
        System.out.println("Like POST request received.");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "user is null";

        Photo photo = photoRepository.findOne(photoId);
        if (photo == null)
            return "photo is null";

        LikeEntity like = photo.getLikeEntity();
        like.addOne(user);
        System.out.println("back to image controller");
        System.out.println(like.getUserLikes().size());
        like = likeRepository.save(like);
        photo.setLikeEntity(like);
        photo = photoRepository.save(photo);

        return "photo liked";
    }

    @PostMapping(value = "/unlike")
    public @ResponseBody String unlike(@RequestParam(value = "photo_id", required = true) String photoId,
                         HttpServletRequest request) {
        System.out.println("Unlike POST request received.");

        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "user is null";

        Photo photo = photoRepository.findOne(photoId);
        if (photo == null)
            return "photo is null";

        LikeEntity like = photo.getLikeEntity();

        boolean successfullyRemoved = like.remove(user);
        if (!successfullyRemoved) {
            System.out.println("User nem volt a like listában");
        }
        if (photo.getLikeEntity().getLikeCount() < 0) {
            System.out.println("- like count?? lol");
        }
        like = likeRepository.save(like);
        photo = photoRepository.save(photo);

        return "photo unliked";
    }
}
