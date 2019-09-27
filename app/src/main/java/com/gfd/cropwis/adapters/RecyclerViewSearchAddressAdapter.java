package com.gfd.cropwis.adapters;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gfd.cropwis.R;
import com.gfd.cropwis.cropgrowingseason.CropGrowingSeasonActivity;
import com.gfd.cropwis.cropgrowingseason.CropGrowingSeasonFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RecyclerViewSearchAddressAdapter extends RecyclerView.Adapter<RecyclerViewSearchAddressAdapter.RecyclerViewHolder> {
    private List<Address> mData;
    private Context mContext;
    private CropGrowingSeasonFragment mFragment;

    public RecyclerViewSearchAddressAdapter(List<Address> data, Context context, CropGrowingSeasonFragment fragment) {
        this.mData = data;
        this.mContext = context;
        this.mFragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerViewSearchAddressAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_address, parent, false);
        return new RecyclerViewSearchAddressAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSearchAddressAdapter.RecyclerViewHolder holder, int position) {
        final Address address = mData.get(position);
        final CropGrowingSeasonActivity mapsActivity = (CropGrowingSeasonActivity) mContext;

        String addressAsString = address.getLocality();
        if (address.getAddressLine(0) != null ) {
            addressAsString = address.getAddressLine(0);
        }
        holder.tvAddressTitle.setText(addressAsString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.moveCameraToLatLng(new LatLng(address.getLatitude(), address.getLongitude()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvAddressTitle;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mapping(itemView);
        }

        private void mapping(View view) {
            tvAddressTitle = view.findViewById(R.id.tvAddressTitle);
        }
    }
}
