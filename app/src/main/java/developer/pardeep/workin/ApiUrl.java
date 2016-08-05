package developer.pardeep.workin;

/**
 * Created by pardeep on 01-08-2016.
 */
public class ApiUrl {
    private static String getFileViewDefault="http://api.medimojo.in/v1/files/list/?uid=mm004&access_token=9dee019efb4f7a293f2ffd2ad3c964785f54d564205ec58fe31951a43c756d68";

    public static String getGetFileViewDefault() {
        return getFileViewDefault;
    }

    public static void setGetFileViewDefault(String getFileViewDefault) {
        ApiUrl.getFileViewDefault = getFileViewDefault;
    }
}
