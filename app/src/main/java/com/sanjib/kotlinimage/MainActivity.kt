package com.sanjib.kotlinimage
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    lateinit var mrecylerview : RecyclerView
    lateinit var ref: DatabaseReference
    lateinit var show_progress: ProgressBar
    lateinit var linearlayout : RelativeLayout
    lateinit var logout:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        logout=findViewById(R.id.logout)
        ref = FirebaseDatabase.getInstance().getReference().child("Fooditems")
        mrecylerview = findViewById(R.id.reyclerview)
        mrecylerview.layoutManager = LinearLayoutManager(this)
        show_progress = findViewById(R.id.progress_bar)
        linearlayout = findViewById(R.id.linearlayout)

        firebaseData()

        logout.setOnClickListener {

            val intent= Intent(this,LoginActivity::class.java)

            startActivity(intent)

        }


    }

    fun firebaseData() {


        val option = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(ref, Model::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Model, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@MainActivity).inflate(R.layout.cardview,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Model) {
                val placeid = getRef(position).key.toString()

                ref.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@MainActivity, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        show_progress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE
                        holder.txt_name.setText(model.Name)
                        holder.shortdescription.setText(model.ShortDesc)
                        holder.txt_price.setText(model.Price)
                        Picasso.get().load(model.Image).placeholder(R.drawable.spinnerorange).into(holder.img_vet)
                        holder.buy.setOnClickListener{
                            val intent = Intent(applicationContext, BuyActivity::class.java)
                            intent.putExtra("Ordername",model.Name)
                            intent.putExtra("Orderprice",model.Price)
                            intent.putExtra("orderimg",model.Image)
                            startActivity(intent)

                        }
                    }
                })
            }
        }
        mrecylerview.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var txt_name: TextView = itemView!!.findViewById(R.id.Display_title)
        internal var img_vet: ImageView = itemView!!.findViewById(R.id.Display_img)
        internal var shortdescription:TextView=itemView!!.findViewById(R.id.shortdesc)
        internal var txt_price: TextView = itemView!!.findViewById(R.id.Price)
        internal var buy:Button=itemView!!.findViewById(R.id.buy)
    }


}
