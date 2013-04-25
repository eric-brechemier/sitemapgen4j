package com.redfin.sitemapgenerator;

import junit.framework.TestCase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class GoogleImageSitemapUrlTest extends TestCase {
	
	File dir;
	GoogleImageSitemapGenerator wsg;

    private static String BASE_STRING = "http://www.example.com";
    private static final URL BASE_URL = newURL(BASE_STRING);
    private static final URL CONTENT_URL = newURL(BASE_STRING + "/index.html");
    private static final URL IMAGE_URL = newURL(BASE_STRING + "/images/thumbnail.png");

    private static URL newURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {}
        return null;
    }

	public void setUp() throws Exception {
		dir = File.createTempFile(GoogleImageSitemapUrlTest.class.getSimpleName(), "");
		dir.delete();
		dir.mkdir();
		dir.deleteOnExit();
	}
	
	public void tearDown() {
		wsg = null;
		for (File file : dir.listFiles()) {
			file.deleteOnExit();
			file.delete();
		}
		dir.delete();
		dir = null;
	}
	
	public void testSimpleUrl() throws Exception {
		wsg = new GoogleImageSitemapGenerator(BASE_URL, dir);
		GoogleImageSitemapUrl url = new GoogleImageSitemapUrl(CONTENT_URL);
		wsg.addUrl(url);
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
			"<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" " +
			"xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\" >\n" +
			"  <url>\n" + 
			"    <loc>" + CONTENT_URL + "</loc>\n" +
			"  </url>\n" + 
			"</urlset>";
		String sitemap = writeSingleSiteMap(wsg);
		assertEquals(expected, sitemap);
	}
	
	private String writeSingleSiteMap(GoogleImageSitemapGenerator wsg) {
		List<File> files = wsg.write();
		assertEquals("Too many files: " + files.toString(), 1, files.size());
		assertEquals("Sitemap misnamed", "sitemap.xml", files.get(0).getName());
		return TestUtil.slurpFileAndDelete(files.get(0));
	}

    public void testOptions() throws Exception {
        wsg = new GoogleImageSitemapGenerator(BASE_URL, dir);
        GoogleImageSitemapUrl url = new GoogleImageSitemapUrl.Options(CONTENT_URL)
                .addImage(new GoogleImageSitemapUrl.ImageTag(IMAGE_URL).title("Title").caption("Caption text"))
                .addImage(new GoogleImageSitemapUrl.ImageTag(IMAGE_URL))
                .build();
        wsg.addUrl(url);
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" " +
                "xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\" >\n" +
                "  <url>\n" +
                "    <loc>" + CONTENT_URL + "</loc>\n" +
                "    <image:image>\n" +
                "      <image:loc>http://www.example.com/images/thumbnail.png</image:loc>\n" +
                "      <image:caption>Caption text</image:caption>\n" +
                "      <image:title>Title</image:title>\n" +
                "    </image:image>\n" +
                "    <image:image>\n" +
                "      <image:loc>http://www.example.com/images/thumbnail.png</image:loc>\n" +
                "    </image:image>\n" +
                "  </url>\n" +
                "</urlset>";
        String sitemap = writeSingleSiteMap(wsg);
        assertEquals(expected, sitemap);
    }
}
