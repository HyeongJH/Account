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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증처리
    private DatabaseReference mDatabaseRef; //실시간 파이어베이스
    private EditText mEtEmail, mEtPwd;
    private Button btn_login, btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Account");

        mEtEmail = findViewById(R.id.userEmail);
        mEtPwd = findViewById(R.id.userPwd);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String struserEmail = mEtEmail.getText().toString();
                String struserPwd = mEtPwd.getText().toString();

                if(struserEmail.isEmpty() && struserPwd.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"이메일과 비밀번호를 모두 입력해주세요",Toast.LENGTH_SHORT).show();

                } else if(struserEmail.isEmpty()){

                    Toast.makeText(LoginActivity.this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();

                } else if(struserPwd.isEmpty()){

                    Toast.makeText(LoginActivity.this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {

                    mFirebaseAuth.signInWithEmailAndPassword(struserEmail, struserPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //로그인 성공
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // 현재 액티비티 파괴
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}