package kr.ac.duksung.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 파이어베이스
    private EditText mEtEmail, mEtPwd, mEtName, mEtAge;
    private Button mBtnRegister;

    //Intent intent;
    //File file;
    //Bitmap bitmap;
    //@SuppressLint("SimpleDateFormat")
    //SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
    //String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Account");

        mEtEmail = findViewById(R.id.userEmail);
        mEtPwd = findViewById(R.id.userPwd);
        mEtName = findViewById(R.id.userName);
        mEtAge = findViewById(R.id.userAge);
        mBtnRegister = findViewById(R.id.btn_register);


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String userEmail = mEtEmail.getText().toString();
                String userPwd = mEtPwd.getText().toString();
                String userName = mEtName.getText().toString();
                String userAge = mEtAge.getText().toString();

                /*Intent intent = new Intent(RegisterActivity.this, RegisterFaceActivity.class);
                intent.putExtra("email", strEmail);
                intent.putExtra("password", strPwd);
                intent.putExtra("name", strName);
                startActivity(intent);*/

                mFirebaseAuth.createUserWithEmailAndPassword(userEmail, userPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();
                            account.setUserIdToken(firebaseUser.getUid());
                            account.setUserEmail(firebaseUser.getEmail());
                            account.setUserPassword((userPwd));
                            account.setUserName((userName));
                            account.setUserAge((userAge));
                            //account.setLandmark((faceMeshResult));

                            //setvalue: database 에 insert(삽입)
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this, "회원가입 성공. 로그인해주세요", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();       //현재 액티비티 파괴

                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                            //Log.e("Firebase", "회원가입 실패: " + task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }
}




