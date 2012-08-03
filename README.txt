A fork of SitemapGen4j, an XML sitemap generator written in Java

Project Home:
http://code.google.com/p/sitemapgen4j/

Project Owner:
=======
The goal of this fork is to simplify the generation of the sitemap
index while avoiding the disruption introduced by a change of entry
point when the threshold of 50,000 URLs is reached: the sitemap index
will be automatically generated instead when several sitemaps are
created, and use the same name "sitemap.xml" as a single sitemap,
to keep the same declaration in robots.txt file:

    Sitemap: http://example.com/sitemap.xml

In case more than one sitemap is generated the following sitemaps
will be: sitemap1.xml, sitemap2.xml... following the current naming
convention of the library.

THIS PROJECT IS NOT AFFILIATED WITH THE ORIGINAL PROJECT.
PLEASE REPORT ISSUES OF THIS PROJECT USING GITHUB TRACKER
AND NOT ON THE ISSUE TRACKER OF THE ORIGINAL PROJECT.

Original Project Home:
http://code.google.com/p/sitemapgen4j/

Original Project Owner:
Dan Fabulich

License:
Apache License 2.0
http://www.apache.org/licenses/LICENSE-2.0
