package io.github.elkan1788.mpsdk4j.api;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import io.github.elkan1788.mpsdk4j.common.MediaType;
import io.github.elkan1788.mpsdk4j.vo.api.Media;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@FixMethodOrder(MethodSorters.JVM)
public class MediaAPITest extends APITestSupport {

    private static final Log log = Logs.get();

    private MediaAPI ma;

    @Override
    @Before
    public void init() {
        super.init();
        ma = WechatAPIImpl.create(mpAct);
    }

    @Test
    public void testUploadImage() {
        File media = new File(this.getClass().getResource("/mpsdk4j-logo.png").getFile());
        Media m = ma.upMedia(MediaType.image.name(), media);
        assertNotNull(m);
        log.info(m);
    }

    @Test
    public void testGet() {
        String mediaId = "";
        File media = ma.dlMedia(mediaId);
        assertNotNull(media);
        log.info(media.getAbsolutePath());
        log.info(media.getName());
    }

}
