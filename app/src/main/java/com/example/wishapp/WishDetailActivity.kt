package com.example.wishapp

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wish_detail.*
import java.io.ByteArrayOutputStream
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class WishDetailActivity : AppCompatActivity() {

    var mStorageRef: StorageReference? = null
    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_detail)


        button_find.setOnClickListener{

        }

        button_save_edited.setOnClickListener{

        }

        button_delete_wish.setOnClickListener{

        }
    }

    fun dispatchSaveEditedWishIntent(){

    }

    fun dispatchDeleteWishIntent(){

    }

    fun uploadImage(uri : Uri, imgName: String) {
        var bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri)
        var byteStream : ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteStream)

        var riversRef : StorageReference  = mStorageRef!!.child("avid-toolbox-5658/$imgName")
        riversRef.putBytes( byteStream.toByteArray() )
            .addOnProgressListener( {taskSnapshot: UploadTask.TaskSnapshot ->
                //Toast.makeText(applicationContext, "Updating", Toast.LENGTH_LONG).show()
            })
            .addOnSuccessListener( {taskSnapshot : UploadTask.TaskSnapshot ->
                // Get a URL to the uploaded content
                taskSnapshot.metadata!!.downloadUrl
                var downloadUrl : Uri? = taskSnapshot.getDownloadUrl()
                Log.i("KEVIN", downloadUrl.toString())
                Log.i("KEVIN", taskSnapshot.metadata!!.downloadUrl.toString())
                Toast.makeText(applicationContext, "Upload worked", Toast.LENGTH_LONG).show()
                searchImage(downloadUrl.toString())
            })
            .addOnFailureListener({exception : Exception ->
                // Handle unsuccessful uploads
                // ...
                Toast.makeText(applicationContext, "Upload failed", Toast.LENGTH_LONG).show()
            })
    }

    fun searchImage(imgPath : String) {
        var base_url : String  = "https://www.google.com/searchbyimage?site=search&sa=X&image_url="
        var google_shop : String = "https://www.google.com/search?q={}&source=lnms&tbm=shop&"
            .format("slowo + klucz")
        val uri = Uri.parse(base_url + imgPath)
        var intent : Intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun saveWish(){
        val message = Toast.makeText(applicationContext, "Changes saved", Toast.LENGTH_LONG)
        message.show()
    }

    fun deleteWish(){
        val message = Toast.makeText(applicationContext, "Deleted wish", Toast.LENGTH_LONG)
        message.show()
    }



}
