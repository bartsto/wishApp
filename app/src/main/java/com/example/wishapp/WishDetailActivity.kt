package com.example.wishapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_wish_detail.*
import java.util.*


class WishDetailActivity : AppCompatActivity() {

    private var filePath: Uri? = null
    private var urlPath: String? = null

    internal var storage:FirebaseStorage? = null
    internal var storageReferences: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_detail)

        storage = FirebaseStorage.getInstance()
        storageReferences = storage!!.reference

        val wishId = intent.getIntExtra("wishId", -1)
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val wish = databaseHandler.getWishList().get(wishId)

        val wish_name_display = findViewById<EditText>(R.id.wish_name_display).apply {
            setText(wish.name)
        }

        val wish_desc_display = findViewById<EditText>(R.id.wish_desc_display).apply {
            setText(wish.description)
        }

        val wishDetailImage = findViewById<ImageView>(R.id.wish_detail_image).apply {
            setImageURI(Uri.parse(Uri.decode(wish.imagePath)))
            filePath = Uri.parse(Uri.decode(wish.imagePath))

        }

        button_find.setOnClickListener{
            uploadFile()
        }

        button_save_edited.setOnClickListener{
            saveWish()
        }

        button_delete_wish.setOnClickListener{
            deleteWish(wish)
        }
    }

    private fun uploadFile() {
        println(filePath)
        if(filePath != null){
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val imageRef = storageReferences!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File uploaded", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener {taskSnapShot ->
                    val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                }
                .addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        urlPath = task.result.toString()
                        println("************************************************** GET URL ADDRESSES")
                        println(urlPath)
                    }

                }

//            searchImage(urlPath)
        }
    }

    fun searchImage(imgPath : String) {
        var base_url : String  = "https://www.google.com/searchbyimage?site=search&sa=X&image_url="
//        var google_shop : String = "https://www.google.com/search?q={}&source=lnms&tbm=shop&"
//            .format("slowo + klucz")
        val uri = Uri.parse(base_url + imgPath)
        var intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun saveWish(){
        val message = Toast.makeText(applicationContext, "Changes saved", Toast.LENGTH_LONG)
        message.show()

        // zapisuje edytowane zmainy, zostaje w tym samym widoku
    }

    fun deleteWish(wish: Wish) {
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)

        val status = databaseHandler.deleteWish(wish)
        if(status > -1){
                Toast.makeText(applicationContext,"Wish deleted.",Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(applicationContext,"Something went wrong.",Toast.LENGTH_LONG).show()
        }

    }

}
