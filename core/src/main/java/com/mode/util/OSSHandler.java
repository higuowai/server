package com.mode.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.util.DigestUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * Deals with put/get object to/from aliyun oss storage. Created by chao on
 * 7/16/15.
 */
public class OSSHandler {
    private static final String BUCKET_NAME = "higuowai";
    private static final String DEFAULT_IMAGE_FOLDER = "images";
    private static final String OSS_INTERNAL_ENDPOINT = "higuowai.oss-cn-qingdao-internal.aliyuncs.com";
    private static final String ACCESSKEY_ID = "";
    private static final String ACCESSKEY_SECRET = "";

    /* Initialize OSSClient */
    private static final OSSClient client = new OSSClient(OSS_INTERNAL_ENDPOINT, ACCESSKEY_ID,
            ACCESSKEY_SECRET);

    /**
     * Put an image to aliyun oss object storage
     *
     * @param image  image object
     * @param folder dest folder
     * @param format format of the image
     * @return object url
     */
    public String putImageToOSS(BufferedImage image, String folder, String format) {
        /* Convert the bufferedImage to bytes */
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            ImageIO.write(image, format, os);

            /*
             * Generate a md5 hash code of the image content and use this unique id
             * as the image name
             */
            String imageUniqueId = DigestUtils.md5DigestAsHex(os.toByteArray());

            /* Metadata */
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(os.size());

            String destination = DEFAULT_IMAGE_FOLDER + "/" + folder + "/" + imageUniqueId + "."
                    + format;

            InputStream im = outputStreamToInputStream(os);

            return putObjectToOSS(im, destination, meta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String putImageToOSS(InputStream object, String folder, String format) {
        /* Convert the bufferedImage to bytes */
        try (ByteArrayOutputStream os = toOutputStream(object)) {

            InputStream im = new ByteArrayInputStream(os.toByteArray());

            /*
             * Generate a md5 hash code of the image content and use this unique id
             * as the image name
             */
            String imageUniqueId = DigestUtils.md5DigestAsHex(os.toByteArray());

            /* Metadata */
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(os.size());

            String destination = DEFAULT_IMAGE_FOLDER + "/" + folder + "/" + imageUniqueId + "."
                    + format;

            return putObjectToOSS(im, destination, meta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Put Object from source to destination Object is some file with some
     * format.
     *
     * @param object      The object to put
     * @param destination The destination path of the image file in OSS
     * @param meta        The metadata to add
     * @return object url
     */
    private String putObjectToOSS(InputStream object, String destination, ObjectMetadata meta) {
        // Put object to oss and generate the file url
        client.putObject(BUCKET_NAME, destination, object, meta);
        return DEFAULT_IMAGE_FOLDER + "/" + destination;
    }

    /**
     * Convert OutputStream to InputStream
     *
     * @param out output stream
     * @return input stream
     */
    private InputStream outputStreamToInputStream(ByteArrayOutputStream out) {
        return new ByteArrayInputStream(out.toByteArray());
    }


    /*
    * Read bytes from inputStream and writes to OutputStream, later converts
    * OutputStream to byte array in Java.
    */
    private static ByteArrayOutputStream toOutputStream(InputStream is) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int reads = is.read();

            while (reads != -1) {
                baos.write(reads);
                reads = is.read();
            }

            return baos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
