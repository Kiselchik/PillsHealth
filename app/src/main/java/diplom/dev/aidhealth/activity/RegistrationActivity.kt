package diplom.dev.aidhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbHandler
import diplom.dev.aidhealth.db.handler.DbUserHandler
import diplom.dev.aidhealth.db.model.Doctor
import diplom.dev.aidhealth.db.model.User

class RegistrationActivity : AppCompatActivity() {
    private lateinit var regEmailEdTxt: EditText
    private lateinit var regPasswordEdTxt: EditText
    private lateinit var regPasswordCheckEdTxt: EditText
    private lateinit var regFirstNameEdTxt: EditText
    private lateinit var regLastNameEdTxt: EditText
    private lateinit var regUserButton: Button
//TODO: проверка на сущестование емейла в таблице
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initialize()
        setListener()
    }

    fun initialize(){
        regEmailEdTxt = findViewById(R.id.regEmailEdTxt)
        regPasswordEdTxt = findViewById(R.id.regPasswordEdTxt)
        regPasswordCheckEdTxt = findViewById(R.id.regPasswordCheckEdTxt)
        regFirstNameEdTxt = findViewById(R.id.regFirstNameEdTxt)
        regLastNameEdTxt = findViewById(R.id.regLastNameEdTxt)
        regUserButton = findViewById(R.id.regUserButton)
    }



    fun setListener(){
        regUserButton.setOnClickListener(){
            //TODO: check password, insert User to Db, toEnterActivity
            val regEmail = regEmailEdTxt.text.toString()
            val regPassword = regPasswordEdTxt.text.toString()
            val regPasswordCheck = regPasswordCheckEdTxt.text.toString()
            val regFirstName = regFirstNameEdTxt.text.toString()
            val regLastName = regLastNameEdTxt.text.toString()

            if(regEmail.length==0 || regPassword.length ==0 || regPasswordCheck.length==0
                || regFirstName.length==0 || regLastName.length==0){
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            else if(regPassword.length < 8){
                Toast.makeText(this, "Слишком короткий пароль", Toast.LENGTH_SHORT).show()
            }
            else if(regPassword.equals(regPasswordCheck, false)){
                var dbCheckUser = DbUserHandler(context = this)
                var dataCheckUser = dbCheckUser.readUser(regEmail)
                if(dataCheckUser.size==0)   {
                var db = DbUserHandler(context = this)
                var user = User(regEmail, regPassword, regFirstName, regLastName)
                //  var db = DbHandler(context = this)
                db.insertUser(user)


                val intent = Intent(this@RegistrationActivity, EnterActivity::class.java)
                startActivity(intent)
            }
            else{
                    Toast.makeText(this, "Учетная запись с данной почтой уже существует", Toast.LENGTH_SHORT).show()
                }

            }
            else {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()

            }
        }
    }
}