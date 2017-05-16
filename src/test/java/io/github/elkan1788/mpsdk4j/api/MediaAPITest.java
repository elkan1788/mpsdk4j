package io.github.elkan1788.mpsdk4j.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.elkan1788.mpsdk4j.RunTestSupport;
import io.github.elkan1788.mpsdk4j.common.MediaType;
import io.github.elkan1788.mpsdk4j.vo.api.Media;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class MediaAPITest extends RunTestSupport {

    private static final Log log = Logs.get();

    @BeforeClass
    public void init() {
        log.info("====== MediaAPITest ======");
    }

    @Test
    public void testUploadImage() {
        log.info("====== MediaAPI#upMedia ======");
        MockUpHttpUpload("{\"type\":\""+MediaType.image.name()+"\",\"media_id\":\""+mediaId+"\",\"created_at\":123456789}");
        Media m = wechatAPI.upMedia(MediaType.image.name(), imgMedia);
        assertNotNull(m);
        assertEquals(m.getMediaId(), mediaId);
    }

    @Test
    public void testGetUploadImage() {
        log.info("====== MediaAPI#dlMedia ======");
        MockUpHttpDownload(imgMedia);
        File media = wechatAPI.dlMedia(mediaId);
        assertNotNull(media);
        assertEquals(media.getName(), imgMedia.getName());
    }

}
