package mx.edu.itm.link.practicafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDB=FirebaseDatabase.getInstance()
        database=myDB.reference
        //database.setValue("Holamundo")
        writeNewuser("002","Abraham","zazazazaze@gmail.com")
        getUser("001")

        val txtId=findViewById<EditText>(R.id.etUserId)
        val txtNombre=findViewById<EditText>(R.id.etUserName)
        val txtCorreo=findViewById<EditText>(R.id.etUserEmail)
        val btnEnviar=findViewById<Button>(R.id.btnSet)
        val btnObtener=findViewById<Button>(R.id.btnGet)

        //Writting
        btnEnviar.setOnClickListener{
            writeNewuser(txtId.text.toString(),txtNombre.text.toString(),txtCorreo.text.toString())
            txtId.text.clear()
            txtNombre.text.clear()
            txtCorreo.text.clear()
        }
    }

    fun writeNewuser(userId:String,name:String,email: String){
        val user=User(name,email)
        database.child("usuarios").child(userId).setValue(user)
    }

    fun getUser(userId:String){
        database.child("usuarios").child(userId).get().addOnSuccessListener { user->
            val json=JSONObject(user.value.toString())
            Log.d("ValorFirebase","${user.value}")
            Log.d("ValorJSON","Nombre: ${json.getString("nombre")}  Correo: ${json.getString("correo")}")
        }

    }
    class User(name:String, email:String){
        val nombre=name
        val correo=email
    }
}