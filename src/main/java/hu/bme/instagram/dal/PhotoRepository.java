package hu.bme.instagram.dal;


import hu.bme.instagram.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository  extends JpaRepository<Photo, String> {

    List<Photo> findByUserName(String name);
    List<Photo> findByUserNameContains(String username);
    List<Photo> findByTitleContains(String title);
    List<Photo> searchForTitle(String tag);
}
