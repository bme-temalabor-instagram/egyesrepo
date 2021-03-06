package hu.bme.instagram.controllers;


import com.cloudinary.Transformation;
import hu.bme.instagram.dal.PhotoRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hu.bme.instagram.entity.Photo;
import hu.bme.instagram.entity.User;

import java.util.*;

import static hu.bme.instagram.Utilities.Constants.cloudinary;


@Controller
@Scope("session")
public class ImageController {

    private static String uploadedPhotoName;

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping("/result")
    public String getResultPage(@ModelAttribute Photo photo) {
        return "result";
    }

    @GetMapping("/main")
    public String loadAllPictures(Model model,
                                  HttpServletRequest request) {
        List<Photo> photos = photoRepository.findAll();
        addImagesToModel(model, photos);

        return loadMain(model, request, photos);
    }

    @GetMapping("/myimages")
    public String currentUserImages(Model model, HttpServletRequest request) {
        String userName = ((User) request.getSession().getAttribute("user")).getName();
        List<Photo> photos = photoRepository.findByUserName(userName);

        return loadMain(model, request, photos);
    }

    @GetMapping("/userimages")
    public String searchedUserImagesPost(Model model, HttpServletRequest request) {
        String searchedUser = request.getParameter("username");
        List<Photo> photos = photoRepository.findByUserNameContains(searchedUser);

        return loadMain(model, request, photos);
    }

    @GetMapping("/tagresultimages")
    public String searchedByTag(Model model, HttpServletRequest request) {
        String searchedTag = request.getParameter("imagetag");
        List<Photo> photos = photoRepository.findByTitleContains(searchedTag);

        return loadMain(model, request, photos);
    }

    private List<PhotoWithUrl> getSearchedUserImagesWithUrls(Iterable<Photo> photos, Transformation transformation) {
        List<PhotoWithUrl> urls = new ArrayList<>();
        for (Photo p : photos) {
            urls.add(new PhotoWithUrl(p, transformation));
        }
        return urls;
    }

    private String loadMain(Model model, HttpServletRequest request, List<Photo> photos) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            addImagesToModel(model, photos);
            model.addAttribute("userName", user.getName());
            model.addAttribute("userImage", user.getGooglePictureUrl());
            return "main";
        }
        return "redirect:signin";
    }

    private void addImagesToModel(Model model, List<Photo> photos) {
        Collections.sort(photos, new PhotoComparator());
        model.addAttribute("images", getSearchedUserImagesWithUrls(photos,
                new Transformation().width(300).height(300).crop("fill")));
    }

    private class PhotoWithUrl {
        private Photo photo;
        private String url;

        PhotoWithUrl(Photo photo, Transformation transformation) {
            this.photo = photo;
            this.url = cloudinary.url()
                    .transformation(transformation).imageTag(photo.getPublic_id());
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    private class PhotoComparator implements Comparator<Photo> {

        @Override
        public int compare(Photo p1, Photo p2) {
            if (p1.getCreated_at().after(p2.getCreated_at()))
                return -1;
            else if (p1.getCreated_at().before(p2.getCreated_at()))
                return 1;
            else
                return 0;
        }

    }

}
