package com.example.cwnu_diner;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.mail.MessagingException;

public class GMail extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_apply; //전송버튼
    private EditText et_title; //메일제목
    private EditText et_content; //메일내용

    String sender_email;
    String title;
    String content;

    ProgressDialog dialog;
    GmailSender gmailSender;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //로그인정보 가져오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gmail_send);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        btn_apply = findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        sender_email=user.getEmail();

        title=et_title.getText().toString();
        content=et_content.getText().toString();

        try {
            gmailSender=new GmailSender("dmswls9509@gmail.com","house0544564634");
            //GMailSender.sendMail(제목, 본문내용, 받는사람);
            gmailSender.sendMail(title,content,sender_email,"dmswls9509@gmail.com");
            Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SendMail",e.getMessage(),e);
            Toast.makeText(GMail.this, "메일 전송 실패", Toast.LENGTH_SHORT).show();
        }
    }
}