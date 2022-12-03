package com.example.appthuongmaidientu.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.SanPham;
import com.example.appthuongmaidientu.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditUserActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String mobile;
    EditText tphone,tname,tmail,tdiachi,tpass,tconfirmpass;
    String email,name,imgUS,anhnen,diaChi,matKhau;
    ImageView avt,backGround;
    int choose=0;
    Bitmap bitmap = null,bitmap1=null;
    boolean check1=false,check2=false;
    Button btnSave;
    String imageUrl,imageUrl1;
    User us;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference= firebaseStorage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        AnhXa();

        mobile=getIntent().getStringExtra("mobile");
        databaseReference.child("users").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email= snapshot.child("email").getValue(String.class);
                name= snapshot.child("tenUser").getValue(String.class);
                imgUS= snapshot.child("imgUS").getValue(String.class);
                anhnen= snapshot.child("anhnen").getValue(String.class);
                diaChi = snapshot.child("diaChi").getValue(String.class);
                matKhau = snapshot.child("matKhau").getValue(String.class);
                System.out.println(mobile+" "+name);
                tphone.setText(mobile);
                tmail.setText(email);
                tname.setText(name);
                tdiachi.setText(diaChi);
                tpass.setText(matKhau);
                tconfirmpass.setText(matKhau);
                Picasso.get().load(imgUS).into(avt);
                Picasso.get().load(anhnen).into(backGround);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        avt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditUserActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_custom);
                dialog.show();
                CardView btchon =  dialog.findViewById(R.id.chonanhavt);
                CardView btxem = dialog.findViewById(R.id.xemanhavt);
                btchon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choose=0;
                        selectIMG();
                        dialog.dismiss();

                    }
                });
                btxem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog1 = new Dialog(EditUserActivity.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(R.layout.dialog_custom1);
                        dialog1.show();
                        dialog.dismiss();
                        ImageView imgz = (ImageView) dialog1.findViewById(R.id.imgzoom);
                        if (check1)
                            imgz.setImageBitmap(bitmap);
                        else
                            Picasso.get().load(imgUS).into(imgz);
                        Button butok = (Button) dialog1.findViewById(R.id.Buttonxemanhok);
                        butok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                            }
                        });
                    }
                });
            }
        });
        backGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(EditUserActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_custom);
                dialog.show();
                CardView btchon =  dialog.findViewById(R.id.chonanhavt);
                CardView btxem = dialog.findViewById(R.id.xemanhavt);
                btchon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        choose=1;
                        selectIMG();
                        dialog.dismiss();
                    }
                });
                btxem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog1 = new Dialog(EditUserActivity.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(R.layout.dialog_custom1);
                        dialog1.show();
                        dialog.dismiss();
                        ImageView imgz = (ImageView) dialog1.findViewById(R.id.imgzoom);
                        if(check2)
                            imgz.setImageBitmap(bitmap1);
                        else
                            Picasso.get().load(anhnen).into(imgz);
                        Button butok = (Button) dialog1.findViewById(R.id.Buttonxemanhok);
                        butok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                            }
                        });
                    }
                });

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    if (check1){
                        Calendar calendar = Calendar.getInstance();
                        storageReference.child("image" + calendar.getTimeInMillis() + ".png");
                        avt.setDrawingCacheEnabled(true);
                        avt.buildDrawingCache();
                        Bitmap bitmap = avt.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();
                        UploadTask uploadTask = storageReference.child("image" + calendar.getTimeInMillis() + ".png").putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imageUrl = uri.toString();
                                                if (imageUrl.isEmpty()) {
                                                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/demotmdt-26982.appspot.com/o/error-image-generic.png?alt=media&token=dbfe9456-ba97-458f-8abf-dfd6e644dd25";
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }else imageUrl=imgUS;
                    if (check2){
                        Calendar calendar = Calendar.getInstance();
                        storageReference.child("image1" + calendar.getTimeInMillis() + ".png");
                        backGround.setDrawingCacheEnabled(true);
                        backGround.buildDrawingCache();
                        Bitmap bitmap = backGround.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();
                        UploadTask uploadTask = storageReference.child("image1" + calendar.getTimeInMillis() + ".png").putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imageUrl1 = uri.toString();
                                                if (imageUrl1.isEmpty()) {
                                                    imageUrl1 = "https://firebasestorage.googleapis.com/v0/b/demotmdt-26982.appspot.com/o/error-image-generic.png?alt=media&token=dbfe9456-ba97-458f-8abf-dfd6e644dd25";
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }else imageUrl1=anhnen;

                    us=new User();
                    us.setTenUser(tname.getText().toString());
                    us.setSDT(mobile);
                    us.setEmail(tmail.getText().toString());
                    us.setAnhnen(imageUrl1);
                    us.setImgUS(imageUrl);
                    us.setDiaChi(tdiachi.getText().toString());
                    us.setMatKhau(tpass.getText().toString());
                    databaseReference.child("users").child(mobile).setValue(us);


                }else
                    Toast.makeText(EditUserActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean check() {
        if (tname.getText().toString().isEmpty()||tmail.getText().toString().isEmpty()||tdiachi.getText().toString().isEmpty()||tpass.getText().toString().isEmpty())
            return false;
        return true;
    }

    private void AnhXa() {
        btnSave = findViewById(R.id.btn_Saveedt);
        tphone = findViewById(R.id.edt_phoneedt);
        tname = findViewById(R.id.edt_nameedt);
        tmail = findViewById(R.id.edt_emailedt);
        tdiachi = findViewById(R.id.edt_diachiedt);
        tpass = findViewById(R.id.edt_passwordedt);
        tconfirmpass = findViewById(R.id.edt_confirmpasswordedt);
        avt=findViewById(R.id.imgedtProfile);
        backGround=findViewById(R.id.edt_background);
    }
    private void selectIMG() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && data !=null && data.getData()!=null){
            if (choose==0)
            {
                check1=true;
                Uri uriimgt=data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(EditUserActivity.this.getContentResolver(), uriimgt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                avt.setImageBitmap(bitmap);
            }

            else {
                check2=true;
                Uri uriimgt=data.getData();
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(EditUserActivity.this.getContentResolver(), uriimgt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                backGround.setImageBitmap(bitmap1);
            }

        }
    }
}