package hu.bme.instagram.controllers;

import hu.bme.instagram.dal.PhotoRepository;
import hu.bme.instagram.entity.Like;
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

    @PostMapping(value = "/load_likes", produces = "application/json")
    public @ResponseBody String loadLikes(@RequestParam(value = "photo_id", required = true) String photoId,
                     HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return "";

        Photo photo = photoRepository.findOne(photoId);
        if (photo == null)
            return "";

        Like like = photo.getLike();

        return "peace";
    }
}
