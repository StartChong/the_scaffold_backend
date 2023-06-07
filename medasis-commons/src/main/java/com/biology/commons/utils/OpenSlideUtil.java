package com.biology.commons.utils;


import com.biology.commons.interact.vo.minio.MinioOpenSlideVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lichong
 * @E-mail: 1207239331@qq.com
 * @create: 2022-04-19 11:05
 * @desc:
 */
@Slf4j
@Component
public class OpenSlideUtil {

    @Value("${open-slide.img-type}")
    private String imgType;

    @Value("${open-slide.img-size}")
    private Integer imgSize;

    @Resource
    private MinIoUtils minIoUtils;

    private static boolean OPENSLIDE_UNAVAILABLE = false;

    private static final String osName = System.getProperty("os.name");

    private static final List<String> WIN_LIBRARIES = Arrays.asList(
            "iconv",
            "libjpeg-62",
            "libsqlite3-0",
            "libpixman-1-0",
            "libffi-6",
            "zlib1",
            "libxml2-2",
            "libopenjp2",
            "libpng16-16",
            "libtiff-5",
            "libintl-8",
            "libglib-2.0-0",
            "libgobject-2.0-0",
            "libcairo-2",
            "libgmodule-2.0-0",
            "libgio-2.0-0",
            "libgthread-2.0-0",
            "libgdk_pixbuf-2.0-0",
            "libopenslide-0"
    );

    static {
        try {
            // Try loading OpenSlide-JNI - hopefully there is a version of OpenSlide on the PATH we should use
            // 确保本系统已经安装了对应版本的openslide文件并设置了环境变量
            if (osName.startsWith("Windows")) {
                log.info("已检测到您当前使用的系统为：WIN");
                System.loadLibrary("openslide-jni");
                log.info("openslide-jni load success");
            } else if (osName.startsWith("Linux")) {
                log.info("已检测到您当前使用的系统为：Linux");
                System.loadLibrary("libopenslide-jni");
                log.info("libopenslide-jni load success");
            }
        } catch (UnsatisfiedLinkError e) {
            try {
                // If we didn't succeed, try loading dependencies in reverse order
                log.error("Couldn't load OpenSlide directly, attempting to load dependencies first...");
                if (osName.startsWith("Windows")) {
                    for (String lib : WIN_LIBRARIES) {
                        System.loadLibrary(lib);
                    }
                }
            } catch (UnsatisfiedLinkError ignored) {
            }
        }
        try {
            // Finally try to get the library version
            //log.info("OpenSlide version {}", OpenSlide.getLibraryVersion());
        } catch (UnsatisfiedLinkError e) {
            log.error("Could not load OpenSlide native libraries", e);
            log.info("If you want to use OpenSlide, you'll need to get the native libraries (either building from source or with a packager manager)\n" +
                    "and add them to your system PATH, including openslide-jni.");
            OPENSLIDE_UNAVAILABLE = true;
        }
    }

    /**
     * cutImg
     * 切割图片文件
     *
     * @author: lichong
     * @param: infile 输入的文件
     * @param: outfile 输出的文件目录
     * @return: String 文件输出地址
     * @description: 获取文件中的附带文件
     * @date: 2022/4/19
     */
    public List<String> cutImg(File infile, File outfile) {
        //SysException.throwException(OPENSLIDE_UNAVAILABLE, ResultCodeEnum.FAIL.code(), "OpenSlide is unavailable - will be skipped");
        //List<String> resultAddress = Lists.newArrayList();
        //String fileName = infile.getName();
        //String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(Constants.POINT));
        //OpenSlide os = null;
        //try {
        //    os = new OpenSlide(infile);
        //    int levelCount = os.getLevelCount();
        //    // 切割
        //    for (int n = levelCount - 1; n > -1; n--) { //n表示层级，也表示需要创建的文件夹名字
        //        int nHeight = (int) (os.getLevel0Height() / os.getLevelHeight(n)); //切高的次数
        //        int nWidth = (int) (os.getLevel0Width() / os.getLevelWidth(n)); //切长的次数
        //        for (int j = 0; j < nWidth; j++) {  //循环切长
        //            for (int i = 0; i < nHeight; i++) {  //循环切高
        //                BufferedImage th = os.createThumbnailImage((int) (j * os.getLevelWidth(n)), (int) (i * os.getLevelHeight(n)), os.getLevelHeight(n), os.getLevelHeight(n), imgSize);  //开始切图
        //                // 输出文件到对应目录
        //                //创建目录
        //                String resultName = outfile + File.separator + n + File.separator + nameWithoutExtension + Constants.UNDERSCORE + j + Constants.UNDERSCORE + i + Constants.POINT + imgType;
        //                File file = new File(outfile + File.separator + n);
        //                if (!file.exists()) {
        //                    System.out.println("文件不存在");
        //                    file.mkdirs();//创建失败会抛出异常throw new IOException("Invalid file path");
        //                }
        //                ImageIO.write(th, imgType, new File(resultName));
        //                System.out.println("保存成功:" + resultName);
        //            }
        //        }
        //    }
        //    // 附件获取
        //    Map<String, AssociatedImage> map = os.getAssociatedImages();
        //    for (Map.Entry<String, AssociatedImage> entry : map.entrySet()) {
        //        BufferedImage img = entry.getValue().toBufferedImage();
        //
        //        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        //        Graphics g = result.getGraphics();
        //        g.drawImage(img, 0, 0, null);
        //        String resultName = outfile + File.separator + nameWithoutExtension + Constants.LINE + entry.getKey() + Constants.POINT + imgType;
        //        // 输出文件到对应目录
        //        ImageIO.write(result, imgType, new File(resultName));
        //        resultAddress.add(resultName);
        //    }
        //    return resultAddress;
        //} catch (IOException e) {
        //    log.error("创建OS对象失败");
        //    e.printStackTrace();
        //    return null;
        //} finally {
        //    assert os != null;
        //    os.close();
        //}
        return null;
    }

    /**
     * cutFileToMinIo
     * 切割文件存放到minio
     *
     * @param inFile
     * @param bucketName
     * @param directory
     * @author: lichong
     * @description
     * @date 2022/5/10
     */
    public MinioOpenSlideVo cutFileToMinIo(MultipartFile inFile, String bucketName, String directory) {
        //SysException.throwException(OPENSLIDE_UNAVAILABLE, ResultCodeEnum.FAIL.code(), "OpenSlide is unavailable - will be skipped");
        //SysException.throwException(StringUtils.isBlank(inFile.getOriginalFilename()), ResultCodeEnum.NOT_FOUND.code(), "切割文件失败！文件不存在！--》" + inFile.getName());
        //OpenSlide os = null;
        //File file = null;
        //MinioOpenSlideVo minioOpenSlideVo = new MinioOpenSlideVo();
        //try {
        //    // 转换临时文件
        //    file = FileUtils.multipartFileToFile(inFile);
        //    String fileName = file.getName();
        //    String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(Constants.POINT));
        //    os = new OpenSlide(file);
        //    int levelCount = os.getLevelCount();
        //    // 切割
        //    for (int n = levelCount - 1; n > -1; n--) { //n表示层级，也表示需要创建的文件夹名字
        //        int nHeight = (int) (os.getLevel0Height() / os.getLevelHeight(n)); //切高的次数
        //        int nWidth = (int) (os.getLevel0Width() / os.getLevelWidth(n)); //切长的次数
        //        for (int j = 0; j < nWidth; j++) {  //循环切长
        //            for (int i = 0; i < nHeight; i++) {  //循环切高
        //                BufferedImage image = os.createThumbnailImage((int) (j * os.getLevelWidth(n)), (int) (i * os.getLevelHeight(n)), os.getLevelHeight(n), os.getLevelHeight(n), imgSize);  //开始切图
        //                // 文件名
        //                String resultName = nameWithoutExtension + Constants.UNDERSCORE + j + Constants.UNDERSCORE + i + Constants.POINT + imgType;
        //                // 文件存放位置
        //                String resultPath = directory + File.separator + n + File.separator + nameWithoutExtension + Constants.UNDERSCORE + j + Constants.UNDERSCORE + i;
        //                // 输出流
        //                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //                ImageIO.write(image, imgType, outputStream);
        //                //转换为MultipartFile
        //                MultipartFile multipartFile = new MockMultipartFile(resultName, resultName, "application/octet-stream", outputStream.toByteArray());
        //                // 上传minio
        //                minIoUtils.upload(multipartFile, bucketName, resultPath);
        //            }
        //        }
        //    }
        //    // 附件获取
        //    Set<String> appendixes = new HashSet<>();
        //    Map<String, AssociatedImage> map = os.getAssociatedImages();
        //    for (Map.Entry<String, AssociatedImage> entry : map.entrySet()) {
        //        BufferedImage image = entry.getValue().toBufferedImage();
        //
        //        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        //        Graphics g = result.getGraphics();
        //        g.drawImage(image, 0, 0, null);
        //        // 文件名
        //        String resultName = nameWithoutExtension + Constants.LINE + entry.getKey() + Constants.POINT + imgType;
        //        // 文件存放位置
        //        String resultPath = directory + File.separator + nameWithoutExtension + Constants.LINE + entry.getKey();
        //        // 输出流
        //        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //        ImageIO.write(image, imgType, outputStream);
        //        //转换为MultipartFile
        //        MultipartFile multipartFile = new MockMultipartFile(resultName, resultName, "application/octet-stream", outputStream.toByteArray());
        //        // 上传minio
        //        minIoUtils.upload(multipartFile, bucketName, resultPath);
        //        appendixes.add(entry.getKey());
        //    }
        //    log.info("文件切割成功！--》" + inFile.getOriginalFilename());
        //    minioOpenSlideVo.setLevelCount(levelCount);
        //    minioOpenSlideVo.setAppendixes(appendixes);
        //    return minioOpenSlideVo;
        //} catch (IOException e) {
        //    log.error("文件切割失败！" + inFile.getOriginalFilename());
        //    e.printStackTrace();
        //    return null;
        //} finally {
        //    // 删除临时文件
        //    FileUtils.deleteTempFile(file);
        //    assert os != null;
        //    os.close();
        //}
        return null;
    }
}
