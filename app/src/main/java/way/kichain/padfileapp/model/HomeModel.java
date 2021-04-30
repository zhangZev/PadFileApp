package way.kichain.padfileapp.model;

import java.io.File;
import java.util.List;

public class HomeModel {
    public File bgFile;
    public List<File> images;
    public File videos;

    public File getBgFile() {
        return bgFile;
    }

    public void setBgFile(File bgFile) {
        this.bgFile = bgFile;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public File getVideos() {
        return videos;
    }

    public void setVideos(File videos) {
        this.videos = videos;
    }
}
