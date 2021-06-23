package diplom.dev.aidhealth.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbHandler
import diplom.dev.aidhealth.db.handler.DbUserHandler

class EnterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var enterButton: Button
    private lateinit var toRegistrationButton: Button
    private lateinit var toChangePasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        initialize()
        setListener()
        shPrefLoadData()

    }

    fun initialize(){
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        enterButton = findViewById(R.id.enterButton)
        toRegistrationButton = findViewById(R.id.toRegistrationButton)
        toChangePasswordButton = findViewById(R.id.toChangePasswordButton)
    }


    fun setListener(){

      //  val sharedPref = getSharedPreferences(email, Context.MODE_PRIVATE )

        enterButton.setOnClickListener(){
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()
            if(email.length==0 || password.length ==0){
                Toast.makeText(this, "Заолните все поля ", Toast.LENGTH_SHORT).show()
            }
            else  //texview с предупреждением. Проверка во время ввода
            {
                var db = DbUserHandler(context = this)
                var data = db.checkUser(email, password)
                var dataUser = arrayListOf<String>()
                //textDoc.text =""
                var userFirstName =""
                var userLastName =""
                if(data.size!=0){
                    for (i in 0..data.size-1) {

                         userFirstName = data.get(i).firstName
                         userLastName = data.get(i).lastName
                    }
                    shPrefSavedData(userFirstName, userLastName)
                    val intent = Intent(this@EnterActivity, MainActivity::class.java)
                    startActivity(intent)

                    //TODO: передать dataUser в Main
                }

                else{
                    Toast.makeText(this, "Проверь ошибки или зарегайся", Toast.LENGTH_SHORT).show()

                }
            }
        }

        toRegistrationButton.setOnClickListener(){
            val intent = Intent(this@EnterActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }


    }

    fun shPrefSavedData(firstName: String, lastName: String){
        var email = emailEditText.text.toString()
        var password = passwordEditText.text.toString()
        val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = shPref.edit()
        editor.apply{
            putString("EMAIL_KEY", email)
            putString("PASSWORD_KEY", password)
            putString("FIRSTNAME_KEY", firstName)
            putString("LASTNAME_KEY", lastName)
        }.apply()

       // Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show()
    }

    fun shPrefLoadData(){
        val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedEmail = shPref.getString("EMAIL_KEY", "")
        val savedPassword = shPref.getString("PASSWORD_KEY", null)
        val savedName = shPref.getString("FIRSTNAME_KEY", null)
        var db = DbHandler(context = this)
        var data = db.readDoctor()
            //    && data.size>0
        if(savedName!=null ){
            val intent = Intent(this@EnterActivity, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        shPref.edit().remove("EMAIL_KEY").commit()
            shPref.edit().remove("PASSWORD_KEY").commit()
            shPref.edit().remove("FIRSTNAME_KEY").commit()

        }
      //  Toast.makeText(this, "${savedEmail}  ${savedPassword}", Toast.LENGTH_SHORT).show()
    }

    fun shPrefClear(){

    }


}