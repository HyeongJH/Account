package kr.ac.duksung.account;

/*
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FileUploadUtils {
    public static void send2Server(Bitmap bitmap, RegisterFaceActivity registerFaceActivity) {
        registerFaceActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(registerFaceActivity, "영수증 스캔중입니다", Toast.LENGTH_SHORT).show();
            }
    });

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "image.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.30:5000/upload") // Server URL 은 본인 IP를 입력
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            String FaceMeshResult = null;

            private FirebaseAuth mFirebaseAuth; // 파이어배이스 인증
            private DatabaseReference mDatabaseRef; // 실시간 데이터터베이스




}*/
