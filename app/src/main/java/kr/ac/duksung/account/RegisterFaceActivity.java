package kr.ac.duksung.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//public class RegisgerFaceActivity extends AppCompatActivity {

class RegisgerFaceActivity extends AppCompatActivity {
    final int CAMERA = 100; // 카메라 선택시 인텐트로 보내는 값
    final int GALLERY = 101; // 갤러리 선택 시 인텐트로 보내는 값
    Intent intent;
    ImageView imageView;
    File file;
    Bitmap bitmap;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_face);

        imageView = findViewById(R.id.iv_main);

//        권한 체크
        boolean hasCamPerm = checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean hasWritePerm = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!hasCamPerm || !hasWritePerm)  // 권한 없을 시  권한설정 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

//        카메라 버튼 이벤트
        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File imageFile = null;
                    try {
                        imageFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (imageFile != null) {
                        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.myapplication2.fileprovider",
                                imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CAMERA);
                    }
                }
            }
        });
//        갤러리 버튼 이벤트
        findViewById(R.id.btn_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY);
            }
        });

//        facemesg=h button 버튼 이벤트
        findViewById(R.id.btn_facemesh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FileUploadUtils.send2Server(bitmap, RegisterFaceActivity.this);
                //회원가입 화면으로 이동
                /*Intent intent = new Intent(RegisterFaceActivity.this, RegisterActivity.class);
                startActivity(intent);*/

            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) { // 결과가 있을 경우
            //Bitmap bitmap = null;
            switch (requestCode) {
                case GALLERY: // 갤러리에서 이미지로 선택한 경우
                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        imagePath = cursor.getString(index);
                        bitmap = BitmapFactory.decodeFile(imagePath);
                        cursor.close();
                    }
//                    InputStream 으로 이미지 세팅하기
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CAMERA: // 카메라로 이미지 가져온 경우
                    file = new File(imagePath);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2; // 이미지 축소 정도. 원 크기에서 1/inSampleSize 로 축소됨
                    bitmap = BitmapFactory.decodeFile(imagePath, options);
                    bitmap = rotateImageIfNeeded(bitmap, imagePath);  // 회전 정보에 따라 이미지를 올바르게 회전
                    break;
            }
            imageView.setImageBitmap(bitmap);
//            sendBitmap2Server(bitmap);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) { // 이전에 정의한 권한 요청 코드
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인된 경우
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
            } else {
                // 권한이 거부된 경우
                Toast.makeText(this, "Permission denied. App might not work properly.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Bitmap rotateImageIfNeeded(Bitmap bitmap, String imagePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateBitmap(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateBitmap(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateBitmap(bitmap, 270);

            default:
                return bitmap;
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, float angle) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @SuppressLint("SimpleDateFormat")
    File createImageFile() throws IOException {
//        이미지 파일 생성
        String timeStamp = imageDate.format(new Date()); // 파일명 중복을 피하기 위한 "yyyyMMdd_HHmmss"꼴의 timeStamp
        String fileName = "IMAGE_" + timeStamp; // 이미지 파일 명
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(fileName, ".jpg", storageDir); // 이미지 파일 생성
        imagePath = file.getAbsolutePath(); // 파일 절대경로 저장하기
        return file;
    }

}