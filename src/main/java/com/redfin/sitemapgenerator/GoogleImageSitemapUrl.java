package com.redfin.sitemapgenerator;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One configurable Google Image Search URL.  To configure, use {@link Options}
 * @author Claes Mogren
 * @see Options
 * @see <a href="http://support.google.com/webmasters/bin/answer.py?answer=178636">Creating Image Sitemaps</a>
 */
public class GoogleImageSitemapUrl extends WebSitemapUrl {

    private List<ImageTag> imageTags;

    /** Options to configure image URLs */
    public static class Options extends AbstractSitemapUrlOptions<GoogleImageSitemapUrl, Options> {

        private List<ImageTag> imageTags = new ArrayList<ImageTag>();

        /** Specifies a landing page URL */
        public Options(URL url) {
            super(url, GoogleImageSitemapUrl.class);
        }

        public Options addImage(ImageTag imageTag){
            if (imageTags.size() == 1000) {
               throw new IllegalArgumentException("No more than 1,000 images for each URL!");
            }
            this.imageTags.add(imageTag);
            return this;
        }
    }

    public GoogleImageSitemapUrl(URL url){
        this(new Options(url));
    }

    /** Configures an url with options */
    public GoogleImageSitemapUrl(Options options) {
        super(options);
        imageTags = Collections.unmodifiableList(options.imageTags);
    }

    public static class ImageTag {
        private URL imageLoc; // Required
        private String caption;
        private String geoLocation; //geo_location
        private String title;
        private URL license;

        /**
         * Creates an imageTag to be added to a @see GoogleImageSitemapUrl
         *
         * @param imageLoc the URL of the image
         */
        public ImageTag(URL imageLoc) {
            this.imageLoc = imageLoc;
        }

        /** Specifies the caption of the image */
        public ImageTag caption(String caption) {
            this.caption = caption;
            return this;
        }

        /** Specifies the geo location of the image */
        public ImageTag geoLocation(String geoLocation) {
            this.geoLocation = geoLocation;
            return this;
        }

        /** Specifies the title of the image */
        public ImageTag title(String title) {
            this.title = title;
            return this;
        }

        /** Specifies the URL of the license for the image */
        public ImageTag licenseUrl(URL licenseUrl) {
            this.license = licenseUrl;
            return this;
        }

        public URL getImageLoc() {
            return imageLoc;
        }

        public String getCaption() {
            return caption;
        }

        public String getGeoLocation() {
            return geoLocation;
        }

        public String getTitle() {
            return title;
        }

        public URL getLicense() {
            return license;
        }
    }

    public List<ImageTag> getImageTags() {
        return imageTags;
    }
}