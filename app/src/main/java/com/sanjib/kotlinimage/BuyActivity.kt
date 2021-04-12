package com.sanjib.kotlinimage

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.checkout.view.*

import kotlinx.android.synthetic.main.promocode.view.*


class BuyActivity : AppCompatActivity() {


    lateinit var cname:EditText
    lateinit var cphone:EditText
    lateinit var caddress:EditText
    lateinit var result : TextView
    var count = 0
    lateinit var total : TextView
    lateinit var discount:TextView
    lateinit var bill:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)


         total=findViewById(R.id.total)
         discount=findViewById(R.id.disc)
         bill=findViewById(R.id.bill)
         result=findViewById(R.id.result)
         cname = findViewById(R.id.Entername)
         cphone = findViewById(R.id.Enterphone)
         caddress = findViewById(R.id.enteraddress)
        val itemName = findViewById<TextView>(R.id.ordername)
        val itemPrice = findViewById<TextView>(R.id.orderprice)
        val itempic=findViewById<ImageView>(R.id.orderimg)


        val dataname = intent.extras!!.getString("Ordername")
        val dataPrice = intent.extras!!.getString("Orderprice")
        val imageorder=intent.extras!!.getString("orderimg")


        total.setText(dataPrice)
        bill.setText(dataPrice)
         result.setText("0")
        itemName.setText(dataname)
        Picasso.get().load(imageorder).placeholder(R.drawable.spinnerorange).into(itempic)
        itemPrice.setText(dataPrice)


        promo.setOnClickListener {

            val mDilogue=LayoutInflater.from(this).inflate(R.layout.promocode,null)

            val builder = AlertDialog.Builder(this).setView(mDilogue).setTitle("Promocode")
            val malertDialog = builder.show()

           mDilogue.okbtn.setOnClickListener {


               malertDialog.dismiss()


            }
        }

        plus.setOnClickListener {

            result.setText(""+count++ )

            val totalbill = count++ * dataPrice!!.toInt()

             bill.setText(""+totalbill)
             total.setText(""+totalbill)
        }

        minus.setOnClickListener {


                result.setText("" + count--)
                val totalbill = count-- * dataPrice!!.toInt()

                bill.setText(""+totalbill)
                total.setText(""+totalbill)


                    }

        checkout.setOnClickListener{


           // Toast.makeText(this,"order placed!",Toast.LENGTH_SHORT).show()
          //  intent=Intent(this,Payment::class.java)
          //  startActivity(intent)

            val mDilogue=LayoutInflater.from(this).inflate(R.layout.checkout,null)

            val builder = AlertDialog.Builder(this).setView(mDilogue).setTitle("Checkout")
            val malertDialog = builder.show()

                mDilogue.cancel.setOnClickListener {


                    malertDialog.dismiss()


                }

                 mDilogue.cod.setOnClickListener {

                      uploadcdata()

                 }

               mDilogue.paytm.setOnClickListener {

//                   intent=Intent(this,Payment::class.java)
//                    startActivity(intent)

                   val intent = Intent(this, checksum::class.java)
                   intent.putExtra("orderid", orderid.getText().toString())
                   intent.putExtra("custid", custid.getText().toString())
                   intent.putExtra("price",)
                   startActivity(intent)

               }


        }
    }



    private fun uploadcdata() {

        val cpname = cname.text.toString().trim()
        val cpphone = cphone.text.toString().trim()
        val cpaddress = caddress.text.toString().trim()
        val dataname2 = intent.extras!!.getString("Ordername").toString().trim()
        val price2 =   intent.extras!!.getString("Orderprice").toString().trim()

        if (cpname.isEmpty()){
            cname.error="please Enter your name"
            return
        }
        if (cpphone.isEmpty()){
            cphone.error="please Enter your phone"
            return
        }
        if (cpaddress.isEmpty()){
            caddress.error="please Enter your address"
            return
        }


        val ref = FirebaseDatabase.getInstance().getReference("orderitems")
        val cusid = ref.push().key
        val customer = CustomerModel(cusid,cpname,cpphone,cpaddress,dataname2,price2)
        ref.child(cusid!!).setValue(customer).addOnCanceledListener {

            Toast.makeText(applicationContext,"saved",Toast.LENGTH_SHORT).show()




        }

    }
}
