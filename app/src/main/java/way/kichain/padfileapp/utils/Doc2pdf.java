/*
package way.kichain.padfileapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class Doc2pdf {
    */
/* Word转PDF操作
     *@param sourcerFile 源文件
     *@param targetFile 目标文件
     *//*

    public static String htmlPath = Environment.getExternalStorageDirectory() + "/AApadData/htmls";
    public static String htmlName = "/temp.pdf";

    public static File doc2pdf(File file) {
        File dir = new File(htmlPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
           */
/* String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License license = new License();
            license.setLicense(is);*//*

            com.aspose.words.Document document = new com.aspose.words.Document(file.getPath());
            document.save(new FileOutputStream(new File(htmlPath + htmlName)), SaveFormat.PDF);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new File(htmlPath + htmlName);
    }
}
*/
