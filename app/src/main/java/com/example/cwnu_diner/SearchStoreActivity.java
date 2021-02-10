
package com.example.cwnu_diner;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SearchStoreActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchstore);
        /* 위젯과 멤버변수 참조 획득 */
        listView = (ListView)findViewById(R.id.listView);

        /* 아이템 추가 및 어댑터 등록 */
        dataSetting();

        ImageButton imageButton=findViewById(R.id.backBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void dataSetting() {

 /*  myadapter 오류뜨는데 이거 고쳐주세요 @강다은
        MyAdapter myAdapter = new MyAdapter();



        for (int i=0; i<10; i++) {
            myAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_launcher_foreground), "name_" + i, "contents_" + i);
        }
*/
        /* 리스트뷰에 어댑터 등록 */
//     listView.setAdapter(myAdapter);
    }

}