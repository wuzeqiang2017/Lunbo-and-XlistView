package test.bwie.com.wuzeqiang1507d20170825;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 移动1507D  武泽强
 * 2017/8/25.
 * 作用： 帮助类 读取网络的图片
 */

public class StramTools {
    public static String GETRead(InputStream is){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] arr= new byte[1024];
            while ((len=is.read(arr))!=-1){
                baos.write(arr,0,len);
            }
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
