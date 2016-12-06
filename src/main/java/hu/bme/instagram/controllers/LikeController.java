package hu.bme.instagram.controllers;

import hu.bme.instagram.dal.PhotoRepository;
import hu.bme.instagram.entity.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("session")
public class LikeController {

    @Autowired
    private PhotoRepository photoRepository;

    @PostMapping(value = "/load_likes", produces = "application/json")
    public @ResponseBody String loadLikes(@RequestParam(value = "photo_id", required = true) String photoId,
                     HttpServletRequest request) {
        System.out.println("Load likes post received");
        User user = (User) request.getSession().getAttribute("user");

        return "peace";
    }
}
