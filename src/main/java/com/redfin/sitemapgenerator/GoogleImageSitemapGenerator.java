package com.redfin.sitemapgenerator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Builds a Google Image Sitemap, consisting of URLs with image content.  To configure options, use {@link #builder(java.net.URL, java.io.File)}
 *
 * @author Claes Mogren
 * @see <a href="http://support.google.com/webmasters/bin/answer.py?answer=178636">Creating Image Sitemaps</a>
 */
public class GoogleImageSitemapGenerator extends SitemapGenerator<GoogleImageSitemapUrl, GoogleImageSitemapGenerator> {

    /** Configures a builder so you can specify sitemap generator options
     *
     * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
     * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
     * @return a builder; call .build() on it to make a sitemap generator
     */
    public static SitemapGeneratorBuilder<GoogleImageSitemapGenerator> builder(URL baseUrl, File baseDir) {
        return new SitemapGeneratorBuilder<GoogleImageSitemapGenerator>(baseUrl, baseDir, GoogleImageSitemapGenerator.class);
    }

    /** Configures a builder so you can specify sitemap generator options
     *
     * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
     * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
     * @return a builder; call .build() on it to make a sitemap generator
     */
    public static SitemapGeneratorBuilder<GoogleImageSitemapGenerator> builder(String baseUrl, File baseDir) throws MalformedURLException {
        return new SitemapGeneratorBuilder<GoogleImageSitemapGenerator>(baseUrl, baseDir, GoogleImageSitemapGenerator.class);
    }

    GoogleImageSitemapGenerator(AbstractSitemapGeneratorOptions<?> options) {
        super(options, new Renderer());
    }

    /** Configures the generator with a base URL and directory to write the sitemap files.
     *
     * @param baseUrl All URLs in the generated sitemap(s) should appear under this base UR
     * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
     * @throws java.net.MalformedURLException
     */
    public GoogleImageSitemapGenerator(String baseUrl, File baseDir) throws MalformedURLException {
        this(new SitemapGeneratorOptions(baseUrl, baseDir));
    }

    /** Configures the generator with a base URL and directory to write the sitemap files.
     *
     * @param baseUrl All URLs in the generated sitemap(s) should appear under this base URL
     * @param baseDir Sitemap files will be generated in this directory as either "sitemap.xml" or "sitemap1.xml" "sitemap2.xml" and so on.
     */
    public GoogleImageSitemapGenerator(URL baseUrl, File baseDir) {
        this(new SitemapGeneratorOptions(baseUrl, baseDir));
    }

    private static class Renderer extends AbstractSitemapUrlRenderer<GoogleImageSitemapUrl> implements ISitemapUrlRenderer<GoogleImageSitemapUrl> {

        public Class<GoogleImageSitemapUrl> getUrlClass() {
            return GoogleImageSitemapUrl.class;
        }

        public void render(GoogleImageSitemapUrl url, OutputStreamWriter out, W3CDateFormat dateFormat) throws IOException {
            StringBuilder sb = new StringBuilder();
            for (GoogleImageSitemapUrl.ImageTag it : url.getImageTags()) {
                sb.append("    <image:image>\n");
                renderTag(sb, "image", "loc", it.getImageLoc());
                renderTag(sb, "image", "caption", it.getCaption());
                renderTag(sb, "image", "geo_location", it.getGeoLocation());
                renderTag(sb, "image", "title", it.getTitle());
                renderTag(sb, "image", "license", it.getLicense());
                sb.append("    </image:image>\n");
            }

            super.render(url, out, dateFormat, sb.toString());
        }

        public String getXmlNamespaces() {
            return "xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\"";
        }
    }
}