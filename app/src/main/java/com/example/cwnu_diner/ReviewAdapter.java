package com.example.cwnu_diner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.mail.Store;

public class ReviewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ReviewData> arrayList;
    String userID, IP_ADDRESS = "http:/3.34.134.116/reviewDelete.php";
    public ReviewAdapter(Context context, ArrayList<ReviewData> arrayList, String userID){
        this.context = context;
        this.arrayList = arrayList;
        this.userID = userID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.review_itemlist,parent,false);

        VH holder = new VH(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        final ReviewData data = arrayList.get(position);
        //리사이클러뷰 아이템에 데이터 입력
        vh.tv_review.setText(data.getReview());
        vh.tv_userName.setText(data.getUserID());
        vh.tv_menu.setText(data.getMenu());
        vh.tv_star.setText("★ "+data.getStarRating());

        //데이터 삭제 버튼 보이게
        if(userID.equals(data.getUserID())) {
            int VISIBLE = 0x00000000;
            vh.btn_removeReveiw.setVisibility(VISIBLE);

            vh.btn_removeReveiw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                final DeleteReview task = new DeleteReview();

                AlertDialog.Builder alt_bld = new AlertDialog.Builder(view.getContext());
                    alt_bld.setMessage("리뷰를 삭제 하시겠습니까?");
                    alt_bld.setCancelable(false);
                    alt_bld.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            task.execute(IP_ADDRESS, userID, data.getStoreName(), data.getDate());

                        }
                    });
                    alt_bld.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = alt_bld.create();
                alert.setTitle("리뷰 삭제");
                alert.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class VH extends RecyclerView.ViewHolder{ ////리사이클러뷰 아이템 추가

        TextView tv_review, tv_menu, tv_star, tv_userName;
        Button btn_removeReveiw;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv_review = itemView.findViewById(R.id.tv_review);
            tv_menu = itemView.findViewById(R.id.tv_menu);
            tv_star = itemView.findViewById(R.id.tv_star);
            tv_userName = itemView.findViewById(R.id.tv_username);
            btn_removeReveiw = itemView.findViewById(R.id.btn_removeReview);
        }
    }


    /////////////// 리뷰 삭제 요청 //////////////////////
    class DeleteReview extends AsyncTask<String, Void, String >
    {
        //ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(StoreClickActivity.this,"Please Wait",null, true, true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //  progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[1];
            String storeName = (String)params[2];
            String date = (String)params[3];

            String serverURL = (String)params[0];
            String postParameters = "userID="+userID+"&storeName="+storeName+"&date="+date;

            try{
                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                Log.d("tagggggg",postParameters);
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("TAGGGGGG","POSTrsponsecode"+responseStatusCode);

            }catch (Exception e){
                Log.d("TAGGGWRONG", "아진짜 이게 터지네");
            }


            return null;
        }
    }
}