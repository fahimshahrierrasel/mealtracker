package com.fahimshahrierrasel.mealtracker.adapters
import android.content.Context;
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.support.v7.widget.CardView
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fahimshahrierrasel.mealtracker.R
import com.fahimshahrierrasel.mealtracker.models.Feature


/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class FeaturesAdapter(val context: Context, var mFeatures: ArrayList<Feature>) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturesAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val featureView = inflater.inflate(R.layout.feature_layout, parent, false)
        return ViewHolder(featureView)
    }

    override fun onBindViewHolder(holder: FeaturesAdapter.ViewHolder, position: Int) {
        val aFeture = mFeatures[position]

        holder.featureName.setText(aFeture.featureName)
        holder.featureImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), aFeture.featureImage, null))
    }

    override fun getItemCount(): Int {
        return mFeatures.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var featureImage: ImageView
        var featureName: TextView
        var featureCard: CardView

        init {

            featureCard = itemView.findViewById(R.id.feature_card)
            featureImage = itemView.findViewById(R.id.feature_image)
            featureName = itemView.findViewById(R.id.feature_name)

            featureCard.setOnClickListener {
                val position = adapterPosition
                println(position)
                if (position != RecyclerView.NO_POSITION) {
                    val feature = mFeatures[position]
                    context.startActivity(Intent(context, feature.activityClass))
                }
            }
        }
    }
}