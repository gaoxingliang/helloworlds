package ctrl;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试文件上传
 */
@Controller
public class UploadCtrl {

    @RequestMapping("/test2")
    public ModelAndView upload() {
        return new ModelAndView("upload");
    }

    @RequestMapping("/upload")
    public ModelAndView upload(@RequestParam("picture")MultipartFile pic) throws IOException {
        System.out.println(pic.getOriginalFilename());
        // save to local
        File recvFile = new File("recv-" + System.currentTimeMillis() + "-" + pic.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(recvFile);
        IOUtils.copy(pic.getInputStream(), fos);
        // should close on finally
        IOUtils.closeQuietly(pic.getInputStream());
        IOUtils.closeQuietly(fos);
        ModelAndView modelAndView =  new ModelAndView("uploadres");
        modelAndView.addObject("result", "收到文件 -" + recvFile.getCanonicalPath() + " size:" + recvFile.length());
        return modelAndView;
    }
}
