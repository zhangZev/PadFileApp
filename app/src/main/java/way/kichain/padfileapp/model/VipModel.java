package way.kichain.padfileapp.model;

import java.io.File;

/**
 * @time:{2021/4/28}
 * @auhor:{ZhangXW}
 */
public class VipModel {
    private File imageFile;
    private File ContentFile;

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public File getContentFile() {
        return ContentFile;
    }

    public void setContentFile(File contentFile) {
        ContentFile = contentFile;
    }


}
